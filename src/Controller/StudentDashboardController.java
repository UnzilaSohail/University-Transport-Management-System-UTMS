package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import JavaFiles.studentFeeDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class StudentDashboardController {

int userId;
    @FXML
    private TableView<studentFeeDetails> tableView;

    @FXML
    private TableColumn<studentFeeDetails, String> colFirstName;

    @FXML
    private TableColumn<studentFeeDetails, String> colLastName;

    @FXML
    private TableColumn<studentFeeDetails, String> colRouteName;

    @FXML
    private TableColumn<studentFeeDetails, String> colStatus;

    @FXML
    private Label lblStudentInfo;
    @FXML
    private Button btnGenerateChallan;

    private ObservableList<studentFeeDetails> studentList = FXCollections.observableArrayList();

    @FXML
    private Button btnHomepage;
    @FXML
    private Button btnView;

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

    @FXML
    public void initialize() {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colRouteName.setCellValueFactory(new PropertyValueFactory<>("routeName"));

        tableView.setEditable(true);

        fetchDataForLoggedInUser();
    }

    private void fetchDataForLoggedInUser() {
        System.out.println("Fetching data for user: " + LoginController.username);

        // Database connection details
        String url = "jdbc:mysql://localhost:3307/utmss";
        String user = "root";
        String password = "Kuwait914";
int userId = 0;
        // SQL query to fetch fee details for the logged-in user
        String query = "SELECT * FROM students1 WHERE username = ?";
        String idQuery = "SELECT id FROM students1 WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(query)) {

            // Set the username parameter in the prepared statement
            statement.setString(1, LoginController.username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String routeName = resultSet.getString("route_name");
                    // double fee = resultSet.getDouble("fee");
                    // String status = resultSet.getString("status");

                    // Create studentFeeDetails object and add it to the TableView
                    studentFeeDetails feeDetails = new studentFeeDetails(firstName, lastName, routeName, 0.0, "unpaid");

                    System.out.println("Adding fee details: " + firstName + " " + lastName + " " + routeName + " " + 0.0 + " " + "unpaid");
                    tableView.getItems().add(feeDetails);
                }
            }
        } catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//                PreparedStatement idStatement = conn.prepareStatement(idQuery)) {
//
//               // Set the username parameter in the prepared statement
//               idStatement.setString(1, LoginController.username);
//
//               // Execute the query to fetch the ID of the logged-in user
//               try (ResultSet idResultSet = idStatement.executeQuery()) {
//                   if (idResultSet.next()) {
//                       userId = idResultSet.getInt("id");
//                       
//                   }
//               }
//        } catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
         
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
}

