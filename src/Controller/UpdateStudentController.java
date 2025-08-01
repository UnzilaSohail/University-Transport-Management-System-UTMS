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
public class UpdateStudentController {


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
    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    private String currentUsername;

    @FXML
    void updateStudent(ActionEvent event) {
        String newUsername = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String routeName = routeNameField.getText();
        double fee = Double.parseDouble(feeField.getText()); 

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE students1 SET first_name = ?, last_name = ?, email = ?, username = ?, route_name = ?, fee = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, newUsername); 
            pstmt.setString(5, routeName); 
            pstmt.setDouble(6, fee); 
            pstmt.setString(7, currentUsername); 

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
                closeDialog();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update student: " + e.getMessage());
        }
    }

    @FXML
    void cancelUpdate(ActionEvent event) {
        closeDialog();
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

    public void initData(String username, String firstName, String lastName, String email, String routeName, double fee) {
        currentUsername = username;
        usernameField.setText(username);
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        routeNameField.setText(routeName);
        feeField.setText(String.valueOf(fee));
    }


}
