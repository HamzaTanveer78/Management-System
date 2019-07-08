/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import classes.Student;
import classes.Validation;
import classes.DbHandler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;


public class StudentProfileController implements Initializable {
	@FXML
    private TableView<Student> studenttable;

    @FXML
    private TableColumn<Student, String> idcol;

    @FXML
    private TableColumn<Student, String> namecol;

    @FXML
    private TableColumn<Student, String> emailcol;

    @FXML
    private TableColumn<Student, String> contactcol;

    @FXML
    private TableColumn<Student, String> cniccol;

    @FXML
    private TableColumn<Student, String> gendercol;

    @FXML
    private TableColumn<Student, String> dobcol;

    @FXML
    private TableColumn<Student, String> fathernamecol;

    @FXML
    private TableColumn<Student, String> foccuptioncol;

    @FXML
    private TableColumn<Student, String> fcontactcol;

    @FXML
    private TableColumn<Student, String> regestrationcol;

    @FXML
    private TableColumn<Student, String> departmentcol;

    @FXML
    private TableColumn<Student, String> classcol;

    @FXML
    private TableColumn<Student, String> sessioncol;
   
    @FXML
    private JFXComboBox<String> combodepartment;

    @FXML
    private JFXComboBox<String> comboclass;

    @FXML
    private JFXComboBox<String> combosemester;

    @FXML
    private JFXComboBox<String> combosession;
    @FXML
    private JFXTextField searchID;

    @FXML
    private JFXButton btnSearch;
    
    DbHandler db;
    Connection connection;
    ResultSet result;
    PreparedStatement pst;
    Validation validate=new Validation();
    Alert alert=new Alert(AlertType.ERROR) ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                db=new DbHandler();
    	
                combodepartment.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
       			 
