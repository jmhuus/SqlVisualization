/*
  _________________  .____      ____   ____.__                    .__  .__                __  .__
 /   _____/\_____  \ |    |     \   \ /   /|__| ________ _______  |  | |__|____________ _/  |_|__| ____   ____
 \_____  \  /  / \  \|    |      \   Y   / |  |/  ___/  |  \__  \ |  | |  \___   /\__  \\   __\  |/  _ \ /    \
 /        \/   \_/.  \    |___    \     /  |  |\___ \|  |  // __ \|  |_|  |/    /  / __ \|  | |  (  <_> )   |  \
/_______  /\_____\ \_/_______ \    \___/   |__/____  >____/(____  /____/__/_____ \(____  /__| |__|\____/|___|  /
        \/        \__>       \/                    \/           \/              \/     \/                    \/
 */




package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import org.apache.commons.io.FileUtils;



public class Main {

    public static void main(String[] args) {

        String errorMessage = "";

        // All nodes tracked within DiagramNodeManager
        DiagramNodeManager diagramNodeManager = new DiagramNodeManager();
        String[] sqlCommands = {
            "ALTER",
            "COMMENT",
            "CREATE",
            "DELETE",
            "DROP",
            "EXECUTE",
            "INSERT",
            "MERGE",
            "REPLACE",
            "SELECT",
            "TRUNCATE",
            "UPDATE",
            "UPSERT",
            "VALUES"
        };


        try {
            // Read file
            File file = new File("./TestSqlScript.sql");
            List<String> lines = FileUtils.readLines(file, "UTF-8");

            // Read queries and build diagram nodes
            StringBuilder stringBuilder = new StringBuilder();
            List<String> statements = new ArrayList<>();
            boolean statementFound = false;
            for (String line : lines) {
                // Append each query line
                if (stringContainsItemFromList(line, sqlCommands) && !(isSqlComment(line))) {

                    // Add query node to diagram
                    if (statementFound) {
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

            // Append String
            statements.add(stringBuilder.toString());


            // References to all DiagramNodes
            for (String statement : statements) {
                diagramNodeManager.addDiagramNode(statement);
            }
        } catch (JSQLParserException jspe){
            // Bad SQL statement syntax
            errorMessage = jspe.getCause().toString();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }


        VisualizationManager.openVisualization(diagramNodeManager, errorMessage);

    }

    /**
     * Determines if a string contains any element in string array
     * @param inputStr to search for specific items
     * @param items to search for in a string
     * @return True if the line contains at least one item in the list
     */
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        inputStr = inputStr.toUpperCase();
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }



    public static boolean isSqlComment(String sqlLine){
        sqlLine = sqlLine.trim();
        return (sqlLine.substring(0, 2).equals("--")) || sqlLine.substring(0, 2).equals("/*") ? true : false;
    }
}
