/*
 * package Controller; import javafx.event.ActionEvent; import javafx.fxml.FXML;
 * import javafx.fxml.FXMLLoader; import javafx.scene.Parent; import
 * javafx.scene.Scene; import javafx.scene.control.Alert; import
 * javafx.scene.control.Button; import javafx.scene.control.DatePicker; import
 * javafx.scene.control.TextField; import java.time.LocalDate; import
 * java.time.LocalTime; import javafx.stage.Stage;
 * 
 * import java.io.IOException; import java.sql.Connection; import java.sql.Date;
 * import java.sql.DriverManager; import java.sql.PreparedStatement; import
 * java.sql.SQLException; import java.sql.Time; import JavaFiles.Event;
 * 
 * public class EventUpdateController {
 * 
 * @FXML private TextField eventNameField;
 * 
 * @FXML private DatePicker eventDateField;
 * 
 * @FXML private TextField eventTimeField;
 * 
 * @FXML private TextField eventLocationField;
 * 
 * @FXML private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
 * 
 * @FXML private final String DB_USER = "root";
 * 
 * @FXML private final String DB_PASSWORD = "Kuwait914";
 * 
 * private String currentEventName;
 * 
 * @FXML void updateEvent(ActionEvent event) { String newEventName =
 * eventNameField.getText(); LocalDate eventDate = eventDateField.getValue();
 * LocalTime eventTime = LocalTime.parse(eventTimeField.getText()); String
 * eventLocation = eventLocationField.getText();
 * 
 * try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
 * DB_PASSWORD)) { String query =
 * "UPDATE events SET event_name = ?, event_date = ?, event_time = ?, event_location = ? WHERE event_name = ?"
 * ; PreparedStatement pstmt = conn.prepareStatement(query); pstmt.setString(1,
 * newEventName); pstmt.setDate(2, Date.valueOf(eventDate)); pstmt.setTime(3,
 * Time.valueOf(eventTime)); pstmt.setString(4, eventLocation);
 * pstmt.setString(5, currentEventName);
 * 
 * int affectedRows = pstmt.executeUpdate(); if (affectedRows > 0) {
 * showAlert(Alert.AlertType.INFORMATION, "Success",
 * "Event updated successfully."); } else { showAlert(Alert.AlertType.ERROR,
 * "Error", "Failed to update event."); } } catch (SQLException e) {
 * e.printStackTrace(); showAlert(Alert.AlertType.ERROR, "Database Error",
 * "Failed to update event: " + e.getMessage()); } }
 * 
 * private void showAlert(Alert.AlertType alertType, String title, String
 * message) { Alert alert = new Alert(alertType); alert.setTitle(title);
 * alert.setHeaderText(null); alert.setContentText(message);
 * alert.showAndWait(); }
 * 
 * public void initData(String eventName, LocalDate eventDate, LocalTime
 * eventTime, String eventLocation) { currentEventName = eventName;
 * eventNameField.setText(eventName); eventDateField.setValue(eventDate);
 * eventTimeField.setText(eventTime.toString());
 * eventLocationField.setText(eventLocation); }
 * 
 * public void initData(Event selectedEvent) { if (selectedEvent != null) {
 * eventNameField.setText(selectedEvent.getEventName());
 * 
 * // Convert java.sql.Date to LocalDate LocalDate eventDate =
 * selectedEvent.getEventDate().toLocalDate();
 * eventDateField.setValue(eventDate);
 * 
 * // Convert java.sql.Time to LocalTime LocalTime eventTime =
 * selectedEvent.getEventTime().toLocalTime();
 * eventTimeField.setText(eventTime.toString());
 * 
 * eventLocationField.setText(selectedEvent.getEventLocation()); } }
 * 
 * @FXML void handleBack(ActionEvent event) { try { FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/UserInterface/adminDashboard.fxml"));
 * Parent root = loader.load();
 * 
 * // Get the current stage (window) from the event source Stage stage = (Stage)
 * ((Button) event.getSource()).getScene().getWindow();
 * 
 * // Set the new scene on the current stage stage.setScene(new Scene(root));
 * stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
 * stage.show(); } catch (IOException e) { e.printStackTrace(); }
 * 
 * }
 * 
 * public void setSelectedEvent(Event selectedEvent) { initData(selectedEvent);
 * } }
 */
