package com.company;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;


import java.util.Iterator;
import java.util.List;

public class SelectDesignator implements SelectVisitor {

//    private DiagramNodeManager diagramNodeManager;

//    public SelectDesignator() {
//        this.diagramNodeManager = diagramNodeManager;
//    }

    @Override
    public void visit(PlainSelect plainSelect) {
        // Child nodes
        List selectItems = plainSelect.getIntoTables();
        for (Iterator iter = selectItems.iterator(); iter.hasNext();){
            System.out.println(iter.next());
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
