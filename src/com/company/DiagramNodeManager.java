package com.company;

import java.util.HashMap;

public class DiagramNodeManager {

    private HashMap<String, DiagramNode> diagramNodes;

    public DiagramNodeManager() {
        diagramNodes = new HashMap<>();
    }


    /**
     * Once a new node has been created, it should be added to DiagramManager
     * @param diagramNode new node to be added to the diagram
     * @throws Exception node with provided name already exists
     */
    public void addDiagramNode(DiagramNode diagramNode) {

        // Store new DiagramNode
        diagramNodes.put(diagramNode.getNodeName(), diagramNode);
    }

    /**
     * Access to all diagram nodes; used when needing to retrieve a single diagram node.
     * Don't access all nodes using this method
     * @param nodeName
     * @return Diagram node object
     */
    public DiagramNode getDiagramNode(String nodeName){
        return diagramNodes.get(nodeName);
    }


    /**
     * Use this method to determine if a node already exists.
     * @param nodeName name of the node - used as the unique identifier
     * @return true if the node has already been instantiated
     */
    public boolean nodeExists(String nodeName){
        return diagramNodes.containsKey(nodeName);
    }


    /**
     * When instantiating a new query node, use this method in order to build it's unique name ID
     * @return unique query node name
     */
    public String getNewDiagramNodeQueryName(String nodeType){
        // Find next available node name
        int i=0;
        String newQueryName;
        while(true){
            // Construct new query name
            newQueryName = nodeType + i;

            // Validate new query name doesn't already exists
            if(! diagramNodes.containsKey(newQueryName)){
                break;
            }
            i += 1;
        }

        return newQueryName;
    }
}










