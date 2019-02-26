package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
                System.out.println("\n\nnode name: "+diagramNodeName);
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


        // Find root node; node with no parent nodes
        // TODO: find a better way to begin XML construction recursion
        String rootNodeName = null;
        for(String nodeName: diagramNodeManager.getDiagramNodes().keySet()){
            if(diagramNodeManager.getDiagramNodes().get(nodeName).getParentNodes().size()==0){
                rootNodeName = nodeName;
            }
        }


        try {
            // Construct XML to represent diagram nodes
            // Recursion to build XML tree
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root node to XML
            DiagramNode root = diagramNodeManager.getDiagramNodes().get(rootNodeName);
            Element rootElement = document.createElement(root.getNodeName());


            for (DiagramNode node : root.getChildNodes()) {
                // Parent nodes
            }
        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }
    }
}
