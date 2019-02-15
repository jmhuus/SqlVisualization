package com.company;

import java.util.ArrayList;
import java.util.List;

public class DiagramNode {

    private int id;
    private String name;
    private String nodeType;
    private List<Integer> parentIds;
    private List<Integer> childIds;


    public DiagramNode(int id, String name, String nodeType) {
        this.id = id;
        this.name = name;
        this.nodeType = nodeType;
        this.parentIds = new ArrayList<>();
        this.childIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType(){
        return this.nodeType;
    }

    public void setNodeType(String nodeType){
        this.nodeType = nodeType;
    }

    public List<Integer> getParentIds() {
        return parentIds;
    }

    public void addParent(int parentId){
        parentIds.add(parentId);
    }

    public List<Integer> getChildIds() {
        return childIds;
    }

    public void addChildId(int childId) {
        childIds.add(childId);
    }
}
