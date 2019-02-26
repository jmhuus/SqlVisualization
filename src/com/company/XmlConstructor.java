package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XmlConstructor {


    public static final String QUERY_NODE_ELEMENT_NAME = "QueryNode";
    public static final String CHILD_NODES_ELEMENT_NAME = "ChildNodes";
    public static final String QUERY_NODE_TITLE_ATTRIBUTE = "nodeName";
    private DiagramNodeManager diagramNodeManager;


    public XmlConstructor(DiagramNodeManager diagramNodeManager) {
        this.diagramNodeManager = diagramNodeManager;
    }

    /**
     * XML to represent diagram data.
     * Diagram nodes are nested under each parent node.
     *
     * @return XML Document object containing the XML string.
     */
    public Document getXmlDiagram() {

        // Retrieve random start node - preferably one with no parent nodes
        String rootNodeName = "SELECT0";

        // Construct XML to represent diagram nodes
        DocumentBuilder documentBuilder;
        Document xmlDocument = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlDocument = documentBuilder.newDocument();

            // Add root node
            DiagramNode rootNode = diagramNodeManager.getDiagramNodes().get(rootNodeName);
            Element rootElement = xmlDocument.createElement(QUERY_NODE_ELEMENT_NAME);
            rootElement.setAttribute(QUERY_NODE_TITLE_ATTRIBUTE, rootNode.getNodeName());
            xmlDocument.appendChild(rootElement);

            // Begin adding nodes to XML document; recursion.
            addChildrenNodes(rootNode, rootElement, xmlDocument);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

        return xmlDocument;
    }


    /**
     * Recursion method. Adds all children nodes to the XML Document.
     *
     * @param diagramNode   current node in the tree
     * @param parentElement parent XML element object that children nodes will nest into
     */
    void addChildrenNodes(DiagramNode diagramNode, Element parentElement, Document xmlDocument) {

        // Add child nodes
        Element tmpElement;
        for (DiagramNode child : diagramNode.getChildNodes()) {
            tmpElement = xmlDocument.createElement(CHILD_NODES_ELEMENT_NAME);
            tmpElement.setAttribute(QUERY_NODE_TITLE_ATTRIBUTE, child.getNodeName());
            parentElement.appendChild(tmpElement);
            addChildrenNodes(child, tmpElement, xmlDocument);
        }
    }


    //method to convert Document to String
    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}




















