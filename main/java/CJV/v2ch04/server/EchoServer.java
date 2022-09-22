package CJV.v2ch04.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoServer {
  public static void main(String[] args) throws IOException {
    try (var s = new ServerSocket(8189)) {
      try (Socket incoming = s.accept()) {
        InputStream inputStream = incoming.getInputStream();
        OutputStream outputStream = incoming.getOutputStream();
        try (var in = new Scanner(inputStream, StandardCharsets.UTF_8)) {
          var out =
              new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
          out.println("Hello! Enter BYE to exit.");
          var done = false;
          while (!done && in.hasNextLine()) {
            String line = in.nextLine();
            out.println("Echo: " + line);
            if (line.trim().equals("BYE")) done = true;
          }
        }
      }
    }
  }
}
