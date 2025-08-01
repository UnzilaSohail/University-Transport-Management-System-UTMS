package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import JavaFiles.Event;
import JavaFiles.studentFeeDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
public class StudentEventController {


    @FXML
    private Button btnEventsBusSchedule;

    @FXML
    private Button btnBusesTiming;

    @FXML
    private Button btnBusesInformation;

    @FXML
    private Button btnHomepage;
    
    @FXML
    private TableView<Event> tableView;

    @FXML
    private Button backbutton;

    @FXML
    private Button btnFeeChallan;

    @FXML
    private Button btnTimings;

    @FXML
    private Button btnRoutes;

    @FXML
    private Button btnFeedback;

    @FXML
    private Button btnLogout;

    private List<Event> eventList = new ArrayList<>();

    @FXML
    void handleBtnEventsBusSchedule(ActionEvent event) {
        showAlert("Events Bus Schedule", "Displaying Events Bus Schedule...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/studentEventSchedule.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            stage.setScene(new Scene(root));
            stage.setTitle(" Event schedule"); // Optionally set a new title for the stage
            stage.show();
            fetchEventsFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    @SuppressWarnings("unchecked")
	private void fetchEventsFromDatabase() {
        String query = "SELECT * FROM events";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            eventList.clear();
            while (rs.next()) {
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date"); // Use getDate for java.sql.Date
                Time eventTime = rs.getTime("event_time");
                String eventLocation = rs.getString("event_location");

                Event event = new Event(eventName, eventDate, eventTime, eventLocation);
             
                tableView.getItems().add(event);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to fetch events: " + e.getMessage());
        }
    }

    @FXML
    void handleBtnBusesTiming(ActionEvent event) {
        showAlert("Buses Timing", "Displaying Buses Timing...");
    }

    @FXML
    void handleBtnBusesInformation(ActionEvent event) {
        showAlert("Buses Information", "Displaying Buses Information...");
    }

    @FXML
    void handleBtnHomepage(ActionEvent event) {
        showAlert("Homepage", "Navigating to Homepage...");
    }

    @FXML
    void handleBtnFeeChallan(ActionEvent event) {
        showAlert("Fee Challan", "Displaying Fee Challan...");
    }

    @FXML
    void handleBtnTimings(ActionEvent event) {
        showAlert("Timings", "Displaying Timings...");
    }

    @FXML
    void handleBtnRoutes(ActionEvent event) {
        showAlert("Routes", "Displaying Routes...");
    }

    @FXML
    void handleBtnFeedback(ActionEvent event) {
        showAlert("Feedback", "Displaying Feedback form...");
    }

    @FXML
    void handleBtnLogout(ActionEvent event) {
        showAlert("Logout", "Logging out...");
    }
    @FXML
    void handleBtnBack(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/EventsStudent.fxml"));
             Parent root = loader.load();

             // Get the current stage (window) from the event source
             Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

             // Set the new scene on the current stage
             stage.setScene(new Scene(root));
             stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
             stage.show();
             fetchEventsFromDatabase();
         } catch (IOException e) {
             e.printStackTrace();
         }
        
    }
    @FXML
    void handleView(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/EventsStudent.fxml"));
             Parent root = loader.load();

             // Get the current stage (window) from the event source
             Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

             // Set the new scene on the current stage
             stage.setScene(new Scene(root));
             stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
             stage.show();
             fetchEventsFromDatabase();
         } catch (IOException e) {
             e.printStackTrace();
         }
        
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
