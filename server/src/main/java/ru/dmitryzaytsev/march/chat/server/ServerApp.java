package ru.dmitryzaytsev.march.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        final int PORT = 8189;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту: " + PORT);
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключился");
            String msg;
            int i = 0;
            while (true) {
                msg = in.readUTF();
                if (msg.equals("/stat\n")) {
                    out.writeUTF("Количество сообщений - " + i);
                } else {
                    i++;
                    out.writeUTF("Echo: " + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
