package com.company;

import java.util.ArrayList;
import java.util.List;

public class DiagramNode {

    private String nodeName;
    private String nodeType;
    private List<DiagramNode> parentNodes;
    private List<DiagramNode> childNodes;


    public DiagramNode() {
        this.parentNodes = new ArrayList<>();
        this.childNodes = new ArrayList<>();
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeType(){
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public List<DiagramNode> getParentNodeNames() {
        return parentNodes;
    }

    public void addParent(DiagramNode parentNode){
        parentNodes.add(parentNode);
    }

    public List<DiagramNode> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(DiagramNode childNodeName) {
        childNodes.add(childNodeName);
    }
}
