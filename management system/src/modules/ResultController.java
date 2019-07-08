package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

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

public class ResultController implements Initializable{
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
	    private JFXTextField txtmidmarks1;

	    @FXML
	    private JFXTextField txtmidmarks2;

	    @FXML
	    private JFXTextField txtmidmarks3;

	    @FXML
	    private JFXTextField txtmidmarks4;

	    @FXML
	    private JFXTextField txtmidmarks5;

	    @FXML
	    private JFXTextField txtmidmarks6;

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
	    private JFXTextField txtfinalmarks1;

	    @FXML
	    private JFXTextField txtfinalmarks2;

	    @FXML
	    private JFXTextField txtfinalmarks3;

	    @FXML
	    private JFXTextField txtfinalmarks4;

	    @FXML
	    private JFXTextField txtfinalmarks5;

	    @FXML
	    private JFXTextField txtfinalmarks6;

	    @FXML
	    private JFXTextField txtsessional1;

	    @FXML
	    private JFXTextField txtsessional2;

	    @FXML
	    private JFXTextField txtsessional3;

	    @FXML
	    private JFXTextField txtsessional4;

	    @FXML
	    private JFXTextField txtsessional5;

	    @FXML
	    private JFXTextField txtsessional6;
	    @FXML
	    private JFXTextField txtgpa1;

	    @FXML
	    private JFXTextField txtgpa2;

	    @FXML
	    private JFXTextField txtgpa3;

	    @FXML
	    private JFXTextField txtgpa4;

	    @FXML
	    private JFXTextField txtgpa5;

	    @FXML
	    private JFXTextField txtgpa6;


	   
	    @FXML
	    private JFXButton btnClear;

	    @FXML
	    private JFXButton btnEdit;

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
	/*used to clear all fields*/
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
		txtmidmarks1.clear();
		txtmidmarks2.clear();
		txtmidmarks3.clear();
		txtmidmarks4.clear();
		txtmidmarks5.clear();
		txtmidmarks6.clear();
		txtfinalmarks1.clear();
		txtfinalmarks2.clear();
		txtfinalmarks3.clear();
		txtfinalmarks4.clear();
		txtfinalmarks5.clear();
		txtfinalmarks6.clear();
		txtsessional1.clear();
		txtsessional2.clear();
		txtsessional3.clear();
		txtsessional4.clear();
		txtsessional5.clear();
		txtsessional6.clear();
		txtgpa1.clear();
		txtgpa2.clear();
		txtgpa3.clear();
		
