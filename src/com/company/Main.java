package com.company;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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

            for(String statement: statements){
                diagramNodeManager.addDiagramNode(statement);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }


        // Load data into visualization and open
        JsonConstructor jsonConstructor = new JsonConstructor(diagramNodeManager);
        String json = jsonConstructor.getJsonDiagram();
        try {

            // Build Javascript and insert data
            File file = new File("D3Visualization/src.js");
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            StringBuilder newJavascript = new StringBuilder();
            for(String line: lines){
                if(line.contains("$$JsonData$$")){
                    newJavascript.append(json);
                }else{
                    newJavascript.append(line+"\n");
                }
            }

            // Write new Javascript file
            File newFile = new File("D3Visualization/main.js");
            FileUtils.write(newFile, newJavascript.toString(), "UTF-8");

            File visualizationHtml = new File("file:///C:/Users/jorda/Documents/Computer%20Science/Projects/Repos/SqlVisualization/D3Visualization/index.html");
            Process p = new ProcessBuilder("cmd", "/c", "start", "chrome", visualizationHtml.getPath()).start();
            int exitCode = p.waitFor();
            System.out.println(exitCode==0 ? "Visualization Successful":"Visualization Failed; exit code: "+exitCode);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
