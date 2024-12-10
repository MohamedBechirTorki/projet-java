package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionClient implements ClientManagement {
    public String url = "jdbc:mysql://localhost:3306/banking_db";  // Remplacez par votre URL et base de données
    public String user = "root";  // Utilisateur MySQL
    public String password = "medbechir";  

    public GestionClient() {};
    @Override
    public void ajouterClient(String cin, String nom, String prenom, String telephone, String email) {
        String query = "INSERT INTO Clients (cin, nom, prenom, telephone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, telephone);
            stmt.setString(5, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Client consulterClient(String cin) {
        String query = "SELECT * FROM Clients WHERE cin = ?";
        Client client = null;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = new Client(
                    rs.getString("cin"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    @Override
    public void modifierClient(String cin, String nom, String prenom, String telephone) {
        String query = "UPDATE Clients SET nom = ?, prenom = ?, telephone = ? WHERE cin = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, nom);
             stmt.setString(2, prenom);
             stmt.setString(3, telephone);
             stmt.setString(4, cin);
             stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerClient(String cin) {
        String query = "DELETE FROM Clients WHERE cin = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, cin);
             stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afficherClients() {
        
    }

    @Override
    public Compte[] getComptes(String cin) {
        Compte[] comptes = new Compte[10];
        String query = "SELECT * FROM Comptes WHERE cinClient = ?";
        Compte compte = null;
        int n = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {    
                compte = new Compte(
                    rs.getString("numeroCompte"),
                    rs.getString("cinClient"),
                    rs.getString("solde"),
                    rs.getString("dateOuverture"),
                    rs.getString("typeCompte")
                );
            
                comptes[n++] = compte;
                    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comptes;

    }
    
}
