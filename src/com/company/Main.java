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
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import org.apache.commons.io.FileUtils;



public class Main {

    public static void main(String[] args) {

        String errorMessage = "";

        // All nodes tracked within DiagramNodeManager
        DiagramNodeManager diagramNodeManager = new DiagramNodeManager();


        try {
            // Read file
            File file = new File("./TestSqlScript.sql");
            List<String> lines = FileUtils.readLines(file, "UTF-8");

            // One string
            StringBuilder stringBuilder = new StringBuilder();
            for(String line: lines){
                stringBuilder.append(line + "\n");
            }

            // Parse SQL statements
            String sqlScript = stringBuilder.toString();
            Statements statements = CCJSqlParserUtil.parseStatements(sqlScript);
            List<Statement> statementList = statements.getStatements();


            // References to all DiagramNodes
            for (Statement statement : statementList) {
                diagramNodeManager.addStatementToDiagram(statement);
            }
        } catch (JSQLParserException jspe){
            // Bad SQL statement syntax
            errorMessage = jspe.getCause().toString();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        // Open visualization in Chrome
        VisualizationManager.openVisualization(diagramNodeManager, errorMessage);
    }
}
