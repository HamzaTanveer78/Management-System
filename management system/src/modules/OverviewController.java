/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import classes.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OverviewController implements Initializable {

    @FXML
    private HBox groupHolder;
    @FXML
    private Group groupRegistered;
    @FXML
    private Group groupTarget;
    @FXML
    private Group groupGents;
    @FXML
    private Group groupLadies;
    
    @FXML
    private Label lblstudent;
    @FXML
    private Label lblteacher;
    @FXML
    private Label lbldepartment;
    @FXML
    private Label lblclass;
    @FXML
    private PieChart piechartcourse;
    @FXML
    private PieChart piechartdepartment;
    
    private DbHandler db;
    private Connection connection;
    private Statement stat;
    private ResultSet result;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRipples();
        db=new DbHandler();
        
        loadPieChartCourses();
        loadPieChartDepartment();
        setStudentValues();
        setTeacherValues();
        setDepartmentValues();
        setClassValues();
      
    }

    private void setRipples() {
        JFXRippler rippler1 = new JFXRippler(groupRegistered);
        JFXRippler rippler2 = new JFXRippler(groupGents);
        JFXRippler rippler3 = new JFXRippler(groupLadies);
        JFXRippler rippler4 = new JFXRippler(groupTarget);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler2.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler3.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler4.setMaskType(JFXRippler.RipplerMask.RECT);

        rippler1.setRipplerFill(Paint.valueOf("#1564C0"));
        rippler2.setRipplerFill(Paint.valueOf("#00AACF"));
        rippler3.setRipplerFill(Paint.valueOf("#00B3A0"));
        rippler4.setRipplerFill(Paint.valueOf("#F87951"));

        groupHolder.getChildren().addAll(rippler1, rippler2, rippler3, rippler4);
    }
 
    private void loadPieChartCourses(){
    	
    	try{
    	ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
    	         String sql="select classname,count(classid) from class group by classname";
    	         connection=db.getConnection();
    	         stat=connection.createStatement();
    	         result=stat.executeQuery(sql);
    	         while(result.next()){
    	        	 pieChartData.add(new PieChart.Data(result.getString(1), result.getInt(2)));
    	         }
    			
    	         piechartcourse.setData(pieChartData);
    	
    	}
    	catch(Exception e){
    		
    	}
    	
    }
    
    private void loadPieChartDepartment(){
    	try{
        	ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
        	         String sql="select d.depname, count(s.stuid) from "
        	         		+ "student s join department d on s.depid=d.depid group by d.depname";
        	         connection=db.getConnection();
        	         stat=connection.createStatement();
        	         result=stat.executeQuery(sql);
        	         while(result.next()){
        	        	 pieChartData.add(new PieChart.Data(result.getString(1), result.getInt(2)));
        	         }
        			
        	         piechartdepartment.setData(pieChartData);
        	
        	}
        	catch(Exception e){
        		
        	}
    }
    
   
   private void setStudentValues(){
    	
	   try{
		   connection=db.getConnection();
		   String sql="SELECT count(stuid) from student";
		   stat=connection.createStatement();
		   result=stat.executeQuery(sql);
		   while(result.next()){
			   lblstudent.setText(""+result.getInt(1));
			   
		   }
		   
	   }catch(Exception e){
		   System.out.println(e);
	   }
	   
    };
    private void setTeacherValues(){
    	  try{
   		   connection=db.getConnection();
   		   String sql="SELECT count(teacherid) from teacher";
   		   stat=connection.createStatement();
   		   result=stat.executeQuery(sql);
   		   while(result.next()){
   			   lblteacher.setText(""+result.getInt(1));
   			   
   		   }
   		   
   	   }catch(Exception e){
   		   System.out.println(e);
   	   }
   	   
    };
    private void setDepartmentValues(){
    	  try{
   		   connection=db.getConnection();
   		   String sql="SELECT count(depid) from department";
   		   stat=connection.createStatement();
   		   result=stat.executeQuery(sql);
   		   while(result.next()){
   			   lbldepartment.setText(""+result.getInt(1));
   			   
   		   }
   		   
   	   }catch(Exception e){
   		   System.out.println(e);
   	   }
   	   
    };
    private void setClassValues(){
    	  try{
   		   connection=db.getConnection();
   		   String sql="SELECT count(classid) from class";
   		   stat=connection.createStatement();
   		   result=stat.executeQuery(sql);
   		   while(result.next()){
   			   lblclass.setText(""+result.getInt(1));
   			   
   		   }
   		   
   	   }catch(Exception e){
   		   System.out.println(e);
   	   }
   	   
    };

}
