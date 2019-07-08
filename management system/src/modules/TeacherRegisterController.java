package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import classes.DbHandler;
import classes.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class TeacherRegisterController implements Initializable{
	@FXML
    private JFXTextField txttid;

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
    private JFXTextField txtAddress;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXRadioButton rdMale;

      @FXML
    private JFXRadioButton rdFemale;

    @FXML
    private JFXComboBox<String> comboQualification;

    @FXML
    private JFXComboBox<String> comboDepartment;

    @FXML
    private JFXDatePicker hiredate;

    @FXML
    private JFXComboBox<String> comboType;
    @FXML
    private JFXTextArea textarea;
    @FXML
    private JFXComboBox<String> comboDesignation;
    @FXML
    private GridPane schooldetails;
    FileChooser filechooser;
    Validation validate=new Validation();
    TrayNotification tray=new TrayNotification();
    Alert alert=new Alert(AlertType.ERROR) ;
	    @FXML
	    private JFXButton btnSave;
	    DbHandler db;
	    Connection connection;
	    ResultSet result;
	    PreparedStatement pst;
	    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		fetchDepartment();
		
		
		
		ObservableList<String> education=FXCollections.observableArrayList("Post Doctrate"
				,"PhD","MS");						
		comboQualification.setItems(education);
		ObservableList<String> designation=FXCollections.observableArrayList("Professor"
				,"Associate Professor","Assistant Professor","Lecturer");						
		comboDesignation.setItems(designation);
		ObservableList<String> type=FXCollections.observableArrayList("Permanent Faculty"
				,"Visiting Facaulty");						
		comboType.setItems(type);
		
	}

	private int validateFields(){
		int ans=1;
		if(txttid.getText().isEmpty()|| txttid.getText().trim().isEmpty()) {
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
		
		else if(validate.ValidateName(txtFirstname.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("FIRST NAME CAN ONLY BE CHARACTER");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtLastname.getText().isEmpty()||txtLastname.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("LAST NAME FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.ValidateName(txtLastname.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("LAST NAME CAN ONLY BE CHARACTER");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(txtCNIC.getText().isEmpty()||txtCNIC.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("CNIC FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if(validate.ValidateNumber(txtCNIC.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("CNIC CAN ONLY BE NUMBER WITHOUT DASHES");
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
		
		else if(validate.ValidateNumber(txtPhone.getText())==false) {
			alert.setHeaderText("INPUT ERROR");
	    	  alert.setContentText("PHONE CAN ONLY BE NUMBER WITHOUT DASHES");
	    	  alert.showAndWait();
			ans=0;
		}
		
		else if(txtAddress.getText().isEmpty()||txtAddress.getText().trim().isEmpty()) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("ADDRESS FIELD IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		else if(dob.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("DATE OF BIRTH  IS EMPTY");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		else if(comboQualification.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT EDUCATION");
	    	  alert.showAndWait();
			ans=0;
			
		}
		
		else if(comboDepartment.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT DEPARTMENT");
	    	  alert.showAndWait();
			ans=0;
		}
		
		else if(comboDesignation.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT DESIGNATION");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if (comboType.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT TYPE");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else if (hiredate.getValue()==null) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText("PLEASE SELECT HIRE DATE");
	    	  alert.showAndWait();
			ans=0;
			
		}
		else {
			
			ans=1;
		}
		
		return ans;
	};
	
	
	
	
	
	
	 @FXML
	    public void clearFields() {
	    	
	    	txtFirstname.clear();
	    	txtLastname.clear();
	    	txtEmail.clear();
	    	txtPhone.clear();
	    	txtCNIC.clear();
	    	dob.setValue(null);
	    
	    	comboDesignation.setValue(null);
	    	comboQualification.setValue(null);
	    	hiredate.setValue(null);
	    	txtAddress.clear();
	    	
	    	comboDepartment.setValue(null);
	    	
	     
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
	    				alert.setHeaderText("Operation Failed");
	    		    	  alert.setContentText(e.toString());
	    		    	  alert.showAndWait();
	    			}
	    			
	    		}
	
	 @FXML
	    private void searchTeacher() {
		 clearFields();
		 if(txttid.getText().trim().isEmpty()){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("WARNING");
			 alert.setContentText("PLEASE ENTER TEACHER ID");
			 alert.show();
		 }else{
	    	
	   	   try {
	   		   
	   	   connection=db.getConnection();
	   	   String sql="SELECT * from teacher where teacherid='"+txttid.getText().toString()+"'";
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
	   	   java.sql.Date dateofBirth=result.getDate("teacherdob");
	  		LocalDate date=dateofBirth.toLocalDate();
	  		dob.setValue(date);
	  		dateofBirth=result.getDate("teacherhiredate");
	  		date=dateofBirth.toLocalDate();
	  		hiredate.setValue(date);
	  		String gender=result.getString("teachersex");
	  		if(gender.equals("MALE")) {
	  			rdMale.setSelected(true);
	  		}else {
	  			rdFemale.setSelected(true);
	  		}
	           txtFirstname.setText(result.getString("teacherfname"));
	   		   txtLastname.setText(result.getString("teacherlname"));
	   		   txtEmail.setText(result.getString("teacheremail"));
	   		   txtPhone.setText(result.getString("teacherphone"));
	   		   txtAddress.setText(result.getString("teacheraddress"));
	   		  	txtCNIC.setText(result.getString("teachercnic"));
	   		    comboQualification.setValue(result.getString("teachereducation"));
	   		 comboDepartment.setValue(result.getString("teacherdepartment"));
	   		    comboDesignation.setValue(result.getString("teacherdesignation"));
	            comboType.setValue(result.getString("teachertype"));

   			} 
   			while (result.next());
   	}}

	   	   catch(Exception e) {
	   		alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
	   	   }}
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
	private void saveTeacher() {
		
		 if(searchTeacherExistence()==true){
			  alert.setHeaderText("OPERATION FAILED");
	    	  alert.setContentText("RECORD ALREADY EXISTS AGAINST ENTERED ID");
	    	  alert.showAndWait();
		  }
		 else if(validateFields()==1 && searchTeacherExistence()==false) {
		 
		try {
			connection=db.getConnection();
			 String sql="INSERT INTO teacher (teacherid,teacherfname,teacherlname,"
	    			   + "teacheremail,teacheraddress,teachercnic,teacherphone,teachersex,teacherdepartment,"
	       	   		+ "teacherdob,teachereducation,teacherdesignation,teachertype,teacherhiredate)"
	    	   		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst=connection.prepareStatement(sql);
			  pst.setString(1, txttid.getText().toString());
	    	   pst.setString(2,txtFirstname.getText().toString());
	    	   pst.setString(3,txtLastname.getText().toString());
	    	   pst.setString(4,txtEmail.getText().toString());
	    	   pst.setString(5,txtAddress.getText().toString());
	    	   pst.setString(6,txtCNIC.getText().toString()); 
	    	   pst.setString(7,txtPhone.getText().toString());
	    	   pst.setString(8,getGender());
	    	   pst.setString(9,comboDepartment.getValue().toString());
	    	   pst.setDate(10,Date.valueOf(dob.getValue()));
	    	   pst.setString(11,comboQualification.getValue().toString());
	    	   pst.setString(12,comboDesignation.getValue().toString());
	    	   pst.setString(13,comboType.getValue().toString());
	    	   pst.setString(14,hiredate.getValue().toString());
	    	 int success=pst.executeUpdate();
	    	 
	    	 if(success==1) {
	    		 clearFields();
	    		 tray.setTray("SUCCESS", "TEACHER'S RECORD SAVED SUCCESSFULLY",
	    					new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	                tray.showAndDismiss(Duration.seconds(2)); 
	    		 
	    	 }
	    	
		}catch(Exception e) {
			
			System.out.print(e);
			
		}
		 }
		
		
	}
	 @FXML 
	    private void updateTeacher() {
		  if(searchTeacherExistence()==false){
			  alert.setHeaderText("OPERATION FAILED");
	    	  alert.setContentText("RECORD DOESN'T EXISTS AGAINST ID");
	    	  alert.showAndWait();
		  }
		  else if(validateFields()==1 && searchTeacherExistence()==true) {
		 
	    	 String id=txttid.getText().toString();
	  	   Integer id2=Integer.parseInt(id);
	  	   int id3=id2.intValue();
	  	   try {
	  	   connection=db.getConnection();
	  	   String sql="UPDATE teacher SET teacherfname=?,"
	  	   		+ "teacherlname=?,teacheremail=?,teacheraddress=?,teachercnic=?,"
	  	   		+ "teachersex=?,teacherdob=?,teachereducation=?,teacherdepartment=?,"
	  	   		+ "teacherphone=?,teacherdesignation=?,teachertype=?,teacherhiredate=?"
	  	   		+ "where teacherid='"+txttid.getText().toString()+"'"; 
	  	   
	  	   pst=connection.prepareStatement(sql);
	  	   pst.setString(1, txtFirstname.getText().toString());
	  	   pst.setString(2, txtLastname.getText().toString());
	  	   pst.setString(3, txtEmail.getText().toString());	  	   
	  	   pst.setString(4, txtAddress.getText().toString());
	  	   pst.setString(5, txtCNIC.getText().toString());
	  	   pst.setString(6, getGender());
	  	 pst.setDate(7, Date.valueOf(dob.getValue()));
	  	pst.setString(8, comboQualification.getValue());
	  	 pst.setString(9, comboDepartment.getValue());
	  	 pst.setString(10, txtPhone.getText().toString());
	  	 pst.setString(11, comboDesignation.getValue());
	  	 pst.setString(12, comboType.getValue());
	  	 pst.setDate(13, Date.valueOf(hiredate.getValue()));
	  	   
	  	   int success=pst.executeUpdate();
	  	   if(success==1) {
	  		 clearFields();
			 tray.setTray("SUCCESS", "TEACHER'S RECORD UPDATED SUCCESSFULLY",
						new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.SLIDE);
	            tray.showAndDismiss(Duration.seconds(2));    
	  	   }
	  	   
	  	   }
	  	   catch(Exception e) {
	  		 alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
	  	   }
	  	   
		 }
	    }
	 
	  @FXML
	    private void deleteTeacher() {
		  if(txttid.getText().isEmpty()||txttid.getText().trim().isEmpty()){
				 alert.setAlertType(AlertType.WARNING);
				 alert.setHeaderText("WARNING");
				 alert.setContentText("PLEASE ENTER TEACHER ID");
				 alert.show();
			 }
		  
		  else{
		  		
			try {
				connection=db.getConnection();
				
				String sql="DELETE FROM teacher where teacherid='"+txttid.getText().toString()+"'";
				
				pst=connection.prepareStatement(sql);
				int success=pst.executeUpdate();
				if(success==1) {
					clearFields();
					 tray.setTray("SUCCESS", "TEACHER DELETED SUCCESSFULLY",
								new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.SLIDE);
			            tray.showAndDismiss(Duration.seconds(2));
			        
			        	
				}
				
			} catch (Exception e) 
			{
				alert.setHeaderText("Operation Failed");
		    	  alert.setContentText(e.toString());
		    	  alert.showAndWait();
			}
			 }
	    }
	    
	  @FXML
	    private boolean searchTeacherExistence() {
		    boolean ans= true;
		 if(txttid.getText().trim().isEmpty()){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("WARNING");
			 alert.setContentText("PLEASE ENTER TEACHER ID");
			 alert.show();
		 }else{
	    	
	   	   try {
	  
	   	   connection=db.getConnection();
	   	   String sql="SELECT * from teacher where teacherid='"+txttid.getText().toString()+"'";
	   	   pst=connection.prepareStatement(sql);
	   	   result=pst.executeQuery();
	   	   
	   	if (result.next() == false) 
	   	{ 
	   		ans=false;
	   	 alert.setAlertType(AlertType.WARNING);
		 alert.setHeaderText("EMPTY");
		 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
		 alert.show();
		 }
	   	}   	
	   
	   	   catch(Exception e) {
	   		System.out.println(e);
	   	   }}
		return ans;
	    }
	

	 
	
	
}
