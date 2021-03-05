package ru.dmitryzaytsev.march.chat.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea mainTextArea;
    @FXML
    TextField mainTextField;
    DataInputStream in;
    DataOutputStream out;
    private Socket socket;
    final String HOST = "localhost";
    final int PORT = 8189;

    public void sendMessage() {
        if (!mainTextField.getText().trim().equals("")) {
            try {
                String msg = mainTextField.getText().trim() + "\n";
                out.writeUTF(msg);
                mainTextField.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(HOST, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        mainTextArea.appendText(msg + '\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();

        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to " + HOST + ":" + PORT);
        }
    }
}
