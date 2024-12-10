package banque;

public interface ClientManagement {
    public void ajouterClient(String cin, String nom, String prenom, String telephone, String email); 
    public Client consulterClient(String cin);
    public void modifierClient(String cin, String nom, String prenom, String telephone); 
    public void supprimerClient(String cin);
    public void afficherClients(); 
    public Compte[] getComptes(String cin);
}
