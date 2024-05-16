package server;

public class Main {

    public static void main(String[] args) {
        MessageServer server = new MessageServer();
        server.listenAndProcess();
    }
}
