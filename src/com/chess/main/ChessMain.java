package com.chess.main;

import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.chess.ui.BoardController;
import com.chess.ui.BoardView;

public class ChessMain {

  public static void main(String[] args) {

    long num = 0x1;
    long num2 = 0x9;
    long num3 = 0xA;
    String value = "8000000000000000‬";
    long long_FF = new BigInteger("ff", 16).longValue();
    long num4 = long_FF ^ (1 << 2); // | long_FF << 48;

    long num5 = (1L << 63) | (1L << 3);
    System.out.println(Long.toBinaryString(num4));
    System.out.println(Long.toBinaryString(num5));
    Runnable run = new Runnable() {

      @Override
      public void run() {
        // BoardView cg = new BoardView();
        BoardController controller = new BoardController();
        controller.init();

        JFrame f = new JFrame("ChessChamp");
        f.add(controller.getGui());
        // Ensures JVM closes after frame(s) closed and
        // all non-daemon threads are finished
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // See http://stackoverflow.com/a/7143398/418556 for demo.
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
      }
    };
    // Swing GUIs should be created and updated on the EDT
    // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
    SwingUtilities.invokeLater(run);
  }
}
