package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import classes.Attendance;
import classes.DbHandler;
import classes.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class AttendanceController implements Initializable{
	  @FXML
	    private JFXTextField txtsid;

	    @FXML
	    private JFXTextField txtsname;

	    @FXML
	    private JFXTextField txtdepname;

	    @FXML
	    private JFXTextField txtclassname;

	    @FXML
	    private JFXTextField txtsession;

	    @FXML
	    private JFXComboBox<Integer> combosemester;

	    @FXML
	    private JFXTextField txtmarks1;

	    @FXML
	    private JFXTextField txtmarks2;

	    @FXML
	    private JFXTextField txtmarks3;

	    @FXML
	    private JFXTextField txtmarks4;

	    @FXML
	    private JFXTextField txtmarks5;

	    @FXML
	    private JFXTextField txtmarks6;

	    @FXML
	    private JFXTextField txtsub1;

	    @FXML
	    private JFXTextField txtsub2;

	    @FXML
	    private JFXTextField txtsub3;

	    @FXML
	    private JFXTextField txtsub4;

	    @FXML
	    private JFXTextField txtsub5;

	    @FXML
	    private JFXTextField txtsub6;

	    @FXML
	    private JFXTextField txtsubname1;

	    @FXML
	    private JFXTextField txtsubname2;

	    @FXML
	    private JFXTextField txtsubname3;

	    @FXML
	    private JFXTextField txtsubname4;

	    @FXML
	    private JFXTextField txtsubname5;

	    @FXML
	    private JFXTextField txtsubname6;

	    @FXML
	    private JFXButton btnClear;

	    @FXML
	    private JFXButton btnEdit;
	    @FXML
	    private JFXComboBox<String> comboTerm;

	    @FXML
	    private JFXButton btnSave;
	    Validation validate=new Validation();
	    Alert alert=new Alert(AlertType.ERROR) ;
	    TrayNotification tray=new TrayNotification();
	    DbHandler db;
	    Connection connection;
	    PreparedStatement pst;
	    ResultSet result;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		
		ObservableList<String> term=FXCollections.observableArrayList("Mid Term","Final Term");
		comboTerm.setItems(term);
		combosemester.getSelectionModel().selectedItemProperty().addListener((v, old_val,new_val)->{
			
			clearSubjectFields();
		      getSubjects(combosemester.getValue());
		});
		
	}
	
	/*used to clear subject fields only*/
	private void clearSubjectFields() {
		txtsub1.clear();
		txtsub2.clear();
		txtsub3.clear();
		txtsub4.clear();
		txtsub5.clear();
		txtsub6.clear();
		txtsubname1.clear();
		txtsubname2.clear();
		txtsubname3.clear();
		txtsubname4.clear();
		txtsubname5.clear();
		txtsubname6.clear();
		
	}
	@FXML
	private void clearFields(){
		clearSubjectFields();
		txtsid.clear();
		txtsname.clear();
		txtdepname.clear();
		txtclassname.clear();
		txtsession.clear();
		combosemester.setValue(null);
		txtsub1.clear();
		txtsub2.clear();
		txtsub3.clear();
		txtsub4.clear();
		txtsub5.clear();
		txtsub6.clear();
		txtmarks1.clear();
		txtmarks2.clear();
		txtmarks3.clear();
		txtmarks4.clear();
		txtmarks5.clear();
		txtmarks6.clear();
		comboTerm.setValue(null);
	}
	@FXML
	private void searchStudent(){
		
		
		try {
			
			connection=db.getConnection();
			String sql="SELECT s.stufname,s.stulname,d.depname,cl.classname,s.stusession FROM student s"
					+ " join department d on s.depid=d.depid join class cl on s.classid=cl.classid where s.stuid='"+txtsid.getText().toString()+"'";
			pst=connection.prepareStatement(sql);
			
			result=pst.executeQuery();
			while(result.next()) {
				txtsname.setText(result.getString("stufname")+" "+result.getString("stulname"));
				txtdepname.setText(result.getString("depname"));
				txtclassname.setText(result.getString("classname"));
				txtsession.setText(result.getString("stusession"));
				
				
			}
			setSemesters();
			
		}catch(Exception e) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
		}
		
		
	}
	
	
	private  int fetchSemesters(){
		String val=txtclassname.getText().toString();
		int ans=0;
        try {
			connection=db.getConnection();
		   
			String sql="SELECT semesters from class where classname=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, val);
			result=pst.executeQuery();
			while(result.next()) {
				ans=result.getInt(1);
				System.out.println(ans);
				
				}
			}
		catch(Exception e) {
			alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
		}
		return ans;
		
	}
   @FXML
	private void setSemesters() {
		
		
		int val=fetchSemesters();
    	ObservableList<Integer> sem=FXCollections.observableArrayList();
		
    	if(val==10) {
    		while(val>=1) {
    			
    			sem.add(val);
     			val--;
    			
    			}
    		combosemester.setItems(sem);
        	
    		
    	}else if(val==9) {
            while(val>=1) {
    			
 			sem.add(val);
 			
 			val--;
 			
 			}
 		combosemester.setItems(sem);
 		
 	}else if(val==8) {
        while(val>=1) {
			
			sem.add(val);
			
			val--;
			
			}
		combosemester.setItems(sem);
		
	}
    	else if(val==7) {
               while(val>=1) {
    			
    			sem.add(val);
    			
    			val--;
    			
    			}
    		combosemester.setItems(sem);
    		
    	}
    	else if(val==6) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==5) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			System.out.println(val);
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==4)
    	{
    		 while(val>=1) {
     			
     			sem.add(val);
     			
     			val--;
     			
     			}
     		combosemester.setItems(sem);	
    	}
        else if(val==3) {
        	 while(val>=1) {
     			
     			sem.add(val);
     			
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==2) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==1) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			
     			val--;
     			
     			}
    		 combosemester.setItems(sem);
    		
    	}
    	else 
    	{
    		
    	}
    		
    }
   
   /*Save RESULT IN DATABASE*/
   @FXML
   private void saveAttendance() {
	            
	       if(validateSubjectFields()==true){
	           int success=0;
	           String sql;
	           int sem=combosemester.getValue();
	           
	            try {            	
	            	for(int i=1;i<=6;i++) {
	            		
	            		switch(i) {
	            		case 1:
	            			
	            		sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values(?,?,?,?,?,?)";                     
	    	            pst=connection.prepareStatement(sql);
	    	            pst.setString(1, txtsid.getText());
	    	            pst.setString(2, txtsub1.getText());
	    	            pst.setString(3, txtmarks1.getText());
	    	            pst.setInt(4, combosemester.getValue());
	    	            pst.setString(5, txtsession.getText());
	    	            pst.setString(6, comboTerm.getValue());
	    	            
	            		success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("1 subject");
	            		}
	            		break;
	            		case 2:
	            			
	            			sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values (?,?,?,?,?,?)";                     
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	              	pst.setString(1, txtsid.getText());
		    	            pst.setString(2, txtsub2.getText());
		    	            pst.setString(3, txtmarks2.getText());
		    	            pst.setInt(4, combosemester.getValue());
		    	            pst.setString(5, txtsession.getText());
		    	            pst.setString(6, comboTerm.getValue());
		    	            
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("2 subject");
	            		}
	            		break;
                         case 3:
	            			
	            			sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values (?,?,?,?,?,?)";                     
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	              	pst.setString(1, txtsid.getText());
		    	            pst.setString(2, txtsub3.getText());
		    	            pst.setString(3, txtmarks3.getText());
		    	            pst.setInt(4, combosemester.getValue());
		    	            pst.setString(5, txtsession.getText());
		    	            pst.setString(6, comboTerm.getValue());
		    	            
	            		   success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("3 subject");
	            		}
	            		break;
	            		case 4:
	            			
	            			sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values (?,?,?,?,?,?)";                     
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	              	pst.setString(1, txtsid.getText());
		    	            pst.setString(2, txtsub4.getText());
		    	            pst.setString(3, txtmarks4.getText());
		    	            pst.setInt(4, combosemester.getValue());
		    	            pst.setString(5, txtsession.getText());
		    	            pst.setString(6, comboTerm.getValue());
		    	            
	    	              	
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("4 subject");
	            		}
	            		break;
                          case 5:
	            			
	            			sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values (?,?,?,?,?,?)";                     
	    	              	pst=connection.prepareStatement(sql);
	    	              	pst.setString(1, txtsid.getText());
		    	            pst.setString(2, txtsub5.getText());
		    	            pst.setString(3, txtmarks5.getText());
		    	            pst.setInt(4, combosemester.getValue());
		    	            pst.setString(5, txtsession.getText());
		    	            pst.setString(6, comboTerm.getValue());
		    	            
	            		 success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("5 subject");
	            		}
	            		break;
	            		case 6:
	            		
	            			sql="INSERT INTO attendance (stuid,courseid,attendance,semester,session,term) values (?,?,?,?,?,?)";                     
	    	              	pst=connection.prepareStatement(sql);
	    	              	pst.setString(1, txtsid.getText());
		    	            pst.setString(2, txtsub6.getText());
		    	            pst.setString(3, txtmarks6.getText());
		    	            pst.setInt(4, combosemester.getValue());
		    	            pst.setString(5, txtsession.getText());
		    	            pst.setString(6, comboTerm.getValue());
		    	            
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			clearFields();
	            			 tray.setTray("SUCCESS", "ATTENDANCE SAVED",
	     							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	     		            tray.showAndDismiss(Duration.seconds(2));
	            			
	            		}
	            		break;
	            		}	
	            	}
	            	
	            }catch(Exception e) {
	            	alert.setHeaderText("Operation Failed");
	  	    	  alert.setContentText(e.toString());
	  	    	  alert.showAndWait();
	            	
	  	    	  System.out.println(e);
	            }}
	   	   
   }
   
   
   
   
   
   
   
   @FXML
   private void updateAttendance(){
	   if(searchAttendanceExistence() ==false){
		   alert.setHeaderText("RECORD NOT FOUND");
    	   alert.setContentText("NO RECORD EXISTS TO BE UPDATE");
    	   alert.show();
	   }
	   else if(validateSubjectFields()&&searchAttendanceExistence() ==true){
		   int success=0;
           String sql;
           int sem=combosemester.getValue();
           
            try {            	
            	for(int i=1;i<=6;i++) {
            		
            		switch(i) {
            		case 1:
            			
            			sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
    	              	pst=connection.prepareStatement(sql);
    	              	pst.setString(1, txtmarks1.getText());
    	              	
    	              	pst.setString(2, txtsid.getText());
    	              	pst.setString(3, txtsub1.getText());
	    	            
	    	            pst.setInt(4, combosemester.getValue());
	    	           
	    	            pst.setString(5, comboTerm.getValue());
	    	            pst.setString(6, txtsession.getText());
    	            
            		success=pst.executeUpdate();
            		if(success==1) {
            			System.out.println("1 subject");
            		}
            		break;
            		case 2:
            			
            			sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
    	              	pst=connection.prepareStatement(sql);
    	              	pst.setString(1, txtmarks2.getText());
    	              	
    	              	pst.setString(2, txtsid.getText());
    	              	pst.setString(3, txtsub2.getText());
	    	            
	    	            pst.setInt(4, combosemester.getValue());
	    	           
	    	            pst.setString(5, comboTerm.getValue());
	    	            pst.setString(6, txtsession.getText());
	    	            
            	       success=pst.executeUpdate();
            		if(success==1) {
            			System.out.println("2 subject");
            		}
            		break;
                     case 3:
            			
                    	 sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
     	              	pst=connection.prepareStatement(sql);
     	              	pst.setString(1, txtmarks3.getText());
     	              	
     	              	pst.setString(2, txtsid.getText());
     	              	pst.setString(3, txtsub3.getText());
 	    	            
 	    	            pst.setInt(4, combosemester.getValue());
 	    	           
 	    	            pst.setString(5, comboTerm.getValue());
 	    	            pst.setString(6, txtsession.getText());
	    	            
            		   success=pst.executeUpdate();
            		if(success==1) {
            			System.out.println("3 subject");
            		}
            		break;
            		case 4:
            			
            			sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
    	              	pst=connection.prepareStatement(sql);
    	              	pst.setString(1, txtmarks4.getText());
    	              	
    	              	pst.setString(2, txtsid.getText());
    	              	pst.setString(3, txtsub4.getText());
	    	            
	    	            pst.setInt(4, combosemester.getValue());
	    	           
	    	            pst.setString(5, comboTerm.getValue());
	    	            pst.setString(6, txtsession.getText());
	    	            
    	              	
            	       success=pst.executeUpdate();
            		if(success==1) {
            			System.out.println("4 subject");
            		}
            		break;
                      case 5:
            			
                    	  sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
      	              	pst=connection.prepareStatement(sql);
      	              	pst.setString(1, txtmarks5.getText());
      	              	
      	              	pst.setString(2, txtsid.getText());
      	              	pst.setString(3, txtsub5.getText());
  	    	            
  	    	            pst.setInt(4, combosemester.getValue());
  	    	           
  	    	            pst.setString(5, comboTerm.getValue());
  	    	            pst.setString(6, txtsession.getText());
	    	            
            		 success=pst.executeUpdate();
            		if(success==1) {
            			System.out.println("5 subject");
            		}
            		break;
            		case 6:
            		
            			sql="UPDATE  attendance SET attendance=? where stuid=? and courseid=? and semester=? and term=? and session=?";                    
    	              	pst=connection.prepareStatement(sql);
    	              	pst.setString(1, txtmarks6.getText());
    	              	
    	              	pst.setString(2, txtsid.getText());
    	              	pst.setString(3, txtsub6.getText());
	    	            
	    	            pst.setInt(4, combosemester.getValue());
	    	           
	    	            pst.setString(5, comboTerm.getValue());
	    	            pst.setString(6, txtsession.getText());
	    	            
            	       success=pst.executeUpdate();
            		if(success==1) {
            			clearFields();
            			 tray.setTray("SUCCESS", "ATTENDANCE UPDATED",
     							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
     		            tray.showAndDismiss(Duration.seconds(2));
            			
            		}
            		break;
            		}	
            	}
            	
            }catch(Exception e) {
            	            	
  	    	  System.out.println(e);
            }}
		   
	   }
	   
   
   
   
   
   
  
   /*used to fetch subjects from table*/
   private void  getSubjects(int val) {
	 
             try {
            	 connection=db.getConnection();
            	 
            	 String sql="select cm.courseid,c.coursename from "
            	 		+ "coursemapping cm join course c on cm.courseid=c.courseid "
            	 		+ "join class cl on cm.classid=cl.classid join department d on cm.depid=d.depid"
            	 		+ " where cl.classname='"+ txtclassname.getText().toString() +"' and d.depname='"+txtdepname.getText().toString()+"' and semester="+val
            	 		+" and cm.session='"+txtsession.getText().toString()+"'";
                            	
            	 pst=connection.prepareStatement(sql);
            	 result=pst.executeQuery();
            	int value=1;
            	 while(result.next()) {
            		 
                 switch (value) {
            		       case 1:
            		    	   txtsub1.setText(result.getString(1));
            		    	   txtsubname1.setText(result.getString(2));
            		    	   value++;
            			   break;
            		       case 2:
            		    	   txtsub2.setText(result.getString(1));
            		    	   txtsubname2.setText(result.getString(2));
            		    	   value++; 
            		       break;
            		       case 3:
            		    	   txtsub3.setText(result.getString(1));
            		    	   txtsubname3.setText(result.getString(2));
            		    	   value++;
                		    break;
                		    case 4:
                		    	 txtsub4.setText(result.getString(1));
              		    	   txtsubname4.setText(result.getString(2));
              		    	   value++;
                		    break;
                		    case 5:
                		    	 txtsub5.setText(result.getString(1));
              		    	   txtsubname5.setText(result.getString(2));
              		    	   value++;
                    		break;
                    		case 6:
                    			 txtsub6.setText(result.getString(1));
              		    	   txtsubname6.setText(result.getString(2));
              		    	   value++;
                    		break;
            		 } 
            	 }
            	 
             }catch(Exception e)
             {
            	 alert.setHeaderText("Operation Failed");
   	    	  alert.setContentText(e.toString());
   	    	  alert.showAndWait();
             }
	   
   }
   @FXML
   private void deleteAttendance(){
	       if(txtsid.getText().isEmpty()||txtsid.getText().trim().isEmpty()){
	    	   alert.setHeaderText("WARNING");
	    	   alert.setContentText("PLEASE ENTER STUSENT ID FIRST");
	    	   alert.show();
	    	   
	       }
	       else{
	   
	   try{
	        	connection=db.getConnection();
	        	String sql="DELETE from attendance where stuid="+txtsid.getText()+" and semester=? and session=? and term=?";
	        	pst=connection.prepareStatement(sql);
	        	
	        	pst.setInt(1, combosemester.getValue());
	        	pst.setString(2, txtsession.getText());
	        	pst.setString(3, comboTerm.getValue());
	        	int success=pst.executeUpdate();
	        	System.out.println("i m in dle after execute");
	        	if(success==1){
	        	System.out.println("i m in dle succes");	
	        	clearFields();
	      		tray.setTray("SUCCESS", "ATTENDANCE DELETED",new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	   	            tray.showAndDismiss(Duration.seconds(2));
	   	            
	        	}
	        }catch(Exception e){
	        	System.out.println(e);
	        }
	       }
   }
   
   @FXML
	  private boolean searchAttendanceExistence() {
		      boolean ans=true;
	            if(txtsid.getText().trim().isEmpty()){
	            	alert.setAlertType(AlertType.WARNING);
	   			 alert.setHeaderText("Operation Failed");
	   		   	  alert.setContentText("DSTUDENT ID is empty");
	   		   	  alert.showAndWait();
	            	
	            }
	            else if(combosemester.getValue()==null){
	            	alert.setAlertType(AlertType.WARNING);
		   			 alert.setHeaderText("Operation Failed");
		   		   	  alert.setContentText("PLEASE SELECT SEMESTER");
		   		   	  alert.showAndWait();
	            }
	            else if(comboTerm.getValue()==null){
	            	alert.setAlertType(AlertType.WARNING);
		   			 alert.setHeaderText("Operation Failed");
		   		   	  alert.setContentText("PLEASE SELECT TERM");
		   		   	  alert.showAndWait();
	            }
	            else{
	        
	        try {
	            connection = db.getConnection();
	            
	        	String sql="SELECT attendance from attendance where stuid='"+txtsid.getText()+"' and semester="+combosemester.getValue()+" and term='"+comboTerm.getValue()+"'";
	    					        
	        	pst=connection.prepareStatement(sql);
          
	            result =pst.executeQuery(); 
	          
	            	
	            	if (result.next() == false) 
	    		   	{ 
	    		   	ans=false;	
	    		   	 
	    			 }
	    		   	
	    		   	            
	             } catch (SQLException ex) {
	                  System.out.println(ex);
	        }}
				return ans;
	    }
	
   
   
   @FXML
	  private void searchAttendance() {
		      
	            if(txtsid.getText().trim().isEmpty()){
	            	alert.setAlertType(AlertType.WARNING);
	   			 alert.setHeaderText("Operation Failed");
	   		   	  alert.setContentText("DSTUDENT ID is empty");
	   		   	  alert.showAndWait();
	            	
	            }
	            else if(combosemester.getValue()==null){
	            	alert.setAlertType(AlertType.WARNING);
		   			 alert.setHeaderText("Operation Failed");
		   		   	  alert.setContentText("PLEASE SELECT SEMESTER");
		   		   	  alert.showAndWait();
	            }
	            else if(comboTerm.getValue()==null){
	            	alert.setAlertType(AlertType.WARNING);
		   			 alert.setHeaderText("Operation Failed");
		   		   	  alert.setContentText("PLEASE SELECT TERM");
		   		   	  alert.showAndWait();
	            }
	            else{
	        
	        try {
	            connection = db.getConnection();
	            
	        	String sql="SELECT attendance from attendance where stuid='"+txtsid.getText()+"' and semester="+combosemester.getValue()+" and term='"+comboTerm.getValue()+"'";
	    					        
	        	pst=connection.prepareStatement(sql);
             
	            result =pst.executeQuery(); 
	          
	            	
	            	if (result.next() == false) 
	    		   	{ 
	    		   		
	    		   	 alert.setAlertType(AlertType.WARNING);
	    			 alert.setHeaderText("EMPTY");
	    			 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
	    			 alert.show();
	    			 }
	    		   	
	    		   	else {
	    		   		int i=1;
	    		   		do {
	    		   			
	    		   			switch(i){
	            		 case 1:
	            			 txtmarks1.setText(""+result.getInt("attendance"));
	            			 i++;
	            			 break;
	            		 case 2:
	            			 txtmarks2.setText(""+result.getInt("attendance"));
	            			 i++;
	            			 break;
	            		 case 3:
	            			 txtmarks3.setText(""+result.getInt("attendance"));
		            			 i++;
		            			 break;
		            		 case 4:
		            			 txtmarks4.setText(""+result.getInt("attendance"));
		            			 i++;
		            			 break;
		            		 case 5:
		            			 txtmarks5.setText(""+result.getInt("attendance"));
			            			 i++;
			            			 break;
			            		 case 6:
			            			 txtmarks6.setText(""+result.getInt("attendance"));
			            			 i++;
			            			 
			            			 break;
			            			
	            		 }
	            	 
	             }while(result.next());
	    		   		}
	            
	             } catch (SQLException ex) {
	                  System.out.println(ex);
	        }}
	    }
	
   
   
   
   private boolean validateSubjectFields(){
	   boolean ans=false;
	   if(txtsub1.getText().isEmpty()||txtmarks1.getText().isEmpty()||txtmarks1.getText().trim().isEmpty()){
		  System.out.println("im in if subj1");
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
		   ans=false;
	   }
	   else if(validate.ValidateNumber(txtmarks1.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 ATTENDANCE");
	   alert.show();
            }

	   
	   else if(txtsub2.getText().isEmpty()||txtmarks2.getText().isEmpty()||txtmarks2.getText().trim().isEmpty()){
		   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmarks2.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 ATTENDANCE");
	   alert.show();
           }
	   
	   else if(txtsub3.getText().isEmpty()||txtmarks3.getText().isEmpty()||txtmarks3.getText().trim().isEmpty()){
		   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmarks3.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 ATTENDANCE");
	   alert.show();
           }
	   else if(txtsub4.getText().isEmpty()||txtmarks4.getText().isEmpty()||txtmarks4.getText().trim().isEmpty()){
		   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmarks4.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 ATTENDANCE");
	   alert.show();
           }
	   else if(txtsub5.getText().isEmpty()||txtmarks5.getText().isEmpty()||txtmarks5.getText().trim().isEmpty()){
		   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmarks5.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 ATTENDANCE");
	   alert.show();
           }
	   else if(txtsub6.getText().isEmpty()||txtmarks6.getText().isEmpty()||txtmarks6.getText().trim().isEmpty()){
		   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or ATTENDANCE FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmarks6.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 ATTENDANCE");
	   alert.show();
           }
        
         else {
	               ans=true;
           }
	   
	   
	   return ans;
   }
   
   
   
   
   
   
   
   
   
   
   
   

}
