package com.company;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisualizationManager {

    // TODO: build UI output path input
    private static final String HTML_INDEX_PATH = "\\D3Visualization\\index.html";
    private static final String JSON_DATA_PATH = "D3Visualization/tmp/data.js";
    private static final String ERROR_MESSAGE_DATA_PATH = "D3Visualization/tmp/errorMessage.js";


    /**
     * Parent method used to open SQL Visualization
     * @param diagramNodeManager
     * @param errorMessage
     */
    public static void openVisualization(DiagramNodeManager diagramNodeManager, String errorMessage){
        JsonConstructor jsonConstructor = new JsonConstructor(diagramNodeManager);
        String json = jsonConstructor.getJsonDiagram();
        setJsonDataArray(json);
        setErrorMessage(errorMessage);
        openVisualizationBrowser();
    }

    // Export JSON data to data.js
    private static void setJsonDataArray(String jsonData){
        try {
            // Write JSON to Javascript file
            File newFile = new File(JSON_DATA_PATH);
            FileUtils.write(newFile, "var treeData = [" + jsonData + "];", "UTF-8");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
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
