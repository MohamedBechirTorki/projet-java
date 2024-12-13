package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class GestionClient implements ClientManagement {
    public static String url = "jdbc:mysql://localhost:3306/banking_db";  
    public static  String user = "root";  
    public static String password = "medbechir";  

    public GestionClient() {};
    public static void ajouterClient(String cin, String nom, String prenom, String telephone, String email, String adresse, Date dateDeNaissance, String motDePass) {
        String hashedPassword = PasswordManager.hashPasswordWithMD5(motDePass); // Hash the password using MD5
        
        String query = "INSERT INTO Clients (cin, nom, prenom, telephone, email, adresse, dateDeNaissance, motDePass) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, telephone);
            stmt.setString(5, email);
            stmt.setString(6, adresse);
            stmt.setDate(7, dateDeNaissance); 
            stmt.setString(8, hashedPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Client consulterClient(String cin) {
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
                    rs.getString("email"),
                    rs.getDate("dateDeNaissance") != null ? rs.getDate("dateDeNaissance").toLocalDate() : null,
                    rs.getString("adresse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    
    public static void modifierClient(String cin, String nom, String prenom, String telephone, String email, String adresse, String dateDeNaissance) {
        String query = "UPDATE Clients SET nom = ?, prenom = ?, telephone = ?, email = ?, adresse = ?, dateDeNaissance = ? WHERE cin = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, nom);
             stmt.setString(2, prenom);
             stmt.setString(3, telephone);
             stmt.setString(4, email);
             stmt.setString(5, adresse);
             stmt.setString(6, dateDeNaissance); 
            stmt.setString(7, cin);
             stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerClient(String cin) {
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

    public static Compte getCompteCourant(String cin) {
        Compte compte = null;
        String query = "SELECT * FROM Comptes WHERE cinClient = ? and typeCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            stmt.setString(2, "courant");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                compte = new Compte(
                    rs.getString("numeroCompte"),
                    rs.getString("cinClient"),
                    rs.getString("solde"),
                    rs.getString("dateOuverture"),
                    rs.getString("typeCompte"),
                    rs.getString("decouvert"),
                    rs.getString("tauxInteret"),
                    rs.getString("code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compte;
    } 

    public static Compte getCompteEpargne(String cin) {
        Compte compte = null;
        String query = "SELECT * FROM Comptes WHERE cinClient = ? and typeCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cin);
            stmt.setString(2, "epargne");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                compte = new Compte(
                    rs.getString("numeroCompte"),
                    rs.getString("cinClient"),
                    rs.getString("solde"),
                    rs.getString("dateOuverture"),
                    rs.getString("typeCompte"),
                    rs.getString("decouvert"),
                    rs.getString("tauxInteret"),
                    rs.getString("code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compte;
    } 

    public static Compte[] getComptes(String cin) {
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
                    rs.getString("typeCompte"),
                    rs.getString("decouvert"),
                    rs.getString("tauxInteret"),
                    rs.getString("code")
                );
            
                comptes[n++] = compte;
                    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comptes;

    }
    public static boolean validateCredentials(String cin, String pass) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM Clients WHERE cin = ? AND motDePass = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, PasswordManager.hashPasswordWithMD5(pass));

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

    

    
}
