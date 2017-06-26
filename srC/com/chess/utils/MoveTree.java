package com.chess.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import chesspresso.game.Game;
import chesspresso.game.GameMoveModel;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;

public class MoveTree {

  private Node rootNode;
  private List<String> openingBookFiles;
  private List<Integer> movesAtDepth = new ArrayList<Integer>();
  // control number of games used
  private static final int MAX_NUM_GAMES = Integer.MAX_VALUE;
  // control game size used
  private static final int MAX_GAME_MOVES = 120;

  public MoveTree(List<String> openingBookFiles) {
    this.rootNode = new Node(null);
    this.rootNode.setId("root");
    this.openingBookFiles = openingBookFiles;
    // we can use something like a sequenceInputStream for multiple files
    init();
  }

  private void init() {
    // No moves tree is created if there are no opening book files
    if (openingBookFiles.isEmpty()) {
      return;
    }

    try {
      Vector<InputStream> inputStreams = new Vector<InputStream>();
      for (String filePath : openingBookFiles) {
        inputStreams.add(new FileInputStream(filePath));
      }
      SequenceInputStream sis = new SequenceInputStream(inputStreams.elements());
      PGNReader pgnReader = new PGNReader(sis, "Opening Books");

      // create a moves tree based on these games
      buildMoveTree(pgnReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Build a moves tree using the games obtained from the pgn files
   * 
   * @param pgnReader
   */
  private void buildMoveTree(PGNReader pgnReader) {
    try {
      Game game = pgnReader.parseGame();
      int maxMovesInAGame = 0;
      int counter = 0;

      while ((game != null) && (counter <= MAX_NUM_GAMES)) {
        // prevent unnecessarily long games from being used
        if (game.getNumOfMoves() <= MAX_GAME_MOVES) {
          addGameMoves(getRootNode(), game);
          counter++;

          // TODO: remove maxMovesInAGame after testing
          maxMovesInAGame = Math.max(maxMovesInAGame, game.getNumOfMoves());
        }

        // some games cannot be parsed; hence they are ignored
        try {
          game = pgnReader.parseGame();
        } catch (Exception e) {
          System.err.println("Game ignored: " + e.getMessage());
        }
      }

      // FIXME: temp solution to account for the root node.
      // it was added here so there wouldnt be any indexing issues in addGameMoves
      // we might not even need this
      movesAtDepth.add(0, 1);

      // printTreeDepthAverages(getRootNode(), "-", 0);

      System.out.println("Max moves in a game: " + maxMovesInAGame);
      System.out.println("Depth of tree: " + movesAtDepth.size());

    } catch (PGNSyntaxError e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printTreeDepthAverages(Node node, String appender, int currentDepth) {

    if (currentDepth >= 10) {
      return;
    }

    String output = String.format("%s%s : %f", appender, node.getId(),
        ((double) node.getOccurrence()) / movesAtDepth.get(currentDepth));
    System.out.println(output);
    for (Node each : node.getChildren()) {
      printTreeDepthAverages(each, appender + "-", currentDepth + 1);
    }
  }

  public Node getRootNode() {
    return rootNode;
  }

  public void setRootNode(Node rootNode) {
    this.rootNode = rootNode;
  }

  /**
   * Add all the moves in the provided game. Starts adding from the root of the moves tree.
   * 
   * @param movesRoot
   * @param game
   */
  private void addGameMoves(Node movesRoot, Game game) {
    Node currentNode = movesRoot;
    GameMoveModel moveModel = game.getModel().getMoveModel();
    for (int i = 0; i < game.getNumOfMoves(); i++) {
      if (!moveModel.hasNextMove(i)) {
        continue;
      }
      short move = moveModel.getMove(i);
      currentNode = currentNode.addChild(move);
      // update the number of moves at each depth
      if (movesAtDepth.size() <= i) {
        movesAtDepth.add(1);
      } else {
        int moveCount = movesAtDepth.get(i);
        movesAtDepth.set(i, moveCount + 1);
      }
    }
  }
}
