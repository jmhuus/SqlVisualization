package com.company;

import net.sf.jsqlparser.statement.Statement;

import java.util.*;


class DiagramNodeManager {

    private HashMap<String, DiagramNode> diagramNodes;
    private static Deque<Integer> nodeIds;

    DiagramNodeManager() {
        diagramNodes = new HashMap<>();
        nodeIds = new LinkedList<>();
    }


    /**
     * Adds node objects to DiagramNodeManager
     * @param statement Parsed by CCJSqlParserUtil.parseStatements() and then passed individually
     */
    void addStatementToDiagram(Statement statement) {
        DiagramNode diagramNode = new DiagramNode();
        statement.accept(new StatementDesignator(diagramNode, this));
    }


    /**
     * Once a new node has been created, it should be added to DiagramManager
     * @param diagramNode new node to be added to the diagram
     * @throws Exception node with provided name already exists
     */
    void addDiagramNode(DiagramNode diagramNode) {

        // Store new DiagramNode
        diagramNodes.put(diagramNode.getNodeName(), diagramNode);
    }

    /**
     * Access to all diagram nodes; used when needing to retrieve a single diagram node.
     * Don't access all nodes using this method
     * @param nodeName
     * @return Diagram node object
     */
    DiagramNode getDiagramNode(String nodeName){
        return diagramNodes.get(nodeName);
    }


    /**
     * Use this method to determine if a node already exists.
     * @param nodeName name of the node - used as the unique identifier
     * @return true if the node has already been instantiated
     */
    boolean nodeExists(String nodeName){
        return diagramNodes.containsKey(nodeName);
    }


    /**
     * When instantiating a new query node, use this method in order to build it's unique name ID
     * @return unique query node name
     */
    String getNewDiagramNodeQueryName(String nodeType){
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

    /**
     * @return all diagram nodes
     */
    HashMap<String, DiagramNode> getDiagramNodes(){
        return diagramNodes;
    }

    /**
     * Provides new node IDs
     * @return incremented and stored nodeId
     */
    public static int getNextAvailableNodeId(){
        int newId = nodeIds.peekLast() == null ? 1 : nodeIds.peekLast()+1;
        nodeIds.add(newId);
        return newId;
    }
}










