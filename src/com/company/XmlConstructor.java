package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlConstructor {


    private DiagramNodeManager diagramNodeManager;
    private Document xmlDocument;

    public XmlConstructor(DiagramNodeManager diagramNodeManager) {
        this.diagramNodeManager = diagramNodeManager;
    }

    /**
     * XML to represent diagram data.
     * Diagram nodes are nested under each parent node.
     * @return XML Document object containing the XML string.
     */
    public Document getXmlDiagram(DiagramNodeManager diagramNodeManager){

        // Retrieve random start node - preferably one with no parent nodes
        String rootNodeName = null;
        for(String nodeName: diagramNodeManager.getDiagramNodes().keySet()){
            if(diagramNodeManager.getDiagramNodes().get(nodeName).getParentNodes().size()==0){
                rootNodeName = nodeName;
            }
        }

        // Construct XML to represent diagram nodes
        DocumentBuilder documentBuilder=null;
        Document document=null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.newDocument();

            // Connect children nodes
            DiagramNode root = diagramNodeManager.getDiagramNodes().get(rootNodeName);
            Element rootElement = document.createElement(root.getNodeName());
            Element tmpElement;
            for (DiagramNode node : root.getChildNodes()) {
                tmpElement = document.createElement(node.getNodeName());
            }

            // Connect parent nodes
            for (DiagramNode node : root.getParentNodes()) {

            }
        }catch(
                ParserConfigurationException pce){
            pce.printStackTrace();
        }

        return document;
    }


    /**
     * Recursion method. Adds all children nodes to the XML Document.
     * @param diagramNode
     * @param parentElement
     */
    void addNode(DiagramNode diagramNode, Element parentElement){


    }

}




















