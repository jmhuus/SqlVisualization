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

        JSONObject jsonData = new JSONObject();


        // Nodes without parents begin recursive statement
        for(String nodeName: diagramNodeManager.getDiagramNodes().keySet()){
            if(diagramNodeManager.getDiagramNode(nodeName).getParentNodes().size()==0){

                // Ensure parent exists
                if(diagramNodeManager.getDiagramNode(nodeName).getParentNodes().iterator().hasNext()){
                    jsonData.put("name", nodeName)
                            .put(CHILD_JSON_ATTRIBUTE, diagramNodeManager.getDiagramNode(nodeName).getParentNodes().iterator().next().getNodeName())
                            .put(PARENT_JSON_ATTRIBUTE, getChildJsonDataArray(diagramNodeManager, nodeName));
                }else{
                    jsonData.put("name", nodeName)
                            .put(CHILD_JSON_ATTRIBUTE, "null")
                            .put(PARENT_JSON_ATTRIBUTE, getChildJsonDataArray(diagramNodeManager, nodeName));
                }
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















