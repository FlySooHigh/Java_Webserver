package runner;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by sbt-kadatskiy-av on 09.02.2018.
 */
public class MyMain {
    public static void main(String[] args) {
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(5050);) {
            System.out.println("Server started");
            while(listening){
                new SocketThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
