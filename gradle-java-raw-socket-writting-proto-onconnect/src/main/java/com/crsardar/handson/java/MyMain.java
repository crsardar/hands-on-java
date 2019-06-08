package com.crsardar.handson.java;

import com.silver.hdp5signals.mdcodegen.signals.HDP5Signals.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyMain
{

    static int LENGTH_SIZE = 4;

    public static void main(String... arg)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8080, 1,
                    InetAddress.getByName("0.0.0.0"));
//                    InetAddress.getByName("127.0.0.1"));

            while (true)
            {
                System.out.println(MyMain.class.getSimpleName() + " : Waiting for Client Socket");
                Socket socket = serverSocket.accept();

                System.out.println(
                        MyMain.class.getSimpleName() + " : Accepted a connection request, Socket = "
                                + socket.hashCode() + " :  address = " + socket.getInetAddress());

                new Thread(() -> {

                    int count = 0;
                    try
                    {
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        while (++count <= 3)
                        {
                            HDPMsg.Builder hdp5 = HDPMsg.newBuilder();
                            MsgHeaderSig.Builder hde = MsgHeaderSig.newBuilder();
                            hde.setSeqNum(10);
                            hde.setTimestamp(10);
                            hde.setSourceId(MessageSourceType.MSG_SOURCE_UIC);
                            hdp5.setMessageHeader(hde);
                            hdp5.setDeviceId("DviCe@321");
                            hdp5.setInfuserName("test");
                            hdp5.setDeviceClass(DeviceClassType.PCA);
                            hdp5.setEventType(HDPEventType.ALARM_UPDATE);

                            //write protobuf from UIC
                            writeProtobuf(out, hdp5.build().toByteArray());

                            System.out.println(
                                    MyMain.class.getSimpleName() + " : Written -----------");

                            try
                            {
                                Thread.sleep(5000);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }

                        }

                        System.out.println(
                                MyMain.class.getSimpleName() + " Socket = " + socket.hashCode()
                                        + " Is Connected = " + socket.isConnected());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeProtobuf(DataOutputStream out, byte[] data)
    {
        try
        {
            out.write(appendSize(data));
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /*
     * Add payload length
      BEFORE       (12 bytes)         AFTER  (16 bytes)
     +--------+---------------+      +--------+----------------+
     |  Actual Content        |----->| Length | Actual Content |
     |  "HELLO, WORLD"        |      | 0x000C | "HELLO, WORLD" |
     +--------+---------------+      +--------+----------------+
     */
    static byte[] appendSize(byte[] data)
    {
        int dataLength = data.length;
        byte[] dataOut = new byte[dataLength + LENGTH_SIZE];
        dataOut[0] = (byte) (dataLength >> 24);
        dataOut[1] = (byte) (dataLength >> 16);
        dataOut[2] = (byte) (dataLength >> 8);
        dataOut[3] = (byte) (dataLength);
        System.arraycopy(data, 0, dataOut, 4, dataLength);
        return dataOut;
    }

    static void readProtobuf(DataInputStream input)
    {
        try
        {

            byte[] headerBuffer = new byte[LENGTH_SIZE];

            if (input.read(headerBuffer, 0, LENGTH_SIZE) == LENGTH_SIZE)
            {
                int totalBytesRead = 0;
                int streamBytesRead = 0;
                int payloadSize = byteArrayToInt(headerBuffer);
                byte[] payload = new byte[payloadSize];
                int numBytesLeft = payloadSize;

                while (totalBytesRead < payloadSize)
                {
                    streamBytesRead = input.read(payload, totalBytesRead, numBytesLeft);
                    // Detect end of stream and break the loop.
                    if (streamBytesRead == -1)
                    {
                        break;
                    }
                    totalBytesRead += streamBytesRead;
                    numBytesLeft -= streamBytesRead;
                    System.out.println(
                            "Number of bytes received:" + totalBytesRead + " Payloadsize:"
                                    + payloadSize);
                }
                if (totalBytesRead == payloadSize)
                {
                    System.out.println("Message from CE" + HDPMsg.parseFrom(payload).toString());
                }
                else
                {
                    System.out.println(
                            "Expected bytes:" + payloadSize + " Received bytes:" + totalBytesRead);
                }
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

    }

    public static int byteArrayToInt(byte[] b)
    {
        int value = 0;
        for (int i = 0; i < 4; i++)
        {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

}
