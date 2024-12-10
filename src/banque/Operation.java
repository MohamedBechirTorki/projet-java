package banque;
import java.util.Date;

public class Operation {
    private String typeOperation;  // Type d'opération (Retrait, Versement, Virement)
    private double montant;        // Montant de l'opération
    private String dateOperation;  // Date de l'opération
    private GestionCompte gestionCompte;
    private Compte compteSource;
    private Compte compteDestination;

    // Constructeur pour initialiser une opération
    public Operation(String typeOperation, double montant, Compte compteSource, Compte compteDestination) {
         /*
        Si le type de operation est retrait on va passer null pour le valeur compteDestination
        Si le type de operation est depot on va passer null pour le valeur compteSource
        sinon on va taper le deux comptes
        */
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.dateOperation = new Date().toString();
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    public void executer() {


        switch (typeOperation) {
            case "retrait":
                // Effectuer un retrait
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
                // Effectuer un versement
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
                gestionCompte.depot(compteDestination.numeroCompte, montant);
                System.out.println("Virement de " + montant + " effectué.");

                break;
            default:
                System.out.println("Opération inconnue.");
        }
    }

    // Méthode pour afficher les détails de l'opération
    public void afficherDetails() {
        System.out.println("Type d'opération: " + typeOperation);
        System.out.println("Montant: " + montant);
        System.out.println("Date: " + dateOperation);
    }

    // Getters et Setters
    public String getTypeOperation() {
        return typeOperation;
    }
}