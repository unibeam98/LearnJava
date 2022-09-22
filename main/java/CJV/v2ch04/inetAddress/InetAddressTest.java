package CJV.v2ch04.inetAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
  public static void main(String[] args) throws UnknownHostException {
    if (args.length > 0) {
      String host = args[0];
      InetAddress addresses = InetAddress.getByName(host);
      System.out.println(addresses);
    } else {
      InetAddress localHostAddress = InetAddress.getLocalHost();
      System.out.println(localHostAddress);
    }
  }
}
