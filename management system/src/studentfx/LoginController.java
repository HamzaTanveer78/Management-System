
package studentfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import classes.DbHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class LoginController implements Initializable {

   
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;
     
    Alert alert=new Alert(AlertType.ERROR);
    TrayNotification tray=new TrayNotification();
    DbHandler db;
    Connection connection;
    ResultSet result;
      PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       db=new DbHandler();
        imgProgress.setVisible(false);
    }

    @FXML
    private void login(ActionEvent event) {
    	 
        imgProgress.setVisible(true);
       
        	if(txtUsername.getText().isEmpty()||txtUsername.getText().trim().isEmpty()){
        		alert.setAlertType(AlertType.WARNING);
        		alert.setHeaderText("WARNING");
        		alert.setContentText("USER NAME FIELD IS EMPTY");
        		alert.show();
        	}
        	else if(txtPassword.getText().isEmpty()||txtPassword.getText().trim().isEmpty()){
        		alert.setAlertType(AlertType.WARNING);
        		
        		alert.setHeaderText("WARNING");
        		alert.setContentText("PASSWORD FIELD IS EMPTY");
        		alert.show();
        	}
        	else{
        		if(txtUsername.getText().contains("saim")&&txtPassword.getText().contains("1234")){
        			 completeLogin(); 	
        		}
        		else{
        			alert.setHeaderText("CAN'T LOGIN");
            		alert.setContentText("USERNAME OR PASSWORD IS WRONG");
            		alert.show();
        			
        		}
        		
        	}
        
    }

    

    private void completeLogin() {
        btnLogin.getScene().getWindow().hide();
        tray.setTray("LOGED IN", "WELCOME TO THE STUDENT INFORMATION SYSTEM",
				new Image(getClass().getResource("/icons/success.png").toExternalForm()), Color.BLUE, AnimationType.SLIDE);
        tray.showAndDismiss(Duration.seconds(2));
        
        try {
            imgProgress.setVisible(false);
            Stage dashboardStage = new Stage();
            
            dashboardStage.setTitle("STUDENT INFORMAION SYSTEM");
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            JFXDecorator decorator=new JFXDecorator(dashboardStage, root);
            dashboardStage.getIcons().add(new Image(getClass().getResource("/icons/sis1.png").toExternalForm()));
            Scene scene = new Scene(decorator);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (IOException ex) {
          System.out.println("error");
        }

    }

}
