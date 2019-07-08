/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import classes.DbHandler;
import classes.Validation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author saim
 */
public class StudentRegisterController implements Initializable {

	@FXML
    private JFXTextField txtFirstname;

    @FXML
    private JFXTextField txtLastname;

    @FXML
    private JFXTextField txtCNIC;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtsid;

    @FXML
    private JFXRadioButton rdMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton rdFemale;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXTextField txtFatherphone;

    @FXML
    private JFXComboBox<String> comboOccuption;

    @FXML
    private JFXTextField txtFathername;

    @FXML
    private JFXTextField txtZipcode;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> comboDepartment;

    @FXML
    private JFXComboBox<String> comboClass;

    @FXML
    private JFXTextField txtSession;
    
    @FXML
    private Label lbldepid;

    @FXML
    private Label lblclassid;

    @FXML
    private JFXDatePicker regestrationdate;
    Alert alert=new Alert(AlertType.ERROR) ;
    /*Desktop desktop=Desktop.getDesktop();
    File file;
    FileInputStream fis;*/
    
    TrayNotification tray=new TrayNotification();
	DbHandler db;
	Connection connection;
	PreparedStatement pst;
	Statement stat;
	ResultSet result;
	Validation validate=new Validation();
    
   // FileChooser filechooser;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      db = new DbHandler();
     /* filechooser=new FileChooser();
      filechooser.getExtensionFilters().addAll(new ExtensionFilter("Image","*.jpg","*.png")
    		  );*/
      comboDepartment.getSelectionModel().selectedItemProperty().addListener((v, old_val,new_val)->{
		int id=fetchDepID(comboDepartment.getValue());
		lbldepid.setText(""+id);
		
		fetchClass(id);
      });
      comboClass.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
    	  fetchClassID(comboClass.getValue());
    	  
      });
      
       setOccuption();
      fetchDepartment();
    
    
    
    }
    
    
    
    private void setOccuption() {
    	ObservableList<String> occuption=FXCollections.observableArrayList();
    	occuption.addAll("Engineer","Teacher","Farmer");
    	  comboOccuption.setItems(occuption);
    	
    	
    }
    
   /* @FXML
	 private void browseImage(){
    	Stage stage=new Stage();
		file=filechooser.showOpenDialog(stage);
		if(file !=null) {
			//desktop.open(file);
			textarea.setText(file.getAbsolutePath());
		}
	 }*/
    public int fetchDepID(String depname) {
		 int ans=0;
       try {
			connection=db.getConnection();
		   
			String sql="SELECT depid from department where depname=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, depname);
			result=pst.executeQuery();
			while(result.next()) {
				ans=result.getInt(1);
				
				}
			}
		catch(Exception e) {
			System.out.println(e);
		}
		return ans;
		
	}
    @FXML
    private void fetchDepartment() {
		
    	try {
    				
    				connection=db.getConnection();
    				Statement stat=connection.createStatement();
    				String sql="SELECT depname from department";
    				result=stat.executeQuery(sql);
    				ObservableList<String> depname=FXCollections.observableArrayList();
    				while(result.next()) {
    					depname.add(result.getString(1));	
    					
    					}
    				comboDepartment.setItems(depname);
    				
    				
    			}
    			catch(Exception e) {
    				System.out.println(e);
    			}
    			
    		}
    
         private void fetchClassID(String coursename){
		
		try{
			connection=db.getConnection();
			String sql="SELECT classid from class where classname=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, coursename);
			result=pst.executeQuery();
			
			while(result.next()){
				
				lblclassid.setText(""+result.getInt("classid"));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
         }
         
           private void fetchClass(int val) {
		
    	try {
    				
    				connection=db.getConnection();
    			
    				String sql="SELECT classname from class  where depid="+val;
    				pst=connection.prepareStatement(sql);
    				result=pst.executeQuery();
    				ObservableList<String> depname=FXCollections.observableArrayList();
    				while(result.next()) {
    					
    					depname.add(result.getString(1));	
    					
    					}
    				comboClass.setItems(depname);
    				
    				
    			}
    			catch(Exception e) {
    				System.out.println(e);
    			}
    			
    		}
    		

    @FXML
    public void clearFields() {
    	
    	txtFirstname.clear();
    	txtLastname.clear();
    	txtEmail.clear();
    	txtPhone.clear();
    	txtCNIC.clear();
    	dob.setValue(null);
    	txtFathername.clear();
    	txtFatherphone.clear();
    	comboOccuption.setValue(null);
    	txtAddress.clear();
    	txtZipcode.clear();
    	comboDepartment.setValue(null);
    	comboClass.setValue(null);
    	txtSession.clear();
    	regestrationdate.setValue(null);
    	lblclassid.setText(null);
    	lbldepid.setText(null);
     
    }

    @FXML
    private void saveStudent(ActionEvent event) throws SQLException {
    	if(searchStudentExistence()==true){
   		 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("RECORD EXISTS");
			 alert.setContentText("RECORD ALREADY EXISTS AGAINST ENTERED");
			 alert.show();
   		
   	              }
    	else    if(validateFields()==1 && searchStudentExistence()==false){
    	    
       try {
    	  
    	  
    	   connection=db.getConnection();
    	  
    	   String sql="INSERT INTO student (stuid,stufname,stulname,stufathername,"
    			   + "stuemail,stuaddress,stugender,sturegestration,depid,classid,"
       	   		+ "stusession,stuPhone,stuzip,studob,stufatherphone,stufatheroccuption,stucnic)"
    	   		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	   pst=connection.prepareStatement(sql);
    	   pst.setString(1, txtsid.getText().toString());
    	   pst.setString(2,txtFirstname.getText().toString());
    	   pst.setString(3,txtLastname.getText().toString());
    	   pst.setString(4,txtFathername.getText().toString());  
    	   pst.setString(5,txtEmail.getText().toString());
    	   pst.setString(6,txtAddress.getText().toString());
    	   pst.setString(7,getGender());
    	  pst.setDate(8,Date.valueOf(regestrationdate.getValue()));
    	   pst.setString(9,lbldepid.getText());
    	   pst.setString(10,lblclassid.getText()); 
    	  pst.setString(11,txtSession.getText().toString());
    	   pst.setString(12,txtPhone.getText().toString());
    	   pst.setString(13,txtZipcode.getText().toString());
    	   pst.setDate(14,Date.valueOf(dob.getValue()));
    	   pst.setString(15,txtFatherphone.getText().toString());
    	   pst.setString(16,comboOccuption.getValue().toString());
    	   pst.setString(17,txtCNIC.getText().toString()); 
    	   
    	//  fis=new FileInputStream(file);
    	  
    	//  pst.setBinaryStream(18, (InputStream)fis,(int)file.length());
    	  
    	   int success=pst.executeUpdate();
    	   if(success==1) {
    		   clearFields();
    		   tray.setTray("SUCCESS", "STUDENT SAVED SUCCESSFULLY",
						new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	            tray.showAndDismiss(Duration.seconds(2));
    	   }
    	   
    	   
       }catch(Exception e) {
    	   alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
       }
                }
    }



    private String getGender() {
        String gdr = "";
        if (rdMale.isSelected()) {
            gdr = "MALE";
        } else if (rdFemale.isSelected()) {
            gdr = "FEMALE";
        }
        return gdr;

    }
    
    @FXML 
    private void updateStudent() {
    	if(searchStudentExistence()==false){
    		 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("NO RECORD");
			 alert.setContentText("NO RECORD EXISTS AGAINST ENTERED TO BE UPDATED");
			 alert.show();
    		
    	}
    	
    	else if(validateFields()==1 && searchStudentExistence()==true){
    	
  	   try {
  	   connection=db.getConnection();
  	   String sql="UPDATE student SET stufname=?,"
  	   		+ "stulname=?,stufathername=?, stuemail=?,stuaddress=?,"
  	   		+ "stugender=?,sturegestration=?,depid=?,classid=?,"
  	   		+ "stusession=?,stuPhone=?,stuzip=?,studob=?,stufatherphone=?,"
  	   		+ "stufatheroccuption=?,stucnic=? where stuid='"+txtsid.getText().toString()+"'"; 
  	   
  	   pst=connection.prepareStatement(sql);
  	   pst.setString(1, txtFirstname.getText().toString());
  	   pst.setString(2, txtLastname.getText().toString());
  	   pst.setString(3, txtFathername.getText().toString());
  	   pst.setString(4, txtEmail.getText().toString());
  	   pst.setString(5, txtAddress.getText().toString());
  	   pst.setString(6, getGender());
  	   pst.setDate(7, Date.valueOf(regestrationdate.getValue()));
  	   pst.setString(8, lbldepid.getText());
  	   pst.setString(9, lblclassid.getText());
  	   pst.setString(10, txtSession.getText().toString());
  	   pst.setString(11, txtPhone.getText().toString());
  	   pst.setString(12, txtZipcode.getText().toString());
  	   pst.setDate(13, Date.valueOf(dob.getValue()));
  	   pst.setString(14, txtFatherphone.getText().toString());
  	   pst.setString(15, comboOccuption.getValue());
  	   pst.setString(16, txtCNIC.getText().toString());
  	   
  	   int success=pst.executeUpdate();
  	   if(success==1) {
  		 clearFields();
		 tray.setTray("SUCCESS", "STUDENT'S RECORD UPDATED SUCCESSFULLY",
					new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2)); 
  		   
  	   }
  	  
  	   }
  	   catch(Exception e) {
  		 alert.setHeaderText("Operation Failed");
   	  alert.setContentText(e.toString());
   	  alert.showAndWait();
   	  System.out.println(e);
  	   }}
    }
    @FXML
    private void searchStudent() {
    	clearFields();
    	 if(txtsid.getText().isEmpty()||txtsid.getText().trim().isEmpty()){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("WARNING");
			 alert.setContentText("PLEASE ENTER STUDENT ID");
			 alert.show();
		 }else{
    	           
    	
   	   try {
   	   connection=db.getConnection();
   	String sql="SELECT s.stuid, s.stufname, s.stulname, s.stuaddress,s.stuzip,s.stuemail,s.stuPhone,s.stucnic,s.studob,"
			+ "s.stugender,s.stufathername,s.stufatherphone,s.stufatheroccuption,s.sturegestration,"
			+ "d.depname,cl.classname,s.stusession from student s join department d on"
			+ " s.depid=d.depid join class cl on s.classid=cl.classid where s.stuid='"+txtsid.getText().toString()+"'";
  
   	   pst=connection.prepareStatement(sql);
   	   result=pst.executeQuery();
   	   
   	if (result.next() == false) 
   	{ 
   	 alert.setAlertType(AlertType.WARNING);
	 alert.setHeaderText("EMPTY");
	 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
	 alert.show();
	 }
   	
   	else {
   		do {
   			java.sql.Date dateofBirth=result.getDate("studob");
   			LocalDate date=dateofBirth.toLocalDate();
   			dob.setValue(date);
   			dateofBirth=result.getDate("sturegestration");
   			date=dateofBirth.toLocalDate();
   			regestrationdate.setValue(date);
   			String gender=result.getString("stugender");
   			 		
   			if(gender.equals("MALE")) {
   				rdMale.setSelected(true);
   			}else {
   				rdFemale.setSelected(true);
   			}
   	   		  txtFirstname.setText(result.getString("stufname"));
   	   		   txtLastname.setText(result.getString("stulname"));
   	   		txtSession.setText(result.getString("stusession"));
   	   		   txtEmail.setText(result.getString("stuemail"));
   	   		   txtPhone.setText(result.getString("stuPhone"));
   	   		   txtAddress.setText(result.getString("stuaddress"));
   	   		   txtZipcode.setText(result.getString("stuzip"));
   	   		   txtFathername.setText(result.getString("stufathername"));
   	   		   txtFatherphone.setText(result.getString("stufatherphone"));
   	   		comboOccuption.setValue(result.getString("stufatheroccuption"));
   	   		txtCNIC.setText(result.getString("stucnic"));
   	  		comboDepartment.setValue(result.getString("d.depname"));
   	   	 //   comboClass.setValue(result.getString("classname"));
   		
   	   	
   			} 
   			while (result.next());
   	}

   
   	  /* while(result.next()) {    	
   		java.sql.Date dateofBirth=result.getDate("studob");
		LocalDate date=dateofBirth.toLocalDate();
		dob.setValue(date);
		dateofBirth=result.getDate("sturegestration");
		date=dateofBirth.toLocalDate();
		regestrationdate.setValue(date);
		String gender=result.getString("stugender");
		 		
		if(gender.equals("MALE")) {
			rdMale.setSelected(true);
		}else {
			rdFemale.setSelected(true);
		}
   		  txtFirstname.setText(result.getString("stufname"));
   		   txtLastname.setText(result.getString("stulname"));
   		txtSession.setText(result.getString("stusession"));
   		   txtEmail.setText(result.getString("stuemail"));
   		   txtPhone.setText(result.getString("stuPhone"));
   		   txtAddress.setText(result.getString("stuaddress"));
   		   txtZipcode.setText(result.getString("stuzip"));
   		   txtFathername.setText(result.getString("stufathername"));
   		   txtFatherphone.setText(result.getString("stufatherphone"));
   		comboOccuption.setValue(result.getString("stufatheroccuption"));
   		txtCNIC.setText(result.getString("stucnic"));
  		comboDepartment.setValue(result.getString("d.depname"));
   	 //   comboClass.setValue(result.getString("classname"));
	
   	   }*/
   	   
   	   
   	   }
   	   catch(Exception e) {
   		 alert.setHeaderText("Operation Failed");
   	  alert.setContentText(e.toString());
   	  alert.showAndWait();
   	  System.out.print(e);
   	   }}
    }
    @FXML
    private void deleteStudent() {
    	 if(txtsid.getText().isEmpty()||txtsid.getText().trim().isEmpty()){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("WARNING");
			 alert.setContentText("PLEASE ENTER TEACHER ID");
			 alert.show();
		 }
    	 else{
    	
    						
		try {
			connection=db.getConnection();
			
			String sql="DELETE FROM student where stuid='"+txtsid.getText().toString()+"'";
			
			pst=connection.prepareStatement(sql);
			int success=pst.executeUpdate();
			if(success==1) {
				clearFields();
				 tray.setTray("SUCCESS", "STUDENT DELETED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		        
		        	
			}
			
		} catch (Exception e) {
			
			 alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
		}
		 }
    }
    
    @FXML
    private boolean searchStudentExistence() {
    	
    	boolean ans=true;
    	 if(txtsid.getText().isEmpty()||txtsid.getText().trim().isEmpty()){
    		 ans=false;
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("WARNING");
			 alert.setContentText("PLEASE ENTER STUDENT ID");
			 alert.show();
		 }else{
    	           
    	
   	   try {
   	   connection=db.getConnection();
   	String sql="SELECT* from student where stuid='"+txtsid.getText().toString()+"'";
  
   	   pst=connection.prepareStatement(sql);
   	   result=pst.executeQuery();
   	   
   	if (result.next() == false) 
   	{ 
   		ans=false;
   	
	 }
      	   
   	   
   	   }
   	   catch(Exception e) {
   		
   	  System.out.print(e);
   	   }}
		return ans;
    }

    
    private int validateFields(){
		int ans=1;
		if(txtsid.getText().isEmpty()|| txtsid.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("ID FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
		}
		else if(txtFirstname.getText().isEmpty()||txtFirstname.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("FIRST NAME FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.ValidateName(txtFirstname.getText())==false){
			ans=0;
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DONT NUMBER OR SPECIAL CHARACTER IN FIRST NAME ");
	    	  alert.showAndWait();
		}
		else if(txtLastname.getText().isEmpty()||txtLastname.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("LAST NAME FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.ValidateName(txtLastname.getText())==false){
			ans=0;
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DONT NUMBER OR SPECIAL CHARACTER IN LAST NAME ");
	    	  alert.showAndWait();
		}
		else if(txtCNIC.getText().isEmpty()||txtCNIC.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("CNIC FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.validateContact(txtCNIC.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DON't USE ALPHABETS OR SPECIAL CHARACTER IN CNIC");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		else if(txtEmail.getText().isEmpty()||txtEmail.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("EMAIL FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtPhone.getText().isEmpty()||txtPhone.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PHONE FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.validateContact(txtPhone.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DON't USE ALPHABETS OR SPECIAL CHARACTER IN PHONE");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(dob.getValue()==null){
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("SELECT DATE OF BIRTH");
	    	  alert.showAndWait();
			ans=0;
		}
		else if(dob.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("DATE OF BIRTH  IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtFathername.getText().isEmpty()||txtFathername.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE ENTER FATHER'S NAME");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.ValidateName(txtFathername.getText())==false){
			ans=0;
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DONT NUMBER OR SPECIAL CHARACTER IN FATHER'S NAME ");
	    	  alert.showAndWait();
		}
		else if(txtFatherphone.getText().isEmpty()||txtFatherphone.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE ENTER FATHER'S PHONE NO.");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.validateContact(txtFatherphone.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("DON't USE ALPHABETS OR SPECIAL CHARACTER IN FATHER PHONE FIELD");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(comboOccuption.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("SELECT FATHER'S OCCUPATION");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		
		else if(txtAddress.getText().isEmpty()||txtAddress.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("ADDRESS FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtZipcode.getText().isEmpty()||txtZipcode.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE ENTER ZIP CODE");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		
				
		else if(comboDepartment.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT DEPARTMENT");
	    	  alert.showAndWait();
			ans=0;
		}
		
		else if(comboDepartment.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT DEPARTMENT");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if (comboClass.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT CLASS");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtSession.getText().isEmpty()||txtSession.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE ENTER SESSION");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		
		else if (regestrationdate.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE ENTER REGISTERATION DATE");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		
		
		return ans;
	};
	
	

}
