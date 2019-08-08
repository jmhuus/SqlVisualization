package com.company;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;

import java.util.List;

public class SelectDesignator implements SelectVisitor {

    private DiagramNode diagramNode;
    private DiagramNodeManager diagramNodeManager;

    SelectDesignator(DiagramNode diagramNode, DiagramNodeManager diagramNodeManager) {
        this.diagramNode = diagramNode;
        this.diagramNodeManager = diagramNodeManager;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        try {
            // Child nodes (INTO tables)
            List<Table> selectItems = plainSelect.getIntoTables();
            DiagramNode tmpNode;
            for (Table intoTable : selectItems) {

                // Node doesn't exist; init before adding child object
                if (!diagramNodeManager.nodeExists(intoTable.getName())) {
                    tmpNode = new DiagramNode();
                    tmpNode.setNodeType("TABLE");
                    tmpNode.setNodeName(intoTable.getName());
                    tmpNode.addParent(diagramNode);
                    diagramNodeManager.addDiagramNode(tmpNode);
                } else {
                    tmpNode = diagramNodeManager.getDiagramNode(intoTable.getName());
                    tmpNode.addParent(diagramNode);
                }

                // Add child object
                diagramNode.addChildNode(tmpNode);
            }
        }catch (NullPointerException npe){
            return;
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
