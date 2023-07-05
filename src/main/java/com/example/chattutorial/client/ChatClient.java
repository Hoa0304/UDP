package com.example.chattutorial.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class ChatClient extends Application {

    private static final DatagramSocket socket;
    //Đối tượng DatagramSocket được sử dụng để gửi và nhận các gói tin qua dữ liệu mạng.

    static {
        try {
            socket = new DatagramSocket(); // init to any available port
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static final InetAddress address;
    //Biến address là địa chỉ IP của server. Biến identifier là tên định danh của client.

    static {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String identifier = "Cẩm Hoa";

    private static final int SERVER_PORT = 8000; // send to server
    //Biến SERVER_PORT là số cổng được sử dụng cho server.

    private static final TextArea messageArea = new TextArea();
    //TextArea messageArea là một vùng hiển thị tin nhắn được nhận từ server.

    private static final TextField inputBox = new TextField();
    //TextField inputBox là một hộp văn bản để nhập tin nhắn mới.


    public static void main(String[] args) throws IOException {

        // thread for receiving messages
        ClientThread clientThread = new ClientThread(socket, messageArea);
        clientThread.start();

        // send initialization message to the server
        byte[] uuid = ("init;" + identifier).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, SERVER_PORT);
        socket.send(initialize);

        launch(); // launch GUI
        //Phương thức main của ChatClient khởi tạo một đối tượng ClientThread để lắng nghe các
        // tin nhắn từ server, gửi một tin nhắn khởi tạo đến server để đăng ký với server và cuối cùng là khởi động GUI.

    }

    @Override
    public void start(Stage primaryStage) {

        messageArea.setMaxWidth(500);
        messageArea.setEditable(false);


        inputBox.setMaxWidth(500);
        inputBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String temp = identifier + ":" + inputBox.getText(); // message to send
                messageArea.setText(messageArea.getText() + inputBox.getText() + "\n"); // update messages on screen
                byte[] msg = temp.getBytes(); // convert to bytes
                inputBox.setText(""); // remove text from input box

                // create a packet & send
                DatagramPacket send = new DatagramPacket(msg, msg.length, address, SERVER_PORT);
                try {
                    socket.send(send);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // put everything on screen
        Scene scene = new Scene(new VBox(35, messageArea, inputBox), 550, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        //Phương thức start xử lý GUI của ChatClient. TextArea messageArea sẽ hiển thị các tin nhắn được nhận từ server.
        // TextField inputBox cho phép người dùng nhập tin nhắn mới và gửi đến server khi nhấn phím Enter.
        // Khi người dùng nhập một tin nhắn mới, phương thức start sẽ tạo một DatagramPacket chứa tin nhắn mới và gửi
        // đến server thông qua socket.
    }
}