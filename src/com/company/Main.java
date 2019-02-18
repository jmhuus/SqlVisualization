package com.company;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Main {

    public static void main(String[] args) {
        try {

            // All nodes tracked within DiagramNodeManager
            DiagramNodeManager diagramNodeManager = new DiagramNodeManager();
            DiagramNode diagramNode = new DiagramNode();
            List<String> sqlCommands = new ArrayList<>();
            sqlCommands.add("ALTER");
            sqlCommands.add("COMMENT");
            sqlCommands.add("CREATE");
            sqlCommands.add("DELETE");
            sqlCommands.add("DROP");
            sqlCommands.add("EXECUTE");
            sqlCommands.add("INSERT");
            sqlCommands.add("MERGE");
            sqlCommands.add("REPLACE");
            sqlCommands.add("SELECT");
            sqlCommands.add("TRUNCATE");
            sqlCommands.add("UPDATE");
            sqlCommands.add("UPSERT");
            sqlCommands.add("VALUES");


            /** TODO: use keyword SQL starting statements to recognize new queries
             * ALTER, COMMENT, CREATE, DELETE, DROP, EXECUTE, INSERT, MERGE
             * REPLACE, SELECT, TRUNCATE, UPDATE, UPSERT, VALUES
             */
            File file = new File("./TestSqlScript.sql");
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            for(String line: lines){
                if (line.contains("SELECT")){

                }else if(line.contains("DELETE")){

                }else if(line.contains("ALTER")){

                }else if(line.contains("CREATE")){

                }else if(line.contains("UPDATE")){

                }
            }



            // Parse and return select statement items
            CCJSqlParserManager pm = new CCJSqlParserManager();
            String sql = "SELECT T1.Id, T1.Name, T1.Address INTO ##JhTemp FROM Table1 AS T1 JOIN Table2 AS T2 ON T1.Id = T2.Id";
            Statement statement = pm.parse(new StringReader(sql));
            statement.accept(new StatementDesignator(diagramNode, diagramNodeManager));

            // Testing that diagramNode has available parent and child nodes
            for(DiagramNode child: diagramNode.getChildNodes()){
                System.out.println(child.getNodeName() + " " + child.getNodeType());
            }
            for(DiagramNode parent: diagramNode.getParentNodes()){
                System.out.println(parent.getNodeName() + " " + parent.getNodeType());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
