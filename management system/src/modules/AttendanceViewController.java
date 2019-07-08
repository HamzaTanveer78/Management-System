package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import classes.Attendance;
import classes.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class AttendanceViewController implements Initializable{
	@FXML
    private TableView<Attendance> attendancetable;
	  @FXML
	    private TableColumn<Attendance, String> idcol;

	    @FXML
	    private TableColumn<Attendance, Integer> namecol;
	 @FXML
	    private TableColumn<Attendance, Integer> colsubj1;

	    @FXML
	    private TableColumn<Attendance, Integer> colsubj2;

	    @FXML
	    private TableColumn<Attendance, Integer> colsubj3;

	    @FXML
	    private TableColumn<Attendance, Integer> colsubj4;

	    @FXML
	    private TableColumn<Attendance, Integer> colsubj5;

	    @FXML
	    private TableColumn<Attendance, Integer> colsubj6;
	    @FXML
	    private TableColumn<Attendance, String> colterm;
	    @FXML
	    private JFXComboBox<String> combodepartment;

	    @FXML
	    private JFXComboBox<String> comboclass;

	    @FXML
	    private JFXComboBox<String> combosemester;
	   

	    @FXML
	    private JFXComboBox<String> combosession;
	
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
		    comboclass.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
				 
				 
			      fetchSession();
			  });
		    
		    
		    fetchDepartment();
	     
		   buildTable();
		 
	}

	
	
	private void buildTable(){
		
	          idcol.setCellValueFactory(new PropertyValueFactory<>("id"));

		     namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		     colsubj1.setCellValueFactory(new PropertyValueFactory<>("subject1"));

		     colsubj2.setCellValueFactory(new PropertyValueFactory<>("subject2"));

		      colsubj3.setCellValueFactory(new PropertyValueFactory<>("subject3"));

		      colsubj4.setCellValueFactory(new PropertyValueFactory<>("subject4"));

		      colsubj5.setCellValueFactory(new PropertyValueFactory<>("subject5"));

		       colsubj6.setCellValueFactory(new PropertyValueFactory<>("subject6"));
		       colterm.setCellValueFactory(new PropertyValueFactory<>("term"));
		
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
	
	   @FXML
	  private void showRecord() {
		  attendancetable.getItems().clear();
	        try {
	            connection = db.getConnection();
	            
	        	String sql="SELECT a.stuid,s.stufname,s.stulname,s.stufathername,d.depname,"
	    				+ "cl.classname,s.stusession,a.courseid,a.semester,a.term,c.coursename,a.attendance "
	    				+ "from attendance a join student s on a.stuid=s.stuid join course c on a.courseid=c.courseid "
	    				+ "join department d on s.depid=d.depid join class cl on s.classid=cl.classid"
	    				+ " where d.depname='"+combodepartment.getValue()+"' and cl.classname='"+
	    				comboclass.getValue()+"' and a.semester="+combosemester.getValue()+" and s.stusession='"+combosession.getValue()+"'";
	        
	        	pst=connection.prepareStatement(sql);
                
	            result =pst.executeQuery(); 
	          
	            	int i=1;
	            	int sub1=0,sub2=0,sub3=0,sub4=0,sub5=0,sub6=0;
	            	
	             while(result.next()){ 
	            	 Attendance a=new Attendance();
	            	 a.setId(result.getString("stuid"));
	            	 a.setName(result.getString("stufname")+result.getString("stulname"));
	            	 a.setTerm(result.getString("term"));
	            	 if(i==7 )
	            		 i=1;
	            	 switch(i){
	            		 case 1:
	            			 colsubj1.setText(result.getString("coursename"));
	            			 sub1=result.getInt("attendance");
	            			 i++;
	            			 break;
	            		 case 2:
	            			 colsubj2.setText(result.getString("coursename"));
	            			 sub2=result.getInt("attendance");
	            			 i++;
	            			 break;
	            		 case 3:
		            			 colsubj3.setText(result.getString("coursename"));
		            			 sub3=result.getInt("attendance");
		            			 i++;
		            			 break;
		            		 case 4:
		            			colsubj4.setText(result.getString("coursename"));
		            			sub4=result.getInt("attendance");
		            			 i++;
		            			 break;
		            		 case 5:
			            			colsubj5.setText(result.getString("coursename"));
			            			sub5=result.getInt("attendance");
			            			 i++;
			            			 break;
			            		 case 6:
			            			colsubj6.setText(result.getString("coursename"));
			            			
			            			sub6=result.getInt("attendance");
			            			 i++;
			            			 show(a,sub1,sub2,sub3,sub4,sub5,sub6);
			            			 break;
			            			
	            		 }
	            	 
	             }
	            
	             } catch (SQLException ex) {
	                  System.out.println(ex);
	        }
	    }
	
	private void show(Attendance a,int sub1,int sub2,int sub3,int sub4,int sub5,int sub6){
		 a.setSubject1(sub1);
    	 a.setSubject2(sub2);
    	 a.setSubject3(sub3);
    	 a.setSubject4(sub4);
    	 a.setSubject5(sub5);
    	 a.setSubject6(sub6);
    	 attendancetable.getItems().add(a);	
	}
	
	
	
}
