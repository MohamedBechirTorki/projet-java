package banque;
public class Compte {
    protected String numeroCompte;  // Numéro du compte
    protected String cinClient;     // CIN du propriétaire du compte
    protected String solde;         // Solde du compte
    protected String dateOuverture; // Date d'ouverture du compte
    protected String typeCompte;
    

    public Compte(String numeroCompte, String cinClient, String solde, String dateOuverture, String typeCompte) {
        this.numeroCompte = numeroCompte;
        this.cinClient = cinClient;
        this.solde = solde;
        this.dateOuverture = dateOuverture;
        this.typeCompte = typeCompte;
    }

    public void afficherDetailsCompte() {
        System.out.println("Numéro de compte: " + numeroCompte);
        System.out.println("CIN du propriétaire: " + cinClient);
        System.out.println("Solde: " + solde);
        System.out.println("Date d'ouverture: " + dateOuverture);
        System.out.println("Type compte: " + typeCompte);
    }
}
