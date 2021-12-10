package es.ivan.acceso.files;

import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;
import lombok.NonNull;
import org.w3c.dom.*;
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
                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                factory.setIgnoringComments(true);

                final Document document = factory.newDocumentBuilder().parse(file);
                final Element documentElement = document.getDocumentElement();
                documentElement.normalize();

                Log.normal("\n" + documentElement.getNodeName());
                this.showChildren(documentElement, 2);

            } catch (ParserConfigurationException | SAXException | IOException e) {
                Log.error("El archivo no existe o no está bien formado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("No existe un archivo llamado " + fileName + ".xml");
        }
    }


    private void showChildren(@NonNull Node parent, int tabs) {
        if (!parent.hasChildNodes()) return;
        final NodeList children = parent.getChildNodes();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);

            if (child.getNodeName().startsWith("#")) continue;

            sb.append(this.generateSpacer(tabs)).append(child.getNodeName());

            // Leemos los atributos
            if (child.hasAttributes()) {
                final NamedNodeMap attributes = child.getAttributes();
                sb.append("(");

                for (int j = 0; j < attributes.getLength(); j++) {
                    final Node node = attributes.item(j);

                    if (node.getNodeName().startsWith("#")) continue;

                    sb.append(node.getNodeName()).append("=").append(node.getNodeValue());

                    if (j + 1 != attributes.getLength()) sb.append(", ");
                }
                sb.append(")");
            }

            // Leemos el contenido
            final Node content = child.getChildNodes().item(0);
            if (content.getNodeType() != Node.ELEMENT_NODE) sb.append(": ").append(content.getTextContent());

            Log.normal(this.generateSpacer(tabs) + sb);
            sb.delete(0, sb.length());

            // Si tiene hijos los mostramos
            if (child.hasChildNodes()) this.showChildren(child, tabs + 2);
        }
    }

    private String generateSpacer(int tabs) {
        final StringBuilder sb = new StringBuilder();

        while (tabs > 0) {
            sb.append(" ");
            tabs--;
        }
        return sb.toString();
    }
}
