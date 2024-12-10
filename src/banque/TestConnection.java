package banque;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/banking_db";  // Remplacez par votre URL et base de données
            String user = "root";  // Utilisateur MySQL
            String password = "medbechir";  // Mot de passe MySQL

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à la base de données !");
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
