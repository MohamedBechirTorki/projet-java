package client_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fenetres/LoginSignup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 769, 481);
            
            if (ThemeManager.isDarkMode()) {
                scene.getStylesheets().add(getClass().getClassLoader().getResource("/gui/css/dark-style.css").toExternalForm());
            } else {
                scene.getStylesheets().add(getClass().getClassLoader().getResource("/gui/css/light-style.css").toExternalForm());
            }
                
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Disable resizing
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading LoginSignup.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
