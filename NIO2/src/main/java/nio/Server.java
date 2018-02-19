package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        SocketChannel socketChannel = server.createServerSocketChannel();
        String message = server.readFromSocketChannel(socketChannel);
        System.out.println("Message from client: " + message);
        if (!message.equals("Bue.")){
            server.writeResponseMessage(socketChannel, message);
        }
        else {
            socketChannel.close();
        }
        socketChannel.close();
    }

    private void writeResponseMessage(SocketChannel socketChannel, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(message.getBytes());
        if (buffer.hasRemaining()){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
    }

    private String readFromSocketChannel(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        if (socketChannel.read(buffer) > 0){
            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes, 0, bytes.length);
            return new String(bytes, Charset.forName("UTF-8"));
        }
        return null;
    }

    private SocketChannel createServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocket;
        SocketChannel client;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        System.out.println("Server started at: " + serverSocket.getLocalAddress());
        System.out.println("Waiting for client to connect...");
        client = serverSocket.accept();
        System.out.println("Client connected. " + client.getRemoteAddress());
        return client;
    }
}
