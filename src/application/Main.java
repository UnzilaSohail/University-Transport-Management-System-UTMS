package application;

import Controller.AdminDashboardFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file (adjust the path if needed)
//            Parent root = FXMLLoader.load(getClass().getResource("/UserInterface/homepagee.fxml"));
//            
//            // Create the scene with the loaded FXML file as the root
//            Scene scene = new Scene(root, 800, 500);
//            
//            // Add the CSS stylesheet (if any)
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//            
//            // Set the scene and show the stage
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("University Transport Management System");
//            primaryStage.show();
        	AdminDashboardFacade.showHomePage(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
