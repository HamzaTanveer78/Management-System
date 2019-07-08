package modules;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import classes.DbHandler;
import classes.Fee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class FeeController implements Initializable{

    @FXML
    private JFXTextField txtsid;

    @FXML
    private JFXTextField txtchallanno;

    @FXML
    private JFXTextField txtamount;
    @FXML
    private JFXDatePicker feedata;

    @FXML
    private JFXButton btnSave;
    @FXML
    private TableView<Fee> feetable;
    @FXML
    private TableColumn<Fee, String> sidcol;

    @FXML
    private TableColumn<Fee, String> snamecol;

    @FXML
    private TableColumn<Fee, String> semestercol;

    @FXML
    private TableColumn<Fee, String> challancol;

    @FXML
    private TableColumn<Fee, String> amountcol;

    @FXML
    private TableColumn<Fee, String> datecol;

    @FXML
    private JFXTextField searchID;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<String> combodepartment;

    @FXML
    private JFXComboBox<String> comboclass;

    @FXML
    private JFXComboBox<String> combosession;
    @FXML
    private JFXComboBox<String> combosemester;
    @FXML
    private JFXComboBox<String> combosemesterfee;
    
    Alert alert=new Alert(AlertType.ERROR) ;
    TrayNotification tray=new TrayNotification();
    DbHandler db;
    Connection connection;
    PreparedStatement pst;
    ResultSet result;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		 ObservableList<String> sem=FXCollections.observableArrayList("1","2","3",
		    		"4","5","6","7","8");
		    combosemester.setItems(sem);
		    combosemesterfee.setItems(sem);
		    
		    
		    combodepartment.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
				 
				  int id=fetchDepID(combodepartment.getValue());
			      fetchClass(id);
				  
			  });
			   
			    comboclass.getSelectionModel().selectedItemProperty().addListener((v,old_val,new_val)->{
					 
					 
				      fetchSession();
				  });
			    
			    
			    fetchDepartment();
		    
		    
		    
		    
		buildFeeTable();
		
	}
	
	
	private void buildFeeTable(){
	    sidcol.setCellValueFactory(new PropertyValueFactory("stuid"));

	   snamecol.setCellValueFactory(new PropertyValueFactory("stuname"));

	   semestercol.setCellValueFactory(new PropertyValueFactory("semester"));

	   challancol.setCellValueFactory(new PropertyValueFactory("challanno"));

	  amountcol.setCellValueFactory(new PropertyValueFactory("amount"));

	   datecol.setCellValueFactory(new PropertyValueFactory("date"));
	}
	
	
	@FXML
	private void saveFee(){
		
		try{
			
		if(validateFields()==true){
			connection=db.getConnection();
	
			String sql="INSERT INTO fee(stuid,semester,challanno,amount,paiddate) values(?,?,?,?,?)";
			pst=connection.prepareStatement(sql);
			pst.setString(1, txtsid.getText().toString());
			pst.setString(2, combosemesterfee.getValue());
			pst.setString(3, txtchallanno.getText().toString());
			pst.setString(4, txtamount.getText().toString());
			pst.setDate(5, Date.valueOf(feedata.getValue()));
			
			int success=0;
			success=pst.executeUpdate();
			
			if(success==1){
				clearFields();
   			 tray.setTray("SUCCESS", "FEE SAVED SUCCESSFULLY",
						new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.POPUP);
	            tray.showAndDismiss(Duration.seconds(2));	
			}
			
		}	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
	}
	
	@FXML 
	private void showFeeRecord(){
		feetable.getItems().clear();
		try{
			connection=db.getConnection();
			
			//String sql="select f.stuid,s.stufname,s.stulname,f.semester,f.challanno,f.amount,f.paiddate"
				//	+ " from fee f join student s on f.stuid=s.stuid";
			
			String sql="select f.stuid ,s.stulname,s.stufname,f.semester,f.challanno,f.amount,f.paiddate from fee f join student s on f.stuid=s.stuid"
               +" join department d on s.depid=d.depid join class c on s.classid=c.classid"
                 +" where d.depname='"+combodepartment.getValue()+"' and c.classname='"+comboclass.getValue()+"' and f.semester='"+combosemester.getValue()+"' and s.stusession='"+combosession.getValue()+"'";
			pst=connection.prepareStatement(sql);
			/*pst.setString(1, combodepartment.getValue().toString());
			pst.setString(2, comboclass.getValue().toString());
			pst.setString(3, combosession.getValue().toString());
			pst.setString(4, combosemester.getValue().toString());*/
			result=pst.executeQuery();
			while(result.next()){
				Fee f=new Fee();
				f.setStuid(result.getString(1));
				f.setStuname(result.getString(2)+" "+result.getString(3));
				f.setSemester(result.getString(4));
				f.setChallanno(result.getString(5));
				f.setAmount(result.getString(6));
				f.setDate(result.getDate(7).toString());
				feetable.getItems().add(f);
			}
			
			
			
			
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	
	
	@FXML 
	private void searchFeeRecord(){
		feetable.getItems().clear();
		if(searchID.getText().trim().isEmpty()){
			alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Enter Student ID");
			alert.show();
		}
		else{
		try{
			connection=db.getConnection();
			
			String sql="select f.stuid ,s.stulname,s.stufname,f.semester,f.challanno,f.amount,f.paiddate from fee f join student s on f.stuid=s.stuid"
               +" join department d on s.depid=d.depid join class c on s.classid=c.classid"
                 +" where f.stuid='"+searchID.getText()+"'";
			pst=connection.prepareStatement(sql);
			
			result=pst.executeQuery();
			if(result.next() == false){
				 alert.setAlertType(AlertType.WARNING);
				 alert.setHeaderText("NO RECORD");
				 alert.setContentText("NO RECORD EXISTS AGAINST ENTER ID");
				 alert.show();
			}
			else {
				   do {
					Fee f=new Fee();
					f.setStuid(result.getString(1));
					f.setStuname(result.getString(2)+" "+result.getString(3));
					f.setSemester(result.getString(4));
					f.setChallanno(result.getString(5));
					f.setAmount(result.getString(6));
					f.setDate(result.getDate(7).toString());
					feetable.getItems().add(f);
				   } 
		  			while (result.next());
		  	}}
		
		catch(Exception e){
			System.out.println(e);
		}
		}
		
	}
	
	
	
	
	
	
	
	
	
	private boolean validateFields(){
		boolean ans=false;
		if(txtsid.getText().isEmpty()||txtsid.getText().trim().isEmpty()){
			alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Enter Student ID");
			alert.show();
			
		}
		else if(combosemesterfee.getValue()==null){
			alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Select Semester");
			alert.show();
			
		}else if(txtchallanno.getText().isEmpty()||txtchallanno.getText().trim().isEmpty()){
			
			alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Enter Challan No");
			alert.show();
		}
        else if(txtamount .getText().isEmpty()||txtamount .getText().trim().isEmpty()){
        	alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Enter Amount");
			alert.show();
			
		}else if(feedata.getValue()==null){
			alert.setHeaderText("WARNING");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Please Enter Date");
			alert.show();
			
		}
		
		else{
			ans=true;
		}
		
		
		return ans;
	}
	
	
	private void clearFields(){
		
		
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
