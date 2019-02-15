package com.company;

import java.util.HashMap;

public class DiagramNodeManager {

    private static HashMap<Integer, DiagramNode> diagramNodes;

    public DiagramNodeManager() {
        diagramNodes = new HashMap<>();
    }

    public boolean nodeExists(int diagramId){
        return diagramNodes.containsKey(diagramId);
    }

    public void addDiagramNode(DiagramNode diagramNode) throws Exception{
        // Ensure ID doesn't already exist
        if(diagramNodes.containsKey(diagramNode.getId())){
            throw new Exception("DiagramNode ID: " + diagramNode.getId() + " already exists.");
        }

        // Store new DiagramNode
        diagramNodes.put(diagramNode.getId(), diagramNode);
    }

    public DiagramNode getDiagramNode(int id){
        return diagramNodes.get(id);
    }

    public static int getNewDiagramNodeId(){
        int i=0;
        while(true){
            if(! diagramNodes.containsKey(i)){
                break;
            }

            i += 1;
        }

        return i;
    }
}
