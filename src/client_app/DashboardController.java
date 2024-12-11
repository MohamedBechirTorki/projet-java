package client_app;
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



import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import javafx.fxml.FXML;

import java.util.Optional;

import java.util.Optional;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;


public class DashboardController {
    Compte selected = null;
    Compte compte_courant;
    Compte compte_epargne;

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
    private Label adresse;
    @FXML
    private Label email;
    @FXML
    private Label dateNais;
    @FXML
    private Label numCompte;
    @FXML
    private Label solde;

    @FXML 
    private Button compteCourantBtn;
    @FXML 
    private Button compteEpargneBtn;
    @FXML 
    private Button virBtn;
    @FXML 
    private Button retBtn;

    @FXML
    private TextField numDes;
    @FXML
    private TextField montantV;
    @FXML
    private TextField montantR;
    


    
    @FXML
    private void initialize() {
        profilPic.setFitHeight(140.0);
        profilPic.setFitWidth(140.0);
        Circle clip = new Circle(70, 70, 70); 
        profilPic.setClip(clip);
        profilPic.setPreserveRatio(true);

        tele.setText(ClientManager.getClient().getTelephone());
        cin.setText(ClientManager.getClient().getCin());
        adresse.setText(ClientManager.getClient().getAdresse());
        email.setText(ClientManager.getClient().getEmail());
        nomPrenom.setText(ClientManager.getClient().getNomPrenom());
        LocalDate dateDeNaissance = ClientManager.getClient().getDateDeNaissance();
        if (dateDeNaissance != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateNais.setText(dateDeNaissance.format(formatter));
        } else {
            dateNais.setText(""); 
        }
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                System.out.println("Scene is now available. Applying styles...");
                ouvrirProfil.setStyle("-fx-border-width: 0 7px 0 0;");
                newScene.getStylesheets().add(getClass().getResource("/gui/css/light-style.css").toExternalForm());
            }
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null; 
        };

        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);

        montantV.setTextFormatter(textFormatter1);
        montantR.setTextFormatter(textFormatter2);
        numDes.setTextFormatter(textFormatter3);
        vbox = new VBox(10); 
        vbox.setStyle("-fx-padding: 10;"); 
        histoScroll.setContent(vbox);
        histoScroll.setFitToWidth(true); 
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
        
        compte_courant = GestionClient.getCompteCourant(ClientManager.getClient().getCin());
        compte_epargne = GestionClient.getCompteEpargne(ClientManager.getClient().getCin());
        
        
        if (compte_courant == null) {
            compteCourantBtn.setDisable(true);
            compteCourantBtn.setText("Aucun compte courant");
            selected = compte_epargne;
        } else {
            compteCourantBtn.setDisable(false);
            compteCourantBtn.setText("Compte Courant");
        }

        if (compte_epargne == null) {
            compteEpargneBtn.setDisable(true);
            compteEpargneBtn.setText("Aucun compte épargne");
            if (compte_courant == null) {
                selected = null;
            } else {
                selected = compte_courant;
            }
        } else {
            compteEpargneBtn.setDisable(false);
            compteEpargneBtn.setText("Compte Épargne");
        }

        if (compte_courant != null && compte_epargne != null) {
            selected = compte_courant;
        }

        updateSelected();
    }


    @FXML
    private void handleModifier() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier les informations");
        dialog.setHeaderText("Mettez à jour vos informations");

        VBox vbox = new VBox(10);

        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        
        String[] nameParts = nomPrenom.getText().split(" ");
        if (nameParts.length > 0) {
            nomField.setText(nameParts[0]);
        }
        if (nameParts.length > 1) {
            prenomField.setText(nameParts[1]);
        }

        TextField phoneField = new TextField(tele.getText());
        TextField addressField = new TextField(adresse.getText());
        TextField emailField = new TextField(email.getText());
        DatePicker datePicker = new DatePicker();

        nomField.setPromptText("Nom");
        prenomField.setPromptText("Prénom");
        phoneField.setPromptText("Téléphone");
        addressField.setPromptText("Adresse");
        emailField.setPromptText("Email");
        datePicker.setPromptText("Date de Naissance");

        LocalDate maxDate = LocalDate.now().minusYears(18);

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty || date.isAfter(maxDate));
                    }
                };
            }
        });

        if (dateNais.getText() != null && !dateNais.getText().isEmpty()) {
            String[] dateParts = dateNais.getText().split("-");
            if (dateParts.length == 3) {
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                datePicker.setValue(date);
            }
        }

        vbox.getChildren().addAll(nomField, prenomField, phoneField, addressField, emailField, datePicker);
        dialog.getDialogPane().setContent(vbox);

        ButtonType saveButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButton) {
            nomPrenom.setText(nomField.getText() + " " + prenomField.getText());
            tele.setText(phoneField.getText());
            adresse.setText(addressField.getText());
            email.setText(emailField.getText());
            dateNais.setText(datePicker.getValue() != null ? datePicker.getValue().toString() : "");
            GestionClient.modifierClient(ClientManager.getClient().getCin(), nomField.getText(), prenomField.getText(), phoneField.getText(), email.getText(), adresse.getText(), dateNais.getText());

        }
    }

    @FXML 
    public void selectEpargne() {
        selected = compte_epargne;
        selected.afficherDetailsCompte();
        updateSelected();
    }


    @FXML 
    public void selectCourant() {
        selected = compte_courant;
        selected.afficherDetailsCompte();
        updateSelected();
    }

    public void updateSelected() {
        if (selected != null) {
            numCompte.setText(selected.getNumeroCompte());
            solde.setText(String.valueOf(selected.getSolde()));
        } else {
            numCompte.setText("Aucun compte");
            solde.setText("0.00");
        }
        montantR.setText("");
        montantV.setText("");
        numDes.setText("");
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handelVirement() {
        if (Double.parseDouble(solde.getText())+getDecouvertSiCourant() < Double.parseDouble(montantV.getText())) {
            showAlert("Solde insuffisant", "Votre solde est insuffisant pour effectuer ce virement.");
        } else {
            performVirement(montantV.getText(), numDes.getText());
        }
    }

    @FXML
    public void handelRetrait() {
        System.out.println(Double.parseDouble(selected.getSolde())+getDecouvertSiCourant());
        if (Double.parseDouble(selected.getSolde())+getDecouvertSiCourant() < Double.parseDouble(montantR.getText())) {
            showAlert("Solde insuffisant", "Votre solde est insuffisant pour effectuer ce retrait.");
        } else {
            performRetrait(montantR.getText());
        }
    
    }


    void performVirement(String montant, String numDestination) {
        Operation o = new Operation("virement", Double.parseDouble(montant), selected, GestionCompte.consulterCompte(numDestination));
        o.executer();
        o.souvgarderOperation();
        if (selected == compte_courant) selected = GestionClient.getCompteCourant(ClientManager.getClient().getCin());
        else selected = GestionClient.getCompteEpargne(ClientManager.getClient().getCin());
        compte_courant = GestionClient.getCompteCourant(ClientManager.getClient().getCin());
        compte_epargne = GestionClient.getCompteEpargne(ClientManager.getClient().getCin());
        
        updateSelected();
    }

    double getDecouvertSiCourant() {
        System.out.println(selected.getTypeCompte()+ "  " +selected.getDecouvert());
        if (selected.getTypeCompte().equals("courant")) 
            return Double.parseDouble(selected.getDecouvert());
        return 0;
    }

    void performRetrait(String montant) {
        Operation o = new Operation("retrait", Double.parseDouble(montant), selected, null);
        o.executer();
        o.souvgarderOperation();
        if (selected == compte_courant) selected = GestionClient.getCompteCourant(ClientManager.getClient().getCin());
        else selected = GestionClient.getCompteEpargne(ClientManager.getClient().getCin());
        compte_courant = GestionClient.getCompteCourant(ClientManager.getClient().getCin());
        compte_epargne = GestionClient.getCompteEpargne(ClientManager.getClient().getCin());
        updateSelected();
    }

    @FXML
    private ScrollPane histoScroll; 

    private VBox vbox; 
    @FXML
    private void handelHistorique() { 
        profil.setVisible(false);
        comptes.setVisible(false);
        historique.setVisible(true);
        ouvrirProfil.setStyle("");
        ouvrirComptes.setStyle("");
        ouvrirHistorique.setStyle("-fx-border-width: 0 7px 0 0;");

        String[] historique = Operation.getHistoriqueByCin(ClientManager.getClient().getCin());

        addLinesToScrollPane(historique);
        
        
    }


    public void addLinesToScrollPane(String[] lines) {
        for (String line : lines) {
            Label label = new Label(line);
            vbox.getChildren().add(label);
        }
    }


}
