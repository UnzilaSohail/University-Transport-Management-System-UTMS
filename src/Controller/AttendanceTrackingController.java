package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import JavaFiles.Attendance;
import JavaFiles.BusRoute;
import JavaFiles.DatabaseManager;
import JavaFiles.Driver;

public class AttendanceTrackingController {
	@FXML
	 private Button btnGenerateChallan;
    @FXML
    private ChoiceBox<String> routeChoiceBox;
    @FXML
    private ListView<String> studentListView;
    @FXML
    private TableView<Attendance> attendanceTableView;
    @FXML
    private TableColumn<Attendance, String> attendanceIDColumn;
    @FXML
    private TableColumn<Attendance, String> studentIDColumn;
    @FXML
    private TableColumn<Attendance, String> dateColumn;
    @FXML
    private TableColumn<Attendance, String> statusColumn;

    private Connection connection;

    @FXML
    public void initialize() {
    	attendanceIDColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
    	studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    	statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/utmss", "root", "Kuwait914");
            loadRoutes();
            studentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException e) {
            showErrorAlert("Database Connection Error", "Unable to connect to the database.");
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

    private void loadRoutes() throws SQLException {
        String query = "SELECT route_description FROM busroutes";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            routeChoiceBox.getItems().add(resultSet.getString("route_description"));
        }
    }

