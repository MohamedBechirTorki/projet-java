package banque;
import java.sql.Date;

public interface ClientManagement {
    public void afficherClients(); 
    public Compte[] getComptes(String cin);
}
