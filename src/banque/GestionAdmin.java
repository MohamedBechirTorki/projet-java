package banque;

public class GestionAdmin {
    public static boolean validateCredentials(String pin) {
        return pin.equals("123456");
    }
}