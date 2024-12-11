package banque;

import java.time.LocalDate;

public class Client {
    private String cin;            
    private String nom;           
    private String prenom;        
    private String telephone;      
    private String email;         
    private LocalDate dateDeNaissance;  
    private String adresse;       

    public Client(String cin, String nom, String prenom, String telephone, String email, LocalDate dateDeNaissance, String adresse) {
        this.cin = cin;        
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
