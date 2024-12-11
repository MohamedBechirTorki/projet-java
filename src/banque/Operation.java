package banque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Operation {
    private String typeOperation;  
    private double montant;        
    private GestionCompte gestionCompte;
    private Compte compteSource;
    private Compte compteDestination;

    public static String url = "jdbc:mysql://localhost:3306/banking_db";  
    public static  String user = "root";  
    public static String password = "medbechir";  


    public Operation(String typeOperation, double montant, Compte compteSource, Compte compteDestination) {
         /*
        Si le type de operation est retrait on va passer null pour le valeur compteDestination
        Si le type de operation est depot on va passer null pour le valeur compteSource
        sinon on va taper le deux comptes
        */
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }
    public void executer() {
        switch (typeOperation) {
            case "retrait":
                if (compteSource != null && compteSource.typeCompte.equals("courant"))
                    gestionCompte = new GestionCompteCourant();
                else if (compteSource != null && compteSource.typeCompte.equals("epargne"))
                    gestionCompte = new GestionCompteEpargne();
                else
                    System.out.println("TypeCompte incorrect");
                gestionCompte.retrait(compteSource.numeroCompte, montant);
                System.out.println("Retrait de " + montant + " effectué.");
                break;
            case "versement":
                if (compteDestination != null && compteDestination.typeCompte.equals("courant"))
                    gestionCompte = new GestionCompteCourant();
                else if (compteDestination != null && compteDestination.typeCompte.equals("epargne"))
                    gestionCompte = new GestionCompteEpargne();
                else
                    System.out.println("TypeCompte incorrect");
                gestionCompte.depot(compteDestination.numeroCompte, montant);
                System.out.println("Versement de " + montant + " effectué.");
                break;
            case "virement":
                if (compteSource != null && compteSource.typeCompte.equals("courant"))
                    gestionCompte = new GestionCompteCourant();
                else if (compteSource != null && compteSource.typeCompte.equals("epargne"))
                    gestionCompte = new GestionCompteEpargne();
                else
                    System.out.println("TypeCompte incorrect");
                gestionCompte.retrait(compteSource.numeroCompte, montant);
                if (compteDestination != null) {
                    gestionCompte.depot(compteDestination.numeroCompte, montant);
                    System.out.println("Virement de " + montant + " effectué.");
                } else {
                    System.out.println("Erreur: compte destination est null.");
                }
                break;
            default:
                System.out.println("Opération inconnue.");
        }
    }
    
    public void souvgarderOperation() {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO Operations (typeOperation, montant, date, numeroCompteSource, numeroCompteDest) "
                        + "VALUES (?, ?, CURRENT_DATE, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, typeOperation);
            stmt.setDouble(2, montant);
            stmt.setString(3, (compteSource != null) ? compteSource.numeroCompte : null);
            stmt.setString(4, (compteDestination != null) ? compteDestination.numeroCompte : null);
            stmt.executeUpdate();
            System.out.println("Opération sauvegardée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde de l'opération.");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void afficherDetails() {
        System.out.println("Type d'opération: " + typeOperation);
        System.out.println("Montant: " + montant);
    }

    public String getTypeOperation() {
        return typeOperation;
    }
    public static String[] getHistorique() {
        List<String> operationsList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM Operations ORDER BY date DESC";  // Sorting by date
            stmt = conn.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String typeOperation = resultSet.getString("typeOperation");
                double montant = resultSet.getDouble("montant");
                String numeroCompteSource = resultSet.getString("numeroCompteSource");
                String numeroCompteDest = resultSet.getString("numeroCompteDest");

                String operationString = "";
                switch (typeOperation) {
                    case "retrait":
                        operationString = String.format("Retrait: retiré %.2f Dt du compte %s", montant, numeroCompteSource);
                        break;
                    case "versement":
                        operationString = String.format("Versement: déposé %.2f Dt sur le compte %s", montant, numeroCompteDest);
                        break;
                    case "virement":
                        operationString = String.format("Virement: envoyé %.2f Dt de %s vers %s", montant, numeroCompteSource, numeroCompteDest);
                        break;
                    default:
                        operationString = "Opération inconnue";
                        break;
                }

                operationsList.add(operationString);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'historique.");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return operationsList.toArray(new String[0]);
    }

    public static String[] getHistoriqueByCin(String cin) {
        List<String> operationsList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmtAccounts = null;
        PreparedStatement stmtOperations = null;
        ResultSet rsAccounts = null;
        ResultSet rsOperations = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            String sqlAccounts = "SELECT numeroCompte FROM Comptes WHERE cinClient = ?";
            stmtAccounts = conn.prepareStatement(sqlAccounts);
            stmtAccounts.setString(1, cin);
            rsAccounts = stmtAccounts.executeQuery();

            List<String> accountNumbers = new ArrayList<>();
            while (rsAccounts.next()) {
                accountNumbers.add(rsAccounts.getString("numeroCompte"));
            }

            if (accountNumbers.isEmpty()) {
                return new String[]{"Aucun compte trouvé pour ce CIN."};
            }

            String sqlOperations = "SELECT * FROM Operations WHERE numeroCompteSource = ? OR numeroCompteDest = ? ORDER BY date DESC";
            stmtOperations = conn.prepareStatement(sqlOperations);

            for (String numeroCompte : accountNumbers) {
                stmtOperations.setString(1, numeroCompte);
                stmtOperations.setString(2, numeroCompte);
                rsOperations = stmtOperations.executeQuery();

                while (rsOperations.next()) {
                    String typeOperation = rsOperations.getString("typeOperation");
                    double montant = rsOperations.getDouble("montant");
                    String numeroCompteSource = rsOperations.getString("numeroCompteSource");
                    String numeroCompteDest = rsOperations.getString("numeroCompteDest");

                    String operationString = "";
                    if (numeroCompteSource != null && numeroCompteSource.equals(numeroCompte)) {
                        switch (typeOperation) {
                            case "retrait":
                                operationString = String.format("Retrait: retiré %.2f Dt du compte %s", montant, numeroCompteSource);
                                break;
                            case "versement":
                                operationString = String.format("Versement: déposé %.2f Dt sur le compte %s", montant, numeroCompteDest);
                                break;
                            case "virement":
                                operationString = String.format("Virement: envoyé %.2f Dt de %s vers %s", montant, numeroCompteSource, numeroCompteDest);
                                break;
                            default:
                                operationString = "Opération inconnue";
                                break;
                        }
                    } else if (numeroCompteDest != null && numeroCompteDest.equals(numeroCompte)) {
                        switch (typeOperation) {
                            case "versement":
                                operationString = String.format("Versement: reçu %.2f Dt du compte %s", montant, numeroCompteSource);
                                break;
                            case "virement":
                                operationString = String.format("Virement: reçu %.2f Dt de %s", montant, numeroCompteSource);
                                break;
                        }
                    }

                    operationsList.add(operationString);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"Erreur lors de la récupération de l'historique."};
        } finally {
            try {
                if (rsAccounts != null) rsAccounts.close();
                if (stmtAccounts != null) stmtAccounts.close();
                if (rsOperations != null) rsOperations.close();
                if (stmtOperations != null) stmtOperations.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return operationsList.toArray(new String[0]);
    }
}