package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import classes.ClassMapping;
import classes.Course;
import classes.DbHandler;
import classes.Department;
import classes.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tray.notification.TrayNotification;


@SuppressWarnings("unused")
public class DepartmentController implements Initializable  {
	    @FXML
	    private AnchorPane parentPane;

	    @FXML
	    private JFXTextField txtDepid;

	    @FXML
	    private JFXTextField txtDepname;

	    @FXML
	    private JFXButton btnDepSave;

	    @FXML
	    private JFXTextField txtClassid;
	    @FXML
	    private JFXButton btnClassSave;

	    @FXML
	    private JFXTextField txtClassname;

	    @FXML
	    private JFXComboBox<String> depCombo;

	    @FXML
	    private JFXComboBox<?> semester;
	    
	    @FXML
	    private TableView<Department> depTable;

	    @FXML
	    private TableColumn<Department, Integer> depidCol;

	    @FXML
	    private TableColumn<Department, String> depnameCol;
	   @FXML
	    private JFXTextField txtClassdepid;

	    @FXML
	    private JFXComboBox<?> comboSemester;
	    @FXML
	    private JFXTextField txtSemesterno;
	    
	    @FXML
	    private TableView<ClassMapping> mappingTable;
	    @FXML
	    private TableColumn<ClassMapping, Integer> classCol;

	    @FXML
	    private TableColumn<ClassMapping, String> classnameCol;

	    @FXML
	    private TableColumn<ClassMapping, String> classdepCol;

	    @FXML
	    private TableColumn<ClassMapping, Integer> classSemCol;
	    Alert alert=new Alert(AlertType.ERROR) ;
	    TrayNotification tray=new TrayNotification();
	    DbHandler db;
	    PreparedStatement pst;
	    Connection connection;
	    ResultSet result;
	    Statement stat;
	    Validation v=new Validation();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		
		depCombo.getSelectionModel().selectedItemProperty().addListener((v, old_val,new_val)->{
		
			fetchDepID(new_val);
		}
							);
		fetchDepartment();
		setDepartmentTable();
		setClassTable();
		showDepartment();
		
