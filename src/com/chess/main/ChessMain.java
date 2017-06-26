package com.chess.main;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.chess.ui.BoardController;
import com.chess.utils.MoveTree;
import com.chess.utils.Node;

import chesspresso.game.Game;
import chesspresso.game.GameMoveModel;
import chesspresso.move.Move;
import chesspresso.pgn.PGNReader;

public class ChessMain {

  public static void main(String[] args) {

    // long num = 0x1;
    // long num2 = 0x9;
    // long num3 = 0xA;
    // String value = "8000000000000000‬";
    // long long_FF = new BigInteger("ff", 16).longValue();
    // long num4 = long_FF ^ (1 << 2); // | long_FF << 48;
    //
    // long num5 = (1L << 63) | (1L << 3);
    // System.out.println(Long.toBinaryString(num4));
    // System.out.println(Long.toBinaryString(num5));

    List<Integer> colDiff = Arrays.asList(-7, -5, -3, -1, 1, 3, 5, 7);
//    List<Integer> colDiff = Arrays.asList(5, 54, 75, 7, 6, 2, 0, 89, 445, 1, 0, 57);
//    
//    List<Integer> moves = colDiff.stream()
//        .sorted((n1, n2) -> Integer.compare(n2, n1))
//        .collect(Collectors.toList());
    
//    System.out.println(moves.toArray());
    
    File f;
    try {
//      f = new File("src/resources/book_2014.pgn");
//      FileInputStream fis = new FileInputStream(f);
//      PGNReader pgnReader = new PGNReader(fis, "src/resources/book_2014.pgn");
//      // Hack: we know there are only 120 games in the opening book
//      Node movesRoot = new Node(null);
//      movesRoot.setId("root");
//      
//      Game game = pgnReader.parseGame();
//
//      List<Integer> movesAtDepth = new ArrayList<Integer>();
//      int maxMovesInAGame = 0;
//
//      int counter = 20;
//      while (game != null && counter > 0) {
//        addGameMoves(movesRoot, game, movesAtDepth);
//        // TODO: remove maxMovesInAGame after testing
//        maxMovesInAGame = Math.max(maxMovesInAGame, game.getNumOfMoves());
//
//        game = pgnReader.parseGame();
//        counter--;
//      }
////      printTree(movesRoot, "-");
//      // FIXME: temp solution to account for the root node
//      movesAtDepth.add(0, 1);
//      printTreeDepthAverages(movesRoot, "-", movesAtDepth, 0);
//
//      System.out.println("Max moves in a game: " + maxMovesInAGame);
//      System.out.println("Depth of tree: " + movesAtDepth.size());
      
      
      
      
    } catch (Exception e) {
      System.out.print("Error: \n");
      e.printStackTrace();
    }

    Runnable run = new Runnable() {

      @Override
      public void run() {
        
        List<String> files = new ArrayList<String>();
        files.add("src/resources/book_2014.pgn");
//        files.add("src/resources/book_2015.pgn");
//        files.add("src/resources/book_2016.pgn");
        MoveTree movesTree = new MoveTree(files);
        
        // BoardView cg = new BoardView();
        BoardController controller = new BoardController(movesTree);
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

  /**
   * FIXME: remove the movesAtDepth field later, since its just for testing
   * 
   * @param movesRoot
   * @param game
   * @param movesAtDepth
   */
  private static void addGameMoves(Node movesRoot, Game game, List<Integer> movesAtDepth) {
    Node currentNode = movesRoot;
    GameMoveModel moveModel = game.getModel().getMoveModel();
    for (int i = 0; i < game.getNumOfMoves(); i++) {
      if (!moveModel.hasNextMove(i)) {
        continue;
      }
      short move = moveModel.getMove(i);
      currentNode = currentNode.addChild(move);
      if (movesAtDepth.size() <= i) {
        movesAtDepth.add(1);
      } else {
        int moveCount = movesAtDepth.get(i);
        movesAtDepth.set(i, moveCount + 1);
      }
    }
  }

  private static void printTree(Node node, String appender) {
    System.out.println(appender + node.getId());
    for (Node each : node.getChildren()) {
      printTree(each, appender + "-");
    }
  }

  private static void printTreeDepthAverages(Node node, String appender, List<Integer> movesAtDepth,
      int currentDepth) {
    String output = String.format("%s%s : %f", appender, node.getId(),
        ((double)node.getOccurrence()) / movesAtDepth.get(currentDepth));
    System.out.println(output);
    for (Node each : node.getChildren()) {
      printTreeDepthAverages(each, appender + "-", movesAtDepth, currentDepth+1);
    }
  }
}
