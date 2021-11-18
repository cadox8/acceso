package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLFiles extends AbstractFile {

    /**
     * Constructor principal de la clase.
     * Comprueba si existe la carpeta 'files' y, en el caso de que no exista, la crea
     */
    public XMLFiles() {
        super(FileType.XML);
        final File parent = this.getFile("").getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    /**
     * Muestra el contenido por consola del archivo buscado
     *
     * @param fileName El archivo a buscar
     */
    @Override
    public void showFileInfo(String fileName) {
        final File file = this.getFile(fileName);

        if (file.exists()) {
            try {
                final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                final Element documentElement = document.getDocumentElement();
                documentElement.normalize();

                Log.normal(documentElement.getNodeName());

                final NodeList children = documentElement.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    final Node node = children.item(i);

                    if (node.getNodeName().startsWith("#")) continue;

                    Log.normal("  " + node.getNodeName() + " " + node.getTextContent());
                }

            } catch (ParserConfigurationException | SAXException | IOException e) {
                Log.error("El archivo no existe o no está bien formado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".xml");
        }
    }
}
