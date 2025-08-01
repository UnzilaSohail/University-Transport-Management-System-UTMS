package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentAddController {


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField routeNameField;


    @FXML
    private TextField feeField;

  @FXML  
    private TextField passwordField;
  
  
    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String routeName = routeNameField.getText();
        String password = passwordField.getText();
        double fee = Double.parseDouble(feeField.getText());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || routeName.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter all fields");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connection successful");

            String query = "INSERT INTO students1 (first_name, last_name, username, email, route_name, fee, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, routeName);
            pstmt.setDouble(6, fee);
            pstmt.setString(7, password);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");
                closeDialog();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add student: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeDialog() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