      			  int id=fetchDepID(combodepartment.getValue());
      		      fetchClass(id);
      			  
      		  });
      		   
      		    comboclass.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
      				 
      				 
      			      fetchSession();
      			  });
      		    
      		    
      		    fetchDepartment();
      	    
                
    	    buildStudentTable();
    	    showStudentRecord();
       
    }
    
    @FXML
    private void buildStudentTable() {
    	
    	 idcol.setCellValueFactory(new PropertyValueFactory<Student,String>("stuid"));

        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

         emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

         contactcol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        cniccol.setCellValueFactory(new PropertyValueFactory<>("cnic"));

       gendercol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        dobcol.setCellValueFactory(new PropertyValueFactory<>("dob"));

        fathernamecol.setCellValueFactory(new PropertyValueFactory<>("fathername"));

         foccuptioncol.setCellValueFactory(new PropertyValueFactory<>("fatheroccuption"));

        fcontactcol.setCellValueFactory(new PropertyValueFactory<>("fathercontact"));

         regestrationcol.setCellValueFactory(new PropertyValueFactory<>("regestrationdate"));

        departmentcol.setCellValueFactory(new PropertyValueFactory<>("depname"));

        classcol.setCellValueFactory(new PropertyValueFactory<>("classname"));

         sessioncol.setCellValueFactory(new PropertyValueFactory<>("session"));
    	
    	
    	
    	
    }
    
    @FXML
    private void showStudentRecord() {
    	  studenttable.getItems().clear();
    	try {
    		connection=db.getConnection();
    		String sql="SELECT s.stuid, s.stufname, s.stulname, s.stuaddress,s.stuemail,s.stuPhone,s.stucnic,s.studob,"
    				+ "s.stugender,s.stufathername,s.stufatherphone,s.stufatheroccuption,s.sturegestration,"
    				+ "d.depname,cl.classname,s.stusession from student s join department d on"
    				+ " s.depid=d.depid join class cl on s.classid=cl.classid"
    				+ " where d.depname='"+combodepartment.getValue()+"' and cl.classname='"+
    				comboclass.getValue()+"' and s.stusession='"+combosession.getValue()+"'";
        
    		pst=connection.prepareStatement(sql);
    		result=pst.executeQuery();
    		while(result.next()) {
           Student student=new Student();
           
           student.setStuid((result.getString(1)));
          
           student.setName(result.getString(2)+" "+result.getString(3));
           student.setAddress(result.getString("stuaddress")); 
           student.setEmail(result.getString("stuemail"));
           student.setPhone(result.getString("stuPhone"));
           student.setCnic(result.getString("stucnic"));
           student.setDob(result.getString("studob"));
           student.setGender(result.getString("stugender"));
           student.setFathername(result.getString("stufathername"));
           student.setFathercontact(result.getString("stufatherphone"));
           student.setFatheroccuption(result.getString("stufatheroccuption"));
           student.setRegestrationdate(result.getString("sturegestration"));
           student.setDepname(result.getString("depname"));
           student.setClassname(result.getString("classname"));
           student.setSession(result.getString("stusession"));
           
    		studenttable.getItems().add(student);	
    			
    		}
    				
    		
    		
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    	
    	
    }

    @FXML 
    private void searchStudent() {
  	  studenttable.getItems().clear();
  	  if(searchID.getText().trim().isEmpty()){
  		 alert.setHeaderText("EMPTY");
		 alert.setContentText("PLEASE ENTER ID");
		 alert.show();
  		  
  	  }
  	  else
  	  {
  	
  	try {
  		connection=db.getConnection();
  		String sql="SELECT s.stuid, s.stufname, s.stulname, s.stuaddress,s.stuzip,s.stuemail,s.stuPhone,s.stucnic,s.studob,"
  				+ "s.stugender,s.stufathername,s.stufatherphone,s.stufatheroccuption,s.sturegestration,"
  				+ "d.depname,cl.classname,s.stusession from student s join department d on"
  				+ " s.depid=d.depid join class cl on s.classid=cl.classid where s.stuid='"+searchID.getText().toString()+"'";
  		pst=connection.prepareStatement(sql);
  		
  		  		
  	
  		result=pst.executeQuery();
  		
  		if (result.next() == false) 
  	   	{ 
  	   	 alert.setAlertType(AlertType.WARNING);
  		 alert.setHeaderText("RECORD NOT FOUND");
  		 alert.setContentText(" NO RECORD EXISTS AGAINST ENTER ID");
  		 alert.show();
  		 }
  	   	
  	   	else {
  	   		do {
         Student student=new Student();
         student.setStuid((result.getString(1)));
         student.setName(result.getString(2)+" "+result.getString(3));
        
        student.setAddress(result.getString("stuaddress")); 
        student.setEmail(result.getString("stuemail"));
        student.setPhone(result.getString("stuPhone"));
        student.setCnic(result.getString("stucnic"));
        student.setDob(result.getString("studob"));
         
        
         student.setAddress(result.getString("stuaddress"));  
        
         student.setGender(result.getString("stugender"));
         
         student.setFathername(result.getString("stufathername"));
         student.setFathercontact(result.getString("stufatherphone"));
         student.setFatheroccuption(result.getString("stufatheroccuption"));
         student.setRegestrationdate(result.getString("sturegestration"));
         student.setDepname(result.getString("depname"));
         student.setClassname(result.getString("classname"));
         student.setSession(result.getString("stusession"));
         
  		studenttable.getItems().add(student);	
  			
  		}while (result.next());
  	   	}
  				
  		  		  		
  	}catch(Exception e) {
  		System.out.println(e);
  	}
  	  }
    	
    	
    }
        
private void fetchSession(){
		
		try{
			connection=db.getConnection();
			String sql="select s.stusession from student s join "
					+ "department d on s.depid=d.depid join class cl on "
					+ "s.classid=cl.classid where d.depname=? and cl.classname=? group by s.stusession";
			
			       pst=connection.prepareStatement(sql);
			        pst.setString(1, combodepartment.getValue());
			        pst.setString(2, comboclass.getValue());
			    result=pst.executeQuery();
			    
			    ObservableList<String> session=FXCollections.observableArrayList();
			     while(result.next()){
			    	session.add(result.getString("stusession"));
			    	
			    }
			    combosession.setItems(session);
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	 private int fetchDepID(String depname) {
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
	
       private void fetchClass(int val) {
		
    	       try {		
    	    	   connection=db.getConnection();
    				String sql="SELECT classname from class  where depid="+val;
    				pst.setInt(1, val);
    				pst=connection.prepareStatement(sql);
    				result=pst.executeQuery();
    				ObservableList<String> depname=FXCollections.observableArrayList();
    				while(result.next()) {
    					depname.add(result.getString(1));	
    					}
    				comboclass.setItems(depname);
    			}
    			catch(Exception e) {
    				System.out.println(e);
    			}
    			
    		}

	
	      @FXML
	    private void fetchDepartment() {
			
	    	try {		connection=db.getConnection();
	    				Statement stat=connection.createStatement();
	    				String sql="SELECT depname from department";
	    				result=stat.executeQuery(sql);
	    				ObservableList<String> depname=FXCollections.observableArrayList();
	    				while(result.next()) {
	    					depname.add(result.getString(1));	
	    					
	    					}
	    				combodepartment.setItems(depname);
	    				
	    			}
	    			catch(Exception e) {
	    				System.out.println(e);
	    			}
	    			
	    		}

    
    
    
    
}
