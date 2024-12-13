package admin_app;
import banque.GestionAdmin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.w3c.dom.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;

public class LoginController {
    @FXML
    private ImageView darkLightImageView;  
    @FXML
    private HBox root;
    @FXML
    private Button connectBtn;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            Scene scene = root.getScene(); 
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
            } else {
                System.out.println("Erreur : Scene non disponible.");
            }
        });  
    }    

    

    @FXML
    private void toggleStyle() {
        ThemeManager.toggleTheme();
        Scene scene = darkLightImageView.getScene();
        scene.getStylesheets().clear();
        System.out.println(ThemeManager.isDarkMode());

        if (ThemeManager.isDarkMode()) {
            scene.getStylesheets().add(getClass().getResource("/gui/css/dark-style.css").toExternalForm());
            darkLightImageView.setImage(new Image("/gui/image/sun.jpg"));
        } else {
            scene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
            darkLightImageView.setImage(new Image("/gui/image/moon.png"));
        }
    }
 

    @FXML
    private PasswordField passField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String pin = passField.getText();
        if (pin.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de formulaire", "Veuillez remplir le PIN.");
        } else if (GestionAdmin.validateCredentials(pin)) {
            openDashboard(event);
        } else {
            showAlert(AlertType.ERROR, "Échec de la connexion", "Le numero PIN est incorrect.");
        }
    }

    
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");
        alert.showAndWait();
    }

    private void openDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fenetres/AdminDashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        Stage currentStage = (Stage) passField.getScene().getWindow();
        currentStage.close();

        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Tableau de Bord");
        dashboardStage.show();
    }

    
}
