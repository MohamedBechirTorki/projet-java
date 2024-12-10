package banque;
public interface CompteManagement {
    public void ajouterCompte(String numeroCompte, String cinClient, String typeCompte);
    public Compte consulterCompte(String numeroCompte);
    public void supprimerCompte(String numeroCompte);
    public abstract void retrait(String numeroCompte, double montant);
}
