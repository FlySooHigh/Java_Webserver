package com.flysoohigh;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class FileReceiver {
    public static void main(String[] args) throws IOException {
        FileReceiver server = new FileReceiver();
        SocketChannel socketChannel = server.createServerSocketChannel();
        server.readFromSocketChannel(socketChannel);
    }

    private void readFromSocketChannel(SocketChannel socketChannel) throws IOException {
        String filePath = "NIOFileChannel/receivedConfig/sample.txt";

        Path path = Paths.get(filePath);

        FileChannel fileChannel = FileChannel.open(path, EnumSet.of(
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) > 0){
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("Received file successfully!");
        socketChannel.close();
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
