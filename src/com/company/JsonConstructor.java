package com.company;


import org.json.JSONObject;

public class JsonConstructor {
    private DiagramNodeManager diagramNodeManager;

    public JsonConstructor(DiagramNodeManager diagramNodeManager) {
        this.diagramNodeManager = diagramNodeManager;
    }


    public String getJsonDiagram(){

        JSONObject jsonData = new JSONObject();


        // Nodes without parents begin recursive statement
        for(String nodeName: diagramNodeManager.getDiagramNodes().keySet()){
            if(diagramNodeManager.getDiagramNode(nodeName).getParentNodes().size()==0){
                jsonData.put(nodeName, getChildJsonData(diagramNodeManager, nodeName));
            }
        }

        return jsonData.toString();
    }


    /**
     * Recursive method used to gather all child JSON diagram data.
     * @param diagramNodeManager
     * @param rootName
     * @return a nested JSON object; the root JSON object contains child objects.
     */
    private JSONObject getChildJsonData(DiagramNodeManager diagramNodeManager, String rootName){

        // Init new JSONObject for child nodes
        JSONObject childJsonData = new JSONObject();

        // Children
        for(DiagramNode childNode: diagramNodeManager.getDiagramNode(rootName).getChildNodes()){
            childJsonData.put(childNode.getNodeName(), getChildJsonData(diagramNodeManager, childNode.getNodeName()));
        }

        return childJsonData;
    }
}















