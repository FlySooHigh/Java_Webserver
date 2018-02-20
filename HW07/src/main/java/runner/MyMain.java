package runner;

import java.io.IOException;
import java.net.ServerSocket;

public class MyMain {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            System.out.println("Server started");
            while(true){
                new SocketThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
