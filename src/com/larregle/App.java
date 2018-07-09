package com.larregle;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setDefaultLookAndFeelDecorated(true);
            f.setResizable(false);
            MandelbrotExplorer me = new MandelbrotExplorer();
            f.add(me, BorderLayout.CENTER);
            f.pack();
            f.setVisible(true);
        });
    }
}
