package CJV.v2ch04.socket;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class SocketTest {
    public static void main(String[] args) throws IOException
    {
        try (var s = new Socket("103.235.46.39", 80 );
                var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8)){
            while (in.hasNextLine()){
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
