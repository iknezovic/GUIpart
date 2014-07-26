/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipart.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ivan
 */
public class Person {
    private final IntegerProperty id;
    private final IntegerProperty balance;
    private final IntegerProperty creditLine;   
    private final BooleanProperty fraud;
    
    private final StringProperty gender; 
    private final IntegerProperty trans;
    private final IntegerProperty intlTrans;
    private final IntegerProperty cardholders;
    
    public Person(){
      this(Integer.SIZE, Integer.SIZE, Integer.MIN_VALUE, Boolean.TRUE);
    }
    
    public Person(Integer id,Integer balance,Integer creditLine,Boolean fraud){
        this.id = new SimpleIntegerProperty(id);
        this.balance = new SimpleIntegerProperty(balance);
        this.creditLine = new SimpleIntegerProperty(creditLine);
        this.fraud = new SimpleBooleanProperty(fraud);
        
        //dummy data to populate fields
        this.gender = new SimpleStringProperty("unknown");
        this.trans = new SimpleIntegerProperty(0);
        this.intlTrans = new SimpleIntegerProperty(0);
        this.cardholders = new SimpleIntegerProperty(1);
    }
    
    public Integer getID(){
        return this.id.get();
    }
    
    public void setID(Integer id){
        this.id.set(id);
    }
    
    public IntegerProperty getIDProperty(){
        return this.id;
    }
    
    
    public Integer getBalance(){
        return this.balance.get();
    }
    
    public void setBalance(Integer balance){
        this.balance.set(balance);
    }
    
    public IntegerProperty getBalanceProperty(){
        return this.balance;
    }
    
    
    public Integer getCreditLine(){
        return this.creditLine.get();
    }
    
    public void setCreditLine(Integer creditLine){
        this.creditLine.set(creditLine);
    }
    
    public IntegerProperty getCreditLineProperty(){
        return this.creditLine;
    }
    
    
    public Boolean getFraud(){
        return this.fraud.get();
    }
    
    public void setFraud(Boolean fraud){
        this.fraud.set(fraud);
    }
    
    public BooleanProperty getFraudProperty(){
        return this.fraud;
    }
    
    public void setGender(String gender){
        this.gender.set(gender);
    }
    
    public void setTransactions(Integer transaction){
        this.trans.set(transaction);
    }
    
    
    public void setIntlTransactions(Integer transaction){
        this.intlTrans.set(transaction);
    }
    
    public void setCardholders(Integer cardholders){
        this.cardholders.set(cardholders);
    }
    
    public String getGender(){
        return this.gender.toString();
    }
    
    public String getTransactions(){
        return this.trans.toString();
    }
    
    public String getIntlTransactions(){
        return this.intlTrans.toString();
    }
    
    public String getCardholders(){
        return this.cardholders.toString();
    }
    
    
    
}
