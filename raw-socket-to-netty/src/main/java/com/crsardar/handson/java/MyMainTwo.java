package com.crsardar.handson.java;


import com.silver.hdp5signals.mdcodegen.signals.HDP5Signals.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyMainTwo
{
    static int LENGTH_SIZE = 1;

    public static void main(String[] args)
    {

        System.out.println();
        try
        {
            Socket socket = new Socket("10.74.34.158", 9191);

            System.out.println("Connected");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream
                    = new DataOutputStream(socket.getOutputStream());

            System.out.println("---- Waiting for reading ----");

            readProtobuf(dataInputStream);

            System.out.println("--------------- Read ---------------");

            Thread.sleep(1000);

            HDPMsg hdpMsg = getMessage();
            System.out.println("--------------- Writing ---------------");
            System.out.println(hdpMsg.toString());

            writeProtobuf(dataOutputStream, hdpMsg.toByteArray());

            System.out.println("---- Waiting Done ----");
        }
        catch (IOException | InterruptedException i)
        {
            System.out.println(i);
        }
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
                    System.out.println("Read bytes = [");
                    System.out.print(headerBuffer[0]);
                    for (byte bite: payload)
                    {
                        System.out.print(", " + bite);
                    }
                    System.out.println("\n]");

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
        value += (b[0] & 0x0000007F);
        System.out.println("--------> length = " + value);
        return value;
    }

    public static HDPMsg getMessage()
    {
        long timeNow = System.currentTimeMillis();

        String deviceId = "#1";
        String deviceName = "AssignedByInfuserPanel";

        MsgHeaderSig.Builder msgHeaderSigBuilder = MsgHeaderSig.newBuilder();
        msgHeaderSigBuilder.setTimestamp(timeNow);
        msgHeaderSigBuilder.setSourceId(MessageSourceType.MSG_SOURCE_UIC);

        RegistrationRes.Builder registrationResBuilder = RegistrationRes.newBuilder();
        registrationResBuilder.setMessageHeader(msgHeaderSigBuilder);
        registrationResBuilder.setResponseCode(RegistrationResponseCodeType.ACCEPTED);
        registrationResBuilder.setSecuritySessionKey("ThisIsDummySecuritySessionKey");

        RegistrationSig.Builder registrationSigBuilder = RegistrationSig.newBuilder();
        registrationSigBuilder.setDeviceId(deviceId);
        registrationSigBuilder.setResponse(registrationResBuilder);

        HDPMsg.Builder hdpMsgBuilder = HDPMsg.newBuilder();
        hdpMsgBuilder.setMessageHeader(msgHeaderSigBuilder);
        hdpMsgBuilder.setDeviceId(deviceId);
        hdpMsgBuilder.setInfuserName(deviceName);
        hdpMsgBuilder.setDeviceClass(DeviceClassType.PLUM_SILVER_LVP_DUAL);
        hdpMsgBuilder.setEventType(HDPEventType.REGISTRATION);
        hdpMsgBuilder.setRegistration(registrationSigBuilder);

        HDPMsg hdpMsg = hdpMsgBuilder.build();

        return hdpMsg;
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

    static byte[] appendSize(byte[] data)
    {
        int dataLength = data.length;
        byte[] dataOut = new byte[dataLength + LENGTH_SIZE];
        dataOut[0] = (byte) (dataLength);
        System.arraycopy(data, 0, dataOut, LENGTH_SIZE, dataLength);
        return dataOut;
    }
}
