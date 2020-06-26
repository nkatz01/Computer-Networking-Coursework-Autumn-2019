package com.company;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) {
      try {
          InetAddress ip = InetAddress.getLocalHost();
          String hostname = ip.getHostName();
          InetAddress address = InetAddress.getByName(hostname);
          System.out.println(ip);
          System.out.println(address);

      }catch (UnknownHostException e) {

          e.printStackTrace();
      }

    }
}
