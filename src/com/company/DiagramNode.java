package com.company;

public class DiagramNode {

    private int id;
    private String name;
    private int[] parentIds;
    private int[] childIds;


    public DiagramNode(int id, String name, int[] parentIds, int[] childIds) {
        this.id = id;
        this.name = name;
        this.parentIds = parentIds;
        this.childIds = childIds;
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

    public int[] getParentIds() {
        return parentIds;
    }

    public void setParentIds(int[] parentIds) {
        this.parentIds = parentIds;
    }

    public int[] getChildIds() {
        return childIds;
    }

    public void addChildId(int childId) {
        childIds = new int[childIds.length];
        childIds[childIds.length] = childId;
    }
}
