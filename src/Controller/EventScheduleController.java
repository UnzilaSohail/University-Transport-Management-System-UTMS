/*
 * package Controller;
 * 
 * import javafx.collections.FXCollections; import
 * javafx.collections.ObservableList; import javafx.event.ActionEvent; import
 * javafx.fxml.FXML; import javafx.fxml.FXMLLoader; import javafx.scene.Node;
 * import javafx.scene.Parent; import javafx.scene.Scene; import
 * javafx.scene.control.Alert; import javafx.scene.control.Button; import
 * javafx.scene.control.TableColumn; import javafx.scene.control.TableView;
 * import javafx.scene.control.TextField; import
 * javafx.scene.control.cell.PropertyValueFactory; import javafx.stage.Modality;
 * import javafx.stage.Stage;
 * 
 * import java.io.IOException; import java.sql.Connection; import java.sql.Date;
 * import java.sql.DriverManager; import java.sql.PreparedStatement; import
 * java.sql.ResultSet; import java.sql.SQLException; import java.sql.Time;
 * 
 * import JavaFiles.Event; import JavaFiles.studentFeeDetails; public class
 * EventScheduleController {
 * 
 * 
 * @FXML private TableView<Event> eventScheduleTable;
 * 
 * @FXML private TableColumn<Event, String> eventNameColumn;
 * 
 * @FXML private TableColumn<Event, Integer> eventDateColumn;
 * 
 * @FXML private TableColumn<Event, Integer> eventTimeColumn;
 * 
 * @FXML private TableColumn<Event, String> eventLocationColumn;
 * 
 * @FXML private Button addButton;
 * 
 * @FXML private Button deleteButton;
 * 
 * @FXML private Button updateButton;
 * 
 * private final String DB_URL = "jdbc:mysql://localhost:3307/utmss"; private
 * final String DB_USER = "root"; private final String DB_PASSWORD =
 * "Kuwait914";
 * 
 * private ObservableList<Event> eventList;
 * 
 * 
 * @FXML void initialize() { eventNameColumn.setCellValueFactory(new
 * PropertyValueFactory<>("eventName")); eventDateColumn.setCellValueFactory(new
 * PropertyValueFactory<>("eventDate")); eventTimeColumn.setCellValueFactory(new
 * PropertyValueFactory<>("eventTime"));
 * eventLocationColumn.setCellValueFactory(new
 * PropertyValueFactory<>("eventLocation"));
 * 
 * eventList = FXCollections.observableArrayList();
 * eventScheduleTable.setItems(eventList);
 * 
 * fetchEventsFromDatabase(); }
 * 
 * @FXML void addEvent(ActionEvent event) { try { FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/UserInterface/AddEvent.fxml")); Parent
 * root = loader.load();
 * 
 * Stage stage = new Stage(); stage.initModality(Modality.APPLICATION_MODAL);
 * stage.setTitle("Add Student"); stage.setScene(new Scene(root));
 * stage.showAndWait(); fetchEventsFromDatabase(); } catch (IOException e) {
 * e.printStackTrace(); showAlert(Alert.AlertType.ERROR, "Error",
 * "Failed to open dialog: " + e.getMessage()); } }
 * 
 * @FXML void deleteEvent(ActionEvent event) { Event selectedEvent =
 * eventScheduleTable.getSelectionModel().getSelectedItem(); if (selectedEvent
 * == null) { showAlert(Alert.AlertType.WARNING, "Warning",
 * "Please select an event to delete."); return; }
 * 
 * try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
 * DB_PASSWORD)) { String query =
 * "DELETE FROM events WHERE event_name = ? AND event_date = ? AND event_time = ? AND event_location = ?"
 * ; PreparedStatement pstmt = conn.prepareStatement(query); pstmt.setString(1,
 * selectedEvent.getEventName());
 * 
 * // Convert Event object's date to a java.sql.Date object java.sql.Date
 * sqlDate = new java.sql.Date(selectedEvent.getEventDate().getTime());
 * pstmt.setDate(2, sqlDate); // Use setDate for java.sql.Date
 * 
 * pstmt.setTime(3, selectedEvent.getEventTime()); pstmt.setString(4,
 * selectedEvent.getEventLocation());
 * 
 * int affectedRows = pstmt.executeUpdate(); if (affectedRows > 0) {
 * showAlert(Alert.AlertType.INFORMATION, "Success",
 * "Event deleted successfully."); fetchEventsFromDatabase(); } else {
 * showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete event."); } }
 * catch (SQLException e) { e.printStackTrace();
 * showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete event: "
 * + e.getMessage()); } }
 * 
 * @FXML void updateEvent(ActionEvent event) { Event selectedEvent =
 * eventScheduleTable.getSelectionModel().getSelectedItem(); if (selectedEvent
 * == null) { showAlert(Alert.AlertType.WARNING, "Warning",
 * "Please select an event to update."); return; }
 * 
 * 
 * 
 * 
 * try { FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/UserInterface/EventUpdate.fxml")); Parent
 * root = loader.load();
 * 
 * // Get the update controller EventUpdateController updateController =
 * loader.getController(); updateController.initData(selectedEvent); // Pass the
 * selected event
 * 
 * // Show the update window Stage stage = new Stage();
 * stage.initModality(Modality.APPLICATION_MODAL);
 * stage.setTitle("Update Event"); stage.setScene(new Scene(root));
 * stage.show(); } catch (IOException e) { e.printStackTrace(); } }
 * 
 * private void fetchEventsFromDatabase() { String query =
 * "SELECT * FROM events";
 * 
 * try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
 * DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query);
 * ResultSet rs = pstmt.executeQuery()) {
 * 
 * eventList.clear(); while (rs.next()) { String eventName =
 * rs.getString("event_name"); Date eventDate = rs.getDate("event_date"); // Use
 * getDate for java.sql.Date Time eventTime = rs.getTime("event_time"); String
 * eventLocation = rs.getString("event_location");
 * 
 * Event event = new Event(eventName, eventDate, eventTime, eventLocation);
 * eventList.add(event); } } catch (SQLException e) { e.printStackTrace();
 * showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch events: "
 * + e.getMessage()); } }
 * 
 * @FXML void handleBtnBack(ActionEvent event) { try { FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/UserInterface/EventsStudent.fxml"));
 * Parent root = loader.load();
 * 
 * // Get the current stage (window) from the event source Stage stage = (Stage)
 * ((Button) event.getSource()).getScene().getWindow();
 * 
 * // Set the new scene on the current stage stage.setScene(new Scene(root));
 * stage.setTitle(" bus schedule"); // Optionally set a new title for the stage
 * stage.show(); fetchEventsFromDatabase(); } catch (IOException e) {
 * e.printStackTrace(); }
 * 
 * }
 * 
 * private void showAlert(Alert.AlertType alertType, String title, String
 * message) { Alert alert = new Alert(alertType); alert.setTitle(title);
 * alert.setHeaderText(null); alert.setContentText(message);
 * alert.showAndWait(); } }
 * 
 */
