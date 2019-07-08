package modules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
/*import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;*/


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;

import classes.DbHandler;
import classes.Student;
import classes.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class TeacherViewController implements Initializable{
	   

	    @FXML
	    private TableView<Teacher> teachertable;

	    @FXML
	    private TableColumn<Teacher, String> idcol;

	    @FXML
	    private TableColumn<Teacher,String> namecol;

	    @FXML
	    private TableColumn<Teacher,String> emailcol;

	    @FXML
	    private TableColumn<Teacher,String> contactcol;

	    @FXML
	    private TableColumn<Teacher,String> cniccol;

	    @FXML
	    private TableColumn<Teacher,String> gendercol;

	    @FXML
	    private TableColumn<Teacher,String> dobcol;

	    @FXML
	    private TableColumn<Teacher,String> educationcol;

	    @FXML
	    private TableColumn<Teacher,String> departmentcol;

	    @FXML
	    private TableColumn<Teacher,String> designationcol;

	    @FXML
	    private TableColumn<Teacher,String> typecol;

	    @FXML
	    private TableColumn<Teacher,String> hiredatecol;
	    @FXML
	    private JFXTextField searchID;
	    
	    @FXML
	    private JFXButton btnExport;

	    @FXML
	    private JFXButton btnSearch;
	    
	    Alert alert=new Alert(AlertType.ERROR) ;
	    Connection connection;
	    ResultSet result;
	     DbHandler db  ;
	    java.sql.PreparedStatement pst;
	    
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		
		setTeacherColumns();
		setTeacherRecord();
		
	}

	private void setTeacherColumns() {
		
		   idcol.setCellValueFactory(new PropertyValueFactory<>("tid"));

	        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

	         emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

	         contactcol.setCellValueFactory(new PropertyValueFactory<>("phone"));

	        cniccol.setCellValueFactory(new PropertyValueFactory<>("cnic"));

	        gendercol.setCellValueFactory(new PropertyValueFactory<>("gender"));

	        dobcol.setCellValueFactory(new PropertyValueFactory<>("dob"));
	        educationcol.setCellValueFactory(new PropertyValueFactory<>("education"));

	       departmentcol.setCellValueFactory(new PropertyValueFactory<>("depname"));

	       designationcol.setCellValueFactory(new PropertyValueFactory<>("designation"));
          typecol.setCellValueFactory(new PropertyValueFactory<>("type"));

	      hiredatecol.setCellValueFactory(new PropertyValueFactory<>("hiredate"));

		
	}
	@FXML
	private void setTeacherRecord() {
		     teachertable.getItems().clear();
		try {
			connection=db.getConnection();
			String sql="SELECT * from teacher";
           pst=connection.prepareStatement(sql);
           result=pst.executeQuery();
           while(result.next()) {
        	Teacher t=new Teacher();
        	t.setTid(result.getString("teacherid"));
        	t.setName(result.getString("teacherfname")+"  "+result.getString("teacherlname"));
        	t.setEmail(result.getString("teacheremail"));
        	t.setPhone(result.getString("teacherphone"));
            t.setAddress(result.getString("teacheraddress"));
        	t.setCnic(result.getString("teachercnic"));   
        	t.setDob(result.getDate("teacherdob").toString());  
        	t.setGender(result.getString("teachersex"));
        	t.setDepname(result.getString("teacherdepartment"));
        	t.setDesignation(result.getString("teacherdesignation"));
        	t.setEducation(result.getString("teachereducation"));
        	t.setHiredate(result.getString("teacherhiredate"));
        	t.setType(result.getString("teachertype"));
        	teachertable.getItems().add(t);
           }
			
		}
		catch(Exception e) {
			 alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
			
		}
		
	}
	
	@FXML
	private void searchTeacherRecord() {
		     teachertable.getItems().clear();
		    if(searchID.getText().trim().isEmpty()){
		    	 alert.setAlertType(AlertType.WARNING);
				 alert.setHeaderText("WARNING");
				 alert.setContentText("PLEASE ENTER TEACHER ID");
				 alert.show();
		    	
		    }
		    else{
		     try {
		 	connection=db.getConnection();
			String sql="SELECT * from teacher where teacherid='"+searchID.getText()+"'";
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
           
        	Teacher t=new Teacher();
        	t.setTid(result.getString("teacherid"));
        	t.setName(result.getString("teacherfname")+"  "+result.getString("teacherlname"));
        	t.setEmail(result.getString("teacheremail"));
        	t.setPhone(result.getString("teacherphone"));
            t.setAddress(result.getString("teacheraddress"));
        	t.setCnic(result.getString("teachercnic"));   
        	t.setDob(result.getDate("teacherdob").toString());  
        	t.setGender(result.getString("teachersex"));
        	t.setDepname(result.getString("teacherdepartment"));
        	t.setDesignation(result.getString("teacherdesignation"));
        	t.setEducation(result.getString("teachereducation"));
        	t.setHiredate(result.getString("teacherhiredate"));
        	t.setType(result.getString("teachertype"));
        	teachertable.getItems().add(t);
			} 
  			while (result.next());
  	}}

		catch(Exception e) {
			 alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
			
		}
		    }
	}
	
	
	
	
	 @FXML
	    private void printAction(ActionEvent event) throws JRException {


	        String report = "/reports/Teacher.jrxml";
	        connection = db.getConnection();
	        	        JasperReport jr = JasperCompileManager.compileReport(report);
	        JasperPrint jp = JasperFillManager.fillReport(jr, null, connection);
	        JasperViewer.viewReport(jp, false);
	    }
	 
	/* @FXML
	    private void exportAction(ActionEvent event) {
	        FileChooser chooser = new FileChooser();
	        // Set extension filter
	        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xls)", "*.xls");
	        chooser.getExtensionFilters().add(filter);
	        // Show save dialog
	        File file = chooser.showSaveDialog(btnExport.getScene().getWindow());
	        if (file != null) {
	            saveXLSFile(file);

	        }
	    }
	
	@FXML
	private void saveXLSFile(File file) {
        try {
            //System.out.println("Clicked,waiting to export....");
            connection = db.getConnection();
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("sheet 0");
            Row row1 = workSheet.createRow((short) 0);
            row1.createCell(0).setCellValue("EMPLOYEE ID");
            row1.createCell(1).setCellValue("NATIONAL ID");
            row1.createCell(2).setCellValue("SUR NAME");
            row1.createCell(3).setCellValue("MID NAME");
            row1.createCell(4).setCellValue("LAST NAME");
            row1.createCell(5).setCellValue("MOBILE NO");
            row1.createCell(6).setCellValue("REG DATE");
            row1.createCell(7).setCellValue("BIRTH DATE");
            row1.createCell(8).setCellValue("CATEGORY");
            row1.createCell(9).setCellValue("WAGE");
            row1.createCell(10).setCellValue("STATUS");
            Row row2;

            result = connection.createStatement().executeQuery("select * from mg_employees");
            while (result.next()) {
                int a = result.getRow();
                row2 = workSheet.createRow((short) a);
                row2.createCell(0).setCellValue(result.getInt(1));
                row2.createCell(1).setCellValue(result.getInt(2));
                row2.createCell(2).setCellValue(result.getString(3));
                row2.createCell(3).setCellValue(result.getString(4));
                row2.createCell(4).setCellValue(result.getString(5));
                row2.createCell(5).setCellValue(result.getInt(6));
                row2.createCell(6).setCellValue(result.getDate(7));
                row2.createCell(7).setCellValue(result.getDate(8));
                row2.createCell(8).setCellValue(result.getString(9));
                row2.createCell(9).setCellValue(result.getDouble(10));
                row2.createCell(10).setCellValue(result.getString(11));
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            result.close();

            connection.close();
            TrayNotification tn = new TrayNotification("NEW EXCEL FILE", "Specified excel file successfully generated", NotificationType.SUCCESS);
            tn.showAndWait();
        } catch (SQLException | IOException e) {
            TrayNotification tn = new TrayNotification("NEW EXCEL FILE", "Could not generate specified file", NotificationType.ERROR);
            tn.showAndWait();
            System.err.println(e);

        }
    }
*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
