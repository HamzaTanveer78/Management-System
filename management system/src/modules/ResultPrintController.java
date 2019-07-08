package modules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import classes.DbHandler;
import classes.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResultPrintController  implements Initializable{
	@FXML
    private JFXComboBox<String> combodepartment;

    @FXML
    private JFXComboBox<String> combocourse;

    @FXML
    private JFXComboBox<String> combosemester;

    @FXML
    private JFXTextField txtsid;

    @FXML
    private ImageView imageview;

    @FXML
    private Label lblrollno;

    @FXML
    private Label lblsname;

    @FXML
    private Label lblsession;

    @FXML
    private Label lblfathername;

    @FXML
    private Label lbldepname;

    @FXML
    private Label lblclass;

    @FXML
    private Label lblsubject6;

    @FXML
    private Label lblsubject5;

    @FXML
    private Label lblsubject4;

    @FXML
    private Label lblsubject3;

    @FXML
    private Label lblsubject2;

    @FXML
    private Label lblsubject1;

    @FXML
    private Label lblmarks2;

    @FXML
    private Label lblmarks1;

    @FXML
    private Label lblsubjectname1;

    @FXML
    private Label lblsubjectname2;

    @FXML
    private Label lblsubjectname3;

    @FXML
    private Label lblsubjectname4;

    @FXML
    private Label lblsubjectname5;

    @FXML
    private Label lblcredithours1;

    @FXML
    private Label lblsubjectname6;

    @FXML
    private Label lblcredithours2;

    @FXML
    private Label lblcredithours3;

    @FXML
    private Label lblcredithours4;

    @FXML
    private Label lblmarks6;

    @FXML
    private Label lblcredithours5;

    @FXML
    private Label lblmarks3;

    @FXML
    private Label lblmarks4;

    @FXML
    private Label lblmarks5;

    @FXML
    private Label lblcredithours6;
    @FXML
    private Label lblsemester;
    @FXML
    private JFXComboBox comsemester;
    Alert alert=new Alert(AlertType.ERROR) ;
	    Image image;
	    
	    DbHandler db;
	    Connection connection;
	    ResultSet result;
	    PreparedStatement pst;
	    
	    
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db=new DbHandler();
		ObservableList<String> sem=FXCollections.observableArrayList("1","2",
				"3","4","5","6","7","8","9","10");
		comsemester.setItems(sem);
		
		
	}

     @FXML
	private void showResult() {
		     clearFields();
	            String id=txtsid.getText().toString();
	            Integer id2=Integer.parseInt(id);
	            int id3=id2.intValue();
	            String sem1=(String) comsemester.getValue();
	            Integer sem2=Integer.parseInt(sem1);
	            int sem=sem2.intValue();
		try {
			connection=db.getConnection();
			String sql="SELECT r.stuid,s.stufname,s.stulname,s.stufathername,d.depname,"
					+ "cl.classname,s.stusession,r.courseid,r.semester,c.coursename,c.credithours,s.stuimage, r.marks "
					+ "from result r join student s on r.stuid=s.stuid join course c on r.courseid=c.courseid "
					+ "join department d on s.depid=d.depid join class cl on s.classid=cl.classid "
					+ "where r.stuid="+id3+" and r.semester="+sem;
			pst=connection.prepareStatement(sql);
			result=pst.executeQuery();
			
			int value=1;
			while(result.next()) {
				lblrollno.setText(""+result.getInt("stuid"));
				lblsname.setText(result.getString("stufname")+" "+result.getString("stulname"));
				lblfathername.setText(result.getString("stufathername"));
				lblsession.setText(result.getString("stusession"));
				//lblsemester.setText(result.getString("semester"));
				lbldepname.setText(result.getString("depname"));
				lblclass.setText(result.getString("classname"));
				lblsemester.setText(""+result.getInt("semester"));
			    InputStream is=result.getBinaryStream("stuimage");
				System.out.println("after image");
				OutputStream os=new FileOutputStream(new File("photo.jpg"));
				byte[] content=new byte[1024];
				int size=0;
				while((size= is.read(content))!= -1) {
					os.write(content, 0, size);
				}
				
				image=new Image("file:photo.jpg",100,150,true,true);
				imageview.setImage(image);
				switch(value) {
				case 1:
					lblsubject1.setText(""+result.getInt("courseid"));
					lblsubjectname1.setText(result.getString("coursename"));
					lblcredithours1.setText(result.getString("credithours"));
					lblmarks1.setText(""+result.getInt("marks"));
					value++;
					break;
				case 2:
					lblsubject2.setText(""+result.getInt("courseid"));
					lblsubjectname2.setText(result.getString("coursename"));
					lblcredithours2.setText(result.getString("credithours"));
					lblmarks2.setText(""+result.getInt("marks"));
					value++;
					break;
				case 3:
					lblsubject3.setText(""+result.getInt("courseid"));
					lblsubjectname3.setText(result.getString("coursename"));
					lblcredithours3.setText(result.getString("credithours"));
					lblmarks3.setText(""+result.getInt("marks"));
					value++;
					break;
				case 4:
					lblsubject4.setText(""+result.getInt("courseid"));
					lblsubjectname4.setText(result.getString("coursename"));
					lblcredithours4.setText(result.getString("credithours"));
					lblmarks4.setText(""+result.getInt("marks"));
					value++;
					break;
				case 5:
					lblsubject5.setText(""+result.getInt("courseid"));
					lblsubjectname5.setText(result.getString("coursename"));
					lblcredithours5.setText(result.getString("credithours"));
					lblmarks5.setText(""+result.getInt("marks"));
					value++;
					break;
				case 6:
					lblsubject6.setText(""+result.getInt("courseid"));
					lblsubjectname6.setText(result.getString("coursename"));
					lblcredithours6.setText(result.getString("credithours"));
					lblmarks6.setText(""+result.getInt("marks"));
					value++;
					break;
				
				
		      }
				}
			
			
			
		}
		catch(Exception e)
		{
			  alert.setHeaderText("Operation Failed");
	    	  alert.setContentText(e.toString());
	    	  alert.showAndWait();
		}
	}
     
     private void clearFields(){
    	 lblsname.setText(null);
    	 lblfathername.setText(null);
    	 lbldepname.setText(null);
    	 lblclass.setText(null);
    	 lblsession.setText(null);
    	 lblrollno.setText(null);
    	 lblsemester.setText(null);
    	 Image img=null;
    	 imageview.setImage(img);
    	 
    	 lblsubject1.setText(null);
    	 lblsubjectname1.setText(null);
    	 lblcredithours1.setText(null);
    	 lblmarks1.setText(null);
    	 
    	 lblsubject2.setText(null);
    	 lblsubjectname2.setText(null);
    	 lblcredithours2.setText(null);
    	 lblmarks2.setText(null);
    	 
    	 lblsubject3.setText(null);
    	 lblsubjectname3.setText(null);
    	 lblcredithours3.setText(null);
    	 lblmarks3.setText(null);
    	 
    	 lblsubject4.setText(null);
    	 lblsubjectname4.setText(null);
    	 lblcredithours4.setText(null);
    	 lblmarks4.setText(null);
    	 
    	 lblsubject5.setText(null);
    	 lblsubjectname5.setText(null);
    	 lblcredithours5.setText(null);
    	 lblmarks5.setText(null);
    	 
    	 lblsubject6.setText(null);
    	 lblsubjectname6.setText(null);
    	 lblcredithours6.setText(null);
    	 lblmarks6.setText(null);
    	 
    	 
     }
}
