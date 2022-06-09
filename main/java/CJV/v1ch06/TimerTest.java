package CJV.v1ch06;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;

public class TimerTest {
    public static void main(String[] args)
    {
        var listener = new TimePrinter();

        var timer = new Timer(1000, listener);
        timer.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TimePrinter implements ActionListener{

    public void actionPerformed(ActionEvent event) {
        System.out.println("At the tone, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}
