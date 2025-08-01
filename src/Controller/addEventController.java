package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate; 
public class addEventController {


    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker eventDateField;

    @FXML
    private TextField eventTimeField;

    @FXML
    private TextField eventLocationField;


    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    @FXML
    void handleSaveButton(ActionEvent event) {
        String eventName = eventNameField.getText();
        LocalDate eventDate = eventDateField.getValue(); // Use getValue() instead of getText()
        String eventTime = eventTimeField.getText();
        String eventLocation = eventLocationField.getText();

        // Perform validation if needed

        // Insert event into the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO events (event_name, event_date, event_time, event_location) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, eventName);
            pstmt.setDate(2, java.sql.Date.valueOf(eventDate)); // Convert LocalDate to java.sql.Date
            pstmt.setString(3, eventTime);
            pstmt.setString(4, eventLocation);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Event added successfully.");
                // clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add event: " + e.getMessage());
        }
    }
  private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//  private void clearFields() {
//      eventNameField.clear();
//      eventDateField.setValue(null); // Set DatePicker to null for clearing
//      eventTimeField.clear();
//      eventLocationField.clear();
//  }
}
