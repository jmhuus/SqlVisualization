package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlConstructor {


    private DiagramNodeManager diagramNodeManager;


    public XmlConstructor(DiagramNodeManager diagramNodeManager) {
        this.diagramNodeManager = diagramNodeManager;
    }

    /**
     * XML to represent diagram data.
     * Diagram nodes are nested under each parent node.
     * @return XML Document object containing the XML string.
     */
    public Document getXmlDiagram(){

        // Retrieve random start node - preferably one with no parent nodes
        String rootNodeName = "SELECT0";

        // Construct XML to represent diagram nodes
        DocumentBuilder documentBuilder;
        Document xmlDocument=null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlDocument = documentBuilder.newDocument();

            // Begin adding nodes to XML document; recursion.
            DiagramNode rootNode = diagramNodeManager.getDiagramNodes().get(rootNodeName);
            Element rootElement = xmlDocument.createElement(rootNode.getNodeName());
            xmlDocument.appendChild(rootElement);
            addChildrenNodes(rootNode, rootElement, xmlDocument);
        } catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }

        return xmlDocument;
    }


    /**
     * Recursion method. Adds all children nodes to the XML Document.
     * @param diagramNode current node in the tree
     * @param parentElement parent XML element object that children nodes will nest into
     */
    void addChildrenNodes(DiagramNode diagramNode, Element parentElement, Document xmlDocument){

        // Add child nodes
        Element tmpElement;
        System.out.println(diagramNode.getNodeName() + " " + diagramNode.getChildNodes().size());
        for(DiagramNode child: diagramNode.getChildNodes()){
            tmpElement = xmlDocument.createElement(child.getNodeName());
            parentElement.appendChild(tmpElement);
            addChildrenNodes(child, tmpElement, xmlDocument);
        }
    }

}




















