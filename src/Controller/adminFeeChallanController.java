package Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JavaFiles.studentFeeDetails;

public class adminFeeChallanController {


    @FXML
    private TableView<studentFeeDetails> tableView;
    @FXML
    private TableColumn<studentFeeDetails, String> firstNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> lastNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> routeNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, Double> feeColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> statusColumn;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Button btnGenerateChallan;

    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    // List to store student fee details
    private ObservableList<studentFeeDetails> studentFeeDetailsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Set cell value factories for each column
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        routeNameColumn.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));


        ObservableList<String> statusOptions = FXCollections.observableArrayList("Paid", "Unpaid");
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(statusOptions));
        statusColumn.setOnEditCommit(event -> {
            studentFeeDetails student = event.getRowValue();
            student.setStatus(event.getNewValue());
            updateStudentStatusInDatabase(student);
        });
        statusColumn.setEditable(true);

        tableView.setEditable(true);

        displayAllStudentFeeDetails();

        // Set up the mouse click event handler for the TableView
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                // Get the selected studentFeeDetails object
                studentFeeDetails selectedStudent = tableView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    // Display the student details in a dialog box
                    displayStudentDetailsDialog(selectedStudent);
                }
            }
        });
    }
    private void displayStudentDetailsDialog(studentFeeDetails student) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Student Details");
        DialogPane dialogPane = new DialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        VBox content = new VBox();
        content.getChildren().addAll(
                new Label("First Name: " + student.getFirstName()),
                new Label("Last Name: " + student.getLastName()),
                new Label("Route Name: " + student.getRouteName()),
                new Label("Fee: " + student.getFee()),
                new Label("Status: " + student.getStatus())
               
        );
        dialogPane.setContent(content);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }

    @FXML
    void searchByUsername() {
        String username = usernameTextField.getText();
        if (!username.isEmpty()) {
            tableView.getItems().clear();
            String query = "SELECT students1.first_name, students1.last_name, students1.route_name, students1.fee " +
                           "FROM students1 " +
                           "WHERE students1.first_name = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String routeName = rs.getString("route_name");
                        double fee = rs.getDouble("fee");
                        String status = "Unpaid"; 

                        // Add to the table view
                        tableView.getItems().add(new studentFeeDetails(firstName, lastName, routeName, fee, status));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch student fee details: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username.");
        }
    }


  
    private void displayAllStudentFeeDetails() {
        String query = "SELECT students1.first_name, students1.last_name, students1.route_name, students1.fee " +
                       "FROM students1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String routeName = rs.getString("route_name");
                double fee = rs.getDouble("fee");
                String status = "Unpaid"; 

                // Add to the list
                studentFeeDetailsList.add(new studentFeeDetails(firstName, lastName, routeName, fee, status));
            }

            // Set the items to the table view
            tableView.setItems(studentFeeDetailsList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch student fee details: " + e.getMessage());
        }
    }

    private void saveUpdatesToDatabase(ObservableList<studentFeeDetails> items) {
        // Iterate over each studentFeeDetails object in the TableView
        for (studentFeeDetails student : items) {
            // Update the status for each student in the database
            updateStudentStatusInDatabase(student);
        }
    }

    public void updateStudentStatusInDatabase(studentFeeDetails student) {
        String query = "UPDATE FeeChallans SET status = ? WHERE student_id = (SELECT id FROM students WHERE first_name = ? AND last_name = ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getStatus());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Status updated successfully for " + student.getFirstName() + " " + student.getLastName());
            } else {
                System.out.println("Failed to update status for " + student.getFirstName() + " " + student.getLastName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update student status: " + e.getMessage());
        }
    }


    // Call this method whenever you want to save the updates, for example, when the user clicks a "Save" button
    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        ObservableList<studentFeeDetails> items = tableView.getItems();
        saveUpdatesToDatabase(items);
        showAlert(Alert.AlertType.INFORMATION, "Success", "saved successfully.");
    }
    @FXML
    void handleBackButtonAction(ActionEvent event) {
    	// tableView.getItems().clear();
    	displayAllStudentFeeDetails();
      
    }@FXML
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
   
  

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
