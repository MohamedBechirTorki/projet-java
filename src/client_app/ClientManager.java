package client_app;
import banque.Client;


public class ClientManager {
    private static Client client = null;  

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client c) {
        client = c;
    }
}
