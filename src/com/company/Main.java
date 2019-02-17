package com.company;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        try {


            DiagramNodeManager diagramNodeManager = new DiagramNodeManager();


            /** TODO: use keyword SQL starting statements to recognize new queries
             * ALTER, COMMENT, CREATE, DELETE, DROP, EXECUTE, INSERT, MERGE
             * REPLACE, SELECT, TRUNCATE, UPDATE, UPSERT, VALUES
             */


            // All nodes tracked within DiagramNodeManager
            DiagramNode diagramNode = diagramNodeManager.getNewNode();

            // Parse and return select statement items
            CCJSqlParserManager pm = new CCJSqlParserManager();
            String sql = "SELECT T1.Id, T1.Name, T1.Address INTO ##JhTemp FROM Table1 AS T1 JOIN Table2 AS T2 ON T1.Id = T2.Id";
            Statement statement = pm.parse(new StringReader(sql));
            statement.accept(new StatementDesignator(diagramNode, diagramNodeManager));
            System.out.println();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