		showClassMapping();
		
	}
	
	private void clearDep() {
		
		txtDepid.clear();
		txtDepname.clear();
	}
	private void clearClassMappingFields(){
		txtClassid.clear();
		txtClassname.clear();
		txtClassdepid.clear();
		depCombo.setValue(null);
		txtSemesterno.clear();
		
		
	}
	
	private void fetchDepID(String depname) {
          try {
			connection=db.getConnection();
		
			String sql="SELECT depid from department where depname=?";
			
			pst=connection.prepareStatement(sql);
			pst.setString(1, depname);
			result=pst.executeQuery();
			while(result.next()) {
				txtClassdepid.setText(result.getString(1));
				
				}
			}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	private void setDepartmentTable() {
		depidCol.setCellValueFactory(new PropertyValueFactory<>("depid"));
		depnameCol.setCellValueFactory(new PropertyValueFactory<>("depname"));
		
		
	}
	private void setClassTable() {
		
		  classCol.setCellValueFactory(new PropertyValueFactory<>("classid"));

	     classnameCol.setCellValueFactory(new PropertyValueFactory<>("classname"));

	     classdepCol.setCellValueFactory(new PropertyValueFactory<>("depname"));

	    classSemCol.setCellValueFactory(new PropertyValueFactory<>("semesters"));
		
		
	}
	

	@FXML
	private void saveDepartment(ActionEvent e) {
		
		if(searchDepartment()==true){
			alert.setHeaderText("RECOED EXISTS");
			 alert.setContentText("RECOED EXISTS AGAINST ENTERED ID");
		   	  alert.showAndWait();
			
		}
		else
			{
		if(validateDepartmentFields()==true){
			
		String id=txtDepid.getText().toString();
		Integer id2=Integer.parseInt(id);
    	int id3=id2.intValue();
    	
		try {
			connection=db.getConnection();
			String sql="INSERT INTO department (depid,depname) values (?,?)";
			pst=connection.prepareStatement(sql);
			pst.setInt(1, id3);
			pst.setString(2, txtDepname.getText());
			 int success = pst.executeUpdate();
			 if (success == 1) {
				 tray.setTray("SUCCESS", "DEPARTMENT SAVED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearDep();
		            fetchDepartment();
		            showDepartment();
		            
		        } 
		
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		
		}
		
		}
		
	}
	
	
	
	
	@FXML
	private void updateDepartment() {
		if(searchDepartment()==false){
			alert.setHeaderText("NO RECOED FOUND");
			 alert.setContentText("NO RECOED EXISTS AGAINST ENTERED ID");
		   	  alert.showAndWait();
		   	 
		}
		
		else
		{
		if(validateDepartmentFields()==true){
		String id=txtDepid.getText().toString();
    	Integer id2=Integer.parseInt(id);
    	int id3=id2.intValue();
    						
		try {
			connection=db.getConnection();
			
			String sql="UPDATE studentmanagement.department SET depname=? where depid="+id3;
			
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtDepname.getText().toString());
			System.out.println(txtDepname.getText().toString());
			System.out.println(txtDepid.getText().toString());
			int success=pst.executeUpdate();
			if(success==1) {
				 clearDep();
		        	showDepartment();
		        	showClassMapping();
				 tray.setTray("SUCCESS", "DEPARTMENT UPDATED",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		           
		        	
			}
		
			
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		}}
		
		
		}
		
	
	
	@FXML
	private void deleteDepartment() {
		
		if(txtDepid.getText().isEmpty()||txtDepid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("Department ID is empty");
		   	  alert.showAndWait();
		}
		else if(v.ValidateNumber(txtDepid.getText())==false){
			
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR DEPARTMENT ID");
		   	  alert.showAndWait();
		}
		else if(searchDepartment()==false){
			 alert.setHeaderText("NO RECOED FOUND");
			 alert.setContentText("NO RECOED EXISTS AGAINST ENTERED ID");
		   	  alert.showAndWait();
		}
		else{
		String id=txtDepid.getText().toString();
    	Integer id2=Integer.parseInt(id);
    	int id3=id2.intValue();
    						
		try {
			connection=db.getConnection();
			
			String sql="DELETE FROM department  where depid="+id3;
			
			pst=connection.prepareStatement(sql);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "DEPARTMENT DELETED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearDep();
		        	showDepartment();
		        	showClassMapping();
			}
			
			
				
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		}
		
	}
	
	@FXML
	private boolean searchDepartment() {
		boolean ans=true;
		if(txtDepid.getText().isEmpty()||txtDepid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("Department ID is empty");
		   	  alert.showAndWait();
		   	  ans=false;
		}
		else if(v.ValidateNumber(txtDepid.getText())==false){
			
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR DEPARTMENT ID");
		   	  alert.showAndWait();
		   	  ans=false;
		}
		else{
					
		String val=txtDepid.getText().toString();
		Integer val2=Integer.parseInt(val);
		int value=val2.intValue();
		try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			String sql="SELECT depname from department where depid="+value;
			result=stat.executeQuery(sql);
			   
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
		}
		}
		return ans;
		
	}
	@FXML
	private boolean searchDepartmentName() {
		boolean ans=true;
		if(txtDepid.getText().isEmpty()||txtDepid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("Department ID is empty");
		   	  alert.showAndWait();
		   	  ans=false;
		}
		else if(v.ValidateNumber(txtDepid.getText())==false){
			
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR DEPARTMENT ID");
		   	  alert.showAndWait();
		   	  ans=false;
		}
		
		else{
					
		String val=txtDepid.getText().toString();
		Integer val2=Integer.parseInt(val);
		int value=val2.intValue();
		try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			String sql="SELECT depname from department where depid="+value;
			result=stat.executeQuery(sql);
			   
		   	if (result.next() == false) 
		   	{ 
		   		ans=false;
		   	 alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("EMPTY");
			 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
			 alert.show();
			 }
		   	
		   	else {
		   		do {
			
				txtDepname.setText(result.getString(1));
				
				}while(result.next());
			
		}
		   
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
		}
		return ans;
		
	}
	
	private void showDepartment() {
    try {
			depTable.getItems().clear();
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			
			String sql="SELECT * from department";
			result=stat.executeQuery(sql);
			
			while(result.next()) {
				Department dep=new Department();
				dep.setDepid(result.getInt(1));
				dep.setDepname(result.getString(2));
				
				
				depTable.getItems().add(dep);
				
				}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
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
					depCombo.setItems(depname);
					
					
				}
				catch(Exception e) {
					System.out.println(e);
				}
				
		
	}
	
	@FXML
	private void saveClass() {
		if(searchClassExistence()==true){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("COURSE ALREADY EXISTS");
		   	  alert.showAndWait();
		}
		else if(validateClassFields()==true && searchClassExistence()==false){
		try {
			
			connection=db.getConnection();
			String id=txtClassid.getText().toString();
			Integer id2=Integer.parseInt(id);
			int id3=id2.intValue();
			String sql="INSERT INTO class(classid,classname,depid,semesters) values (?,?,?,?)";
			pst=connection.prepareStatement(sql);
			pst.setInt(1, id3);
			pst.setString(2, txtClassname.getText().toString());
			id=txtClassdepid.getText().toString();
			id2=Integer.parseInt(id);
			id3=id2.intValue();
			pst.setInt(3, id3);
			id=txtSemesterno.getText().toString();
			id2=Integer.parseInt(id);
			id3=id2.intValue();
			pst.setInt(4, id3);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "CLASS SAVED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
				clearClassMappingFields();
				showClassMapping();
				
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		}
	}
	
	@FXML
	private void showClassMapping() {
		try {
			mappingTable.getItems().clear();
			connection=db.getConnection();
			stat=connection.createStatement();
			String sql="SELECT c.classid,  c.classname, d.depname, c.semesters from "
					+ "class c JOIN department d on c.depid=d.depid";
			result=stat.executeQuery(sql);
			while(result.next()) {
				ClassMapping mapping=new ClassMapping();
				mapping.setClassid(result.getInt(1));
				mapping.setClassname(result.getString(2));
				mapping.setDepname(result.getString(3));
				mapping.setSemesters(result.getInt(4));
				mappingTable.getItems().add(mapping);
				
			}
			
		}
		catch(Exception e ) {
			System.out.println(e);
		}
		
		
	}
	
	
	
	@FXML
	private void searchClass() {
		
		if(txtClassid.getText().isEmpty()||txtClassid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("CLASS ID is empty");
		   	  alert.showAndWait();
		   	  
		}
		
		else if(v.ValidateNumber(txtClassid.getText())==false){
			
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR COURSE ID");
		   	  alert.showAndWait();
		}
		else if(searchClassExistence()==false){
			
				alert.setAlertType(AlertType.WARNING);
				 alert.setHeaderText("NO RECORD FOUND");
			   	  alert.setContentText("NO RECORD EXISTS AGAINST ENTERD ID");
			   	  alert.showAndWait();
			
		}
		
		else{
		
		String val=txtClassid.getText().toString();
		Integer val2=Integer.parseInt(val);
		int value=val2.intValue();
		try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			String sql="SELECT c.classname,d.depname from studentmanagement.class c "
					+ "join studentmanagement.department d "
					+ "on c.depid=d.depid where c.classid="+value;
					result=stat.executeQuery(sql);
				 	if (result.next() == false) 
				   	{ 
				   	 alert.setAlertType(AlertType.WARNING);
					 alert.setHeaderText("EMPTY");
					 alert.setContentText("EMPTY RECORD, NO RECORD EXISTS AGAINST ENTER ID");
					 alert.show();
					 }
				   	
				else {
				do {
				txtClassname.setText(result.getString(1));
				
				depCombo.setValue(result.getString(2));
				
				}while(result.next()) ;
			
			
				   	}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		}
	}
	@FXML
	private boolean searchClassExistence() {
		boolean ans=true;
		if(txtClassid.getText().isEmpty()||txtClassid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("CLASS ID is empty");
		   	  alert.showAndWait();
		   	  
		}
		else if(v.ValidateNumber(txtClassid.getText())==false){
			ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR COURSE ID");
		   	  alert.showAndWait();
		}
		else{
		
		String val=txtClassid.getText().toString();
		Integer val2=Integer.parseInt(val);
		int value=val2.intValue();
		try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			String sql="SELECT c.classname,d.depname from studentmanagement.class c "
					+ "join studentmanagement.department d "
					+ "on c.depid=d.depid where c.classid="+value;
					result=stat.executeQuery(sql);
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
	private void updateClass() {
		if(searchClassExistence()==false){
			
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("NO RECORD FOUND");
		   	  alert.setContentText("NO RECORD EXISTS IN DATABASE TO BE UPDATED AGAINST ENTERED ID");
		   	  alert.showAndWait();
		}
		else if(validateClassFields()==true && searchClassExistence()==true){
		
		String id=txtClassid.getText().toString();
    	Integer id2=Integer.parseInt(id);
    	int id3=id2.intValue();
    						
		try {
			connection=db.getConnection();
			
			String sql="UPDATE class SET classname=?,depid=?,semesters=? where classid="+id3;
			
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtClassname.getText().toString());
			id=txtClassdepid.getText().toString();
			id2=Integer.parseInt(id);
			id3=id2.intValue();
			pst.setInt(2, id3);
			id=txtSemesterno.getText().toString();
			id2=Integer.parseInt(id);
			id3=id2.intValue();
			pst.setInt(3, id3);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "CLASS UPDATED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearClassMappingFields();
		        	showClassMapping();
		        	
			}
			
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		
		}
	}
	
	@FXML
	private void deleteClass() {
		
		if(txtClassid.getText().isEmpty()||txtClassid.getText().trim().isEmpty()){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("Operation Failed");
		   	  alert.setContentText("COURSE ID is empty");
		   	  alert.showAndWait();
		}
		else if(v.ValidateNumber(txtClassid.getText())==false){
			
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR COURSE ID");
		}
		
		else if(searchClassExistence()==false){
			alert.setAlertType(AlertType.WARNING);
			 alert.setHeaderText("NO RECORD FOUND");
		   	  alert.setContentText("NO RECORD EXISTS IN DATABASE TO BE DELETED AGAINST ENTERED ID");
		   	  alert.showAndWait();	
		}
		
		else{
		String id=txtClassid.getText().toString();
    	Integer id2=Integer.parseInt(id);
    	int id3=id2.intValue();
    						
		try {
			connection=db.getConnection();
			
			String sql="DELETE FROM class  where classid="+id3;
			
			pst=connection.prepareStatement(sql);
			int success=pst.executeUpdate();
			if(success==1) {
				 tray.setTray("SUCCESS", "CLASS DELETED SUCCESSFULLY",
							new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
		            tray.showAndDismiss(Duration.seconds(2));
		            clearClassMappingFields();
		        	showClassMapping();
		        	
			}
			
		} catch (Exception e1) {
			
			System.out.println(e1);
		}
		
	}}
	
        private boolean validateClassFields(){
		boolean ans=true;
		if(txtClassid.getText().isEmpty()||txtClassid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("CLASS ID CAN'T BE EMPTY");
			alert.show();
			
		}
		else if(v.ValidateNumber(txtClassid.getText())==false){
			ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR COURSE ID");
		   	  alert.showAndWait();
		}
		else if(txtClassname.getText().isEmpty()||txtClassname.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("CLASS NAME CAN'T BE EMPTY");
			alert.show();
			
		}
		else if(v.ValidateName(txtClassname.getText())==false){
			ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY STRING VALUE FOR COURSE NAME");
		   	  alert.showAndWait();
		}
		
		else if(depCombo.getValue()==null){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("DEPARTMENT NAME CAN'T BE EMPTY");
			alert.show();
		}
		else if(txtClassdepid.getText().isEmpty()||txtClassdepid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("DEPARTMENT ID CAN'T BE EMPTY");
			alert.show();
		}
		else if(txtSemesterno.getText().isEmpty()||txtSemesterno.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER SEMESTER VALUE");
			alert.show();
			
		}
		else if(v.ValidateNumber(txtSemesterno.getText())==false){
			ans=false;
			 alert.setHeaderText("INPUT ERROR");
			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR SEMESTER FIELD");
		   	  alert.showAndWait();
		}
		
		
		return ans;
	      }
	
        private boolean validateDepartmentFields(){
    		boolean ans=true;
    		if(txtDepid.getText().trim().isEmpty()){
    			ans=false;
    			alert.setAlertType(AlertType.WARNING);
    			alert.setHeaderText("WARNING");
    			alert.setContentText("DEPARTMENT ID CAN'T BE EMPTY");
    			alert.show();
    			
    		}
    		else if(v.ValidateNumber(txtDepid.getText())==false){
    			ans=false;
    			 alert.setHeaderText("INPUT ERROR");
    			 alert.setContentText("PLEASE ENTER ONLY NUMERIC VALUE FOR DEPARTMENT ID");
    		   	  alert.showAndWait();
    		}
    		else if(txtDepname.getText().trim().isEmpty()){
    			ans=false;
    			alert.setAlertType(AlertType.WARNING);
    			alert.setHeaderText("WARNING");
    			alert.setContentText("DEPARTMENT NAME CAN'T BE EMPTY");
    			alert.show();
    			
    		}
    		else if(v.ValidateName(txtDepname.getText())==false){
    			ans=false;
    			 alert.setHeaderText("INPUT ERROR");
    			 alert.setContentText("PLEASE ENTER ONLY STRING VALUE FOR DEPARTMENT NAME");
    		   	  alert.showAndWait();
    		}
    		
    		
    		return ans;
    	      }
    	
	
	}


      
	
	
	
	



