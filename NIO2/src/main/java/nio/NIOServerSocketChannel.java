package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOServerSocketChannel {

    public static void main(String[] args) {
        boolean running = true;
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(5050))){
            System.out.println("Server started");
            while (running){
                System.out.println("11111");
                new NIOClientSocketChannel(serverSocketChannel.accept()).start();
                System.out.println("22222");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
