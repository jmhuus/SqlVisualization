package com.company;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


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
            // Loop through parent nodes
            for (DiagramNode parenteNodeName : diagramNodeManager.getDiagramNode(currentNodeName).getParentNodes()){
                dotFileData += "    " + parenteNodeName.getNodeName() + " -> " + currentNodeName + ";\n";
            }
        }
        dotFileData = "digraph G {\n" + dotFileData + "\n}";

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
            }

            // Open Graphviz result


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
