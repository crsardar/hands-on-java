package com.crsardar.handson.java.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

    public static void main(String... arg) {

        try {

            ServerSocket serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));

            while (true) {

                System.out.println(MyServerSocket.class.getSimpleName() + " : Waiting for Client Socket");
                Socket socket = serverSocket.accept();

                System.out.println(MyServerSocket.class.getSimpleName()
                        + " : Accepted a connection request, Socket = " + socket.hashCode());

                new Thread(() -> {

                    try {

                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                        while (true) {

                            try {

                                Object object = objectInputStream.readObject();
                                System.out.println(MyServerSocket.class.getSimpleName()
                                        + " : InputStream = " + objectInputStream + " : Read = " + object.toString());

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
