package client_app;
import banque.Client;
import banque.GestionClient;
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
            // Set the image for dark mode
            darkLightImageView.setImage(new Image("/gui/image/sun.jpg"));
        } else {
            scene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
            // Set the image for light mode
            darkLightImageView.setImage(new Image("/gui/image/moon.png"));
        }
    }
 
    @FXML
    private TextField cinField;

    @FXML
    private PasswordField passField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String cin = cinField.getText();
        String password = passField.getText();

        if (cin.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de formulaire", "Veuillez remplir le CIN et le mot de passe.");
        } else if (validateCredentials(cin, password)) {
            GestionClient gestionClient = new GestionClient();
            Client client = gestionClient.consulterClient(cin);
            ClientManager.setClient(client);
            openDashboard(event);
        } else {
            showAlert(AlertType.ERROR, "Échec de la connexion", "CIN ou mot de passe incorrect.");
        }
    }

    private boolean validateCredentials(String cin, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String url = "jdbc:mysql://localhost:3306/banking_db";
            String user = "root";
            String pass = "medbechir";
            connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM Clients WHERE cin = ? AND motDePass = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fenetres/Dashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        Stage currentStage = (Stage) cinField.getScene().getWindow();
        currentStage.close();

        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Tableau de Bord");
        dashboardStage.show();
    }
}
