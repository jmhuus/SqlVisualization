package com.company;

import java.util.ArrayList;
import java.util.List;


/**
 * DiagramNode represents a single node in the diagram - Queries, temporary tables, server tables, sub-queries, etc.
 * References to child(INTO) and parent(FROM) node objects are available - although stored with scope in DiagramNodeManager,
 */
public class DiagramNode {

    private String nodeName;
    private String nodeType;
    private List<DiagramNode> parentNodes;
    private List<DiagramNode> childNodes;
    private int id;


    DiagramNode() {
        this.parentNodes = new ArrayList<>();
        this.childNodes = new ArrayList<>();
        this.id = DiagramNodeManager.getNextAvailableNodeId();
    }

    void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    String getNodeName() {
        return nodeName;
    }

    public String getNodeType(){
        return this.nodeType;
    }

    void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    List<DiagramNode> getParentNodes() {
        return parentNodes;
    }

    void addParent(DiagramNode parentNode){
        parentNodes.add(parentNode);
    }

    List<DiagramNode> getChildNodes() {
        return childNodes;
    }

    void addChildNode(DiagramNode childNodeName) {
        childNodes.add(childNodeName);
    }

    public int getId() {
        return id;
    }

    public int[] getParentNodeIds(){
        int[] parentIds = new int[parentNodes.size()];
        for (int i = 0; i < parentNodes.size(); i++) {
            parentIds[i] = parentNodes.get(i).getId();
        }

        return parentIds;
    }
}
