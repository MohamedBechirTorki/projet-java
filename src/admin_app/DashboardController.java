package admin_app;
import banque.GestionClient;
import banque.GestionCompte;
import banque.Compte;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.UnaryOperator;

import banque.Compte;
import banque.GestionClient;
import banque.GestionCompte;
import banque.Operation;
import client_app.ClientManager;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;

import java.util.Random;




import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import javafx.fxml.FXML;


import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


import java.sql.Date;



public class DashboardController {
    @FXML
    private ScrollPane histoScroll; 
    @FXML
    private VBox vbox; 
    @FXML
    private TextField nomLabel;
    @FXML
    private TextField taux;
    @FXML
    private TextField decouvert;
    @FXML
    private TextField taux2;
    @FXML
    private TextField decouvert2;
    @FXML
    private TextField prenomLabel;
    @FXML
    private TextField teleLabel;
    @FXML
    private TextField cinLabel;
    @FXML
    private TextField cinLabel2;
    @FXML
    private TextField cinLabel3;
    @FXML
    private TextField addLabel;
    @FXML
    private DatePicker dateNais;
    @FXML
    private TextField emailLabel;
    @FXML
    private TextField numCompte;
    @FXML
    private TextField numCompte2;
    @FXML
    private TextField montant;
    @FXML
    private RadioButton typeCourant;
    @FXML
    private RadioButton typeEpargne;
    @FXML
    private RadioButton typeCourant2;
    @FXML
    private RadioButton typeEpargne2;
    @FXML
    private HBox root;
    @FXML
    private Pane gestion;
    @FXML
    private Pane depot;
    @FXML
    private Pane historique;
    @FXML
    private Pane ouvrirGestion;
    @FXML
    private Pane ouvrirDepot;
    @FXML
    private Pane ouvrirHistorique;
    @FXML
    private Pane decouvertPane;
    @FXML
    private Pane tauxPane;
    @FXML
    private Pane decouvertPane2;
    @FXML
    private Pane tauxPane2;
    @FXML
    private ImageView darkLightImageView;
  
    

