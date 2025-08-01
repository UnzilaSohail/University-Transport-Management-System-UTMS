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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JavaFiles.DatabaseConnectionManager;
import JavaFiles.studentFeeDetails;




public class StudentController<Int> {

    @FXML
    private TableView<studentFeeDetails> tableView;
    @FXML
    private TableColumn<studentFeeDetails, String> firstNameColumn;
    
    @FXML
    private TableColumn<studentFeeDetails, Int> idField;
    @FXML
    private TableColumn<studentFeeDetails, String> lastNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> routeNameColumn;

    @FXML
    private TableColumn<studentFeeDetails, String> emailColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> usernameColumn;
    @FXML
    private TableColumn<studentFeeDetails, Double> feeColumn;

    @FXML
    private TableColumn<studentFeeDetails, String> passwordColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button btnGenerateChallan;
    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    private final String DB_URL = "jdbc:mysql://localhost:3307/utmss";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Kuwait914";

    private ObservableList<studentFeeDetails> studentFeeDetailsList;

    @FXML
    void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        routeNameColumn.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

        studentFeeDetailsList = FXCollections.observableArrayList();
        tableView.setItems(studentFeeDetailsList);

        displayAllStudentFeeDetails();
    }

    @FXML
    void addStudent(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/addstudent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Student");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            displayAllStudentFeeDetails();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open dialog: " + e.getMessage());
        }
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        studentFeeDetails selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a student to delete.");
            return;
        }

        String username = selectedStudent.getUsername();
        String firstName = selectedStudent.getFirstName();
        String lastName = selectedStudent.getLastName();
        String email = selectedStudent.getEmail();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM students1 WHERE first_name = ? AND last_name = ? AND email = ? AND username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
                displayAllStudentFeeDetails();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete student: " + e.getMessage());
        }
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    void searchByUsername() {
        String username = usernameTextField.getText();
        if (!username.isEmpty()) {
            tableView.getItems().clear();
            displayStudentFeeDetails(username); 
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username.");
        }
    }

  
    	@FXML
    	void updateStudent(ActionEvent event) {
    	    studentFeeDetails selectedStudent = tableView.getSelectionModel().getSelectedItem();
    	    if (selectedStudent == null) {
    	        showAlert(Alert.AlertType.WARNING, "Warning", "Please select a student to update.");
    	        return;
    	    }

    	    String username = selectedStudent.getUsername();
    	    String firstName = selectedStudent.getFirstName();
    	    String lastName = selectedStudent.getLastName();
    	    String email = selectedStudent.getEmail();
    	    String RouteName=selectedStudent.getRouteName();
    	    double fee=selectedStudent.getFee();
    	    // You can choose to display a dialog or navigate to a new scene for updating student details
    	    // For simplicity, let's assume you have an update form in another FXML file named "updateStudent.fxml"
    	    try {
    	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/DeleteStudent.fxml"));
    	        Parent root = loader.load();
    	        UpdateStudentController controller = loader.getController();
    	        controller.initData(username, firstName, lastName, email, RouteName, fee); 
    	        Stage stage = new Stage();
    	        stage.initModality(Modality.APPLICATION_MODAL);
    	        stage.setTitle("Update Student");
    	        stage.setScene(new Scene(root));
    	        stage.showAndWait();
    	        displayAllStudentFeeDetails();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	        showAlert(Alert.AlertType.ERROR, "Error", "Failed to open update dialog: " + e.getMessage());
    	    }
    	
    }

    	
    	 @FXML
    	    void handleBackButtonAction(ActionEvent event) {
    	    	// tableView.getItems().clear();
    	         displayAllStudentFeeDetails();
    	      
    	    }

    	 private void displayStudentFeeDetails(String username) {
    		    String query = "select * from students1 "
    		                 + "WHERE students1.username = ?"; 

    		    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		         PreparedStatement pstmt = conn.prepareStatement(query)) {

    		        pstmt.setString(1, username); // Set the username parameter

    		        try (ResultSet rs = pstmt.executeQuery()) {
    		            while (rs.next()) {
    		                String firstName = rs.getString("first_name");
    		                String lastName = rs.getString("last_name");
    		                String routeName = rs.getString("route_name");
    		                String email = rs.getString("email");
    		                double fee = rs.getDouble("fee");

    		                tableView.getItems().add(new studentFeeDetails(firstName, lastName, routeName, username, email, fee));
    		            }
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch student fee details: " + e.getMessage());
    		    }
    		}

    	 private void displayAllStudentFeeDetails() {
    		    String query = "SELECT * from students1";
    		    
    		    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		         PreparedStatement pstmt = conn.prepareStatement(query);
    		         ResultSet rs = pstmt.executeQuery()) {

    		        studentFeeDetailsList.clear(); // Clear existing data
    		        while (rs.next()) {
    		            String firstName = rs.getString("first_name");
    		            String lastName = rs.getString("last_name");
    		            String routeName = rs.getString("route_name");
    		            String username = rs.getString("username");
    		            String email = rs.getString("email");
    		            double fee = rs.getDouble("fee");
    		            studentFeeDetails student = new studentFeeDetails(firstName, lastName, routeName, username, email, fee);
    		            studentFeeDetailsList.add(student);
    		          
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to fetch student fee details: " + e.getMessage());
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
    	

    	 
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
