package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by sbt-kadatskiy-av on 09.02.2018.
 */
public class SocketThread extends Thread{
    private Socket clientSocket;

    public SocketThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equals("Bue.")) break;
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
