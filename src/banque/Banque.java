package banque;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class Banque {
    public static void main(String[] args) {
        String cin = "13525111"; // Replace with the CIN you want to query
        String[] historique = Operation.getHistoriqueByCin(cin);

        for (String operation : historique) {
            System.out.println(operation);
        }
    }
}