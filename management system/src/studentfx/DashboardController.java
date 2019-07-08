/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToolbar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class DashboardController implements Initializable {

    private Label lblDash;
    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane holderPane;
    @FXML
    private AnchorPane sideAnchor;
    @FXML
    private Label lblMenu;
    @FXML
    private JFXToolbar toolBar;
    @FXML
    private HBox toolBarRight;
    @FXML
    private VBox overflowContainer;
    @FXML
    private ToggleButton menuHome;
    @FXML
    private ToggleButton menuAdd;
    @FXML
    private ToggleButton menuList;
    @FXML
    private ToggleButton menuLogg;

    private AnchorPane home, addresult, department,subject,
    student,viewstudent,attendance,teacherregister,teacherview,viewresult,printresult,
    subjectmapping,viewattendance,fee,subjectwise;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnView;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnCourse;
    @FXML
    private JFXButton btnAdddepartment;
    @FXML
    private JFXButton btnRegisterStudent;
    @FXML
    private JFXButton btnViewStudents;
    @FXML
    private JFXButton btnAddResult;
    @FXML
    private JFXButton btnFee;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      /*  JFXRippler fXRippler = new JFXRippler(lblDash);
        JFXRippler fXRippler2 = new JFXRippler(lblMenu);
        fXRippler2.setMaskType((JFXRippler.RipplerMask.RECT));
        sideAnchor.getChildren().add(fXRippler);
        toolBarRight.getChildren().add(fXRippler2);*/
    //    openMenus();
        createPages();

    }

    private void openMenus() {
        JFXPopup popup = new JFXPopup();
        popup.setContent(overflowContainer);
        popup.setPopupContainer(stackPane);
        popup.setSource(lblMenu);
        lblMenu.setOnMouseClicked((MouseEvent e) -> {
            popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -10, 40);
        });

    }

    //Set selected node to a content holder
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    //Load all fxml files to a cahce for swapping
    private void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("/modules/Overview.fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void openHome(ActionEvent event) {
    	 try {
             home = FXMLLoader.load(getClass().getResource("/modules/Overview.fxml"));
             setNode(home);
         } catch (IOException ex) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    	
    	   }

    @FXML
    private void openDepartment(ActionEvent event) {
    	 try {
			department = FXMLLoader.load(getClass().getResource("/modules/Department.fxml"));
			setNode(department);
    	 } catch (IOException e) {
			
			e.printStackTrace();
		}
        
    }
    @FXML
    private void openSubject(ActionEvent event) {
    	 try {
    		 subject = FXMLLoader.load(getClass().getResource("/modules/Subject.fxml"));
    	        setNode(subject);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}   
    	 
    }
    @FXML
    private void openSubjectMapping(){
    	 try {
    		 subjectmapping = FXMLLoader.load(getClass().getResource("/modules/SubjectMapping.fxml"));
    	        setNode(subjectmapping);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}  
    }
    @FXML
    private void openRegisterStudent(ActionEvent event) {
    	 try {
    		 student = FXMLLoader.load(getClass().getResource("/modules/Register.fxml"));
    	        setNode(student);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	
    }
    @FXML
    private void openViewStudent(ActionEvent event) {
    	 try {
    		 viewstudent = FXMLLoader.load(getClass().getResource("/modules/Profile.fxml"));
    	        setNode(viewstudent);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	  
    }

    
    @FXML
    private void openAddResult(ActionEvent event) {
    	 try {
    		  addresult = FXMLLoader.load(getClass().getResource("/modules/Result.fxml"));
    	        setNode(addresult);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	 
    }
    @FXML
    private void openResultView(ActionEvent event) {
    	 try {
    		 viewresult=FXMLLoader.load(getClass().getResource("/modules/ResultView.fxml"));
    	        setNode(viewresult);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	
    }
    
    @FXML
    private void openFee(ActionEvent event) {
    	 try {
    		 fee=FXMLLoader.load(getClass().getResource("/modules/FeeManagement.fxml"));
    	        setNode(fee);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	
    }
    
    @FXML
    private void openAttendanceModule(ActionEvent event) {
    	 try {
    		  attendance = FXMLLoader.load(getClass().getResource("/modules/Attendance.fxml"));
    		  setNode(attendance);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
       
        
    }
    @FXML
    private void openAttendanceView(ActionEvent event) {
    	 try {
    		  viewattendance = FXMLLoader.load(getClass().getResource("/modules/AttendanceView.fxml"));
    		  setNode(viewattendance);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
       
        
    }
    @FXML
    private void openTeacherRegister(ActionEvent event) {
    	 try {
    		 teacherregister=FXMLLoader.load(getClass().getResource("/modules/TeacherRegister.fxml"));
             
    	        setNode(teacherregister);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	  
    }
   
    @FXML
    private void openSubjectWise(ActionEvent event) {
    	 try {
    		 subjectwise=FXMLLoader.load(getClass().getResource("/modules/SubjectWiseView.fxml"));
             
    	        setNode(subjectwise);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	  
    }
    @FXML
    private void openTeacherView(ActionEvent event) {
    	 try {
    		 teacherview=FXMLLoader.load(getClass().getResource("/modules/TeacherView.fxml"));
    	        setNode(teacherview);
     	 } catch (IOException e) {
 			
 			e.printStackTrace();
 		}
    	 
    }
    /*@FXML
    private void openAssignCourse(){
    	try {
   		 assigncourse=FXMLLoader.load(getClass().getResource("/modules/AssignCourse.fxml"));
   	        setNode(teacherview);
    	 } catch (IOException e) {
			
			e.printStackTrace();
		}
    }*/
    @FXML
    private void logOff(ActionEvent event) {
    	        btnLogout.getScene().getWindow().hide();
           try{
        	   Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
        	   Stage stage=new Stage();
        	   JFXDecorator decorator=new JFXDecorator(stage,root);
        	   stage.getIcons().add(new Image(getClass().getResource("/icons/sis1.png").toExternalForm()));
        	   Scene scence =new Scene(decorator);
        	   stage.setScene(scence);
        	   stage.setResizable(false);
        	   stage.show();
        	   
           }
           catch(Exception e){
        	   System.out.println(e);
           }
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
