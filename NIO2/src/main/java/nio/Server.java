package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        System.out.println("Server started");
        System.out.println("Server local address -> " + serverSocket.getLocalAddress());
        System.out.println("Waiting for client to connect...");
        while (true){
            SocketChannel client = serverSocket.accept();
            new ThreadForClient(client).start();
            System.out.println("Client connected. Client's remote address -> " + client.getRemoteAddress());
        }
    }

}
