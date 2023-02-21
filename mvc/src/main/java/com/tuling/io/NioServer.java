package com.tuling.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fox
 */
public class NioServer {

    public static void main(String[] args)  {

        List<SocketChannel> list = new ArrayList<>();  // 缓存所有的socket
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // 缓存区的大小

        try {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 监听8080
            serverSocketChannel.bind(new InetSocketAddress(8080));
            // channel非阻塞
            serverSocketChannel.configureBlocking(false);

            System.out.println("NioServer 启动....");

            while (true){
                // 非阻塞
                SocketChannel socketChannel = serverSocketChannel.accept();
                Thread.sleep(1000);
                if(socketChannel == null){
                    System.out.println("没有新的客户端建立连接");
                }else {
                    System.out.println("新的客户端建立连接");
                    // channel非阻塞
                    socketChannel.configureBlocking(false);
                    // 将新的socket添加到 list
                    list.add(socketChannel);
                }
                //遍历所有的socket
                for(SocketChannel channel:list){
                    int read = channel.read(byteBuffer);
                    //读模式
                    byteBuffer.flip();
                    System.out.println("读取客户端发送的数据：" +new String(byteBuffer.array(),0,read));
                    byteBuffer.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }


}
