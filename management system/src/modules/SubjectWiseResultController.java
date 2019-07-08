package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import classes.Attendance;
import classes.DbHandler;
import classes.SubjectResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubjectWiseResultController implements Initializable{

	 @FXML
	    private TableView<SubjectResult> resulttable;

	    @FXML
	    private TableColumn<SubjectResult, String> idcol;

	    @FXML
	    private TableColumn<SubjectResult, String> namecol;

	    @FXML
	    private TableColumn<SubjectResult, String> midmarkscol;

	    @FXML
	    private TableColumn<SubjectResult, String> finalmarkscol;

	    @FXML
	    private TableColumn<SubjectResult, String> sessionalmarkscol;

	    @FXML
	    private TableColumn<SubjectResult, String> totalmarkscol;

	    @FXML
	    private TableColumn<SubjectResult, String> gpacol;
	    @FXML
	    private TableColumn<SubjectResult, String> subjectnamecol;
	    @FXML
	    private JFXComboBox<String> combodepartment;

	    @FXML
	    private JFXComboBox<String> comboclass;

	    @FXML
	    private JFXComboBox<String> combosemester;
	    @FXML
	    private JFXComboBox<String>   combosubject;
	   

	    @FXML
	    private JFXComboBox<String> combosession;
	    
	    Alert alert=new Alert(AlertType.ERROR) ;
	    DbHandler db;
	    PreparedStatement pst;
	    Connection connection;
	    ResultSet result;
	    Statement stat;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 db=new DbHandler();
	     combodepartment.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
			 
			  int id=fetchDepID(combodepartment.getValue());
		      fetchClass(id);
			  
		  });
		    ObservableList<String> sem=FXCollections.observableArrayList("1","2","3",
		    		"4","5","6","7","8","9","10");
		    combosemester.setItems(sem);
		    
		    combosemester.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
				 
				 
			      fetchSession();
			      fetchSubjects();
			  });
		    comboclass.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
				 
				 
			      fetchSession();
			  });
		    combosession.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
				 
				 fetchSubjects();
				  
			  });
		    
		    fetchDepartment();
		
		buildTable();
		
	}

	
	
	
	
	private void buildTable(){
		
		 idcol.setCellValueFactory(new PropertyValueFactory("id"));

		    namecol.setCellValueFactory(new PropertyValueFactory("name"));

		     midmarkscol.setCellValueFactory(new PropertyValueFactory("midmarks"));

		     finalmarkscol.setCellValueFactory(new PropertyValueFactory("finalmarks"));

		    sessionalmarkscol.setCellValueFactory(new PropertyValueFactory("sessionalmarks"));

		    totalmarkscol.setCellValueFactory(new PropertyValueFactory("totalmarks"));

		     gpacol.setCellValueFactory(new PropertyValueFactory("gpa"));
		     subjectnamecol.setCellValueFactory(new PropertyValueFactory("subjectname"));
		
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
	      
	      
	      private void fetchSubjects(){
	    	  
	    	  try {
	            	 connection=db.getConnection();
	            	 
	            	 String sql="select cm.courseid,c.coursename from "
	            	 		+ "coursemapping cm join course c on cm.courseid=c.courseid "
	            	 		+ "join class cl on cm.classid=cl.classid join department d on cm.depid=d.depid"
	            	 		+ " where cl.classname='"+ comboclass.getValue() +"' and d.depname='"+combodepartment.getValue()+"' and semester="+combosemester.getValue()
	            	 		+" and cm.session='"+combosession.getValue()+"'";
	            	/* 
	            	 String sql="select cm.courseid,c.coursename from studentmanagement.coursemapping cm join studentmanagement.course c on cm.courseid=c.courseid"+
	            	 		" join studentmanagement.class cl on cm.classid=cl.classid join studentmanagement.department d on cm.depid=d.depid"+
	            	 		 " where cl.classname='BS' and d.depname='Computer Science' and semester=1"+
	            	 		 " and cm.session='2015-19'";*/
	            	 
	                            	
	            	 pst=connection.prepareStatement(sql);
	            	 result=pst.executeQuery();
                     ObservableList subjects=FXCollections.observableArrayList();

	            	 while(result.next()) {
	            		 subjects.add(result.getString("c.coursename"));
	                	  
	            	 }
	            	 combosubject.setItems(subjects);
	            	 
	             }catch(Exception e)
	             {
	            	 System.out.println(e);
	             }
		
	      }
     
	      @FXML
		  private void showRecord() {
	    	  resulttable.getItems().clear();
		        try {
		            connection = db.getConnection();
		            
		        	String sql="SELECT r.stuid,s.stufname,s.stulname,r.courseid,c.coursename,"
		    				+ "r.midmarks,r.finalmarks,r.sessionalmarks,r.gpa "
		    				+ "from result r join student s on r.stuid=s.stuid join course c on r.courseid=c.courseid "
		    				+ "join department d on s.depid=d.depid join class cl on s.classid=cl.classid"
		    				+ " where d.depname='"+combodepartment.getValue()+"' and cl.classname='"+
		    				comboclass.getValue()+"' and r.semester="+combosemester.getValue()+" and s.stusession='"+combosession.getValue()+"' and c.coursename='"+combosubject.getValue()+"'";
		    				
		              
		        	pst=connection.prepareStatement(sql);
	                
		            result =pst.executeQuery(); 
		           while(result.next()){
		        	   SubjectResult s=new SubjectResult();
		        	   s.setId(result.getString(1));
		        	   s.setName(result.getString(2)+" "+result.getString(3));
		        	   s.setSubjectname(result.getString(5));
		        	   s.setMidmarks(result.getString(6));
		        	   s.setFinalmarks(result.getString(7));
		        	   s.setSessionalmarks(result.getString(8));
		        	   s.setGpa(result.getString(9));
		        	   resulttable.getItems().add(s);
		           }
		            
		            
		            
		             } 
		             catch (SQLException ex) {
		                  System.out.println(ex);
		        }
		    
	
	
}}
