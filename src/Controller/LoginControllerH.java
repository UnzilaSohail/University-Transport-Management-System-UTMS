package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
public class LoginControllerH {


	@FXML
    private TextField busIdField;
	
    @FXML
    void handleStudentLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/student.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Student Feedback");
        stage.show();
    }

    @FXML
    void handleAdminLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/admin.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Admin View");
        stage.show();
    }
    
	
	  @FXML void handleBack(ActionEvent event) throws Exception { Stage stage =
	  (Stage) ((Node) event.getSource()).getScene().getWindow(); Parent root =
	  FXMLLoader.load(getClass().getResource("/UserInterface/login.fxml"));
	  stage.setScene(new Scene(root, 600, 400)); stage.setTitle("Feedback System");
	  
	  // Reset bus ID field if (busIdField != null) { busIdField.clear(); }
	  
	  stage.show(); }
	 
}
