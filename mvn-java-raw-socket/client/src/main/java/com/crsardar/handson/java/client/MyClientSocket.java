package com.crsardar.handson.java.client;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {

    public static void main(String... arg){

        try {

            Scanner scanner = new Scanner(System.in);

            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8080);

            ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());

            while (true){

                System.out.println(MyClientSocket.class.getSimpleName() + " : Please type a message & press Enter :\n");

                String message = scanner.nextLine();
                objectOutput.writeObject(message);

                System.out.println(MyClientSocket.class.getSimpleName() + " : Message" + message + " Sent");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
