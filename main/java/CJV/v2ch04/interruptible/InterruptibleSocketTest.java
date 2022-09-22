package CJV.v2ch04.interruptible;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InterruptibleSocketTest {

  public static void main(String[] args) {
    EventQueue.invokeLater(() ->
            {
              var frame = new InterruptibleSocketFrame();
              frame.setTitle("InterruptibleSockerTest");
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
            });
  }
}

class InterruptibleSocketFrame extends JFrame
{
  private Scanner in;
  private JButton interruptibleButton;
  private JButton blockingButton;
  private JButton cancelButton;
  private JTextArea messages;
  private TestServer server;
  private Thread connectThread;
  public InterruptibleSocketFrame()
  {
    var northPanel = new JPanel();
    add(northPanel, BorderLayout.NORTH);

    final int TEXT_ROWS = 20;
    final int TEXT_COLUMNS = 60;

    messages = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
    add(new JScrollPane(messages));

    interruptibleButton = new JButton("Interruptible");
    blockingButton = new JButton("Blocking");

    northPanel.add(interruptibleButton);
    northPanel.add(blockingButton);

    interruptibleButton.addActionListener(event ->
    {
      interruptibleButton.setEnabled(false);
      blockingButton.setEnabled(false);
      cancelButton.setEnabled(true);
      connectThread = new Thread(() ->
      {
        try{
          connectInterruptibly();
        }
        catch (IOException e)
        {
          messages.append("\nInterruptibleSocketTest.connectInterruptibly: " + e);
        }
      });
      connectThread.start();
    });

    blockingButton.addActionListener(event ->
    {
      interruptibleButton.setEnabled(true);
      blockingButton.setEnabled(false);
      cancelButton.setEnabled(true);
      connectThread = new Thread(() ->
      {
        try{
          connectBlocking();
        }catch (IOException e)
        {
          messages.append("\nInterruptibleSocketTest.connectBlocking: " + e);
        }
      });
      connectThread.start();
    });

    cancelButton = new JButton("Cancel" );
    cancelButton.setEnabled(false);
    northPanel.add(cancelButton);
    cancelButton.addActionListener(event ->
    {
      connectThread.interrupt();
      cancelButton.setEnabled(false);
    });
    server = new TestServer();
    new Thread(server).start();
    pack();
  }

  public void connectInterruptibly() throws IOException
  {
    messages.append("Interruptible: \n");
    try(SocketChannel channel1 = SocketChannel.open(new InetSocketAddress("localhost", 8189)))
    {
      in = new Scanner(channel1, StandardCharsets.UTF_8);
      while(!Thread.currentThread().isInterrupted())
      {
        messages.append("Reading");
        if(in.hasNextLine())
        {
          String line = in.nextLine();
          messages.append(line);
          messages.append("\n");
        }
      }
    }
    finally{
      EventQueue.invokeLater(() ->
      {
        messages.append("Channel closed\n");
        interruptibleButton.setEnabled(true);
        blockingButton.setEnabled(true);
      });
    }
  }


  public void connectBlocking() throws IOException
  {
    messages.append("Blocking\n");
    try(var sock = new Socket("localhost", 8189)){
      in = new Scanner(sock.getInputStream(), StandardCharsets.UTF_8);
      while(!Thread.currentThread().isInterrupted())
      {
        messages.append("Reading ");
        if(in.hasNextLine())
        {
          String line = in.nextLine();
          messages.append(line);
          messages.append("\n");
        }
      }
    }
    finally{
      EventQueue.invokeLater(() ->
      {
        messages.append("Socket closed\n");
        interruptibleButton.setEnabled(true);
        blockingButton.setEnabled(true);
      });
    }
  }

  class TestServer implements Runnable
  {
    @Override
    public void run() {
      try (var s = new ServerSocket(8189))
      {
        while (true)
        {
          Socket incoming = s.accept();
          Runnable r = new TestServerHandler(incoming);
          new Thread(r).start();
        }
      }
      catch (IOException e)
      {
        messages.append("\nTestServer.run: " + e);
      }
    }
  }

  class TestServerHandler implements Runnable
  {
    private Socket incoming;
    private int counter;

    public TestServerHandler(Socket i)
    {
      incoming = i;
    }

    @Override
    public void run() {
      try{
        try{
          OutputStream outputStream = incoming.getOutputStream();
          var out = new PrintWriter(
                  new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
                  true
          );
          while (counter < 100)
          {
            counter++;
            if(counter <= 10)
              out.println(counter);
            Thread.sleep(100);
          }
        }
        finally{
          incoming.close();
          messages.append("Closing server\n");
        }
      }
      catch (Exception e)
      {
        messages.append("\nTestServerHandler.run " + e);
      }
    }
  }
}


