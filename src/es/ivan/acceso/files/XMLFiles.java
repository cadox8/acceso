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
import java.util.*;

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

    public void writeNewXML(String fileName, String parentNode, Scanner scanner) {
        final File file = this.getFile(fileName);

        if (!file.exists()) {
            try {
                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                final Document document = factory.newDocumentBuilder().newDocument();

                final Element root = document.createElement(parentNode);

                String line;
                while (!(line = scanner.nextLine()).equalsIgnoreCase("listo")) {

                }
            } catch (ParserConfigurationException e) {
                Log.error("El archivo no existe o no está bien formado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".xml");
        }
    }

    /**
     * Ask4Child, return parent with child / siblings
     *
     * @param document
     * @param parent
     * @param line
     * @return
     */
    private Element ask4Child(Document document, Element parent, String line) {
        Element element;

        // Formato: key(atrib): value || key(atrib)
        final String parsedXML = line.replaceAll("\\s", ""); // Borramos todos los espacios

        if (parsedXML.contains(":")) {
            final String[] realElement = parsedXML.split(":");
            realElement[0] = realElement[0].split("\\(")[0];

            if (realElement[1] == null) {
                realElement[1] = "";
                Log.warning("Elemento sin valor.");
            }

            element = document.createElement(realElement[0]);
            element.setNodeValue(realElement[1]);
            this.parseAttributes(parsedXML.split("\\((.*?)\\)")[0].replace("(", "").replace(")", "").split("=")).forEach(element::setAttribute);

            parent.appendChild(element);

            Log.normal("¿Añadir hermano? [S/N]");

        } else {
            // Elemento con hijos

            element = null;

            parent = null; //new element from here
        }

        return parent;
    }

    private HashMap<String, String> parseAttributes(String[] attributesString) {
        final HashMap<String, String> attributes = new HashMap<>();
        for (int i = 0; i < attributesString.length - 1; i += 2) {
            if (attributesString[i] == null) attributesString[i] = "";
            if (attributesString[i + 1] == null) attributesString[i + 1] = null;
            attributes.put(attributesString[i], attributesString[i + 1]);
        }
        return attributes;
    }

    /**
     * Muestra los hijos de cada Nodo
     *
     * @param parent El Nodo padre
     * @param tabs Las tabulaciones a pasar para imprimir
     */
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
            if (content != null && content.getNodeType() != Node.ELEMENT_NODE) sb.append(": ").append(content.getTextContent());

            Log.normal(this.generateSpacer(tabs) + sb);
            sb.delete(0, sb.length());

            // Si tiene hijos los mostramos
            if (child.hasChildNodes()) this.showChildren(child, tabs + 2);
        }
    }

    /**
     * Genera las tabulaciones requeridas
     *
     * @param tabs Las tabulaciones a generar
     * @return Un String con las tabulaciones generadas
     */
    private String generateSpacer(int tabs) {
        final StringBuilder sb = new StringBuilder();

        while (tabs > 0) {
            sb.append(" ");
            tabs--;
        }
        return sb.toString();
    }
}
