package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;

import JavaFiles.studentFeeDetails;

public class FeeChallanController extends LoginController {
	@FXML
    private Button btnFeedback;

    @FXML
    private TableView<studentFeeDetails> tableView;

    @FXML
    private TableColumn<studentFeeDetails, String> firstNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> lastNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> routeNameColumn;
    @FXML
    private TableColumn<studentFeeDetails, Double> feesColumn;
    @FXML
    private TableColumn<studentFeeDetails, String> statusColumn;

    @FXML
    private Button backButton;
    @FXML
    private Button btnGenerateChallan;

    public void setLoggedInUsername(LoginController username) {
        // Call a method to fetch data using the logged-in username
        System.out.println("Setting loggedInUsername: " + LoginController.username);
        if (LoginController.username != null && !LoginController.username.isEmpty()) {
            fetchDataForLoggedInUser();
        }
    }

    @FXML
    public void initialize() {
    	firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	routeNameColumn.setCellValueFactory(new PropertyValueFactory<>("routeName"));
    	feesColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
    	statusColumn.setCellValueFactory(new PropertyValueFactory<>("routeName"));

        tableView.setEditable(true);

        fetchDataForLoggedInUser();
    }
    private void fetchDataForLoggedInUser() {
        System.out.println("Fetching data for user: " + LoginController.username);

        // Database connection details
        String url = "jdbc:mysql://localhost:3307/utmss";
        String user = "root";
        String password = "Kuwait914";

        // SQL query to fetch fee details for the logged-in user
        String query = "SELECT students1.first_name, students1.last_name, students1.route_name, students1.fee, FeeChallans.status "
                + "FROM students1 "
                + "left JOIN RouteFees ON students1.route_id = RouteFees.route_id "
                + "left JOIN FeeChallans ON students1.id = FeeChallans.student_id "
                + "WHERE students1.username = ?"; // Assuming you have a username column in the students table

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(query)) {

            // Set the username parameter in the prepared statement
            statement.setString(1, LoginController.username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String routeName = resultSet.getString("route_name");
                    double fee = resultSet.getDouble("fee");
                    String status = resultSet.getString("status");

                    // Create studentFeeDetails object and add it to the TableView
                    studentFeeDetails feeDetails = new studentFeeDetails(firstName, lastName, routeName, fee , status);

                    System.out.println("Adding fee details: " + firstName + " " + lastName + " " + routeName + " " + fee + " " + status);
                    tableView.getItems().add(feeDetails);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    @FXML
    void handleBackButtonAction(ActionEvent event) {
        try {
            // Load the homepage FXML file
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface/homepage.fxml"));
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
}}
