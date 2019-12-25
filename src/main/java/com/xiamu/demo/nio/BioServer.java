package com.xiamu.demo.nio;

import com.xiamu.demo.juc.threadpool.CustomizeThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author XiaMu
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor threadPool = CustomizeThreadPool.threadPool;
        ServerSocket serverSocket = new ServerSocket(8080);
        // (1) 接收新连接线程
        threadPool.execute(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    threadPool.execute(() -> {
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                // (3) 按字节流方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("读取数据失败");
                        }
                    });
                } catch (IOException e) {
                    System.out.println("连接失败");
                }
            }
        });
    }
}