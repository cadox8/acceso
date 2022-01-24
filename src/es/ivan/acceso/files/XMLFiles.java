package es.ivan.acceso.files;

import es.ivan.acceso.api.XMLGenerator;
import es.ivan.acceso.files.type.FileType;
import es.ivan.acceso.utils.Log;
import es.ivan.acceso.utils.Normalize;
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

                // No queremos comentarios en la consola :c
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

    /**
     * Método para crear nuevos documentos XML
     *
     * @param fileName El nombre del archivo
     * @param parentNode El nodo padre
     * @param scanner El Scanner del menú
     */
    public void writeNewXML(String fileName, String parentNode, Scanner scanner) {
        final File file = this.getFile(fileName);

        if (!file.exists()) {
            try {
                // Creamos una instancia de mi clase XMLGenerator
                XMLGenerator xmlGenerator = new XMLGenerator(parentNode);

                Log.normal("Escribe el nodo hijo");
                xmlGenerator = this.ask4Child(scanner, xmlGenerator, 0, scanner.nextLine());

                // Con esto lo guardamos :D
                final Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                tr.transform(new DOMSource(xmlGenerator.build()), new StreamResult(file.getAbsolutePath()));

                Log.success("Archivo guardado!");
            } catch (ParserConfigurationException | TransformerException e) {
                Log.error("El archivo no existe o no está bien formado");
                Log.stack(e.getStackTrace());
            }
        } else {
            Log.error("Ya existe un archivo llamado " + fileName + ".xml");
        }
    }

    /**
     * Método para preguntar si queremos añadir hijos/hermanos y crearlos! Tambien sirve para comentarios
     *
     * @param scanner El scanner del menu
     * @param xmlGenerator Una instancia de mi XMLGenerator
     * @param type El tipo de nodo que estoy creado. 0 -> Hijo | 1 -> Hermano
     * @param line La línea introducida por consola
     * @return Una instancia de mi XMLGenerator
     *
     * @see XMLGenerator
     */
    private XMLGenerator ask4Child(Scanner scanner, XMLGenerator xmlGenerator, int type, String line) {
        line = Normalize.normalizeWord(line);

        // Formato: key(atrib): value || key(atrib) || key: value || key
        final String parsedXML = line.replaceAll("\\s", ""); // Borramos todos los espacios

        if (parsedXML.startsWith("#")) {
            // Si es un comentario el flujo del programa va por aquí
            xmlGenerator.addComment(line);
        } else {
            // Comprobamos si el nodo tiene contenido (Valor)
            final String[] realElement = parsedXML.split(":");
            realElement[0] = realElement[0].split("\\(")[0];

            // Obtenemos los atributos (si hay)
            // Para ello comprobamos si existe un "(". Si existe hacemos una substring desde ese caracter + 1 hasta el caracter final ")".
            final HashMap<String, String> attributes = line.contains("(") ?
                    this.parseAttributes(line.substring(line.indexOf("(") + 1, line.indexOf(")")).split(" ")) : new HashMap<>();

            if (type == 0) {
                // Comprobamos si existe información detras de los :
                if (realElement.length > 1 && realElement[1] != null) {
                    // Creamos el hijo con contenido
                    xmlGenerator.addChildrenValue(realElement[0], realElement[1], attributes);
                } else {
                    // Creamos el hijo sin contenido
                    xmlGenerator.addChildren(realElement[0], attributes);
                }
            } else {
                // Si el tipo es hermano entra por aquí
                if (realElement.length > 1 && realElement[1] != null) {
                    xmlGenerator.addSiblingValue(realElement[0], realElement[1], attributes);
                } else {
                    xmlGenerator.addSibling(realElement[0], attributes);
                }
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

    /**
     * Método para parsear los atributos
     *
     * *Importante*: Los atributos debem seguir el esquema (atributo=valor atributo2=valor2)
     *
     * @param attributesString Los atributos que residen entre los ()
     * @return Key - Value de los atributos
     */
    private HashMap<String, String> parseAttributes(String[] attributesString) {
        final HashMap<String, String> attributes = new HashMap<>();
        for (String s : attributesString) {
            final String[] values = s.split("=");
            attributes.put(values[0], values[1]);
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
        // Si no tien hijos adios
        if (!parent.hasChildNodes()) return;
        final NodeList children = parent.getChildNodes();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);

            // Si el nodo empieza con # no lo mostramos
            if (child.getNodeName().startsWith("#")) continue;

            // Genero los espacios y añado el nombre del nodo
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
            // Comprobamos que el contenido no sea nulo, que no sea un nodo y que no este vacio. Si se cumple todo, se añade
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
