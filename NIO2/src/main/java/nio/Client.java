package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    private static final String MESSAGE = "Privet";
    private static final String GOODBYE_MESSAGE = "Bue.";
    private static final int MESSAGES_TO_SEND = 100;
    private static AtomicInteger messageCounter = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        SocketChannel socketChannel = client.createChannel();
        for (int i = 0; i < MESSAGES_TO_SEND; i++){
            client.sendMessageToServer(socketChannel);
            client.readMessageFromServer(socketChannel);
        }
        socketChannel.close();
    }

    private synchronized void readMessageFromServer(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        if (socketChannel.read(buffer) > 0){
            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes, 0, bytes.length);
            String answer = new String(bytes, Charset.forName("UTF-8"));
            switch (answer) {
                case MESSAGE:
                    System.out.println(messageCounter.incrementAndGet() + " - Received the same message! " + MESSAGE + " " + Thread.currentThread());
                    break;
                case GOODBYE_MESSAGE:
                    System.out.println(messageCounter.incrementAndGet() + " - Received the same message! " + GOODBYE_MESSAGE + " " + Thread.currentThread());
                    break;
                default:
                    System.out.println("Something went wrong...");
                    break;
            }
        }
    }

    private synchronized void sendMessageToServer(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        if (messageCounter.get() < MESSAGES_TO_SEND - 1){
            buffer.put(MESSAGE.getBytes());
        }
        else {
            buffer.put(GOODBYE_MESSAGE.getBytes());
        }

        if (buffer.hasRemaining()){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
    }

    private SocketChannel createChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9000));
        return socketChannel;
    }
}
