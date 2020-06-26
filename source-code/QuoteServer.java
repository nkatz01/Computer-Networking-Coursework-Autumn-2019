package com.company;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * https://www.codejava.net/java-se/networking/java-udp-client-server-program-example
 */
public class QuoteServer {
    private DatagramSocket socket;


    public QuoteServer() throws SocketException {
        socket = new DatagramSocket(8014);

    }

    public static void main(String[] args) {


        try {
            QuoteServer server = new QuoteServer();
            System.out.println("Server running...");
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {

            System.out.println("Server Listening...");
            String stringsConcatenated = "";
            byte[] buffer = new byte[512];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);

            for (int i = 0; i < 3; i++) {

                socket.receive(request);
                stringsConcatenated += new String(buffer, 0, request.getLength()) + "#";
                System.out.println("Server receiving request " + (i +1));
            }
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            System.out.println("Server serving results...");
            System.out.println();
            byte[] bufferSend = stringsConcatenated.substring(0, stringsConcatenated.length() - 1).getBytes();
            DatagramPacket response = new DatagramPacket(bufferSend, bufferSend.length, clientAddress, clientPort);
            socket.send(response);

            bufferSend = Integer.toString(stringsConcatenated.length() - 3).getBytes();
            response = new DatagramPacket(bufferSend, bufferSend.length, clientAddress, clientPort);
            socket.send(response);

        }
    }


}