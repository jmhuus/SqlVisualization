package com.company;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisualizationManager {

    // TODO: build UI output path input
    private static final String DOT_DATA_PATH = "D3Visualization/tmp/";
    private static final String DOT_DATA_FILE_NAME = "input.dot";
    private static final String VIZ_OUTPUT_PATH = "D3Visualization/tmp/";
    private static final String VIZ_OUTPUT_FILE_NAME = "output.svg";
    private static final String HTML_INDEX_PATH = "\\D3Visualization\\index.html";
    private static final String ERROR_MESSAGE_DATA_PATH = "D3Visualization/tmp/errorMessage.js";


    /**
     * Parent method used to open SQL Visualization
     * @param diagramNodeManager
     * @param errorMessage
     */
    public static void openVisualization(DiagramNodeManager diagramNodeManager, String errorMessage){

        // Build dot file string
        String dotFileData = "";
        // TODO: refactor to pull DiagramNode object instead of keyset
        for (String currentNodeName : diagramNodeManager.getDiagramNodes().keySet()){
            // Loop through all children
            for (DiagramNode childNodeName : diagramNodeManager.getDiagramNode(currentNodeName).getChildNodes()){
                dotFileData += currentNodeName + " -> " + childNodeName.getNodeName() + ";\n";
            }
        }
        dotFileData = "digraph G {\n" + dotFileData + "\n}";
        System.out.println(dotFileData);


        try {
            // Create dot file
            File newFile = new File(DOT_DATA_PATH + DOT_DATA_FILE_NAME);
            FileUtils.write(newFile, dotFileData, "UTF-8");


            // Open execute Graphviz cmd
            ProcessBuilder builder = new ProcessBuilder(
                    "dot", "-Tsvg", DOT_DATA_PATH + DOT_DATA_FILE_NAME, "-o", VIZ_OUTPUT_PATH + VIZ_OUTPUT_FILE_NAME);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }

            // Open Graphviz result



        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        // TODO: convert back to using browser; full-featured site?
//        JsonConstructor jsonConstructor = new JsonConstructor(diagramNodeManager);
//        String json = jsonConstructor.getJsonDiagram();
//        setJsonDataArray(json);
//        setErrorMessage(errorMessage);
//        openVisualizationBrowser();
    }


    // TODO: specify SQL source and line number location of syntax error
    // Export error message to errorMessage.js
    private static void setErrorMessage(String errorMessage){
        // Write message to errorMessage.js
        try {
            if(errorMessage == null || errorMessage.equals("")){
                File newFile = new File(ERROR_MESSAGE_DATA_PATH);
                FileUtils.write(newFile, "var errorMessage = [''];", "UTF-8");
            }else{
                File newFile = new File(ERROR_MESSAGE_DATA_PATH);
                errorMessage = errorMessage.replace("\n", "', '");
                FileUtils.write(newFile, "var errorMessage = ['Woops! SQL syntax error:', '', '"+errorMessage+"'];", "UTF-8");
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    // TODO: Migrate to front-end framework
    // Open D3 visualization tree in browser (Chrome)
    private static void openVisualizationBrowser(){
        try {
            // Open visualization in default browser
            String url = "D3Visualization/index.html";
            File htmlFile = new File(url);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
