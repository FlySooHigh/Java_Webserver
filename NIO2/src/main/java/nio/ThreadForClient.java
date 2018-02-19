package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

class ThreadForClient extends Thread{

    private SocketChannel socketChannel;
    private static int messageCounter = 0;

    ThreadForClient(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        while (true) {
            String message = readFromSocketChannel(socketChannel);
            System.out.println(++messageCounter + " - Message from client: " + message + " " + Thread.currentThread());
            if (!message.equals("Bue.")) {
                writeResponseMessage(socketChannel, message);
            } else {
                writeResponseMessage(socketChannel, message);
                break;
            }
        }
//        try {
//            socketChannel.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private String readFromSocketChannel(SocketChannel socketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        try {
            if (socketChannel.read(buffer) > 0){
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes, 0, bytes.length);
                return new String(bytes, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeResponseMessage(SocketChannel socketChannel, String message) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(message.getBytes());
        if (buffer.hasRemaining()){
            buffer.flip();
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }
    }
}
