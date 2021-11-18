package es.ivan.acceso.api;

import es.ivan.acceso.utils.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;

/**
 * Código sacado de StackOverflow ()
 */
public class BinParser extends ObjectInputStream {

    private final String oldNameSpace;
    private final String newNameSpace;

    /**
     * Constructor
     *
     * @param in El imput Stream
     * @param oldNameSpace El antiguo paquete
     * @param newNameSpace El paquete nuevo
     * @throws IOException
     */
    public BinParser(InputStream in, String oldNameSpace, String newNameSpace) throws IOException {
        super(in);
        this.oldNameSpace = oldNameSpace;
        this.newNameSpace = newNameSpace;
    }

    @Override
    public ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        final ObjectStreamClass result = super.readClassDescriptor();
        try {
            if (result.getName().contains(oldNameSpace)) {
                final String newClassName = result.getName().replace(oldNameSpace, newNameSpace);
                // Test the class exists
                final Class<?> localClass = Class.forName(newClassName);

                final Field nameField = ObjectStreamClass.class.getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(result, newClassName);

                final ObjectStreamClass localClassDescriptor = ObjectStreamClass.lookup(localClass);
                final Field suidField = ObjectStreamClass.class.getDeclaredField("suid");
                suidField.setAccessible(true);
                suidField.set(result, localClassDescriptor.getSerialVersionUID());
            }
        } catch(Exception e) {
            Log.clear();
            Log.error("Error al intentar reemplazar el namespace");
            Log.stack(e.getStackTrace());
        }
        return result;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        return desc.getName().contains(oldNameSpace) ? Class.forName(desc.getName().replace(oldNameSpace, newNameSpace)) : super.resolveClass(desc);
    }
}
