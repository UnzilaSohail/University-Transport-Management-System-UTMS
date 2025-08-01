package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
//import UserInterface.*;
public class MainHomePageController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private void loadStudentAttendance() {
        loadUI("/UserInterface/studentattendance");
    }

    @FXML
    private void loadBusRouteManagement() {
        loadUI("/UserInterface/busroutemanagement");
    }
    @FXML
    private void loadCoastersAndVansManagement() {
        loadUI("/UserInterface/coastervan");
    }

    private void loadUI(String ui) {
        try {
            VBox view = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
            rootPane.setCenter(view);
        } catch (IOException e) {
            showAlert("Error", "Failed to load the page: " + ui);
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
