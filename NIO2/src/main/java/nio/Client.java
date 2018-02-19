package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    private static final String MESSAGE = "Privet";

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        SocketChannel socketChannel = client.createChannel();
        client.sendMessageToServer(socketChannel);
        client.readMessageFromServer(socketChannel);
    }

    private void readMessageFromServer(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        if (socketChannel.read(buffer) > 0){
            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes, 0, bytes.length);
            String answer = new String(bytes, Charset.forName("UTF-8"));
            if (answer.equals(MESSAGE)){
                System.out.println("Received the same message! Woohoo!");
            }
            else {
                System.out.println("Something went wrong...");
            }
        }
        socketChannel.close();
    }

    private void sendMessageToServer(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(MESSAGE.getBytes());

        if (buffer.hasRemaining()){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
//        socketChannel.close();
//        System.out.println("Client finished work and disconnected");
    }

    private SocketChannel createChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9000));
        return socketChannel;
    }
}
