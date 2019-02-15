package com.company;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        try {


            ArrayList<DiagramNode> diagramNodes = new ArrayList<>();

            // Parse and return select statement items
            CCJSqlParserManager pm = new CCJSqlParserManager();
            String sql = "SELECT T1.Id, T1.Name, T1.Address INTO ##JhTemp FROM Table1 AS T1 JOIN Table2 AS T2 ON T1.Id = T2.Id";
            Statement statement = pm.parse(new StringReader(sql));
            statement.accept(new StatementDesignator(diagramNodes));


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
