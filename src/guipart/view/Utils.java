/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipart.view;

import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author ivan
 */
public class Utils {
    
    protected static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("Select CSV");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
    }
    
    
    protected static void runLogistic(String pathCSV,String pathModel){
    
    }
    
}
