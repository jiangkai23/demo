package com.xiamu.demo.nio;

import com.xiamu.demo.juc.threadpool.CustomizeThreadPool;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author XiaMu
 */
public class IoClient {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = CustomizeThreadPool.threadPool;
        threadPool.execute(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                while (true) {
                    try {
                        socket.getOutputStream().write("1".getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        System.out.println("发送失败");
                    }
                }
            } catch (IOException e) {
                System.out.println("连接失败");
            }
        });
    }
}