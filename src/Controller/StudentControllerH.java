package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class StudentControllerH {


    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private TextField driverIdField;

    @FXML
    private TextField ratingField;
    
    @FXML
    private TextField busIdField;
    @FXML
    private Button btnFeeChallan;

    @FXML
    private Button btnTimings;

    @FXML
    private Button btnRoutes;

    @FXML
    private Button btnFeedback;
    @FXML
    private Button btnGenerateChallan;
    @FXML
    void handleSubmit(ActionEvent event) {
        String feedback = feedbackTextArea.getText().trim();
        String driverId = driverIdField.getText().trim();
        String busId= busIdField.getText().trim();
        String ratingStr = ratingField.getText().trim();

        if (feedback.isEmpty() || driverId.isEmpty() || busId.isEmpty() || ratingStr.isEmpty())
        {
            showAlert("Error", "Feedback Submission", "All fields must be filled.");
        }
//        else 
//        {
//            // Insert feedback into the database
//            insertFeedback(Integer.parseInt(driverId),Integer.parseInt(busId), Integer.parseInt(rating), feedback);
//        }
        int rating;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Feedback Submission", "Rating must be a number.");
            return;
        }

        if (rating < 1 || rating > 5) {
            showAlert("Error", "Feedback Submission", "Rating must be between 1 and 5.");
            return;
        }

        try {
            int driverIdInt = Integer.parseInt(driverId);
            int busIdInt = Integer.parseInt(busId);

            // Insert feedback into the database
            insertFeedback(driverIdInt, busIdInt, rating, feedback);
        } catch (NumberFormatException e) {
            showAlert("Error", "Feedback Submission", "Driver ID and Bus ID must be numbers.");
        }
    }
    @FXML
    void handleView(ActionEvent event) {
        // Add logic to handle the homepage button click
		/*
		 * VBox vbox = new VBox(); String imagePath =
		 * "C:\\Users\\eshal\\Downloads\\Picture" + userId + ".png";
		 * 
		 * 
		 * try { FileInputStream input = new FileInputStream(imagePath); Image image =
		 * new Image(input); ImageView imageView = new ImageView(image);
		 * vbox.getChildren().add(imageView); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); }
		 * 
		 * 
		 * Scene scene = new Scene(vbox, 800, 600); Stage stage = (Stage)
		 * btnView.getScene().getWindow(); stage.setScene(scene);
		 * stage.setTitle("QR Code Viewer"); stage.show();
		 * 
		 */
    	    // Add logic to handle the view button click
    	    Stage stage = new Stage();
    	    VBox vbox = new VBox();
    	    String imagePath = "C:\\Users\\eshal\\Downloads\\Picture21.png";

    	    try {
    	        FileInputStream input = new FileInputStream(imagePath);
    	        Image image = new Image(input);
    	        ImageView imageView = new ImageView(image);
    	        vbox.getChildren().add(imageView);
    	    } catch (FileNotFoundException e) {
    	        e.printStackTrace();
    	    }

    	    Scene scene = new Scene(vbox, 800, 600);
    	    stage.setScene(scene);
    	    stage.setTitle("QR Code Viewer");
    	    stage.show();
    	

    }
    @FXML
    void handleBtnHomepage(ActionEvent event) {
        // Add logic to handle the homepage button click
    }

    @FXML
    void handleBtnFeeChallan(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/feechallan.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    void handleBtnEvents(ActionEvent event) {
        // Add logic to handle the timings button click
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/EventSchedule2.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
            stage.show();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @FXML
    void handleRoutes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/busroute.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
            stage.show();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFeedback() {
   	 try {
   	 FXMLLoader loaderr = new FXMLLoader(getClass().getResource("/UserInterface/studentH.fxml"));
   		//loader.setController(new Controller.BusRouteManagementController());
   		
   		Parent root = loaderr.load();

          
          // Get the main stage from the button's scene
          Stage stage = (Stage) btnFeedback.getScene().getWindow();
          
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
    void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/StudentDashboard.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Feedback System");        
        stage.show();
    }
    
    private void insertFeedback(int driverId,int busId, int rating, String comments) {
        String url = "jdbc:mysql://localhost:3307/utmss";
        String user = "root";
        String password = "Kuwait914";

        String query = "INSERT INTO FeedbackTr (driver_id,bus_Id, rating, comments, feedback_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverId);
            pstmt.setInt(2, busId);
            pstmt.setInt(3, rating);
            pstmt.setString(4, comments);
            pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

            pstmt.executeUpdate();

            showAlert("Success", "Feedback Submission", "Feedback submitted successfully.");
            feedbackTextArea.clear();
            driverIdField.clear();
            busIdField.clear();
            ratingField.clear();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            showAlert("Error", "Feedback Submission", "Failed to submit feedback.");
        }
    }
    @FXML
    void handleBtnLogout(ActionEvent event) {
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
    void handleBtnhome(ActionEvent event) {
    	try {
         	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/StudentDashboard.fxml"));
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
    

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
