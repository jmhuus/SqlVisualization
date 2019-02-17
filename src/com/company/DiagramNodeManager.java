package com.company;

import java.util.HashMap;

public class DiagramNodeManager {

    private static HashMap<String, DiagramNode> diagramNodes;

    public DiagramNodeManager() {
        diagramNodes = new HashMap<>();
    }

    public boolean nodeExists(int diagramId){
        return diagramNodes.containsKey(diagramId);
    }

    public void addDiagramNode(DiagramNode diagramNode) throws Exception{
        // Ensure ID doesn't already exist
        if(diagramNodes.containsKey(diagramNode.getNodeName())){
            throw new Exception("Diagram node query name: " + diagramNode.getNodeName() + " already exists.");
        }

        // Store new DiagramNode
        diagramNodes.put(diagramNode.getNodeName(), diagramNode);
    }

    /**
     * Access to all diagram nodes; used when needing to retrieve a single diagram node.
     * Don't access all nodes using this method
     * @param nodeName
     * @return Diagram node object
     */
    public static DiagramNode getDiagramNode(String nodeName){
        return diagramNodes.get(nodeName);
    }


    /**
     * Use this method to determine if a node already exists.
     * @param nodeName name of the node - used as the unique identifier
     * @return true if the node has already been instantiated
     */
    public static boolean nodeExists(String nodeName){
        return diagramNodes.containsKey(nodeName);
    }


    /**
     * @return query name to designate to each query node
     *         Non-query nodes are tables; table names are used as node names
     */
    public DiagramNode getNewNode(){

        // Init new diagram node
        DiagramNode newDiagramNode = new DiagramNode();

        // Find next available node name
        int i=0;
        String newQueryName;
        while(true){
            // Construct new query name
            newQueryName = "Query" + i;

            // Validate new query name doesn't already exists
            if(! diagramNodes.containsKey(newQueryName)){
                break;
            }
            i += 1;
        }

        // Set new diagram node name
        newDiagramNode.setNodeName(newQueryName);

        // Store reference to new diagram node
        diagramNodes.put(newDiagramNode.getNodeName(), newDiagramNode);

        return newDiagramNode;
    }
}
