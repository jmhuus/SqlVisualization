package com.company;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class VisualizationManager {

    // TODO: build UI output path input
    private static final String HTML_INDEX_PATH = "file:///C:/Users/jorda/Documents/Computer%20Science/Projects/Repos/SqlVisualization/D3Visualization/index.html";
    private static final String JSON_DATA_PATH = "D3Visualization/data.js";
    private static final String ERROR_MESSAGE_DATA_PATH = "D3Visualization/errorMessage.js";


    public static void openVisualization(DiagramNodeManager diagramNodeManager, String errorMessage){
        JsonConstructor jsonConstructor = new JsonConstructor(diagramNodeManager);
        String json = jsonConstructor.getJsonDiagram();
        setJsonDataArray(json);
        setErrorMessage(errorMessage);
        openVisualizationBrowser();
    }
    private static void setJsonDataArray(String jsonData){
        try {
            // Write JSON into Javascript file
            File newFile = new File(JSON_DATA_PATH);
            FileUtils.write(newFile, "var dataArray = [" + jsonData + "];", "UTF-8");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    private static void setErrorMessage(String errorMessage){
        System.out.println(errorMessage);
    }
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
