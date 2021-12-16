package es.ivan.acceso.files;

import es.ivan.acceso.api.XMLGenerator;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;
import lombok.NonNull;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
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
                XMLGenerator xmlGenerator = new XMLGenerator(parentNode);

                Log.normal("Escribe el nodo hijo");
                xmlGenerator = this.ask4Child(scanner, xmlGenerator, 0, scanner.nextLine());

                final Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                tr.transform(new DOMSource(xmlGenerator.build()), new StreamResult(fileName));

                Log.success("Archivo guardado!");
            } catch (ParserConfigurationException | TransformerException e) {
                Log.error("El archivo no existe o no está bien formado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".xml");
        }
    }

    private XMLGenerator ask4Child(Scanner scanner, XMLGenerator xmlGenerator, int type, String line) {
        // Formato: key(atrib): value || key(atrib)
        final String parsedXML = line.replaceAll("\\s", ""); // Borramos todos los espacios

        final String[] realElement = parsedXML.split(":");
        realElement[0] = realElement[0].split("\\(")[0];

        final HashMap<String, String> attributes = this.parseAttributes(parsedXML.split("\\((.*?)\\)")[0].replace("(", "").replace(")", "").split("="));

        if (type == 0) {
            if (realElement.length > 1 && realElement[1] != null) {
                xmlGenerator.addChildrenValue(realElement[0], realElement[1], attributes);
            } else {
                xmlGenerator.addChildren(realElement[0], attributes);
            }
        } else {
            if (realElement.length > 1 && realElement[1] != null) {
                xmlGenerator.addSiblingValue(realElement[0], realElement[1], attributes);
            } else {
                xmlGenerator.addSibling(realElement[0], attributes);
            }
        }

        Log.normal("¿Añadir hijo? [S/N]");

        if (scanner.nextLine().equalsIgnoreCase("s")) {
            Log.normal("Escribe el nodo hijo");
            return this.ask4Child(scanner, xmlGenerator, 0, scanner.nextLine());
        } else {
            Log.normal("¿Añadir hermano? [S/N]");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                Log.normal("Escribe el nodo hermano");
                return this.ask4Child(scanner, xmlGenerator, 1, scanner.nextLine());
            }
        }

        return xmlGenerator;
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
            if (content != null && content.getNodeType() != Node.ELEMENT_NODE && !content.getTextContent().trim().isEmpty()) sb.append(": ").append(content.getTextContent());

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
