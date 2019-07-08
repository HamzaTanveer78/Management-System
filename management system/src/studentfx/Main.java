/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfx;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author saim
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       try { 
    	   
    	Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
    	JFXDecorator decorator=new JFXDecorator(stage, root);
    	stage.setTitle("STUDENT INFORMSTION SYSTEM");
		stage.getIcons().add(new Image(getClass().getResource("/icons/sis1.png").toExternalForm()));
   
        Scene scene = new Scene(decorator);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
       
       
       } 
       catch(Exception e) {
    	   System.out.println(e);
    	   
       }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
