package banque;

public class Compte {
    protected String numeroCompte;
    protected String cinClient;
    protected String solde;
    protected String dateOuverture;
    protected String typeCompte;
    protected String decouvert;
    protected String tauxInteret;
    protected String code;

    public Compte(String numeroCompte, String cinClient, String solde, String dateOuverture, String typeCompte, String decouvert, String tauxInteret, String code) {
        this.numeroCompte = numeroCompte;
        this.cinClient = cinClient;
        this.solde = solde;
        this.dateOuverture = dateOuverture;
        this.typeCompte = typeCompte;
        this.decouvert = decouvert;
        this.tauxInteret = tauxInteret;
        this.code = code;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getCinClient() {
        return cinClient;
    }

    public void setCinClient(String cinClient) {
        this.cinClient = cinClient;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(String dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    public String getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(String decouvert) {
        this.decouvert = decouvert;
    }

    public String getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(String tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void afficherDetailsCompte() {
        System.out.println("Numéro de compte: " + numeroCompte);
        System.out.println("CIN du propriétaire: " + cinClient);
        System.out.println("Solde: " + solde);
        System.out.println("Date d'ouverture: " + dateOuverture);
        System.out.println("Type compte: " + typeCompte);
        System.out.println("Découvert: " + decouvert);
        System.out.println("Taux d'intérêt: " + tauxInteret);
        System.out.println("Code: " + code);
    }
}
