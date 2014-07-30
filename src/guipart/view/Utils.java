/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipart.view;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.stage.FileChooser;

/**
 *
 * @author ivan
 */
public class Utils {
    
    static void configureFileChooserCSV(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("Select CSV");
            /*fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            ); */                
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
    }
    
    static void configureFileChooserModel(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("Select model");
        
    }
    
 
     
     static BufferedReader open(String inputFile) throws IOException {
        InputStream in;
        
        try{
          in = Resources.getResource(inputFile).openStream();
        } catch (IllegalArgumentException e) {
          in = new FileInputStream(new File(inputFile));
        }
        
        return new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
      }
     
    
}
