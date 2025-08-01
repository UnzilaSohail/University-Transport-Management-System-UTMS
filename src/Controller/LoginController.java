package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class LoginController {


    public static String username;

	@FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root"; // replace with your database username
    private final String DB_PASSWORD = "Kuwait914"; // replace with your database password

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
         username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and Password cannot be empty.");
            return;
        }

        if (validateLogin(username, password)) {
            try {
                // Load the student dashboard FXML file
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/StudentDashboard.fxml"));
                Parent root = loader.load();

                // Get the reference to the current stage
                Stage stage = (Stage) usernameField.getScene().getWindow();

                // Set the student dashboard as the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load student dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
        }
    }
    private boolean validateLogin(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database.");
        }

        return isValid;
    }
    @FXML
    private Button backButton;
    @FXML
    void handleBackButtonAction(ActionEvent event) {
    	  try {
    	        // Load the homepage FXML file
    		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/homepagee.fxml"));
    	        Parent root = loader.load();
    	        
    	        // Get the reference to the current stage
    	        Stage stage = (Stage) backButton.getScene().getWindow();
    	        
    	        // Set the homepage as the new scene
    	        Scene scene = new Scene(root);
    	        stage.setScene(scene);
    	        stage.show();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
