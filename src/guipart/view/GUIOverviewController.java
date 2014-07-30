/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipart.view;

import guipart.GUIPart;
import guipart.model.Person;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ivan
 */
public class GUIOverviewController {
    /*******************************************************************
        Table fields
    *********************************************************************/
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person,String> id;
    @FXML private TableColumn<Person,String> balance;
    @FXML private TableColumn<Person,String> creditLine;
    @FXML private TableColumn<Person,String> fraud;
    
    /***************************************************************
     *  Label fields
     ****************************************************************/
    
    @FXML private Label gender;
    @FXML private Label transaction;
    @FXML private Label intlTransaction;
    @FXML private Label cardholders;
    
    @FXML private Button openButton;
    @FXML private TextField textFieldCSV;
    @FXML private TextField textFieldModel;
    
    /****************************************************************
     * path to csv file and model
     *****************************************************************/
    private String pathCSV = null;
    private String pathModel = null;
    
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
    
    
    @FXML void handleOpenFile(ActionEvent event){
        
        final FileChooser fileChooser = new FileChooser();
        Utils.configureFileChooserCSV(fileChooser);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            pathCSV = file.getPath();
            
            textFieldCSV.setText(pathCSV);
        }
                
    }
    
    @FXML void handleOpenModel(ActionEvent event){
        
        final FileChooser fileChooser = new FileChooser();
        Utils.configureFileChooserModel(fileChooser);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            pathModel = file.getPath();
            
            textFieldModel.setText(pathModel);
        }
                
    }
    
    @FXML void handleClassifyModel(ActionEvent event) throws IOException{
        
        if(pathModel != null && pathCSV != null){
            
            Auc collector = new Auc();
            LogisticModelParameters lmp = LogisticModelParameters.loadFrom(new File(pathModel));

            CsvRecordFactory csv = lmp.getCsvRecordFactory();
            OnlineLogisticRegression lr = lmp.createRegression();

            BufferedReader in = Utils.open(pathCSV);


            String line = in.readLine();
            csv.firstLine(line);
            line = in.readLine();
            int correct = 0;
            int wrong = 0;
            Boolean booltemp;
            
            while(line != null){
            
                Vector v = new SequentialAccessSparseVector(lmp.getNumFeatures());
                int target = csv.processLine(line, v);
                String [] split = line.split(",");
                
                /*for (String split1 : split) {
                    System.out.println(split1);
                }
                Integer a = Integer.parseInt(split[0]);
                System.out.println("AAAAA je:"+a);
                
                Person temp = new Person(Integer.parseInt(split[0]),Integer.parseInt(split[4]),Integer.parseInt(split[7]),Boolean.parseBoolean(split[8]), 
                        split[1],Integer.parseInt(split[5]),Integer.parseInt(split[6]),Integer.parseInt(split[2]));
                
                guiPart.addPerson(temp);*/


                double score = lr.classifyFull(v).maxValueIndex();
                if(score == target)
                   correct++;
                else
                   wrong++;

                System.out.println("Target is: "+target+" Score: "+score);
                
                booltemp = score != 0;
                
                Person temp = new Person(Integer.parseInt(split[0]),Integer.parseInt(split[4]),Integer.parseInt(split[7]),booltemp, 
                        split[1],Integer.parseInt(split[5]),Integer.parseInt(split[6]),Integer.parseInt(split[3]));
                
                guiPart.addPerson(temp);
                
                line = in.readLine();
                collector.add(target, score);

            }
            double posto = ((double)wrong/(double)(correct+wrong))*100;
            System.out.println("Total: "+(correct+wrong)+" Correct: "+correct+" Wrong: "+wrong+" Wrong pct: "+posto +"%");
            //PrintWriter output = null;
            Matrix m = collector.confusion();
            //output.printf(Locale.ENGLISH, "confusion: [[%.1f, %.1f], [%.1f, %.1f]]%n",m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
            System.out.println("Confusion:"+m.get(0, 0)+" "+m.get(1, 0)+"\n \t   "+m.get(0, 1)+" "+m.get(1, 1)+" ");
    //        m = collector.entropy();
            //output.printf(Locale.ENGLISH, "entropy: [[%.1f, %.1f], [%.1f, %.1f]]%n",m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
        }else{
            
            
            Dialogs.create()
        .owner(guiPart.getPrimaryStage())
        .title("Error Dialog")
        .masthead("Look, an Error Dialog")
        .message("Ooops, there was an error!")
        .showError();
            
            
        
        }   
    }
 
}
