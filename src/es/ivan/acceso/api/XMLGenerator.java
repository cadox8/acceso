package es.ivan.acceso.api;

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
    @Getter private final Element root;

    @Getter private Element lastElement;

    public XMLGenerator(String root) throws ParserConfigurationException {
        this.dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        this.root = this.dom.createElement(root);
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
    public XMLGenerator addChildren(Element parent, String children) {
        return this.addChildren(parent, children, new HashMap<>());
    }
    public XMLGenerator addChildren(String children, HashMap<String, String> attributes) {
        return this.addChildren(this.lastElement, children, attributes);
    }
    public XMLGenerator addChildren(Element parent, String children, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(children);
        attributes.forEach(element::setAttribute);
        parent.appendChild(element);
        this.lastElement = element;
        return this;
    }

    public XMLGenerator addChildrenValue(String children, String value) {
        return this.addChildrenValue(this.root, children, value);
    }
    public XMLGenerator addChildrenValue(Element parent, String children, String value) {
        return this.addChildrenValue(parent, children, value, new HashMap<>());
    }
    public XMLGenerator addChildrenValue(String children, String value, HashMap<String, String> attributes) {
        return this.addChildrenValue(this.lastElement, children, value, attributes);
    }
    public XMLGenerator addChildrenValue(Element parent, String children, String value, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(children);
        element.setNodeValue(value);
        attributes.forEach(element::setAttribute);
        parent.appendChild(element);
        this.lastElement = element;
        return this;
    }

    // --- Siblings ---

    public XMLGenerator addSibling(Element sibling, String name) {
        return this.addSibling(sibling, name, new HashMap<>());
    }
    public XMLGenerator addSibling(String name, HashMap<String, String> attributes) {
        return this.addSibling(this.lastElement, name, attributes);
    }
    public XMLGenerator addSibling(Element sibling, String name, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(name);
        attributes.forEach(element::setAttribute);
        sibling.getParentNode().appendChild(element);
        this.lastElement = element;
        return this;
    }

    public XMLGenerator addSiblingValue(Element sibling, String name, String value) {
        return this.addSiblingValue(sibling, name, value, new HashMap<>());
    }
    public XMLGenerator addSiblingValue(String name, String value, HashMap<String, String> attributes) {
        return this.addSiblingValue(this.lastElement, name, value, attributes);
    }
    public XMLGenerator addSiblingValue(Element sibling, String name, String value, HashMap<String, String> attributes) {
        final Element element = this.dom.createElement(name);
        element.setNodeValue(value);
        attributes.forEach(element::setAttribute);
        sibling.getParentNode().appendChild(element);
        this.lastElement = element;
        return this;
    }

    // --- Comments ---
    public XMLGenerator addComment(Element information, String commentText) {
        final Comment comment = this.dom.createComment(commentText);
        information.appendChild(comment);
        return this;
    }

    // --- Build ---
    public Document build() {
        this.dom.appendChild(this.root);
        return this.dom;
    }
}
