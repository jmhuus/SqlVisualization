package com.company;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class VisualizationManager {

    // TODO: build UI output path input
    private static final String HTML_INDEX_PATH = "C:\\Users\\jordanhuus\\Documents\\Computer Science\\Repos\\SqlVisualization\\D3Visualization\\index.html";
    private static final String JSON_DATA_PATH = "D3Visualization/data.js";
    private static final String ERROR_MESSAGE_DATA_PATH = "D3Visualization/errorMessage.js";


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
            FileUtils.write(newFile, "var dataArray = [" + jsonData + "];", "UTF-8");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

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
            File visualizationHtml = new File(HTML_INDEX_PATH);
            Process p = new ProcessBuilder("cmd", "/c", "start", "chrome", visualizationHtml.getPath()).start();
            int exitCode = p.waitFor();
            System.out.println(exitCode == 0 ? "Visualization Successful" : "Visualization Failed; exit code: " + exitCode);
        } catch(InterruptedException ie){
            ie.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
