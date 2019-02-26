package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;


public class Main {

    public static void main(String[] args) {

        // All nodes tracked within DiagramNodeManager
        DiagramNodeManager diagramNodeManager = new DiagramNodeManager();
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


        try {
            /**
             * ALTER, COMMENT, CREATE, DELETE, DROP, EXECUTE, INSERT, MERGE
             * REPLACE, SELECT, TRUNCATE, UPDATE, UPSERT, VALUES
             */
            File file = new File("./TestSqlScript.sql");
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            List<String> statements = new ArrayList<>();
            boolean statementFound = false;
            for (String line: lines) {
                // Append each query line
                if (line.contains("SELECT")) {

                    // Add query node to diagram
                    if(statementFound){
                        statements.add(stringBuilder.toString());
                    }

                    // First statement found; don't attempt parsing none-statement strings
                    statementFound = true;

                    // New SQL statement string
                    stringBuilder = new StringBuilder();
                }

                // Begin building statement SQL string
                stringBuilder.append(line + " \n");
            }
            statements.add(stringBuilder.toString());

            // TEST: statements are being grouped correctly
            for(String statement: statements){
                System.out.println(statement);
                diagramNodeManager.addDiagramNode(statement);
            }

            // TEST: nodes initialized correctly
            for(String diagramNodeName: diagramNodeManager.getDiagramNodes().keySet()){
                System.out.println("\n\n========== "+diagramNodeName+" ==========");
                System.out.println("parent nodes:");
                for(DiagramNode diagramNode: diagramNodeManager.getDiagramNode(diagramNodeName).getParentNodes()){
                    System.out.println("    "+diagramNode.getNodeName());
                }
                System.out.println("\nchild nodes:");
                for(DiagramNode diagramNode: diagramNodeManager.getDiagramNode(diagramNodeName).getChildNodes()){
                    System.out.println("    "+diagramNode.getNodeName());
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }


        // TEST: XML output
        XmlConstructor xmlConstructor = new XmlConstructor(diagramNodeManager);
        Document xmlDocument = xmlConstructor.getXmlDiagram();
        System.out.println();
        System.out.println("========== XML ==========");
        System.out.println(XmlConstructor.getStringFromDocument(xmlDocument));

    }
}
