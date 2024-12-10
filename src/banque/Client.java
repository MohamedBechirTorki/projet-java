package banque;

public class Client {
    private String cin;            
    private String nom;           
    private String prenom;        
    private String telephone;      
    private String email;

    public Client(String cin, String nom, String prenom, String telephone, String email) {
        this.cin = cin;        
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;      
    }

    public String getCin() {
        return cin;
    }

    public String getNomPrenom() {
        return nom + " " + prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }
}
