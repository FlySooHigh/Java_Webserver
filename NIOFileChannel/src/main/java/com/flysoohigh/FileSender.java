package com.flysoohigh;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender {
    public static void main(String[] args) throws IOException {
        FileSender client = new FileSender();
        SocketChannel socketChannel = client.createChannel();
        client.sendFile(socketChannel);
    }

    private void sendFile(SocketChannel socketChannel) throws IOException {
        String filePath = "NIOFileChannel/senderConfig/sample.txt";

        Path path = Paths.get(filePath);
        FileChannel inChannel = FileChannel.open(path);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) > 0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
        System.out.println("Client finished work and disconnected");
    }

    private SocketChannel createChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9000));
        return socketChannel;
    }
}
