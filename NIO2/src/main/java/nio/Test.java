package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;

public class Test {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 5050));
            System.out.println("Connected to 127.0.0.1:5050 -> " + socketChannel.isConnected());

            String message = "Privet";

            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());

            // В буфере что-то есть

            System.out.println("buffer.toString() -> " + buffer.toString());

//            buffer.flip();
//            buffer.clear()

            // записываем инфо из буфера в канал

            while(buffer.hasRemaining()){
                System.out.println("Write from buffer to channel -> " + socketChannel.write(buffer));
            }
//            System.out.println("Bytes from buffer into channel -> " + socketChannel.write(buffer));

//            buffer.clear();
//            buffer.flip();

            // теперь хотим прочитать инфо из кАНАЛа в буфер, как это сделать ??

            ByteBuffer newByteBuffer = ByteBuffer.allocate(64);
            newByteBuffer.clear();
            System.out.println("Bytes read into buffer -> " + socketChannel.read(newByteBuffer));

            StringBuilder messageFromNewBuffer = new StringBuilder();

//            while(buffer.hasRemaining()){
//                messageFromNewBuffer.append((char) buffer.get());
//            }

//            buffer.flip();
            if (newByteBuffer.hasRemaining()) {
                byte [] src = new byte[newByteBuffer.limit()];
                newByteBuffer.get(src);
                messageFromNewBuffer.append(new String(src));
            }

            System.out.println("messageFromNewBuffer -> " + messageFromNewBuffer.toString());

//            System.out.println("buffer.toString() -> " + buffer.toString());
//            buffer.flip();
//
//            StringBuilder messageFromBuffer = new StringBuilder();
//
//            while (buffer.hasRemaining()){
//                messageFromBuffer.append((char) buffer.get());
//            }
//            System.out.println("messageFromBuffer -> " + messageFromBuffer.toString());
//
//            buffer.clear();
//            System.out.println("buffer.toString() -> " + buffer.toString());

//            while (true){
//
//            }
//            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