		txtgpa4.clear();
		txtgpa5.clear();
		txtgpa6.clear();
		
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
    			System.out.println(val);
    			val--;
    			
    			}
    		combosemester.setItems(sem);
        	
    		
    	}else if(val==9) {
            while(val>=1) {
    			
 			sem.add(val);
 			System.out.println(val);
 			val--;
 			
 			}
 		combosemester.setItems(sem);
 		
 	}else if(val==8) {
        while(val>=1) {
			
			sem.add(val);
			System.out.println(val);
			val--;
			
			}
		combosemester.setItems(sem);
		
	}
    	else if(val==7) {
               while(val>=1) {
    			
    			sem.add(val);
    			System.out.println(val);
    			val--;
    			
    			}
    		combosemester.setItems(sem);
    		
    	}
    	else if(val==6) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			System.out.println(val);
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
     			System.out.println(val);
     			val--;
     			
     			}
     		combosemester.setItems(sem);	
    	}
        else if(val==3) {
        	 while(val>=1) {
     			
     			sem.add(val);
     			System.out.println(val);
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==2) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			System.out.println(val);
     			val--;
     			
     			}
     		combosemester.setItems(sem);
    		
    	}
    	else if(val==1) {
    		 while(val>=1) {
     			
     			sem.add(val);
     			System.out.println(val);
     			val--;
     			
     			}
    		 combosemester.setItems(sem);
    		
    	}
    	else 
    	{
    		
    	}
    		
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
      /*Save RESULT IN DATABASE*/
   @FXML
   private void saveResult() {
	   
	          if(validateSubjectFields()==true){
	           int success=0;
	           String sql;
	            try {            	
	            	for(int i=1;i<=6;i++) {
	            		
	            		switch(i) {
	            		case 1:
	            			System.out.print("before string");
	            			sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)";                  
	    	              	pst=connection.prepareStatement(sql);
	    	              	System.out.print("after pst ");
	    	              	pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub1.getText().toString());
	    	              	
	    	              	pst.setString(3, txtmidmarks1.getText().toString());
	    	            	pst.setString(4, txtfinalmarks1.getText().toString());
	    	            	pst.setString(5, txtsessional1.getText().toString());
	    	            	pst.setString(6, txtgpa1.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	    	            
	            		success=pst.executeUpdate();
	            		System.out.print("after execute ");
	            		if(success==1) {
	            			System.out.println("1 subject");
	            		}
	            		break;
	            		case 2:
	            			sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)";                   
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	            	pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub2.getText().toString());
	    	              	
	    	              	pst.setString(3, txtmidmarks2.getText().toString());
	    	            	pst.setString(4, txtfinalmarks2.getText().toString());
	    	            	pst.setString(5, txtsessional2.getText().toString());
	    	            	pst.setString(6, txtgpa2.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("2 subject");
	            		}
	            		break;
                         case 3:
	            			
	            			
                        	 sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)";                  
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	            	pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub3.getText().toString());
	    	              	
	    	              	pst.setString(3, txtmidmarks3.getText().toString());
	    	            	pst.setString(4, txtfinalmarks3.getText().toString());
	    	            	pst.setString(5, txtsessional3.getText().toString());
	    	            	pst.setString(6, txtgpa3.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	    	            
	            		   success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("3 subject");
	            		}
	            		break;
	            		case 4:
	            			
	            			sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)";                   
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	            	pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub4.getText().toString());
	    	              	
	    	              	pst.setString(3, txtmidmarks4.getText().toString());
	    	            	pst.setString(4, txtfinalmarks4.getText().toString());
	    	            	pst.setString(5, txtsessional4.getText().toString());
	    	            	pst.setString(6, txtgpa4.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("4 subject");
	            		}
	            		break;
                          case 5:
	            			
                        	  sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)"; 
	            			pst=connection.prepareStatement(sql);
	            			pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub5.getText().toString());
	   	    	              	pst.setString(3, txtmidmarks5.getText().toString());
	    	            	pst.setString(4, txtfinalmarks5.getText().toString());
	    	            	pst.setString(5, txtsessional5.getText().toString());
	    	            	pst.setString(6, txtgpa5.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	    	            
	            		 success=pst.executeUpdate();
	            		if(success==1) {
	            			System.out.println("5 subject");
	            		}
	            		break;
	            		case 6:
	            			
	            			sql="INSERT INTO result (stuid,courseid,midmarks,finalmarks,sessionalmarks,gpa,semester) values(?,?,?,?,?,?,?)";                  
	    	              	pst=connection.prepareStatement(sql);
	    	            
	    	            	pst.setString(1, txtsid.getText().toString());
	            			pst.setString(2, txtsub6.getText().toString());
	    	              	
	    	              	pst.setString(3, txtmidmarks6.getText().toString());
	    	            	pst.setString(4, txtfinalmarks6.getText().toString());
	    	            	pst.setString(5, txtsessional6.getText().toString());
	    	            	pst.setString(6, txtgpa6.getText().toString());
	    	              	pst.setInt(7, combosemester.getValue());
	    	              	
	            	       success=pst.executeUpdate();
	            		if(success==1) {
	            			clearFields();
	            			 tray.setTray("SUCCESS", "RESULT SAVED",
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
	            	
	            }
	   	   
   }}
   
   @FXML
   private void calculateGPA(){
	   if(validateForGPA()==true){
	      Integer midmarks=Integer.parseInt(txtmidmarks1.getText());  
	      Integer finalmarks=Integer.parseInt(txtfinalmarks1.getText());
	      Integer sessional=Integer.parseInt(txtsessional1.getText());
	      int ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa1.setText(""+getGPA(ans));
	      
	       midmarks=Integer.parseInt(txtmidmarks2.getText());  
	      finalmarks=Integer.parseInt(txtfinalmarks2.getText());
	      sessional=Integer.parseInt(txtsessional2.getText());
	      ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa2.setText(""+getGPA(ans));
	      
	      midmarks=Integer.parseInt(txtmidmarks3.getText());  
	      finalmarks=Integer.parseInt(txtfinalmarks3.getText());
	      sessional=Integer.parseInt(txtsessional3.getText());
	      ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa3.setText(""+getGPA(ans));
	      
	      midmarks=Integer.parseInt(txtmidmarks4.getText());  
	      finalmarks=Integer.parseInt(txtfinalmarks4.getText());
	      sessional=Integer.parseInt(txtsessional4.getText());
	      ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa4.setText(""+getGPA(ans));
	      
	      midmarks=Integer.parseInt(txtmidmarks5.getText());  
	      finalmarks=Integer.parseInt(txtfinalmarks5.getText());
	      sessional=Integer.parseInt(txtsessional5.getText());
	      ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa5.setText(""+getGPA(ans));
	      
	      midmarks=Integer.parseInt(txtmidmarks6.getText());  
	      finalmarks=Integer.parseInt(txtfinalmarks6.getText());
	      sessional=Integer.parseInt(txtsessional6.getText());
	      ans=midmarks.intValue()+finalmarks.intValue()+sessional.intValue();
	      txtgpa6.setText(""+getGPA(ans));
	   }
	      
   }
   
   
   
   private double getGPA(int marks){
	   double gpa=0.0;
	    if(marks>=80){
	    	gpa=4;
	    	
	    }
	    else if(marks>=78 && marks<80){
	    	gpa=3.9;
	    }else if(marks>=77 && marks<78){
	    	gpa=3.8;
	    }
	    else if(marks>=75 && marks<77){
	    	gpa=3.7;
	    }
	    else if (marks>=74 && marks<75){
	    	gpa=3.6;
	    }
	    else if(marks>=72 && marks<74){
	    	gpa=3.5;
	    }else if(marks>=71 && marks<72){
	    	gpa=3.4;
	    }
	    else if(marks>=69 && marks<71){
	    	gpa=3.3;
	    }
	    
	    else if(marks>=65 && marks<69){
	    	gpa=3.0;
	    }
	    else if(marks>=60 && marks<65){
	    	gpa=2.8;
	    	
	    }
	    else if(marks>=51 && marks<60){
	    	gpa=2.5;
	    }
	    else if(marks<50){
	    	gpa=0.0;
	    }
	    
	   
	   return gpa;
   }
   
   
   
   
     private boolean validateSubjectFields(){
	   boolean ans=false;
	   if(txtmidmarks1.getText().trim().isEmpty()||txtfinalmarks1.getText().trim().isEmpty()||txtsessional1.getText().trim().isEmpty()||txtgpa1.getText().trim().isEmpty()){
		  
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD 1 CAN'T BE EMPTY");
		   alert.show();
		   ans=false;
	   }
	   
	   else if(validate.ValidateNumber(txtmidmarks1.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 MID MARKS");
	   alert.show();
  }
	   else if(validate.ValidateNumber(txtfinalmarks1.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 FINAL MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtsessional1.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 SESSIONAL MARKS");
	   alert.show();
 }
	   else if(txtmidmarks2.getText().trim().isEmpty()||txtfinalmarks2.getText().trim().isEmpty()||txtsessional2.getText().trim().isEmpty()||txtgpa2.getText().trim().isEmpty()){
			    ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   
	   else if(validate.ValidateNumber(txtmidmarks2.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 MID MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtfinalmarks2.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 FINAL MARKS");
	   alert.show();
}
	   else if(validate.ValidateNumber(txtsessional2.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 SESSIONAL MARKS");
	   alert.show();
}
	   
	   else if(txtmidmarks3.getText().trim().isEmpty()||txtfinalmarks3.getText().trim().isEmpty()||txtsessional3.getText().trim().isEmpty()||txtgpa3.getText().trim().isEmpty()){
			   ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   
	   else if(validate.ValidateNumber(txtmidmarks3.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 MID MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtfinalmarks3.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 FINAL MARKS");
	   alert.show();
}
	   else if(validate.ValidateNumber(txtsessional3.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 SESSIONAL MARKS");
	   alert.show();
}
	   else if(txtmidmarks4.getText().trim().isEmpty()||txtfinalmarks4.getText().trim().isEmpty()||txtsessional4.getText().trim().isEmpty()||txtgpa4.getText().trim().isEmpty()){
			    ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   
	   else if(validate.ValidateNumber(txtmidmarks4.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 MID MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtfinalmarks4.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 FINAL MARKS");
	   alert.show();
}
	   else if(validate.ValidateNumber(txtsessional4.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 SESSIONAL MARKS");
	   alert.show();
}
	   
	   else if(txtmidmarks5.getText().trim().isEmpty()||txtfinalmarks5.getText().trim().isEmpty()||txtsessional5.getText().trim().isEmpty()||txtgpa5.getText().trim().isEmpty()){
			     ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   
	   else if(validate.ValidateNumber(txtmidmarks5.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 MID MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtfinalmarks5.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 FINAL MARKS");
	   alert.show();
}
	   else if(validate.ValidateNumber(txtsessional5.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 SESSIONAL MARKS");
	   alert.show();
}
	   else if(txtmidmarks6.getText().trim().isEmpty()||txtfinalmarks6.getText().trim().isEmpty()||txtsessional6.getText().trim().isEmpty()||txtgpa6.getText().trim().isEmpty()){
			     ans=false;
		   alert.setAlertType(AlertType.WARNING);
		   alert.setHeaderText("WARNING");
		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
		   alert.show();
	   }
	   else if(validate.ValidateNumber(txtmidmarks6.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 MID MARKS");
	   alert.show();
 }
	   else if(validate.ValidateNumber(txtfinalmarks6.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 FINAL MARKS");
	   alert.show();
}
	   else if(validate.ValidateNumber(txtsessional6.getText())==false){
		    ans=false;
	   alert.setAlertType(AlertType.ERROR);
	   alert.setHeaderText("INPUT TYPE ERROR");
	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 SESSIONAL MARKS");
	   alert.show();
}
        
         else {
	               ans=true;
           }
	   
	   
	   return ans;
   }
   
     private boolean validateForGPA(){
  	   boolean ans=false;
  	   Integer m1=Integer.parseInt(txtmidmarks1.getText());
  	   int m2=m1.intValue();
  	   if(txtmidmarks1.getText().trim().isEmpty()||txtfinalmarks1.getText().trim().isEmpty()||txtsessional1.getText().trim().isEmpty()){
  		  
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD 1 CAN'T BE EMPTY");
  		   alert.show();
  		   ans=false;
  	   }
  	   
  	   else if(validate.ValidateNumber(txtmidmarks1.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 MID MARKS");
  	   alert.show();
    }
  	   else if(validate.ValidateNumber(txtfinalmarks1.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 FINAL MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtsessional1.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 1 SESSIONAL MARKS");
  	   alert.show();
   }
  	   else if(txtmidmarks2.getText().trim().isEmpty()||txtfinalmarks2.getText().trim().isEmpty()||txtsessional2.getText().trim().isEmpty()){
  			    ans=false;
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
  		   alert.show();
  	   }
  	   
  	   else if(validate.ValidateNumber(txtmidmarks2.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 MID MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtfinalmarks2.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 FINAL MARKS");
  	   alert.show();
  }
  	   else if(validate.ValidateNumber(txtsessional2.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 2 SESSIONAL MARKS");
  	   alert.show();
  }
  	   
  	   else if(txtmidmarks3.getText().trim().isEmpty()||txtfinalmarks3.getText().trim().isEmpty()||txtsessional3.getText().trim().isEmpty()){
  			   ans=false;
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
  		   alert.show();
  	   }
  	   
  	   else if(validate.ValidateNumber(txtmidmarks3.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 MID MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtfinalmarks3.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 FINAL MARKS");
  	   alert.show();
  }
  	   else if(validate.ValidateNumber(txtsessional3.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 3 SESSIONAL MARKS");
  	   alert.show();
  }
  	   else if(txtmidmarks4.getText().trim().isEmpty()||txtfinalmarks4.getText().trim().isEmpty()||txtsessional4.getText().trim().isEmpty()){
  			    ans=false;
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
  		   alert.show();
  	   }
  	   
  	   else if(validate.ValidateNumber(txtmidmarks4.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 MID MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtfinalmarks4.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 FINAL MARKS");
  	   alert.show();
  }
  	   else if(validate.ValidateNumber(txtsessional4.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 4 SESSIONAL MARKS");
  	   alert.show();
  }
  	   
  	   else if(txtmidmarks5.getText().trim().isEmpty()||txtfinalmarks5.getText().trim().isEmpty()||txtsessional5.getText().trim().isEmpty()){
  			     ans=false;
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
  		   alert.show();
  	   }
  	   
  	   else if(validate.ValidateNumber(txtmidmarks5.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 MID MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtfinalmarks5.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 FINAL MARKS");
  	   alert.show();
  }
  	   else if(validate.ValidateNumber(txtsessional5.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 5 SESSIONAL MARKS");
  	   alert.show();
  }
  	   else if(txtmidmarks6.getText().trim().isEmpty()||txtfinalmarks6.getText().trim().isEmpty()||txtsessional6.getText().trim().isEmpty()){
  			     ans=false;
  		   alert.setAlertType(AlertType.WARNING);
  		   alert.setHeaderText("WARNING");
  		   alert.setContentText("SUBJECT or MARKS FIELD CAN'T BE EMPTY");
  		   alert.show();
  	   }
  	   else if(validate.ValidateNumber(txtmidmarks6.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 MID MARKS");
  	   alert.show();
   }
  	   else if(validate.ValidateNumber(txtfinalmarks6.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 FINAL MARKS");
  	   alert.show();
  }
  	   else if(validate.ValidateNumber(txtsessional6.getText())==false){
  		    ans=false;
  	   alert.setAlertType(AlertType.ERROR);
  	   alert.setHeaderText("INPUT TYPE ERROR");
  	   alert.setContentText("PLEASE ENTER ONNLY NUMBERS IN SUBJECT 6 SESSIONAL MARKS");
  	   alert.show();
  }
          
           else {
  	               ans=true;
             }
  	   
  	   
  	   return ans;
     }
  
   
	
	
}
