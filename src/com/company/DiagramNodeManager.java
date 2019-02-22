package com.company;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

public class DiagramNodeManager {

    private HashMap<String, DiagramNode> diagramNodes;

    public DiagramNodeManager() {
        diagramNodes = new HashMap<>();
    }


    /**
     * Adds node objects to DiagramNodeManager
     * @param queryString multi-line SQL query string - recognized by SQL commands (SELECT, DELETE, etc.)
     */
    public void addDiagramNode(String queryString){
        DiagramNode diagramNode = new DiagramNode();
        try {
            // Parse and return select statement items
            CCJSqlParserManager pm = new CCJSqlParserManager();
            Statement statement = pm.parse(new StringReader(queryString));
            statement.accept(new StatementDesignator(diagramNode, this));
        } catch (Exception e){
            e.printStackTrace();
        }
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


    public HashMap<String, DiagramNode> getDiagramNodes(){
        return diagramNodes;
    }
}










