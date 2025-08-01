package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import JavaFiles.Driver;
import JavaFiles.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class DriverController {


    @FXML
    private TextField driverIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField licenseNumberField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Driver> driversTable;

    @FXML
    private TableColumn<Driver, Integer> driverIdColumn;

    @FXML
    private TableColumn<Driver, String> nameColumn;

    @FXML
    private TableColumn<Driver, String> licenseNumberColumn;

    @FXML
    private TableColumn<Driver, String> phoneColumn;

    @FXML
    private TableColumn<Driver, String> emailColumn;
    @FXML
    private Button btnGenerateChallan;

    private ObservableList<Driver> driverData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        licenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadDriverData();

        // Add listener to handle row selection
        driversTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDriverDetails(newValue)
        );
    }

    private void loadDriverData() {
        try {
            List<Driver> drivers = DatabaseManager.getAllDrivers();
            driverData.clear();
            driverData.addAll(drivers);
            driversTable.setItems(driverData);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to load drivers data: " + e.getMessage());
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

    private void showDriverDetails(Driver driver) {
        if (driver != null) {
            driverIdField.setText(Integer.toString(driver.getDriverId()));
            nameField.setText(driver.getName());
            licenseNumberField.setText(driver.getLicenseNumber());
            phoneField.setText(driver.getPhone());
            emailField.setText(driver.getEmail());
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        driverIdField.clear();
        nameField.clear();
        licenseNumberField.clear();
        phoneField.clear();
        emailField.clear();
    }

    @FXML
    private void onUpdateButtonClicked() {
        if (validateFields()) {
            String driverIdStr = driverIdField.getText();
            if (driverIdStr.isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "Please enter a driver ID.");
                return;
            }

            String name = nameField.getText();
            String licenseNumber = licenseNumberField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (!isValidName(name)) {
                showAlert(AlertType.ERROR, "Error", "Name should only contain characters and spaces.");
                return;
            }

            try {
                int driverId = Integer.parseInt(driverIdStr);
                Driver driver = new Driver(driverId, name, licenseNumber, phone, email);
                boolean updated = DatabaseManager.updateDriver(driver);
                if (updated) {
                    showAlert(AlertType.INFORMATION, "Success", "Driver information updated successfully.");
                    clearFields();
                    loadDriverData();
                } else {
                    showAlert(AlertType.ERROR, "Error", "Driver not found.");
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Error", "Invalid driver ID.");
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to update driver information: " + e.getMessage());
            }
        }
    }

    @FXML
    private void addDriver() {
        if (validateFields()) {
            String name = nameField.getText();
            String licenseNumber = licenseNumberField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (!isValidName(name)) {
                showAlert(AlertType.ERROR, "Error", "Name should only contain characters and spaces.");
                return;
            }

            try {
                Driver driver = new Driver(name, licenseNumber, phone, email);
                DatabaseManager.saveDriver(driver);
                showAlert(AlertType.INFORMATION, "Success", "Driver added successfully.");
                clearFields();
                loadDriverData();
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to add driver: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onDeleteButtonClicked() {
        Driver selectedDriver = driversTable.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            try {
                boolean deleted = DatabaseManager.deleteDriver(selectedDriver.getDriverId());
                if (deleted) {
                    showAlert(AlertType.INFORMATION, "Success", "Driver deleted successfully.");
                    clearFields();
                    loadDriverData();
                } else {
                    showAlert(AlertType.ERROR, "Error", "Driver not found.");
                }
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to delete driver: " + e.getMessage());
            }
        } else {
            showAlert(AlertType.ERROR, "Error", "Please select a driver from the table.");
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
    private void onSearchButtonClicked() {
        String searchText = searchField.getText();
        if (searchText == null || searchText.isEmpty()) {
            driversTable.setItems(driverData);
        } else {
            List<Driver> filteredList = driverData.stream()
                    .filter(driver -> driver.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
            driversTable.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    @FXML
    private void onBackButtonClicked() {
        searchField.clear();
        driversTable.setItems(driverData);
    }

    private boolean validateFields() {
        String name = nameField.getText();
        String licenseNumber = licenseNumberField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || licenseNumber.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
            return false;
        } else if (!isValidName(name)) {
            showAlert(AlertType.ERROR, "Error", "Name should only contain characters and spaces.");
            return false;
        } else if (!isValidPhoneNumber(phone)) {
            showAlert(AlertType.ERROR, "Error", "Phone number must be 11 digits long and contain only numbers.");
            return false;
        } else if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Error", "Invalid email address format.");
            return false;
        }

        return true;
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{11}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
