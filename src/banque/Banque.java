package banque;

import java.sql.*;

public class Banque {
    public static void main(String[] args) {

        GestionCompteCourant gestionCompte = new GestionCompteCourant();
        Compte c1 = gestionCompte.consulterCompte("4567158975347844");
        Compte c2 = gestionCompte.consulterCompte("4567158975347899");
        Operation o = new Operation("virement", 560, c1, c2);
        o.executer();



    }
}
