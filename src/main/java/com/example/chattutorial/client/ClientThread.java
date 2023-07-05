package com.example.chattutorial.client;



import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientThread extends Thread {

    private DatagramSocket socket;
    private byte[] incoming = new byte[256];

    private TextArea textArea;

    public ClientThread(DatagramSocket socket, TextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }
//    Đối tượng DatagramSocket được sử dụng để nhận các gói tin qua dữ liệu mạng.
//    Biến incoming là một mảng byte để lưu các dữ liệu nhận được từ server.
//    TextArea textArea là một vùng hiển thị tin nhắn được nhận từ server.
    @Override
    public void run() {
        System.out.println("starting thread");
        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData(), 0, packet.getLength()) + "\n";
            String current = textArea.getText();
            textArea.setText(current + message);
        }
        //Phương thức run của ClientThread khởi động một vòng lặp vô hạn để lắng nghe các tin nhắn từ server.
        // Đối tượng DatagramPacket được khởi tạo để nhận các gói tin từ server thông qua socket.
        // Sau khi nhận được gói tin, phương thức run sẽ chuyển đổi gói tin thành một chuỗi tin nhắn và
        // hiển thị nó lên textArea.
    }
}