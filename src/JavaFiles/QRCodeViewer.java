package JavaFiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class QRCodeViewer extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        
        String[] imagePaths = {
            "C:\\Users\\eshal\\Downloads\\Picture1.png",
            "C:\\Users\\eshal\\Downloads\\Picture2.png",
            "C:\\Users\\eshal\\Downloads\\Picture3.png",
            "C:\\Users\\eshal\\Downloads\\Picture4.png",
            "C:\\Users\\eshal\\Downloads\\Picture5.png",
            "C:\\Users\\eshal\\Downloads\\Picture6.png",
            "C:\\Users\\eshal\\Downloads\\Picture7.png",
            "C:\\Users\\eshal\\Downloads\\Picture8.png",
            "C:\\Users\\eshal\\Downloads\\Picture9.png",
            "C:\\Users\\eshal\\Downloads\\Picture10.png",
            "C:\\Users\\eshal\\Downloads\\Picture11.png",
            "C:\\Users\\eshal\\Downloads\\Picture12.png",
            "C:\\Users\\eshal\\Downloads\\Picture13.png",
            "C:\\Users\\eshal\\Downloads\\Picture14.png",
            "C:\\Users\\eshal\\Downloads\\Picture15.png",
            "C:\\Users\\eshal\\Downloads\\Picture16.png",
            "C:\\Users\\eshal\\Downloads\\Picture17.png",
            "C:\\Users\\eshal\\Downloads\\Picture18.png",
            "C:\\Users\\eshal\\Downloads\\Picture19.png",
            "C:\\Users\\eshal\\Downloads\\Picture20.png",
            "C:\\Users\\eshal\\Downloads\\Picture21.png",
          
        };

        for (String path : imagePaths) {
            try {
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                vbox.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QR Code Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