/*
 * package Controller;
 * 
 * import javafx.event.ActionEvent; import javafx.fxml.FXML; import
 * javafx.scene.control.Alert; import javafx.scene.control.DatePicker; import
 * javafx.scene.control.TextField; import java.time.LocalDate; import
 * java.time.LocalTime; import javafx.stage.Stage;
 * 
 * import java.sql.Connection; import java.sql.Date; import
 * java.sql.DriverManager; import java.sql.PreparedStatement; import
 * java.sql.SQLException; import java.sql.Time; import JavaFiles.Event;
 * 
 * public class EventUpdateController {
 * 
 * @FXML private TextField eventNameField;
 * 
 * @FXML private DatePicker eventDateField;
 * 
 * @FXML private TextField eventTimeField;
 * 
 * @FXML private TextField eventLocationField;
 * 
 * @FXML private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
 * 
 * @FXML private final String DB_USER = "root";
 * 
 * @FXML private final String DB_PASSWORD = "Kuwait914";
 * 
 * private String currentEventName;
 * 
 * 
 * 
 * 
 * @FXML void updateEvent(ActionEvent event) { String newEventName =
 * eventNameField.getText(); LocalDate eventDate = eventDateField.getValue();
 * LocalTime eventTime = LocalTime.parse(eventTimeField.getText()); String
 * eventLocation = eventLocationField.getText();
 * 
 * try (Connection conn=DriverManager.getConnection(DB_URL, DB_USER,
 * DB_PASSWORD)) { String query =
 * "UPDATE events SET event_name = ?, event_date = ?, event_time = ?, event_location = ? WHERE event_name = ?"
 * ; PreparedStatement pstmt = conn.prepareStatement(query); pstmt.setString(1,
 * newEventName); pstmt.setDate(2, Date.valueOf(eventDate)); pstmt.setTime(3,
 * Time.valueOf(eventTime)); pstmt.setString(4, eventLocation);
 * pstmt.setString(5, currentEventName);
 * 
 * int affectedRows = pstmt.executeUpdate(); if (affectedRows > 0) {
 * showAlert(Alert.AlertType.INFORMATION, "Success",
 * "Event updated successfully."); } else { showAlert(Alert.AlertType.ERROR,
 * "Error", "Failed to update event."); } } catch (SQLException e) {
 * e.printStackTrace(); showAlert(Alert.AlertType.ERROR, "Database Error",
 * "Failed to update event: " + e.getMessage()); } }
 * 
 * private void showAlert(Alert.AlertType alertType, String title, String
 * message) { Alert alert = new Alert(alertType); alert.setTitle(title);
 * alert.setHeaderText(null); alert.setContentText(message);
 * alert.showAndWait(); }
 * 
 * public void initData(String eventName, LocalDate eventDate, LocalTime
 * eventTime, String eventLocation) { currentEventName = eventName;
 * eventNameField.setText(eventName); eventDateField.setValue(eventDate);
 * eventTimeField.setText(eventTime.toString());
 * eventLocationField.setText(eventLocation); }
 * 
 * public void initData(Event selectedEvent) { if (selectedEvent != null) {
 * eventNameField.setText(selectedEvent.getEventName());
 * 
 * // Convert java.sql.Date to LocalDate LocalDate eventDate =
 * selectedEvent.getEventDate().toLocalDate();
 * eventDateField.setValue(eventDate);
 * 
 * // Convert java.sql.Time to LocalTime LocalTime eventTime =
 * selectedEvent.getEventTime().toLocalTime();
 * eventTimeField.setText(eventTime.toString());
 * 
 * eventLocationField.setText(selectedEvent.getEventLocation()); } }
 * 
 * public void setSelectedEvent(Event selectedEvent) { initData(selectedEvent);
 * } }
 * 
 * 
 */

package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import JavaFiles.Event;

public class EventUpdateController {

    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker eventDateField;

    @FXML
    private TextField eventTimeField;

    @FXML
    private TextField eventLocationField;

    private String currentEventName;

    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    @FXML
    void updateEvent(ActionEvent event) {
        String newEventName = eventNameField.getText();
        LocalDate eventDate = eventDateField.getValue();
        LocalTime eventTime = LocalTime.parse(eventTimeField.getText());
        String eventLocation = eventLocationField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE events SET event_name = ?, event_date = ?, event_time = ?, event_location = ? WHERE event_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newEventName);
            pstmt.setDate(2, Date.valueOf(eventDate));
            pstmt.setTime(3, Time.valueOf(eventTime));
            pstmt.setString(4, eventLocation);
            pstmt.setString(5, currentEventName);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Event updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update event: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initData(String eventName) {
        currentEventName = eventName;
        eventNameField.setText(eventName);
    }

    public void initData(Event selectedEvent) {
        if (selectedEvent != null) {
            currentEventName = selectedEvent.getEventName();
            eventNameField.setText(selectedEvent.getEventName());
            eventDateField.setValue(selectedEvent.getEventDate().toLocalDate());
            eventTimeField.setText(selectedEvent.getEventTime().toString());
            eventLocationField.setText(selectedEvent.getEventLocation());
        }
    }
}
