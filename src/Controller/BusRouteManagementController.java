package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JavaFiles.BusRoute;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BusRouteManagementController {

    @FXML
    private TextField routeNameTextField;
    @FXML
    private TextField startPointTextField;
    @FXML
    private TextField endPointTextField;
    @FXML
    private TextArea routeDescriptionTextArea;
    @FXML
    private TextField busNumberTextField;
    @FXML
    private Button btnGenerateChallan;
    @FXML
    private TableView<BusRoute> busRouteTableView;
    @FXML
    private TableColumn<BusRoute, String> routeNameColumn;
    @FXML
    private TableColumn<BusRoute, String> startPointColumn;
    @FXML
    private TableColumn<BusRoute, String> endPointColumn;
    @FXML
    private TableColumn<BusRoute, String> routeDescriptionColumn;
    @FXML
    private TableColumn<BusRoute, Integer> busNumberColumn;
    @FXML
    private Button btnFeedback;

    private final String url = "jdbc:mysql://localhost:3307/utmss";
    private final String user = "root";
    private final String pass = "Kuwait914";

    @FXML
    public void initialize() {
        routeNameColumn.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        startPointColumn.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
        endPointColumn.setCellValueFactory(new PropertyValueFactory<>("endPoint"));
        routeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("routeDescription"));
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
    }

    @FXML
    private void addInformation() {
        // Retrieve values from input fields
        String routeName = routeNameTextField.getText().trim();
        String startPoint = startPointTextField.getText().trim();
        String endPoint = endPointTextField.getText().trim();
        String routeDescription = routeDescriptionTextArea.getText().trim();
        String busNumberStr = busNumberTextField.getText().trim();

        // Validate input
        if (routeName.isEmpty() || startPoint.isEmpty() || endPoint.isEmpty() || busNumberStr.isEmpty()) {
            showAlert("Incomplete Information", "Please provide all required information.");
            return;
        }

        // Check length of input fields
        if (routeName.length() > 100 || startPoint.length() > 100 || endPoint.length() > 100) {
            showAlert("Validation Error", "Route Name, Start Point, and End Point should not exceed 100 characters.");
            return;
        }

        int busNumber;
        try {
            busNumber = Integer.parseInt(busNumberStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Bus Number should be a valid integer.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            // Create and execute insert query
            String insertQuery = "INSERT INTO busroutes (route_name, start_point, end_point, route_description, bus_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, routeName);
            statement.setString(2, startPoint);
            statement.setString(3, endPoint);
            statement.setString(4, routeDescription);
            statement.setInt(5, busNumber);
            statement.executeUpdate();

            showAlert("Success", "Bus route information has been added successfully.");
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                showAlert("Error", "Route Name already exists. Please use a different name.");
            } else {
                showAlert("Error", "Failed to update bus route information.");
            }
            e.printStackTrace();
        }
    }
    @FXML
    void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/adminDashboard.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Feedback System");        
        stage.show();
    }

    @FXML
    private void updateInformation() {
        // Retrieve values from input fields
        String routeName = routeNameTextField.getText().trim();
        String startPoint = startPointTextField.getText().trim();
        String endPoint = endPointTextField.getText().trim();
        String routeDescription = routeDescriptionTextArea.getText().trim();
        String busNumberStr = busNumberTextField.getText().trim();

        // Validate input
        if (routeName.isEmpty() || startPoint.isEmpty() || endPoint.isEmpty() || busNumberStr.isEmpty()) {
            showAlert("Incomplete Information", "Please provide all required information.");
            return;
        }

        // Check length of input fields
        if (routeName.length() > 100 || startPoint.length() > 100 || endPoint.length() > 100) {
            showAlert("Validation Error", "Route Name, Start Point, and End Point should not exceed 100 characters.");
            return;
        }

        int busNumber;
        try {
            busNumber = Integer.parseInt(busNumberStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Bus Number should be a valid integer.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            // Create and execute update query
            String updateQuery = "UPDATE busroutes SET route_name = ?, start_point = ?, end_point = ?, route_description = ? WHERE bus_number = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, routeName);
            statement.setString(2, startPoint);
            statement.setString(3, endPoint);
            statement.setString(4, routeDescription);
            statement.setInt(5, busNumber);
            statement.executeUpdate();

            showAlert("Success", "Bus route information has been updated successfully.");
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            showAlert("Error", "Failed to update bus route information.");
            e.printStackTrace();
        }
    }
    @FXML
    private void deleteInformation() {
        // Retrieve bus number
        String busNumberStr = busNumberTextField.getText().trim();

        // Validate input
        if (busNumberStr.isEmpty()) {
            showAlert("Incomplete Information", "Please provide the Bus Number to delete.");
            return;
        }

        int busNumber;
        try {
            busNumber = Integer.parseInt(busNumberStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Bus Number should be a valid integer.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            // Check if there are associated records in bustimings
            String checkQuery = "SELECT COUNT(*) AS count FROM bustimings WHERE route_id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, busNumber);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count");

            if (count > 0) {
                // There are associated records in bustimings, prompt user or handle as appropriate
                showAlert("Associated Records", "There are associated records in bustimings. Delete them first.");
                return;
            }

            // No associated records, proceed with deletion
            String deleteQuery = "DELETE FROM busroutes WHERE bus_number = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, busNumber);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Bus route information has been deleted successfully.");
            } else {
                showAlert("Error", "No bus route found with the provided Bus Number.");
            }
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete bus route information.");
            e.printStackTrace();
        }
    }

    @FXML
    private void viewInfo() {
        ObservableList<BusRoute> busRoutes = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT route_name, start_point, end_point, route_description, bus_number FROM busroutes";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String routeName = resultSet.getString("route_name");
                String startPoint = resultSet.getString("start_point");
                String endPoint = resultSet.getString("end_point");
                String routeDescription = resultSet.getString("route_description");
                int busNumber = resultSet.getInt("bus_number");
                busRoutes.add(new BusRoute(routeName, startPoint, endPoint, routeDescription, busNumber));
            }

            busRouteTableView.setItems(busRoutes);
        } catch (SQLException e) {
            showAlert("Error", "Failed to retrieve bus routes from the database.");
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
    private void clearFields() {
        routeNameTextField.clear();
        startPointTextField.clear();
        endPointTextField.clear();
        routeDescriptionTextArea.clear();
        busNumberTextField.clear();
    }
}