package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import JavaFiles.Event;

public class EventScheduleController {

    @FXML
    private TableView<Event> eventScheduleTable;
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, Integer> eventDateColumn;
    @FXML
    private TableColumn<Event, Integer> eventTimeColumn;
    @FXML
    private TableColumn<Event, String> eventLocationColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button btnGenerateChallan;
    @FXML
    private Button btnFeedback;

    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    private ObservableList<Event> eventList;


    @FXML
    void initialize() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));

        eventList = FXCollections.observableArrayList();
        eventScheduleTable.setItems(eventList);

        fetchEventsFromDatabase();
    }

    @FXML
    void addEvent(ActionEvent event) {
    	try {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/AddEvent.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Student");
        stage.setScene(new Scene(root));
        stage.showAndWait();
        fetchEventsFromDatabase();
    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to open dialog: " + e.getMessage());
    }
    }
    @FXML
    void deleteEvent(ActionEvent event) {
        Event selectedEvent = eventScheduleTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an event to delete.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM events WHERE event_name = ? AND event_date = ? AND event_time = ? AND event_location = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, selectedEvent.getEventName());

            // Convert Event object's date to a java.sql.Date object
            java.sql.Date sqlDate = new java.sql.Date(selectedEvent.getEventDate().getTime());
            pstmt.setDate(2, sqlDate);  // Use setDate for java.sql.Date

            pstmt.setTime(3, selectedEvent.getEventTime());
            pstmt.setString(4, selectedEvent.getEventLocation());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Event deleted successfully.");
                fetchEventsFromDatabase();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete event: " + e.getMessage());
        }
    }

    @FXML
    void updateEvent(ActionEvent event) {
        Event selectedEvent = eventScheduleTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an event to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/EventUpdate.fxml"));
            Parent root = loader.load();

            // Get the update controller
            EventUpdateController updateController = loader.getController();
            updateController.initData(selectedEvent);  // Pass the selected event
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Event");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // If the dialog is closed and changes are made, fetch events again
            fetchEventsFromDatabase();
           

            // Rest of the code to open the update scene...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch events: " + e.getMessage());
        }
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


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

