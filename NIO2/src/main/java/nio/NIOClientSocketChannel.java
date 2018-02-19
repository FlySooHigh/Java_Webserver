package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClientSocketChannel extends Thread{
    private SocketChannel clientSocketChannel;

    public NIOClientSocketChannel(SocketChannel socketChannel) {
        this.clientSocketChannel = socketChannel;
    }

    @Override
    public void run() {
//        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Client connected");

//            ByteBuffer byteBuffer = ByteBuffer.allocate(256);

//            System.out.println("Empty buffer -> " + byteBuffer.toString());

//            int bytesRead = clientSocketChannel.read(byteBuffer);

////            System.out.println("Buffer with message -> " + byteBuffer.toString());
////
//            System.out.println("Bytes read from buffer -> " + bytesRead);
//
////            while (bytesRead != -1) {
////                byteBuffer.flip();
//                StringBuilder messageFromBuffer = new StringBuilder();
//                while (byteBuffer.hasRemaining()) {
//                    messageFromBuffer.append((char) byteBuffer.get());
//                }
//
//                System.out.println("messageFromBuffer -> " + messageFromBuffer.toString());
//
//                byteBuffer.clear();
//
//                bytesRead = clientSocketChannel.read(byteBuffer);
////                String message = messageFromBuffer.toString();
////                if (message.equals("Bue.")) {
////                    break;
////                }
//
////                break;
////                byteBuffer.put(message.getBytes());
////                clientSocketChannel.write(byteBuffer);
////            }
//            clientSocketChannel.close();
//        }
//        catch (IOException ex){
//            ex.printStackTrace();
//        }
    }
}
