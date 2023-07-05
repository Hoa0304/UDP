package com.example.chattutorial.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {
    private static byte[] incoming = new byte[256];
    // Mảng byte để lưu các tin nhắn đến

    private static final int PORT = 8000;
    private static DatagramSocket socket;
    // Tạo 1 ổ cắm datagram
    //  đối tượng Dùng để gửi và nhận các gói tin nhắn
    //qua dữ liệu mạng

    static {
        try {
            socket = new DatagramSocket(PORT);
        }catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
//    sử dụng khối static để khởi tạo đối tượng socket
//            Nếu có lỗi xra trg quá trình khởi tạo, 1 ngoại lệ se dc nén ra
    //------------------------------=>------------------------//
//Biến PORT là số cổng được sử dụng cho server. Biến socket là đối tượng DatagramSocket
//    được sử dụng để gửi và nhận các gói tin qua dữ liệu mạng.
    private static ArrayList<Integer> users = new ArrayList<>();
    //ArrayList users sẽ lưu trữ các cổng của các client đã kết nối với server.
    private static final InetAddress address;
    // Biến address là địa chỉ IP của server.
    static {
        try {
            address = InetAddress.getByName("localhost");
        }catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

//        server sẽ lắng nghe các gói tin nhắn đến từ các client thông qua một vòng lặp vô hạn
//        Khi một gói tin nhắn đến, server sẽ kiểm tra xem nó có chứa chuỗi "init;" hay không.
//        Nếu có, server sẽ thêm cổng của client gửi tin nhắn vào danh sách users. Nếu không,
//        server sẽ chuyển tiếp tin nhắn đến tất cả các client khác (trừ client gửi tin nhắn) bằng
//        cách gửi các gói tin nhắn tới các cổng của các client trên danh sách users.
        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length); // prepare packet
            //Đối tượng DatagramPacket được tạo ra để chứa các gói tin nhắn đến
            try {
                socket.receive(packet); // receive packet
                //Phương thức socket.receive() được sử dụng để nhận các gói tin nhắn đến từ client.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String message = new String(packet.getData(), 0, packet.getLength()); // create a string
            //Đối tượng packet sẽ được chuyển đổi thành một chuỗi message để xử lý.
            System.out.println("Server received: " + message);


            if (message.contains("init")) {
                users.add(packet.getPort());
                //Nếu tin nhắn chứa chuỗi "init;", cổng của client gửi tin nhắn sẽ được thêm vào danh sách users.
            }
            // forward
            else {
                int userPort = packet.getPort();  // get port from the packet
                byte[] byteMessage = message.getBytes(); // convert the string to bytes

                // forward to all other users (except the one who sent the message)
                for (int forward_port : users) {
                    if (forward_port != userPort) {
                        DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, address, forward_port);
                        try {
                            socket.send(forward);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                //Nếu tin nhắn không chứa chuỗi "init;", server sẽ chuyển tiếp tin nhắn đến tất cả các client khác
                // (trừ client gửi tin nhắn) bằng cách gửi các gói tin nhắn tới các cổng của các client trên danh sách users.
            }
        }
    }
}
