package com.company;

import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.net.InetAddress;


/**
 * https://www.codejava.net/java-se/networking/java-udp-client-server-program-example
 */
public class QuoteClient {


    public static void main(String[] args) {

        String[] input = new String[3];
        String stringsConcatenated = "";
        System.out.println("You will now be prompted for 3 strings, one after another...");
        try (Scanner scanner = new Scanner(System.in)) {


            for (int i = 0; i < 3; i++) {
                System.out.println("Enter a string: ");
                input[i] = scanner.nextLine();
                stringsConcatenated += input[i];
            }


        } catch (Exception e) {
            System.out.println("Exception thrown: " + e);
        }

        int n = stringsConcatenated.length();
         int port = 8014;


        System.out.println();
        System.out.println("Contacting server....");

        try {

            InetAddress address =  InetAddress.getLocalHost();
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket request;
            for (int i = 0; i < 3; i++) {

                byte[] bufferSend = input[i].getBytes();
                request = new DatagramPacket(bufferSend, bufferSend.length, address, port);
                socket.send(request);


            }

            System.out.println("Waiting for server response....");
            Thread.sleep(1000);
            String[] results = new String[3];
            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            String hashesRemoved = "";

            for (int i = 0; i < 2; i++) {

                socket.receive(response);

                results[i] = new String(buffer, 0, response.getLength());

                if (i == 0)
                    hashesRemoved = results[i].replaceAll("#", "");

            }

            System.out.println("The results from the server are: ");
            System.out.println(hashesRemoved);
            System.out.println(results[1]);
            System.out.println();

            System.out.println("You will now be shown the answers, we determined, as to whether the concatenated results, \n" +
                    "received from the server, as well as the sum of their lengths, match the input strings you have provided:");
            if (hashesRemoved.equals(stringsConcatenated) && Integer.parseInt(results[1]) == n) {
                System.out.println("YES");
                System.out.println("YES");
            } else {
                if (!(hashesRemoved.equals(stringsConcatenated) && Integer.parseInt(results[1]) == n)) {
                    System.out.println("NO");
                    System.out.println("NO");
                } else {

                    if (hashesRemoved == stringsConcatenated) {
                        System.out.println("YES");
                        System.out.println("NO");
                    } else {
                        System.out.println("NO");
                        System.out.println("YES");
                    }
                }
            }
            System.out.println();


        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}