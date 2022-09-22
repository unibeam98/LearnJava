package CJV.v2ch04.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketTest {
  public static void main(String[] args) {
    try(var s = new Socket("time-a.nist.gov", 13);
        var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8)){
        while (in.hasNextLine()){
            String line = in.nextLine();
            System.out.printf(line);
        }
    } catch (UnknownHostException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }
}
