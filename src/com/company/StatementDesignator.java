package com.company;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class StatementDesignator implements StatementVisitor {
    private DiagramNode diagramNode;
    private DiagramNodeManager diagramNodeManager;

    StatementDesignator(DiagramNode diagramNode, DiagramNodeManager diagramNodeManager) {
        this.diagramNode = diagramNode;
        this.diagramNodeManager = diagramNodeManager;
    }

    @Override
    public void visit(Comment comment) {

    }

    @Override
    public void visit(Commit commit) {

    }

    @Override
    public void visit(Delete delete) {

    }

    @Override
    public void visit(Update update) {

    }

    @Override
    public void visit(Insert insert) {

    }

    @Override
    public void visit(Replace replace) {

    }

    @Override
    public void visit(Drop drop) {

    }

    @Override
    public void visit(Truncate truncate) {

    }

    @Override
    public void visit(CreateIndex createIndex) {

    }

    @Override
    public void visit(CreateTable createTable) {
        // Node type
        diagramNode.setNodeType("CREATE TABLE");

        // Node name
        diagramNode.setNodeName(diagramNodeManager.getNewDiagramNodeQueryName("CREATE TABLE"));

        // Add new diagramNode to DiagramNodeManager
        diagramNodeManager.addDiagramNode(diagramNode);

        // Add child node
        Table table = createTable.getTable();
        DiagramNode tmpNode;
        if(! diagramNodeManager.nodeExists(table.getName())){
            tmpNode = new DiagramNode();
            tmpNode.setNodeType("TABLE");
            tmpNode.setNodeName(table.getName());
            tmpNode.addParent(diagramNode);

            // Add child
            diagramNode.addChildNode(tmpNode);

            // Child to add parent
            diagramNodeManager.addDiagramNode(tmpNode);
        }else{

            // Child to add parent
            tmpNode = diagramNodeManager.getDiagramNode(table.getName());
            tmpNode.addParent(diagramNode);

            // Add child
            diagramNode.addChildNode(tmpNode);
        }
    }

    @Override
    public void visit(CreateView createView) {

    }

    @Override
    public void visit(AlterView alterView) {

    }

    @Override
    public void visit(Alter alter) {

    }

    @Override
    public void visit(Statements statements) {

    }

    @Override
    public void visit(Execute execute) {

    }

    @Override
    public void visit(SetStatement setStatement) {

    }

    @Override
    public void visit(ShowStatement showStatement) {

    }

    @Override
    public void visit(Merge merge) {

    }

    @Override
    public void visit(Select select) {
        // Node type
        diagramNode.setNodeType("SELECT");

        // Node name
        diagramNode.setNodeName(diagramNodeManager.getNewDiagramNodeQueryName("SELECT"));

        // Add new diagramNode to DiagramNodeManager
        diagramNodeManager.addDiagramNode(diagramNode);

        // Set parent (FROM tables) nodes
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(select);
        DiagramNode tmpNode;
        for (String fromTableName: tableList) {

            // Node doesn't exist, init before adding parent object
            if (! diagramNodeManager.nodeExists(fromTableName)){
                tmpNode = new DiagramNode();
                tmpNode.setNodeType("TABLE");
                tmpNode.setNodeName(fromTableName);
                tmpNode.addChildNode(diagramNode);
                diagramNodeManager.addDiagramNode(tmpNode);
            }else{
                tmpNode = diagramNodeManager.getDiagramNode(fromTableName);
                tmpNode.addChildNode(diagramNode);
            }


            // Add parent object; FROM table
            diagramNode.addParent(tmpNode);
        }


        // Retrieve INTO tables
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(new SelectDesignator(diagramNode, diagramNodeManager));
    }

    @Override
    public void visit(Upsert upsert) {

    }

    @Override
    public void visit(UseStatement useStatement) {

    }

    @Override
    public void visit(Block block) {

    }

    @Override
    public void visit(ValuesStatement valuesStatement) {

    }
}
