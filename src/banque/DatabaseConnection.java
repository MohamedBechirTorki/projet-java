import java.sql.*;

public class DatabaseConnection {
    public static Connection connect() throws SQLException {
        // URL de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/banking_db";
        String user = "root"; // Nom d'utilisateur de MySQL
        String password = "medbechir";  // Mot de passe MySQL (ajustez si nécessaire)
        
        // Etablissement de la connexion
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            
            // Exécuter une requête pour récupérer les clients
            String query = "SELECT * FROM Clients";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                System.out.println("CIN: " + rs.getString("cin"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Prénom: " + rs.getString("prenom"));
                System.out.println("Téléphone: " + rs.getString("telephone"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("-----------");
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