    @FXML
    private void loadStudents() {
        // Clear previous data
        studentListView.getItems().clear();

        // Load students for the selected route
        String selectedRoute = routeChoiceBox.getValue();
        if (selectedRoute != null) {
            try {
                String query = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM students "
                             + "JOIN busroutes ON students.route_id = busroutes.route_id WHERE busroutes.route_description = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, selectedRoute);
                ResultSet resultSet = statement.executeQuery();

                ObservableList<String> students = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    students.add(resultSet.getString("full_name"));
                }
                studentListView.setItems(students);
            } catch (SQLException e) {
                showErrorAlert("Database Query Error", "Unable to load students from the database.");
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Please select a route to load students.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void addAttendance() {
        ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();

        try {
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO Attendance (student_id, date, status) "
                         + "VALUES ((SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?), ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            for (String student : selectedStudents) {
                statement.setString(1, student);
                statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                statement.setString(3, "present"); // Assuming all selected students are present
                statement.executeUpdate();
           }

            connection.commit();

            // Show confirmation alert
            Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully recorded.", ButtonType.OK);
            alert.showAndWait();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            showErrorAlert("Database Insert Error", "Unable to record attendance in the database.");
            e.printStackTrace();
        }
    }
    
   @FXML
    private void updateAttendance() {
	   
	       // Prompt the user to enter the student ID
	       TextInputDialog studentIdDialog = new TextInputDialog();
	       studentIdDialog.setTitle("Update Attendance");
	       studentIdDialog.setHeaderText("Enter Student ID:");
	       studentIdDialog.setContentText("Student ID:");

	       Optional<String> studentIdResult = studentIdDialog.showAndWait();
	       if (studentIdResult.isPresent()) {
	           int studentId;
	           try {
	               studentId = Integer.parseInt(studentIdResult.get());
	           } catch (NumberFormatException e) {
	               showErrorAlert("Invalid Input", "Please enter a valid student ID.");
	               return;
	           }

	           // Prompt the user to enter the date
	           TextInputDialog dateDialog = new TextInputDialog();
	           dateDialog.setTitle("Update Attendance");
	           dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
	           dateDialog.setContentText("Date:");

	           Optional<String> dateResult = dateDialog.showAndWait();
	           if (dateResult.isPresent()) {
	               String dateString = dateResult.get();
	               LocalDate date;
	               try {
	                   date = LocalDate.parse(dateString);
	               } catch (DateTimeParseException e) {
	                   showErrorAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
	                   return;
	               }

	               // Prompt the user to enter the new attendance status
	               TextInputDialog statusDialog = new TextInputDialog();
	               statusDialog.setTitle("Update Attendance");
	               statusDialog.setHeaderText("Enter New Attendance Status:");
	               statusDialog.setContentText("Status:");

	               Optional<String> statusResult = statusDialog.showAndWait();
	               if (statusResult.isPresent()) {
	                   String status = statusResult.get();

	                   try {
	                       connection.setAutoCommit(false);
	                       String updateQuery = "UPDATE Attendance SET status = ? WHERE student_id = ? AND date = ?";
	                       PreparedStatement statement = connection.prepareStatement(updateQuery);
	                       statement.setString(1, status);
	                       statement.setInt(2, studentId);
	                       statement.setDate(3, java.sql.Date.valueOf(date));
	                       int rowsAffected = statement.executeUpdate();
	                       connection.commit();

	                       if (rowsAffected > 0) {
	                           // Show confirmation alert
	                           Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully updated.", ButtonType.OK);
	                           alert.showAndWait();
	                           
	                           // Refresh the attendance table view
	                           viewInfo();
	                       } else {
	                           showErrorAlert("No Record Found", "No attendance record found for the provided student ID and date.");
	                       }
	                   } catch (SQLException e) {
	                       try {
	                           connection.rollback();
	                       } catch (SQLException rollbackEx) {
	                           rollbackEx.printStackTrace();
	                       }
	                       showErrorAlert("Database Update Error", "Unable to update attendance in the database.");
	                       e.printStackTrace();
	                   }
	               }
	           }
	       }
	   

//	       // Prompt the user to enter the student name
//	       TextInputDialog studentNameDialog = new TextInputDialog();
//	       studentNameDialog.setTitle("Update Attendance");
//	       studentNameDialog.setHeaderText("Enter Student Id:");
//	       studentNameDialog.setContentText("Student ID:");
//
//	       Optional<String> studentNameResult = studentNameDialog.showAndWait();
//	       if (studentNameResult.isPresent()) {
//	           String studentName = studentNameResult.get();
//
//	           // Prompt the user to enter the new attendance status
//	           TextInputDialog statusDialog = new TextInputDialog();
//	           statusDialog.setTitle("Update Attendance");
//	           statusDialog.setHeaderText("Enter New Attendance Status:");
//	           statusDialog.setContentText("Status:");
//
//	           Optional<String> statusResult = statusDialog.showAndWait();
//	           if (statusResult.isPresent()) {
//	               String status = statusResult.get();
//
//	               // Prompt the user to enter the date
//	               TextInputDialog dateDialog = new TextInputDialog();
//	               dateDialog.setTitle("Update Attendance");
//	               dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
//	               dateDialog.setContentText("Date:");
//
//	               Optional<String> dateResult = dateDialog.showAndWait();
//	               if (dateResult.isPresent()) {
//	                   String dateString = dateResult.get();
//	                   LocalDate date;
//	                   try {
//	                       date = LocalDate.parse(dateString);
//	                   } catch (DateTimeParseException e) {
//	                       showErrorAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
//	                       return;
//	                   }
//
//	                   try {
//	                       connection.setAutoCommit(false);
//	                       String updateQuery = "UPDATE Attendance SET status = ? WHERE student_id = (SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?) AND date = ?";
//	                       PreparedStatement statement = connection.prepareStatement(updateQuery);
//	                       statement.setString(1, status);
//	                       statement.setString(2, studentName);
//	                       statement.setDate(3, java.sql.Date.valueOf(date));
//	                       int rowsAffected = statement.executeUpdate();
//	                       connection.commit();
//
//	                       if (rowsAffected > 0) {
//	                           // Show confirmation alert
//	                           Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully updated.", ButtonType.OK);
//	                           alert.showAndWait();
//	                           
//	                           // Refresh the attendance table view
//	                           viewInfo();
//	                       } else {
//	                           showErrorAlert("No Record Found", "No attendance record found for the provided student name and date.");
//	                       }
//	                   } catch (SQLException e) {
//	                       try {
//	                           connection.rollback();
//	                       } catch (SQLException rollbackEx) {
//	                           rollbackEx.printStackTrace();
//	                       }
//	                       showErrorAlert("Database Update Error", "Unable to update attendance in the database.");
//	                       e.printStackTrace();
//	                   }
//	               }
//	           }
//	       }
//	   

	   
	   
   }
////        ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
////
////        TextInputDialog dialog = new TextInputDialog();
////        dialog.setTitle("Update Attendance");
////        dialog.setHeaderText("Enter new attendance status:");
////        dialog.setContentText("Status:");
////
////        Optional<String> result = dialog.showAndWait();
////        result.ifPresent(status -> {
////            try {
////                connection.setAutoCommit(false);
////                String updateQuery = "UPDATE Attendance SET status = ? WHERE student_id = (SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?) AND date = ?";
////                PreparedStatement statement = connection.prepareStatement(updateQuery);
////
////                for (String student : selectedStudents) {
////                    statement.setString(1, status);
////                    statement.setString(2, student);
////                    statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
////                    statement.executeUpdate();
////                }
////
////                connection.commit();
////
////                // Show confirmation alert
////                Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully updated.", ButtonType.OK);
////                alert.showAndWait();
////            } catch (SQLException e) {
////                try {
////                    connection.rollback();
////                } catch (SQLException rollbackEx) {
////                    rollbackEx.printStackTrace();
////                }
////                showErrorAlert("Database Update Error", "Unable to update attendance in the database.");
////                e.printStackTrace();
////            }
////        });
   
//    	    ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
//
//    	    // Check if any student is selected
//    	    if (selectedStudents.isEmpty()) {
//    	        showErrorAlert("No Students Selected", "Please select at least one student to update attendance.");
//    	        return;
//    	    }
//
//    	    // Prompt the user to enter the new attendance status
//    	    TextInputDialog dialog = new TextInputDialog();
//    	    dialog.setTitle("Update Attendance");
//    	    dialog.setHeaderText("Enter new attendance status:");
//    	    dialog.setContentText("Status:");
//
//    	    Optional<String> result = dialog.showAndWait();
//    	    if (result.isPresent()) {
//    	        String status = result.get();
//    	        
//    	        try {
//    	            connection.setAutoCommit(false);
//    	            String updateQuery = "UPDATE Attendance SET status = ? WHERE student_id IN (SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?) AND date = ?";
//    	            PreparedStatement statement = connection.prepareStatement(updateQuery);
//
//    	            for (String student : selectedStudents) {
//    	                statement.setString(1, status);
//    	                statement.setString(2, student);
//    	                statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
//    	                statement.executeUpdate();
//    	                statement.addBatch();
//    	                statement.clearParameters();
//    	            }
//
//    	            connection.commit();
//
//    	            // Show confirmation alert
//    	            Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully updated.", ButtonType.OK);
//    	            alert.showAndWait();
//    	            
//    	            // Refresh the attendance table view
//    	            viewInfo();
//    	        } catch (SQLException e) {
//    	            try {
//    	                connection.rollback();
//    	            } catch (SQLException rollbackEx) {
//    	                rollbackEx.printStackTrace();
//    	            }
//    	            showErrorAlert("Database Update Error", "Unable to update attendance in the database.");
//    	            e.printStackTrace();
//    	        }
//    	    }
//    	
//
//    }
    @FXML
    private void deleteAttendance() {
    	
    	    // Prompt the user to enter the student ID
    	    TextInputDialog studentIdDialog = new TextInputDialog();
    	    studentIdDialog.setTitle("Delete Attendance");
    	    studentIdDialog.setHeaderText("Enter Student ID:");
    	    studentIdDialog.setContentText("Student ID:");

    	    Optional<String> studentIdResult = studentIdDialog.showAndWait();
    	    if (studentIdResult.isPresent()) {
    	        int studentId;
    	        try {
    	            studentId = Integer.parseInt(studentIdResult.get());
    	        } catch (NumberFormatException e) {
    	            showErrorAlert("Invalid Input", "Please enter a valid student ID.");
    	            return;
    	        }

    	        // Prompt the user to enter the date
    	        TextInputDialog dateDialog = new TextInputDialog();
    	        dateDialog.setTitle("Delete Attendance");
    	        dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
    	        dateDialog.setContentText("Date:");

    	        Optional<String> dateResult = dateDialog.showAndWait();
    	        if (dateResult.isPresent()) {
    	            String dateString = dateResult.get();
    	            LocalDate date;
    	            try {
    	                date = LocalDate.parse(dateString);
    	            } catch (DateTimeParseException e) {
    	                showErrorAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
    	                return;
    	            }
    	            
    	            try {
    	                connection.setAutoCommit(false);
    	                String deleteQuery = "DELETE FROM Attendance WHERE student_id = ? AND date = ?";
    	                PreparedStatement statement = connection.prepareStatement(deleteQuery);
    	                statement.setInt(1, studentId);
    	                statement.setDate(2, java.sql.Date.valueOf(date));

    	                int rowsAffected = statement.executeUpdate();
    	                connection.commit();

    	                if (rowsAffected > 0) {
    	                    // Show confirmation alert
    	                    Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully deleted.", ButtonType.OK);
    	                    alert.showAndWait();
    	                    
    	                    // Refresh the attendance table view
    	                    viewInfo();
    	                } else {
    	                    showErrorAlert("No Record Found", "No attendance record found for the provided student ID and date.");
    	                }
    	            } catch (SQLException e) {
    	                try {
    	                    connection.rollback();
    	                } catch (SQLException rollbackEx) {
    	                    rollbackEx.printStackTrace();
    	                }
    	                showErrorAlert("Database Delete Error", "Unable to delete attendance from the database.");
    	                e.printStackTrace();
    	            }
    	        }
    	    }
    	

    	
    	
    	
    	
    	
    }
////        ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
////
////        try {
////            connection.setAutoCommit(false);
////            String query = "DELETE FROM Attendance WHERE student_id = (SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?) AND date = ?";
////            PreparedStatement statement = connection.prepareStatement(query);
////
////            for (String student : selectedStudents) {
////                statement.setString(1, student);
////                statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
////                statement.addBatch();
////                
////            }
////
////            statement.executeBatch();
////            connection.commit();
////
////            // Show confirmation alert
////            Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully deleted.", ButtonType.OK);
////            alert.showAndWait();
////        } catch (SQLException e) {
////            try {
////                connection.rollback();
////            } catch (SQLException rollbackEx) {
////                rollbackEx.printStackTrace();
////            }
////            showErrorAlert("Database Delete Error", "Unable to delete attendance from the database.");
////            e.printStackTrace();
////        }
//    	
//    	    ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
//
//    	    try {
//    	        connection.setAutoCommit(false);
//    	        String query = "DELETE FROM Attendance WHERE student_id = (SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?) AND date = ?";
//    	        PreparedStatement statement = connection.prepareStatement(query);
//
//    	        for (String student : selectedStudents) {
//    	            statement.setString(1, student);
//    	            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
//    	            statement.addBatch();
//    	            
//    	            // Clear parameters for the next iteration
//    	            statement.clearParameters();
//    	        }
//
//    	        statement.executeBatch();
//    	        connection.commit();
//
//    	        // Show confirmation alert
//    	        Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully deleted.", ButtonType.OK);
//    	        alert.showAndWait();
//    	    } catch (SQLException e) {
//    	        try {
//    	            connection.rollback();
//    	        } catch (SQLException rollbackEx) {
//    	            rollbackEx.printStackTrace();
//    	        }
//    	        showErrorAlert("Database Delete Error", "Unable to delete attendance from the database.");
//    	        e.printStackTrace();
//    	    }
//    	

    
	/*
	 * // @FXML // private void updateAttendance() { // ObservableList<String>
	 * selectedStudents = studentListView.getSelectionModel().getSelectedItems(); //
	 * // // Check if any student is selected // if (selectedStudents.isEmpty()) {
	 * // showErrorAlert("No Students Selected",
	 * "Please select at least one student to update attendance."); // return; // }
	 * // // // Prompt the user to enter the new attendance status //
	 * TextInputDialog dialog = new TextInputDialog(); //
	 * dialog.setTitle("Update Attendance"); //
	 * dialog.setHeaderText("Enter new attendance status:"); //
	 * dialog.setContentText("Status:"); // // Optional<String> result =
	 * dialog.showAndWait(); // if (result.isPresent()) { // String status =
	 * result.get(); // // try { // connection.setAutoCommit(false); // String
	 * updateQuery =
	 * "UPDATE Attendance SET status = ? WHERE student_id = ? AND date = ?"; //
	 * PreparedStatement statement = connection.prepareStatement(updateQuery); // //
	 * for (String student : selectedStudents) { // int studentId =
	 * getStudentId(student); // if (studentId != -1) { // statement.setString(1,
	 * status); // statement.setInt(2, studentId); // statement.setDate(3,
	 * java.sql.Date.valueOf(LocalDate.now())); // statement.addBatch(); // } // }
	 * // // statement.executeBatch(); // connection.commit(); // // // Show
	 * confirmation alert // Alert alert = new Alert(AlertType.INFORMATION,
	 * "Attendance successfully updated.", ButtonType.OK); // alert.showAndWait();
	 * // // // Refresh the attendance table view // viewInfo(); // } catch
	 * (SQLException e) { // try { // connection.rollback(); // } catch
	 * (SQLException rollbackEx) { // rollbackEx.printStackTrace(); // } //
	 * showErrorAlert("Database Update Error",
	 * "Unable to update attendance in the database."); // e.printStackTrace(); // }
	 * // } // } // // @FXML // private void deleteAttendance() { //
	 * ObservableList<String> selectedStudents =
	 * studentListView.getSelectionModel().getSelectedItems(); // // try { //
	 * connection.setAutoCommit(false); // String query =
	 * "DELETE FROM Attendance WHERE student_id = ? AND date = ?"; //
	 * PreparedStatement statement = connection.prepareStatement(query); // // for
	 * (String student : selectedStudents) { // int studentId =
	 * getStudentId(student); // if (studentId != -1) { // statement.setInt(1,
	 * studentId); // statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
	 * // statement.addBatch(); // } // } // // statement.executeBatch(); //
	 * connection.commit(); // // // Show confirmation alert // Alert alert = new
	 * Alert(AlertType.INFORMATION, "Attendance successfully deleted.",
	 * ButtonType.OK); // alert.showAndWait(); // } catch (SQLException e) { // try
	 * { // connection.rollback(); // } catch (SQLException rollbackEx) { //
	 * rollbackEx.printStackTrace(); // } // showErrorAlert("Database Delete Error",
	 * "Unable to delete attendance from the database."); // e.printStackTrace(); //
	 * } // // } //
	 */    private int getStudentId(String fullName) {
        try {
            String query = "SELECT id FROM students WHERE CONCAT(first_name, ' ', last_name) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fullName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            showErrorAlert("Database Query Error", "Unable to retrieve student ID from the database.");
            e.printStackTrace();
        }
        return -1;
    }
    
//    @FXML
//    private void updateAttendance() {
//        ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
//
//        // Check if any student is selected
//        if (selectedStudents.isEmpty()) {
//            showErrorAlert("No Students Selected", "Please select at least one student to update attendance.");
//            return;
//        }
//
//        // Prompt the user to enter the new attendance status
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Update Attendance");
//        dialog.setHeaderText("Enter new attendance status:");
//        dialog.setContentText("Status:");
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) {
//            String status = result.get();
//
//            // Prompt the user to enter the date
//            TextInputDialog dateDialog = new TextInputDialog();
//            dateDialog.setTitle("Update Attendance");
//            dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
//            dateDialog.setContentText("Date:");
//
//            Optional<String> dateResult = dateDialog.showAndWait();
//            if (dateResult.isPresent()) {
//                String dateString = dateResult.get();
//                LocalDate date;
//                try {
//                    date = LocalDate.parse(dateString);
//                } catch (DateTimeParseException e) {
//                    showErrorAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
//                    return;
//                }
//
//                try {
//                    connection.setAutoCommit(false);
//                    String updateQuery = "UPDATE Attendance SET status = ? WHERE student_id = ? AND date = ?";
//                    PreparedStatement statement = connection.prepareStatement(updateQuery);
//
//                    for (String student : selectedStudents) {
//                        int studentId = getStudentId(student);
//                        if (studentId != -1) {
//                            statement.setString(1, status);
//                            statement.setInt(2, studentId);
//                            statement.setDate(3, java.sql.Date.valueOf(date));
//                            statement.addBatch();
//                        }
//                    }
//
//                    statement.executeBatch();
//                    connection.commit();
//
//                    // Show confirmation alert
//                    Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully updated.", ButtonType.OK);
//                    alert.showAndWait();
//
//                    // Refresh the attendance table view
//                    viewInfo();
//                } catch (SQLException e) {
//                    try {
//                        connection.rollback();
//                    } catch (SQLException rollbackEx) {
//                        rollbackEx.printStackTrace();
//                    }
//                    showErrorAlert("Database Update Error", "Unable to update attendance in the database.");
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @FXML
//    private void deleteAttendance() {
//        ObservableList<String> selectedStudents = studentListView.getSelectionModel().getSelectedItems();
//
//        // Prompt the user to enter the date
//        TextInputDialog dateDialog = new TextInputDialog();
//        dateDialog.setTitle("Delete Attendance");
//        dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
//        dateDialog.setContentText("Date:");
//
//        Optional<String> dateResult = dateDialog.showAndWait();
//        if (dateResult.isPresent()) {
//            String dateString = dateResult.get();
//            LocalDate date;
//            try {
//                date = LocalDate.parse(dateString);
//            } catch (DateTimeParseException e) {
//                showErrorAlert("Invalid Date", "Please enter a valid date in the format YYYY-MM-DD.");
//                return;
//            }
//
//            try {
//                connection.setAutoCommit(false);
//                String query = "DELETE FROM Attendance WHERE student_id = ? AND date = ?";
//                PreparedStatement statement = connection.prepareStatement(query);
//
//                for (String student : selectedStudents) {
//                    int studentId = getStudentId(student);
//                    if (studentId != -1) {
//                        statement.setInt(1, studentId);
//                        statement.setDate(2, java.sql.Date.valueOf(date));
//                        statement.addBatch();
//                    }
//                }
//
//                statement.executeBatch();
//                connection.commit();
//
//                // Show confirmation alert
//                Alert alert = new Alert(AlertType.INFORMATION, "Attendance successfully deleted.", ButtonType.OK);
//                alert.showAndWait();
//            } catch (SQLException e) {
//                try {
//                    connection.rollback();
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//                showErrorAlert("Database Delete Error", "Unable to delete attendance from the database.");
//                e.printStackTrace();
//            }
//        }
//    }
//
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
    private void viewInfo() {
        ObservableList<Attendance> attendance = FXCollections.observableArrayList();
        final String url = "jdbc:mysql://localhost:3307/utmss";
        final String user = "root";
        final String pass = "Kuwait914";
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT attendance_id, student_id, date, status FROM Attendance";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int attendanceId = resultSet.getInt("attendance_id");
                int studentId = resultSet.getInt("student_id");
                java.sql.Date date = resultSet.getDate("date");
                String status = resultSet.getString("status");
                attendance.add(new Attendance(attendanceId, studentId, date.toLocalDate(), status));
            }

            attendanceTableView.setItems(attendance);
        } catch (SQLException e) {
            showErrorAlert("Error", "Failed to retrieve attendance from the database.");
            e.printStackTrace();
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
