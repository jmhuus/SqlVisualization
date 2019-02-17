package com.company;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;


import java.util.Iterator;
import java.util.List;

public class SelectDesignator implements SelectVisitor {

    private DiagramNode diagramNode;
    private DiagramNodeManager diagramNodeManager;

    public SelectDesignator(DiagramNode diagramNode, DiagramNodeManager diagramNodeManager) {
        this.diagramNode = diagramNode;
        this.diagramNodeManager = diagramNodeManager;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        // Child nodes (INTO tables)
        List selectItems = plainSelect.getIntoTables();
        String intoTableName;
        DiagramNode tmpNode;
        for (Iterator iter = selectItems.iterator(); iter.hasNext();){

            // Node doesn't exist; init before adding child object
            intoTableName = ((Table) iter.next()).getName();
            if(! diagramNodeManager.nodeExists(intoTableName)){
                tmpNode = new DiagramNode();
                tmpNode.setNodeType("TABLE");
                tmpNode.setNodeName(intoTableName);
                diagramNodeManager.addDiagramNode(tmpNode);
            }else{
                tmpNode = diagramNodeManager.getDiagramNode(intoTableName);
            }

            // Add child object
            diagramNode.addChildNode(tmpNode);
        }
    }

    @Override
    public void visit(SetOperationList setOperationList) {

    }

    @Override
    public void visit(WithItem withItem) {

    }

    @Override
    public void visit(ValuesStatement valuesStatement) {

    }
}