    @FXML
    private void initialize() {
        updatePaneVisibility();
        updatePaneVisibility2();
        dateNais.setValue(LocalDate.of(2000, 1, 1));
        typeCourant.setOnAction(event -> updatePaneVisibility());
        typeEpargne.setOnAction(event -> updatePaneVisibility());
        typeCourant2.setOnAction(event -> updatePaneVisibility2());
        typeEpargne2.setOnAction(event -> updatePaneVisibility2());
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                ouvrirGestion.setStyle("-fx-border-width: 0 7px 0 0;");
                newScene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
            }
        });
        vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
        histoScroll.setContent(vbox);
        histoScroll.setFitToWidth(true); 
    }

    private void updatePaneVisibility() {
        if (typeCourant.isSelected()) {
            decouvertPane.setVisible(true);
            tauxPane.setVisible(false);
        } else if (typeEpargne.isSelected()) {
            decouvertPane.setVisible(false);
            tauxPane.setVisible(true);
        }
    }
    private void updatePaneVisibility2() {
        if (typeCourant2.isSelected()) {
            decouvertPane2.setVisible(true);
            tauxPane2.setVisible(false);
        } else if (typeEpargne2.isSelected()) {
            decouvertPane2.setVisible(false);
            tauxPane2.setVisible(true);
        }
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
    public static String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Generates a number between 0 and 9
            password.append(digit);
        }

        return password.toString();
    }
    @FXML
    public void handelAjouter(ActionEvent event) {
        try {
            String nom = nomLabel.getText().trim();
            String prenom = prenomLabel.getText().trim();
            String telephone = teleLabel.getText().trim();
            String cin = cinLabel.getText().trim();
            String adresse = addLabel.getText().trim();
            String email = emailLabel.getText().trim();

            LocalDate dateNaissance = dateNais.getValue();
            String typeCompte = typeCourant.isSelected() ? "courant" : "epargne";
            String value;
            if (typeCompte == "courant") 
                value = decouvert.getText() != "" ? decouvert.getText() : "0";
            else 
                value = taux.getText() != "" ? taux.getText() : "0";

            if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || cin.isEmpty() || adresse.isEmpty() || email.isEmpty() || dateNais.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
                return;
            }
            String newPassword = generateRandomPassword();
            GestionClient.ajouterClient(cin, nom, prenom, telephone, email, adresse, Date.valueOf(dateNaissance), newPassword);

            GestionCompte.ajouterCompte(cin, typeCompte, "0");

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le client et son compte ont été ajoutés avec succès le mot de passe est "+newPassword);

            resetFields();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetFields() {
        nomLabel.clear();
        prenomLabel.clear();
        teleLabel.clear();
        cinLabel.clear();
        addLabel.clear();
        dateNais.setValue(null);
        emailLabel.clear();
        typeCourant.setSelected(true);
        taux.clear();
        decouvert.clear();
    }


    @FXML
    private void handelGestion(MouseEvent event) { 
        gestion.setVisible(true);
        depot.setVisible(false);
        historique.setVisible(false);
        ouvrirGestion.setStyle("-fx-border-width: 0 7px 0 0;");
        ouvrirDepot.setStyle("");
        ouvrirHistorique.setStyle("");
    }

    @FXML 
    private void handelAjCompte() {
        String cin = cinLabel2.getText().trim();
        String typeCompte = typeCourant2.isSelected() ? "courant" : "epargne";
        String value;
        if (typeCompte == "courant") 
            value = decouvert2.getText() != "" ? decouvert2.getText() : "0";
        else 
            value = taux2.getText() != "" ? taux2.getText() : "0";

        if (cin.isEmpty() || (decouvert2.getText().isEmpty() && taux2.getText().isEmpty())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }
        
        try {
            GestionCompte.ajouterCompte(cin, typeCompte, value);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Le Compte a été ajoutés avec succès !");
        resetFields2();
    }

    private void resetFields2() {
        cinLabel2.clear();
        typeCourant2.setSelected(true);
        taux2.clear();
        decouvert2.clear();
    }

    @FXML
    private void handelDepot(MouseEvent event) {
        gestion.setVisible(false);
        depot.setVisible(true);
        historique.setVisible(false);
        ouvrirGestion.setStyle("");
        ouvrirDepot.setStyle("-fx-border-width: 0 7px 0 0;");
        ouvrirHistorique.setStyle("");
        
    }


   

    @FXML
    private void supprimerClient() {
        String cin = cinLabel3.getText().trim();
        if (cin.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le CIN doit être remplis !");
            return;
        }
        try {
            Operation.supprimerOperationsParCinClient(cin);
            Compte c1 = GestionClient.getCompteCourant(cin);
            Compte c2 = GestionClient.getCompteEpargne(cin);
            if (c1 != null) GestionCompte.supprimerCompte(c1.getNumeroCompte());
            if (c2 != null) GestionCompte.supprimerCompte(c2.getNumeroCompte());
            GestionClient.supprimerClient(cin);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Le Compte a été supprimer avec succès !");
        cinLabel3.setText("");
    }

    @FXML
    private void supprimerCompte() {
        String num = numCompte.getText().trim();
        if (num.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numero de compte doit être remplis !");
            return;
        }
        try {
            Operation.supprimerOperationsParNumeroCompte(num);
            GestionCompte.supprimerCompte(num);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Le Compte a été supprimer avec succès !");
        numCompte.setText("");
    }

    @FXML 
    private void handelDepotBtn() {
        String num = numCompte2.getText().trim();
        String mon = montant.getText().trim();
        if (num.isEmpty() || mon.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numero de compte et le montant doivent être remplis !");
            return;
        }
        try {
            Operation o = new Operation("versement", Double.parseDouble(mon), null, GestionCompte.consulterCompte(num));
            o.executer();
            o.souvgarderOperation();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Le versement a été effectuer avec succès !");
        numCompte2.setText("");
        montant.setText("");

    }

    
    @FXML
    private void handelHistorique(MouseEvent event) { 
        vbox.getChildren().clear();
        gestion.setVisible(false);
        depot.setVisible(false);
        historique.setVisible(true);
        ouvrirGestion.setStyle("");
        ouvrirDepot.setStyle("");
        ouvrirHistorique.setStyle("-fx-border-width: 0 7px 0 0;");

        String[] histo = Operation.getHistorique();

        addLinesToScrollPane(histo);
        
        
    }


    public void addLinesToScrollPane(String[] lines) {
        for (String line : lines) {
            Label label = new Label(line);
            vbox.getChildren().add(label);
        }
    }
}
