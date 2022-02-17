package es.ivan.acceso.old.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;

@AllArgsConstructor
public class XMLGenerator {

    private final Document dom;
    private final Element root;

    // Guardo el elemento último para su futuro uso
    @Getter
    private Element lastElement;

    /**
     * Constructor de la clase que me genera el DOM
     *
     * @param root El nombre del nodo principal
     * @throws ParserConfigurationException
     */
    public XMLGenerator(String root) throws ParserConfigurationException {
        this.dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        this.root = this.dom.createElement(root);
        this.dom.appendChild(this.root);
        this.lastElement = this.root;
    }

    // --- Children ---

    /**
     * Método usado para generar hijos del elemento ROOT
     *
     * @param children El nombre del hijo
     * @return Esta instancia
     */
    public XMLGenerator addChildren(String children) {
        return this.addChildren(this.root, children);
    }

    /**
     * Método usado para generar hijos del elemento ROOT
     *
     * @param parent   El padre del que depende
     * @param children El nombre del hijo
     * @return Esta instancia
     */
    public XMLGenerator addChildren(Element parent, String children) {
        return this.addChildren(parent, children, new HashMap<>());
    }

    /**
     * Método usado para generar hijos del elemento último
     *
     * @param children   El nombre del hijo
     * @param attributes Los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildren(String children, HashMap<String, String> attributes) {
        return this.addChildren(this.lastElement, children, attributes);
    }

    /**
     * Método usado para generar hijos del elemento último
     *
     * @param parent     El padre del que depende
     * @param children   El nombre del hijo
     * @param attributes Los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildren(Element parent, String children, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(children);
        attributes.forEach(element::setAttribute);
        parent.appendChild(element);
        this.lastElement = element;
        return this;
    }

    /**
     * Método usado para generar hijos del elemento root
     *
     * @param children El nombre del hijo
     * @param value    El contenido del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildrenValue(String children, String value) {
        return this.addChildrenValue(this.root, children, value);
    }

    /**
     * Método usado para generar hijos del elemento último
     *
     * @param parent   El padre del que depende
     * @param children El nombre del hijo
     * @param value    El contenido del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildrenValue(Element parent, String children, String value) {
        return this.addChildrenValue(parent, children, value, new HashMap<>());
    }

    /**
     * Método usado para generar hijos del elemento último
     *
     * @param children   El nombre del hijo
     * @param value      El contenido del elemento
     * @param attributes Los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildrenValue(String children, String value, HashMap<String, String> attributes) {
        return this.addChildrenValue(this.lastElement, children, value, attributes);
    }

    /**
     * Método usado para generar hijos del elemento último
     *
     * @param parent     El padre del que depende
     * @param children   El nombre del hijo
     * @param value      El contenido del elemento
     * @param attributes Los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addChildrenValue(Element parent, String children, String value, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(children);
        element.setTextContent(value);
        attributes.forEach(element::setAttribute);
        parent.appendChild(element);
        this.lastElement = element;
        return this;
    }

    // --- Siblings ---

    /**
     * Método usado para generar hermanos del elemento
     *
     * @param sibling El hermano del que depende
     * @param name    El nombre del hermano
     * @return Esta instancia
     */
    public XMLGenerator addSibling(Element sibling, String name) {
        return this.addSibling(sibling, name, new HashMap<>());
    }

    /**
     * Método usado para generar hermanos del elemento último
     *
     * @param name       El nombre del hermano
     * @param attributes los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addSibling(String name, HashMap<String, String> attributes) {
        return this.addSibling(this.lastElement, name, attributes);
    }

    /**
     * Método usado para generar hermanos del elemento último
     *
     * @param sibling    El nodo del que va a ser hermano
     * @param name       El nombre del hermano
     * @param attributes los atributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addSibling(Element sibling, String name, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(name);
        attributes.forEach(element::setAttribute);
        sibling.getParentNode().appendChild(element);
        this.lastElement = element;
        return this;
    }

    /**
     * Método usado para generar hermanos del elemento último
     *
     * @param sibling El nodo del que va a ser hermano
     * @param name    El nombre del hermano
     * @param value   El valor del nodo
     * @return Esta instancia
     */
    public XMLGenerator addSiblingValue(Element sibling, String name, String value) {
        return this.addSiblingValue(sibling, name, value, new HashMap<>());
    }

    /**
     * Método usado para generar hermanos del elemento último
     *
     * @param name       El nombre del hermano
     * @param value      El valor del nodo
     * @param attributes Los tributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addSiblingValue(String name, String value, HashMap<String, String> attributes) {
        return this.addSiblingValue(this.lastElement, name, value, attributes);
    }

    /**
     * Método usado para generar hermanos del elemento último
     *
     * @param sibling    El elemento del que va a ser hermano
     * @param name       El nombre del hermano
     * @param value      El valor del nodo
     * @param attributes Los tributos del elemento
     * @return Esta instancia
     */
    public XMLGenerator addSiblingValue(Element sibling, String name, String value, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(name);
        element.setTextContent(value);
        attributes.forEach(element::setAttribute);
        sibling.getParentNode().appendChild(element);
        this.lastElement = element;
        return this;
    }

    // --- Comments ---

    /**
     * Método para añadir comentarios al XML
     *
     * @param commentText El contenido
     * @return Esta instancia
     */
    public XMLGenerator addComment(String commentText) {
        final Comment comment = this.dom.createComment(commentText);
        this.lastElement.appendChild(comment);
        return this;
    }

    // --- Build ---

    /**
     * Genera el XML
     *
     * @return El documento
     */
    public Document build() {
        return this.dom;
    }
}
