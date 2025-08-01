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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JavaFiles.CoasterAndVans;

import java.sql.Date;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CoastersAndVansManagementController {

    @FXML
    private TextField busNumberTextField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField makeTextField;
    @FXML
    private TextField modelYearTextField;
    @FXML
    private TextField capacityTextField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private DatePicker lastServicedDatePicker;
    
    @FXML
    private TableView<CoasterAndVans> coastersAndVansTableView;
    @FXML
    private TableColumn<CoasterAndVans, Integer> busNumberColumn;
    @FXML
    private TableColumn<CoasterAndVans, String> typeColumn;
    @FXML
    private TableColumn<CoasterAndVans, String> makeColumn;
    @FXML
    private TableColumn<CoasterAndVans, Integer> modelYearColumn;
    @FXML
    private TableColumn<CoasterAndVans, Integer> capacityColumn;
    @FXML
    private TableColumn<CoasterAndVans, String> statusColumn;
    @FXML
    private TableColumn<CoasterAndVans, Date> lastServicedColumn;
    @FXML
    private Button btnGenerateChallan;

    private final String url = "jdbc:mysql://localhost:3307/utmss";
    private final String user = "root";
    private final String pass = "Kuwait914";

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("Coaster", "Van"));
        statusComboBox.setItems(FXCollections.observableArrayList("Active", "Inactive", "Under Maintenance"));
        
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelYearColumn.setCellValueFactory(new PropertyValueFactory<>("modelYear"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        lastServicedColumn.setCellValueFactory(new PropertyValueFactory<>("lastServiced"));
        
        viewInfo();
    }

    @FXML
    private void addInformation() {
        String busNumberStr = busNumberTextField.getText().trim();
        String type = typeComboBox.getValue();
        String make = makeTextField.getText().trim();
        String modelYearStr = modelYearTextField.getText().trim();
        String capacityStr = capacityTextField.getText().trim();
        String status = statusComboBox.getValue();
        Date lastServiced = lastServicedDatePicker.getValue() != null ? Date.valueOf(lastServicedDatePicker.getValue()) : null;

        if (busNumberStr.isEmpty() || type == null || make.isEmpty() || modelYearStr.isEmpty() || capacityStr.isEmpty() || status == null) {
            showAlert("Incomplete Information", "Please provide all required information.");
            return;
        }

        

        int busNumber, modelYear, capacity;
        try {
            busNumber = Integer.parseInt(busNumberStr);
            modelYear = Integer.parseInt(modelYearStr);
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Bus Number, Model Year, and Capacity should be valid integers.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String insertQuery = "INSERT INTO CoastersAndVans (bus_number, type, make, model_year, capacity, status, last_serviced) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, busNumber);
            statement.setString(2, type);
            statement.setString(3, make);
            statement.setInt(4, modelYear);
            statement.setInt(5, capacity);
            statement.setString(6, status);
            statement.setDate(7, lastServiced);
            statement.executeUpdate();

            showAlert("Success", "Coaster/Van information has been added successfully.");
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                showAlert("Error", "Bus Number already exists. Please use a different number.");
            } else {
                showAlert("Error", "Failed to add coaster/van information.");
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
        String busNumberStr = busNumberTextField.getText().trim();
        String type = typeComboBox.getValue();
        String make = makeTextField.getText().trim();
        String modelYearStr = modelYearTextField.getText().trim();
        String capacityStr = capacityTextField.getText().trim();
        String status = statusComboBox.getValue();
        Date lastServiced = lastServicedDatePicker.getValue() != null ? Date.valueOf(lastServicedDatePicker.getValue()) : null;

        if (busNumberStr.isEmpty() || type == null || make.isEmpty() || modelYearStr.isEmpty() || capacityStr.isEmpty() || status == null) {
            showAlert("Incomplete Information", "Please provide all required information.");
            return;
        }

        if (make.length() > 100) {
            showAlert("Validation Error", "Make should not exceed 100 characters.");
            return;
        }

        int busNumber, modelYear, capacity;
        try {
            busNumber = Integer.parseInt(busNumberStr);
            modelYear = Integer.parseInt(modelYearStr);
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Bus Number, Model Year, and Capacity should be valid integers.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String updateQuery = "UPDATE CoastersAndVans SET type = ?, make = ?, model_year = ?, capacity = ?, status = ?, last_serviced = ? WHERE bus_number = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, type);
            statement.setString(2, make);
            statement.setInt(3, modelYear);
            statement.setInt(4, capacity);
            statement.setString(5, status);
            statement.setDate(6, lastServiced);
            statement.setInt(7, busNumber);
            statement.executeUpdate();

            showAlert("Success", "Coaster/Van information has been updated successfully.");
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            showAlert("Error", "Failed to update coaster/van information.");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteInformation() {
        String busNumberStr = busNumberTextField.getText().trim();

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
            String deleteQuery = "DELETE FROM CoastersAndVans WHERE bus_number = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, busNumber);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Coaster/Van information has been deleted successfully.");
            } else {
                showAlert("Error", "No Coaster/Van found with the provided Bus Number.");
            }
            clearFields();
            viewInfo();
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete coaster/van information.");
            e.printStackTrace();
        }
    }

    @FXML
    private void viewInfo() {
        ObservableList<CoasterAndVans> coastersAndVans = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT bus_number, type, make, model_year, capacity, status, last_serviced FROM CoastersAndVans";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int busNumber = resultSet.getInt("bus_number");
                String type = resultSet.getString("type");
                String make = resultSet.getString("make");
                int modelYear = resultSet.getInt("model_year");
                int capacity = resultSet.getInt("capacity");
                String status = resultSet.getString("status");
                Date lastServiced = resultSet.getDate("last_serviced");
                coastersAndVans.add(new CoasterAndVans(busNumber, type, make, modelYear, capacity, status, lastServiced));
            }

            coastersAndVansTableView.setItems(coastersAndVans);
        } catch (SQLException e) {
            showAlert("Error", "Failed to retrieve coasters and vans from the database.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        busNumberTextField.clear();
        typeComboBox.setValue(null);
        makeTextField.clear();
        modelYearTextField.clear();
        capacityTextField.clear();
        statusComboBox.setValue(null);
        lastServicedDatePicker.setValue(null);
    }
}
