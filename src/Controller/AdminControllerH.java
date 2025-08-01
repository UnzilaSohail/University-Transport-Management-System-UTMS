package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminControllerH {

	@FXML
	 private Button btnGenerateChallan;
    @FXML
    private TextArea feedbackDisplayTextArea;

    @FXML
    private TextField busIdField;

    @FXML
    void initialize() 
    {
        loadAllFeedbackFromDatabase();
    }

    @FXML
    void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/adminDashboard.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Feedback System");        
        stage.show();
    }

    private void loadAllFeedbackFromDatabase() {
        String url = "jdbc:mysql://localhost:3307/utmss";
        String user = "root";
        String password = "Kuwait914";

        String query = "SELECT * FROM FeedbackTr";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        	
            StringBuilder feedback = new StringBuilder();
            while (rs.next()) {
                feedback.append("ID: ").append(rs.getInt("feedback_id")).append("\n")
                        .append("Driver ID: ").append(rs.getInt("driver_id")).append("\n")
                        .append("BusID: ").append(rs.getInt("bus_Id")).append("\n")
                        .append("Rating: ").append(rs.getInt("rating")).append("\n")
                        .append("Comments: ").append(rs.getString("comments")).append("\n")
                        .append("Date: ").append(rs.getDate("feedback_date")).append("\n\n");
            }
            feedbackDisplayTextArea.setText(feedback.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error loading all feedback from database: " + e.getMessage());
        }
    }
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
    }}
    


