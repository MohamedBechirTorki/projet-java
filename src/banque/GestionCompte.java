package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GestionCompte implements CompteManagement {
    public String url = "jdbc:mysql://localhost:3306/banking_db";  // Remplacez par votre URL et base de données
    public String user = "root";  // Utilisateur MySQL
    public String password = "medbechir"; 
    public GestionCompte() {}
    @Override 
    public void ajouterCompte(String numeroCompte, String cinClient, String typeCompte) {
        String query = "INSERT INTO Comptes (numeroCompte, cinClient, solde, dateOuverture, typeCompte, decouvert, tauxInteret) VALUES (?, ?, ?, CURRENT_DATE, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.setString(2, cinClient);
            stmt.setString(3, "0");
            stmt.setString(4, typeCompte);
            stmt.setString(5, "0");
            stmt.setString(6, "0");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Compte consulterCompte(String numeroCompte) {
        String query = "SELECT * FROM Comptes WHERE numeroCompte = ?";
        Compte compte = null;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                compte = new Compte(
                    rs.getString("numeroCompte"),
                    rs.getString("cinClient"),
                    rs.getString("solde"),
                    rs.getString("dateOuverture"),
                    rs.getString("typeCompte")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compte;
    }
    @Override
    public void supprimerCompte(String numeroCompte) {
        String query = "DELETE FROM Comptes WHERE numeroCompte = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
             stmt.setString(1, numeroCompte);
             stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void depot(String numeroCompte, double montant) {
        if (montant > 0) {
            String query = "UPDATE Comptes SET solde = solde + ? WHERE numeroCompte = ?";
            try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, String.valueOf(montant));
                stmt.setString(2, numeroCompte);
                
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public abstract void retrait(String numeroCompte, double montant);

    public double getSolde(String numeroCompte) {
        String query = "SELECT solde FROM Comptes WHERE numeroCompte = ?";
        double solde = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                solde = Double.parseDouble(rs.getString("solde"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solde;
    }

}