package client_app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;


public class DashboardController {
    @FXML
    private HBox root;
    @FXML
    private Pane profil;
    @FXML
    private Pane comptes;
    @FXML
    private Pane historique;
    @FXML
    private Pane ouvrirProfil;
    @FXML
    private Pane ouvrirComptes;
    @FXML
    private Pane ouvrirHistorique;
    @FXML
    private ImageView darkLightImageView;
    @FXML
    private ImageView profilPic;

    @FXML
    private Label nomPrenom;
    @FXML
    private Label tele;
    @FXML
    private Label cin;
    @FXML
    private Label email;


    
    @FXML
    private void initialize() {
        profilPic.setFitHeight(140.0);
        profilPic.setFitWidth(140.0);
        Circle clip = new Circle(70, 70, 70); 
        profilPic.setClip(clip);
        profilPic.setPreserveRatio(true);

        tele.setText(ClientManager.getClient().getTelephone());
        cin.setText(ClientManager.getClient().getCin());
        email.setText(ClientManager.getClient().getEmail());
        nomPrenom.setText(ClientManager.getClient().getNomPrenom());
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                System.out.println("Scene is now available. Applying styles...");
                ouvrirProfil.setStyle("-fx-border-width: 0 7px 0 0;");
                newScene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
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
    private void handelProfil(MouseEvent event) { 
        profil.setVisible(true);
        comptes.setVisible(false);
        historique.setVisible(false);
        ouvrirProfil.setStyle("-fx-border-width: 0 7px 0 0;");
        ouvrirComptes.setStyle("");
        ouvrirHistorique.setStyle("");
    }

    @FXML
    private void handelComptes(MouseEvent event) { 
        profil.setVisible(false);
        comptes.setVisible(true);
        historique.setVisible(false);
        ouvrirProfil.setStyle("");
        ouvrirComptes.setStyle("-fx-border-width: 0 7px 0 0;");
        ouvrirHistorique.setStyle("");
    }

    @FXML
    private void handelHistorique() { 
        profil.setVisible(false);
        comptes.setVisible(false);
        historique.setVisible(true);
        ouvrirProfil.setStyle("");
        ouvrirComptes.setStyle("");
        ouvrirHistorique.setStyle("-fx-border-width: 0 7px 0 0;");
    }
}
