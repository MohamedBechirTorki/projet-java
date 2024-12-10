package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCompteCourant extends GestionCompte {
    public String url = "jdbc:mysql://localhost:3306/banking_db";  // Remplacez par votre URL et base de données
    public String user = "root";  // Utilisateur MySQL
    public String password = "medbechir";  
    public GestionCompteCourant() {}

    @Override
    public void retrait(String numeroCompte, double montant) {

        String query = "UPDATE Comptes SET solde = solde - ? WHERE numeroCompte = ? and ? <= solde + decouvert";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(montant));
            stmt.setString(2, numeroCompte);
            stmt.setString(3, String.valueOf(montant));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double getDecouvert(String numeroCompte) {
        String query = "SELECT decouvert FROM Comptes WHERE numeroCompte = ?";
        double decouvert = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                decouvert = Double.parseDouble(rs.getString("decouvert"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return decouvert;
    }
    public void setDecouvert(double decouvert, String numeroCompte) {
        String query = "UPDATE Comptes SET decouvert ? WHERE numeroCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(decouvert));
            stmt.setString(2, numeroCompte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
