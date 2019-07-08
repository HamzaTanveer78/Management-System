package modules;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import classes.Course;
import classes.DbHandler;
import classes.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class SubjectController implements Initializable  {
	   

	    DbHandler db;
	    PreparedStatement pst;
	    Connection connection;
	    ResultSet result;
	    Statement stat;
	    Validation validate=new Validation();
	    @FXML
	    private AnchorPane parentPane;

	    @FXML
	    private JFXTextField txtCourseid;

	    @FXML
	    private JFXTextField txtCoursename;

	    @FXML
	    private JFXButton btnSaveCourse;

	    @FXML
	    private JFXTextField txtCredithours;
	    @FXML
	    private TableColumn<Course, String> courseidCol;

	    @FXML
	    private TableColumn<Course, String> coursenameCol;

	    @FXML
	    private TableColumn<Course, Integer> credithoursCol;
	    @FXML
	    private TableView<Course> coursetable;
	    TrayNotification tray=new TrayNotification();
        Alert alert=new Alert(AlertType.ERROR);
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		
		setCourseCoulumns();
		showCourse();
		
		
	}
  
	
	private void setCourseCoulumns() {
		courseidCol.setCellValueFactory(new PropertyValueFactory<>("courseid"));
		coursenameCol.setCellValueFactory(new PropertyValueFactory<>("coursename"));
		credithoursCol.setCellValueFactory(new PropertyValueFactory<>("credithours"));
		
		
		
	};

	@FXML
	private void saveCourse(ActionEvent e) {
		
		if(searchCourseExistence()==true){
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("RECORD EXISTS");
			alert.setContentText("RECORD ALREADY EXISTS");
			
			alert.show();
			
		}
		
	else if(validateClassFields()==true && searchCourseExistence()==false){
		
    	String credit=txtCredithours.getText().toString();
    	Integer credit2=Integer.parseInt(credit);
    	int credit3=credit2.intValue();
    	
					
		try {
			connection=db.getConnection();
			String sql="INSERT INTO course (courseid,coursename,credithours) values (?,?,?)";
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtCourseid.getText().toString());
			pst.setString(2, txtCoursename.getText());
			pst.setInt(3, credit3);
			 int success = pst.executeUpdate();
			 if (success == 1) {
		         
		            tray.setTray("SUCCESS", "DEPARTMENT SAVED",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		           
                   clearCourseFields();
                   clearCourseTable(coursetable);
                   showCourse();
		        } 
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		}
		
	}
	
	@FXML
	private void clearCourseFields() {
		  txtCourseid.clear();
		  txtCoursename.clear();   
		  txtCredithours.clear();
		
	
		
	}
	private void clearCourseTable(TableView table){
		table.getItems().clear();
		
	}
	
	
	
	public void showCourse() {
		
		try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			
			String sql="SELECT * from course";
			result=stat.executeQuery(sql);
			
			while(result.next()) {
				Course course=new Course();
				course.setCourseid(result.getString(1));
				course.setCoursename(result.getString(2));
				course.setCredithours(result.getInt(3));
				coursetable.getItems().add(course);
				
				}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		
	}
	
	@FXML
	private void searchCourse() {
		
		if(txtCourseid.getText().isEmpty()||txtCourseid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER SUBJECT ID");
			
			alert.show();
			
		}
		else{
		
		
		try {
			connection=db.getConnection();
			String sql="SELECT coursename,credithours from course where courseid='"+txtCourseid.getText().toString()+"'";
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
			txtCoursename.setText(result.getString(1));
			txtCredithours.setText(result.getString(2));
		
		
		}while(result.next()) ;
	
	
		   	}

			
		}
		catch(Exception e) {
		System.out.println(e);	
		}
		}
	}
	
	@FXML
	private boolean searchCourseExistence() {
		 boolean ans=true;
		if(txtCourseid.getText().isEmpty()||txtCourseid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER SUBJECT ID");
			
			alert.show();
			
		}
		
		else{
		       
		try {
			connection=db.getConnection();
			String sql="SELECT coursename,credithours from course where courseid='"+txtCourseid.getText().toString()+"'";
			pst=connection.prepareStatement(sql);
			result=pst.executeQuery();
			if (result.next() == false) 
		   	{ 
		 		ans=false;
		   	/* alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("EMPTY");
			 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
			 alert.show();*/
			 return ans;
			 }
			
			
		}
		catch(Exception e) {
		System.out.println(e);	
		}
		}
		return ans;
	}
	
	@FXML
	private void updateCourse() {
		if(searchCourseExistence()==false){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("EMPTY");
			 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
			 alert.show();
		}
		
		if(validateClassFields()==true && searchCourseExistence()==true){
		   String id="";
		   Integer id2=0;
		   int id3=0;
    						
		try {
			connection=db.getConnection();
			
			String sql="UPDATE course SET coursename=?,credithours=? where courseid='"+txtCourseid.getText().toString()+"'";
			
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtCoursename.getText().toString());
			id=txtCredithours.getText().toString();
			id2=Integer.parseInt(id);
			id3=id2.intValue();
			pst.setInt(2, id3);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "SUBJECT UPDATED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearCourseFields();
	                   clearCourseTable(coursetable);
	                   showCourse();
		        	
			}
			
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		}
	}
	
	@FXML
	private void deleteCourse() {
		
		if(txtCourseid.getText().isEmpty()||txtCourseid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER SUBJECT ID");
			
			alert.show();
			
		}
		else if(searchCourseExistence()==false){
			 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("EMPTY");
			 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
			 alert.show();
		}
		else{
		
    						
		try {
			connection=db.getConnection();
			
			String sql="DELETE FROM course where courseid='"+txtCourseid.getText().toString()+"'";
			
			pst=connection.prepareStatement(sql);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "SUBJECT DELETED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearCourseFields();
	                   clearCourseTable(coursetable);
	                   showCourse();
	                  
	                  
		        	
			}
			
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		}
	}
	
    private boolean validateClassFields(){
		boolean ans=true;
		if(txtCourseid.getText().isEmpty()||txtCourseid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("SUBJECT ID CAN'T BE EMPTY");
			alert.show();
			
		}
		else if(txtCoursename.getText().isEmpty()||txtCoursename.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE FILL SUBJECT TITLE");
			alert.show();
			
		}
		
		else if(validate.ValidateName(txtCoursename.getText())==false){
			ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY STRING VALUE FOR SUBJECT NAME");
		   	  alert.showAndWait();
		}
		
		else if(txtCredithours.getText().isEmpty()||txtCredithours.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER CREDIT HOURS");
			alert.show();
		}
		else if(validate.ValidateNumber(txtCredithours.getText())==false){
			 ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR CREDIT HOURS");
		   	  alert.showAndWait();
		}
		
		return ans;
	      }
	
	
	
	
	
}
	
	