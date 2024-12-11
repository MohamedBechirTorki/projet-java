package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCompteEpargne extends GestionCompte {
    public GestionCompteEpargne() {}

    @Override
    public void retrait(String numeroCompte, double montant) {
        String query = "UPDATE Comptes SET solde = solde - ? WHERE numeroCompte = ? and ? <= solde";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, montant);
            stmt.setString(2, numeroCompte);
            stmt.setDouble(3, montant);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calculerInterets(String numeroCompte) {
        String query = "UPDATE Comptes SET solde = solde * tauxInteret / 100 WHERE numeroCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTauxInteret(String numeroCompte) {
        String query = "SELECT tauxInteret FROM Comptes WHERE numeroCompte = ?";
        double tauxInteret = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tauxInteret = Double.parseDouble(rs.getString("tauxInteret"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tauxInteret;
    }

    public void setTauxInteret(String numeroCompte,double tauxInteret) {
        String query = "UPDATE Comptes SET tauxInteret = ? WHERE numeroCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(tauxInteret));    
            stmt.setString(2, numeroCompte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
