package com.crsardar.handson.java;

import com.silver.hdp5signals.mdcodegen.signals.HDP5Signals.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyMain
{

    static int LENGTH_SIZE = 4;

    public static void main(String[] args)
    {
        Socket socket = null;
        DataInputStream input = null;
        DataOutputStream out = null;

        try
        {
            socket = new Socket("10.74.34.165", 9191);
            System.out.println("Connected");
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            for (; ; )
            {
                System.out.println("---- Waiting for reading");

                readProtobuf(input);


                System.out.println("--------------- Read ---------------");

                //hdp5 message
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
                //writeProtobuf(out, hdp5.build().toByteArray());

                //read protobuf from CE

            }

        }

        catch (IOException i)
        {
            System.out.println(i);
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
        System.out.println("------");
        for (byte bites : b){
            System.out.print(bites + ", ");
        }
        System.out.println("\n------");

        int value = 0;
        for (int i = 0; i < 4; i++)
        {
            int shift = (4 - 1 - i) * 8;

            value += (b[i] & 0x000000FF) << shift;

            System.out.println(" i = " + i + " : b[i] = " + b[i] + " : shift = " + shift + " : value = " + ((b[i] & 0x000000FF) << shift));

        }

        System.out.println("----------> value = " + value);

        return value;
    }

}
