package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.io.IOException;
//import UserInterface.*;
//import Controller.*;
public class adminDashboardController implements Initializable {

 @FXML
 private TableView<?> tableView; 
 @FXML
 private TableColumn<?, ?> idColumn; 
 @FXML
 private TableColumn<?, ?> nameColumn; 
 @FXML
 private TableColumn<?, ?> roleColumn; 
 @FXML
 private TableColumn<?, ?> statusColumn; 
@FXML
 private Button btnHomepage;
@FXML
private Button btnEvent;
 @FXML
 private Button btnManageStudents;
 @FXML
 private Button btnManageBuses;
 @FXML
 private Button btnBusTimings;
 @FXML
 private Button btnGenerateChallan;
 @FXML
 private Button btnLogout;
 @FXML
 private ImageView imgAdminInfo;
 @FXML
 private ImageView imgStudentInfo;
 @FXML
 private ImageView imgHome;
 @FXML
 private ImageView imgBus;
 @FXML
 private ImageView imgTime;
 @FXML
 private ImageView imgFee;
 @FXML
 private ImageView imgLogout;

 @Override
 public void initialize(URL url, ResourceBundle rb) {
     // Initialize any required data here
     loadStudents();
 }
 

 private void loadStudents() {
     // Simulating loading student names into ComboBox
   //  cmbStudents.getItems().addAll("Student A", "Student B", "Student C");
 }

 @FXML
 private void handleHomepageAction() {
     // Handle the action for Homepage button
     System.out.println("Homepage button clicked");
 }
 @FXML
 private BorderPane rootPane;


 @FXML
 private void handleManageStudentsAction() {
 	  try {
 		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/StudentInfo.fxml"));
           Parent root = loader.load();
           
           // Get the main stage from the button's scene
           Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
           
           // Set the adminFeeChallan page as the new scene
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
 }

 @FXML
 private void handleManageBusesAction() {
 	try {
 		FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/busroutemanagement.fxml"));
 		//loader.setController(new Controller.BusRouteManagementController());
		
		Parent root = loaderr.load();

          
          // Get the main stage from the button's scene
          Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
          
          // Set the adminFeeChallan page as the new scene
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
     System.out.println("Manage Buses button clicked");
 }
 @FXML
 private void handleEventAction() {
 	try {
 		FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/EventSchedule.fxml"));
 		//loader.setController(new Controller.BusRouteManagementController());
		
		Parent root = loaderr.load();

          
          // Get the main stage from the button's scene
          Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
          
          // Set the adminFeeChallan page as the new scene
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
     System.out.println("Manage Buses button clicked");
 }

 @FXML
 private void handleCoastersAndVansAction() {
	 try {
	 FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/coastervan.fxml"));
		//loader.setController(new Controller.BusRouteManagementController());
		
		Parent root = loaderr.load();

       
       // Get the main stage from the button's scene
       Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
       
       // Set the adminFeeChallan page as the new scene
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
 } catch (IOException e) {
       e.printStackTrace();
   }
  System.out.println("Manage Buses button clicked");
 }
 @FXML
 private void handleDriverAction() {
	 try {
	 FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/manage_driver.fxml"));
		//loader.setController(new Controller.BusRouteManagementController());
		
		Parent root = loaderr.load();

       
       // Get the main stage from the button's scene
       Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
       
       // Set the adminFeeChallan page as the new scene
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
 } catch (IOException e) {
       e.printStackTrace();
   }
  System.out.println("Manage Buses button clicked");
 }
 @FXML
 private void handleViewFeedbackAction() {
	 try {
	 FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/adminH.fxml"));
		//loader.setController(new Controller.BusRouteManagementController());
		
		Parent root = loaderr.load();

       
       // Get the main stage from the button's scene
       Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
       
       // Set the adminFeeChallan page as the new scene
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
 } catch (IOException e) {
       e.printStackTrace();
   }
  System.out.println("Manage Buses button clicked");
 }

 @FXML
 private void handleGenerateChallan() {
     try {
     	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/adminInvoice.fxml"));
         Parent root = loader.load();
         
         // Get the main stage from the button's scene
         Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
         
         // Set the adminFeeChallan page as the new scene
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
 @FXML
 private void handleStudentAttendanceAction() {
     try {
     	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/studentattendance.fxml"));
         Parent root = loader.load();
         
         // Get the main stage from the button's scene
         Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
         
         // Set the adminFeeChallan page as the new scene
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
 @FXML
 private void handleLogoutAction() {
	 
	     try {
	     	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/homepagee.fxml"));
	         Parent root = loader.load();
	         
	         // Get the main stage from the button's scene
	         Stage stage = (Stage) btnGenerateChallan.getScene().getWindow();
	         
	         // Set the adminFeeChallan page as the new scene
	         Scene scene = new Scene(root);
	         stage.setScene(scene);
	         stage.show();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	 }
 }

