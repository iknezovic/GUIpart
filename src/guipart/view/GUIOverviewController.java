/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipart.view;

import guipart.GUIPart;
import guipart.model.Person;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

/**
 *
 * @author ivan
 */
public class GUIOverviewController {
    
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person,String> id;
    @FXML private TableColumn<Person,String> balance;
    @FXML private TableColumn<Person,String> creditLine;
    @FXML private TableColumn<Person,String> fraud;
    
    @FXML private Label gender;
    @FXML private Label transaction;
    @FXML private Label intlTransaction;
    @FXML private Label cardholders;
    
    @FXML private Button openButton;
    private final FileChooser fileChooser = new FileChooser();
    
    
    //reference to GUIPart;
    private GUIPart guiPart;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public GUIOverviewController(){
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    
    @FXML
    private void initialize() {
        
        id.setCellValueFactory(cellData -> cellData.getValue().getIDProperty().asString());
        balance.setCellValueFactory(cellData -> cellData.getValue().getBalanceProperty().asString());
        creditLine.setCellValueFactory(cellData -> cellData.getValue().getCreditLineProperty().asString());
        fraud.setCellValueFactory(cellData -> cellData.getValue().getFraudProperty().asString());
        //firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        
        // Clear person details.
        setPersonDetails(null);
            
        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> setPersonDetails(newValue));
    }
    
    
    public void setMainApp(GUIPart mainApp) {
        this.guiPart = mainApp;

        // Add observable list data to the table
        personTable.setItems(guiPart.getPersonData());
    }
    
    public void setPersonDetails(Person person){
        
        if(person != null){
            
            gender.setText(person.getGender());
            transaction.setText(person.getTransactions());
            intlTransaction.setText(person.getIntlTransactions());
            cardholders.setText(person.getCardholders());
        }else{
        
            gender.setText("");
            transaction.setText("");
            intlTransaction.setText("");
            cardholders.setText("");
        }
    
    }
    
    
    @FXML protected void handleOpenFile(ActionEvent event){
        
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            //openFile(file);
        }
                
    }
}
