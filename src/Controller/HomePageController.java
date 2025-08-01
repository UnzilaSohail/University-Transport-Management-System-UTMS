package Controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class HomePageController {

	

	

	    @FXML
	    private Button studentLoginButton;

	    @FXML
	    private Button adminLoginButton;

	    @FXML
	    void handleStudentLogin(ActionEvent event) {
	        showAlert("Student Login", "Redirecting to Student Login Page");
	        try {
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/login.fxml"));
	            Parent root = loader.load();
	            
	            // Get the main stage from the button's scene
	            Stage stage = (Stage) studentLoginButton.getScene().getWindow();
	            
	            // Set the student login page as the new scene
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }	    }

	    @FXML
	    void handleAdminLogin(ActionEvent event) {
	        showAlert("Admin Login", "Redirecting to Admin Login Page");
	        try {
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/loginadmin.fxml"));
	            Parent root = loader.load();
	            
	            // Get the main stage from the button's scene
	            Stage stage = (Stage) adminLoginButton.getScene().getWindow();
	            
	            // Set the student login page as the new scene
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }	    	    }

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	}

