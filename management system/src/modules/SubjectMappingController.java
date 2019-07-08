package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import classes.Course;
import classes.DbHandler;
import classes.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class SubjectMappingController implements Initializable{
    @FXML
    private JFXComboBox<String> comboDepartment;

    @FXML
    private JFXComboBox<String> comboClass;
    @FXML
    private JFXComboBox<String> comboSemester;
    @FXML
    private JFXComboBox<String>  comboTeacher;
    @FXML
    private JFXTextField txtcid;
    @FXML
    private JFXTextField txtsession;
    @FXML
    private JFXTextField txtcname;
    @FXML
    private TableColumn<Course, String> cmcourseidCol;

    @FXML
    private TableColumn<Course, String> cmcoursenameCol;

    @FXML
    private TableColumn<Course, String> cmclassCol;

    @FXML
    private TableColumn<Course, Integer> cmsemesterCol;

    @FXML
    private TableColumn<Course, String> cmdepartmentCol;
    @FXML
    private TableColumn<Course, String> cmteachercol;
    @FXML
    private TableColumn<Course, String> cmsessioncol;
    @FXML
    private TableView<Course> cmTable;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private Label lbldepid;

    @FXML
    private Label lblcourseid;
    @FXML
    private Label lblteacherid;
    
    
    Alert alert=new Alert(AlertType.ERROR);
    TrayNotification tray=new TrayNotification();
    
    DbHandler db;
    PreparedStatement pst;
    Connection connection;
    ResultSet result;
    Statement stat;
    Validation validate=new Validation();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		setCourseCoulumns();
		comboDepartment.getSelectionModel().selectedItemProperty().addListener((v, old_val,new_val)->{
			
		int id=	fetchDepID(new_val);
		lbldepid.setText(""+id);
		fetchClass(id);
		showCourseMapping();
		}
							);
		comboClass.getSelectionModel().selectedItemProperty().addListener((v, old_val,new_val)->{
			
			    fetchClassID(new_val);
		       int sem=fetchSemesters(new_val);
		       setSemesters(sem);
		}
							);
		
		fetchDepartment();
		fetchTeacher();
		showCourseMapping();
		
	}
	
	private void setCourseCoulumns() {
		
		 cmcourseidCol.setCellValueFactory(new PropertyValueFactory<>("courseid"));

		 cmcoursenameCol.setCellValueFactory(new PropertyValueFactory<>("coursename"));

		 cmclassCol.setCellValueFactory(new PropertyValueFactory<>("classname"));

	     cmsemesterCol.setCellValueFactory(new PropertyValueFactory<>("semesterno"));
         cmdepartmentCol.setCellValueFactory(new PropertyValueFactory<>("depname"));
         cmteachercol.setCellValueFactory(new PropertyValueFactory<>("teachername"));
         cmsessioncol.setCellValueFactory(new PropertyValueFactory<>("session"));
	};
	
	
	@FXML
	private void clearCourseFields() {
		 
		  txtcid.clear();
		  comboDepartment.setValue(null);
		  comboDepartment.setValue(null);
		  comboSemester.setValue(null);
	      comboTeacher.setValue(null);
	      txtsession.clear();
	      lbldepid.setText(null);
	      lblcourseid.setText(null);
		
	}
	private void clearCourseTable(TableView table){
		table.getItems().clear();
		
	}
	
	
	private void fetchClassID(String coursename){
		
		try{
			connection=db.getConnection();
			String sql="SELECT classid from class where classname=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, coursename);
			result=pst.executeQuery();
			
			while(result.next()){
				
				lblcourseid.setText(""+result.getInt("classid"));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
			
	}
	private void fetchTeacher() {
		 try {
				
				connection=db.getConnection();
				Statement stat=connection.createStatement();
				String sql="SELECT teacherfname,teacherlname from teacher";
				result=stat.executeQuery(sql);
				ObservableList<String> teacher=FXCollections.observableArrayList();
				while(result.next()) {
					teacher.add(result.getString("teacherfname")+ " "+result.getString("teacherlname"));	
					
					}
				comboTeacher.setItems(teacher);
				
				
			}
			catch(Exception e) {
				System.out.println(e);
			}
		
	}
	
	
	public void fetchDepartment() {
		
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
	
	public int fetchSemesters(String val){
		int ans=0;
        try {
			connection=db.getConnection();
		   
			String sql="SELECT semesters from class where classname=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, val);
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
	private void showCourseMapping() {
		cmTable.getItems().clear();
try {
			
			connection=db.getConnection();
			Statement stat=connection.createStatement();
			
			String sql="select cm.courseid , d.depname, cm.semester,cl.classname,"
					+ " c.coursename, cm.teachername, cm.session from coursemapping cm join "
					+ "course c on cm.courseid=c.courseid  join class cl on cm.classid=cl.classid "
					+ "join department d on cm.depid=d.depid";
			result=stat.executeQuery(sql);
			
			while(result.next()) {
				Course course=new Course();
				course.setCourseid(result.getString(1));
				course.setDepname(result.getString(2));
				course.setSemesterno(result.getInt(3));
				course.setClassname(result.getString(4));
				course.setCoursename(result.getString(5));
				course.setTeachername(result.getString(6));
				course.setSession(result.getString(7));
				cmTable.getItems().add(course);
				
				}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	@FXML 
	private void saveCourseMapping() {
		if(searchCourseExistence()==true){
			
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("NO ENTRY");
			alert.setContentText("SAME SUBJECT MAPPING ALREADY EXISTS");
			alert.show();
		}
		
		else if(validateClassFields()==true &&searchCourseExistence()==false){
		   		  
		try {
			connection=db.getConnection();
			String sql="INSERT INTO coursemapping (courseid,depid,semester,classid,teachername,session) "
					+ "values (?,?,?,?,?,?)";
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtcid.getText().toString());
			pst.setString(2, lbldepid.getText());
			pst.setString(3, comboSemester.getValue());
			pst.setString(4, lblcourseid.getText());
			pst.setString(5, comboTeacher.getValue());
			pst.setString(6, txtsession.getText().toString());
			 int success = pst.executeUpdate();
			 if (success == 1) {
				 clearCourseFields();
                 clearCourseTable(cmTable);
                 showCourseMapping();
				 tray.setTray("SUCCESS", " RECORD SAVED SUCCESSFULLY",
	    					new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	                tray.showAndDismiss(Duration.seconds(2)); 
                  
		        } 
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		}
	}
	
	@FXML
	private void deleteCourseMapping(){
             if(searchCourseExistence()==false){
			
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("NO ENTRY");
			alert.setContentText("NO RECORD EXISTS TO BE UPDATED AGAIN ENTERED ID");
			alert.show();
		}
		
		else if(validateClassFields()==true &&searchCourseExistence()==true){
		   		  
		try {
			connection=db.getConnection();
			String sql="DELETE from coursemapping where courseid=? and depid=? and classid=? and semester=? and session=?";
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtcid.getText().toString());
			pst.setString(2, lbldepid.getText());
			pst.setString(3, lblcourseid.getText());
			pst.setString(4, comboSemester.getValue());
			pst.setString(5, txtsession.getText().toString());
			 int success = pst.executeUpdate();
			 if (success == 1) {
				 clearCourseFields();
                 clearCourseTable(cmTable);
                 showCourseMapping();
				 tray.setTray("SUCCESS", " RECORD DELETED SUCCESSFULLY",
	    					new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	                tray.showAndDismiss(Duration.seconds(2)); 
                  
		        } 
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		}
		
	}
	
	@FXML
	private void searchCourseMapping() {
		if(txtSearch.getText().isEmpty()||txtSearch.getText().trim().isEmpty()){
			
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("SUBJECT ID CAN'T BE EMPTY");
			alert.show();
			
		}
		  else{
            try {
	        cmTable.getItems().clear();
			
			connection=db.getConnection();
			
			String sql="select cm.courseid , d.depname, cm.semester,cl.classname,"
					+ " c.coursename, cm.teachername,cm.session from coursemapping cm join "
					+ "course c on cm.courseid=c.courseid join department d on cm.depid=d.depid"
					+ " join class cl on cm.classid=cl.classid where cm.courseid='"+txtSearch.getText().toString()+"'";
			
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
			Course course=new Course();
			course.setCourseid(result.getString(1));
			course.setDepname(result.getString(2));
			course.setSemesterno(result.getInt(3));
			course.setClassname(result.getString(4));
			course.setCoursename(result.getString(5));
			course.setTeachername(result.getString(6));
			course.setSession(result.getString(7));
			
			cmTable.getItems().add(course);
		
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
		            try {
	        
			connection=db.getConnection();
			
			String sql="select cm.courseid , d.depname, cm.semester,cl.classname,"
					+ " c.coursename, cm.teachername,cm.session from coursemapping cm join "
					+ "course c on cm.courseid=c.courseid join department d on cm.depid=d.depid"
					+ " join class cl on cm.classid=cl.classid where cm.courseid='"+txtcid.getText().toString()+"' and d.depname='"+comboDepartment.getValue()+"' and cl.classname='"+comboClass.getValue()+"' and cm.semester="+comboSemester.getValue()+" and cm.session='"+txtsession.getText()+"'";
			
			pst=connection.prepareStatement(sql);
			result=pst.executeQuery();
			if (result.next() == false) 
		   	{ 
				ans=false;
		   				 }
					
		}
		catch(Exception e) {
			System.out.println(e);
		}
				return ans;
		
		 }
	
	
	
	private boolean validateClassFields(){
		boolean ans=true;
		if(txtcid.getText().isEmpty()||txtcid.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("SUBJECT ID CAN'T BE EMPTY");
			alert.show();
			
		}
		
		
		else if(comboDepartment.getValue()==null){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE SELECT DEPARTMENT");
			alert.show();
		}
		else if(comboClass.getValue()==null){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE SELECT CLASS");
			alert.show();
			
		}
		
		else if(comboSemester.getValue()==null){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE SELECT SEMESTER NO");
			alert.show();
		}
		else if(comboTeacher.getValue()==null){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE SELECT TEACHER NAME");
			alert.show();
			
		}
		
		
		else if(txtsession.getText().isEmpty()||txtsession.getText().trim().isEmpty()){
			ans=false;
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText("WARNING");
			alert.setContentText("PLEASE ENTER SESSION");
			alert.show();
		}
		
		
		return ans;
	      }
	
	
	
	  private void setSemesters(int val) {
	    	ObservableList<String> sem=FXCollections.observableArrayList();
			
	    	if(val==10) {
	    		while(val>=1) {
	    			
	    			sem.add(""+val);
	    			
	    			val--;
	    			
	    			}
	    		comboSemester.setItems(sem);
	        	
	    		
	    	}else if(val==9) {
	            while(val>=1) {
	    			
	 			sem.add(""+val);
	 			
	 			val--;
	 			
	 			}
	 		comboSemester.setItems(sem);
	 		
	 	}else if(val==8) {
	        while(val>=1) {
				
				sem.add(""+val);
				
				val--;
				
				}
			comboSemester.setItems(sem);
			
		}
	    	else if(val==7) {
	               while(val>=1) {
	    			
	    			sem.add(""+val);
	    			
	    			val--;
	    			
	    			}
	    		comboSemester.setItems(sem);
	    		
	    	}
	    	else if(val==6) {
	    		 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);
	    		
	    	}
	    	else if(val==5) {
	    		 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);
	    		
	    	}
	    	else if(val==4)
	    	{
	    		 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);	
	    	}
	        else if(val==3) {
	        	 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);
	    		
	    	}
	    	else if(val==2) {
	    		 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);
	    		
	    	}
	    	else if(val==1) {
	    		 while(val>=1) {
	     			
	     			sem.add(""+val);
	     			
	     			val--;
	     			
	     			}
	     		comboSemester.setItems(sem);
	    		
	    	}
	    	else 
	    	{
	    		
	    	}
	    		
	    }
	

}
