package com.tistory.workshop6349.server;

import com.tistory.workshop6349.receive.ReceiveThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ChatServer {

    public static void main(String[] args) {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        PrintWriter printWriter = null;
        OutputStream outputStream;
        ServerSocket serverSocket;
        Socket socket = null;
        String outMessage = null;

        try {
            serverSocket = new ServerSocket(5434);
            System.out.println("서버 실행 중...");
            socket = serverSocket.accept();
            outputStream = socket.getOutputStream();
            ReceiveThread receiveThread = new ReceiveThread(socket);
            receiveThread.start();

            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            printWriter = new PrintWriter(bufferedWriter, true);
            while (true) {
                outMessage = bufferedReader.readLine();
                if (outMessage.equals("exit")) {
                    break;
                }
                printWriter.println("server: " + outMessage);
            }
            printWriter.close();
            socket.close();

            if (receiveThread.isAlive()) {
                receiveThread.interrupt();
                receiveThread = null;
            }
        } catch (SocketException socketException) {
            System.out.println("클라이언트로부터 연결이 끊어졌습니다. 종료합니다....");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}
