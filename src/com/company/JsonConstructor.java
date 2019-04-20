package com.company;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonConstructor {
    private static String PARENT_JSON_ATTRIBUTE = "children";
    private static String CHILD_JSON_ATTRIBUTE = "parent";
    private DiagramNodeManager diagramNodeManager;

    public JsonConstructor(DiagramNodeManager diagramNodeManager) {
        this.diagramNodeManager = diagramNodeManager;
    }


    public String getJsonDiagram(){

        JSONObject jsonObject = new JSONObject();
        DiagramNode node;
        ArrayList<JSONObject> nodes = new ArrayList<>();
        String jsonData = "";
        int id = 0;

        // Nodes without parents begin recursive statement
        for(String nodeName: diagramNodeManager.getDiagramNodes().keySet()){
            node = diagramNodeManager.getDiagramNode(nodeName);
            jsonObject = new JSONObject();

            jsonObject.put("name", nodeName)
                    .put("id", node.getId())
                    .put("parents", node.getParentNodeIds());

            jsonData += jsonObject + ",\n";
        }

        return jsonData;
    }


    /**
     * Recursive method used to gather all child JSON diagram data.
     * @param diagramNodeManager
     * @param rootName
     * @return a nested JSON object; the root JSON object contains child objects.
     */
    private JSONArray getChildJsonDataArray(DiagramNodeManager diagramNodeManager, String rootName){

        // Init new JSONObject for child nodes
        JSONArray childJsonData = new JSONArray();

        // Children
        for(DiagramNode childNode: diagramNodeManager.getDiagramNode(rootName).getChildNodes()){
            if(childNode.getChildNodes().iterator().hasNext()) {

                // Ensure parent exists
                childJsonData.put(new JSONObject()
                        .put("name", childNode.getNodeName())
                        .put(CHILD_JSON_ATTRIBUTE, childNode.getChildNodes().iterator().next().getNodeName())
                        .put(PARENT_JSON_ATTRIBUTE, getChildJsonDataArray(diagramNodeManager, childNode.getNodeName())));
            }else{
                childJsonData.put(new JSONObject()
                        .put("name", childNode.getNodeName())
                        .put(CHILD_JSON_ATTRIBUTE, "null")
                        .put(PARENT_JSON_ATTRIBUTE, getChildJsonDataArray(diagramNodeManager, childNode.getNodeName())));
            }
        }

        return childJsonData;
    }
}















