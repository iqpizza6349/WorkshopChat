package com.tistory.workshop6349.receive;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveThread extends Thread {
    
    InputStream inputStream;
    BufferedReader bufferedReader;
    ServerSocket serverSocket;
    Socket socket = null;
    String inMessage = null;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                inMessage = bufferedReader.readLine();
                System.out.println(inMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
