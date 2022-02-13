package com.tistory.workshop6349.client;

import com.tistory.workshop6349.receive.ReceiveThread;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient {

    public static void main(String[] args) {
        OutputStream outputStream;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter;
        String outMessage = null;

        try {
            Socket socket = new Socket("127.0.0.1", 5434);
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
                printWriter.println("client: " + outMessage);
            }
            printWriter.close();
            socket.close();

            if (receiveThread.isAlive()) {
                receiveThread.interrupt();
                receiveThread = null;
            }

        } catch (SocketException socketException) {
            System.out.println("서버로부터 연결이 끊어졌습니다. 종료합니다....");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
