package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class AdminDashboardFacade {
	

	 public static void showAdminDashboard(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(AdminDashboardFacade.class.getResource("/UserInterface/adminDashboard.fxml"));
	            Parent root = loader.load();

	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	 public static void showHomePage(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(AdminDashboardFacade.class.getResource("/UserInterface/homepagee.fxml"));
	            Parent root = loader.load();

	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void showEventSchedule(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(AdminDashboardFacade.class.getResource("/UserInterface/EventSchedule.fxml"));
	            Parent root = loader.load();

	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void showStudentInfo(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(AdminDashboardFacade.class.getResource("/UserInterface/StudentInfo.fxml"));
	            Parent root = loader.load();

	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void showBusRouteManagement(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(AdminDashboardFacade.class.getResource("/UserInterface/busroutemanagement.fxml"));
	            Parent root = loader.load();

	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
