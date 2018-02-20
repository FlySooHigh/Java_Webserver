package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

class Client {

    private final String MESSAGE = "Privet";
    private final String GOODBYE_MESSAGE = "Bue.";
    private static final int MESSAGES_TO_SEND = 100;
    private AtomicInteger messageCounter = new AtomicInteger(0);

    void run() throws IOException {
        SocketChannel socketChannel = createChannel();
        for (int i = 0; i < MESSAGES_TO_SEND; i++){
            sendMessageToServer(socketChannel);
            readMessageFromServer(socketChannel);
        }
        socketChannel.close();
    }

    private SocketChannel createChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 5050));
        System.out.println("Client successfully connected");
        return socketChannel;
    }

    private synchronized void readMessageFromServer(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);
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
        ByteBuffer buffer = ByteBuffer.allocate(64);

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

}
