package com.chess.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.chess.pieces.Piece;
import com.chess.utils.MoveTree;
import com.chess.utils.Node;

import chesspresso.move.Move;

public class BoardController implements ActionListener {

  // private static JButton oldBtnEvent = null;
  // private static Color oldColor = null;
  private static BoardView view;
  private static BoardModel model;
  private MoveTree movesTree;
  private Location currentPieceLocation;
  // private List<Integer> inversionOffset = Arrays.asList(-7, -5, -3, -1, 1, 3, 5, 7);
  private Integer[] inversionOffset = {-7, -5, -3, -1, 1, 3, 5, 7};

  /**
   * New game Action Object
   */
  private Action newGameAction = new AbstractAction("New") {
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      // reset view
      view.clearGame();
      view.setupNewGame();
      // reset model
      model.clearChessBoard();
      model.initBoardModel();
      // reset current move node to root node
      movesTree.setupMoveTree();
      // reset the currentPieceLocation
      currentPieceLocation = new Location(-1, -1);
    }
  };

  public BoardController(MoveTree movesTree) {
    this.movesTree = movesTree;
  }

  public void init() {
    model = new BoardModel();
    view = new BoardView(newGameAction, this);
    // init the UI (Just for visual and user input)
    view.initializeGui();
    // init the data model (for keeping track of pieces)
    model.initBoardModel();
  }

  public JComponent getGui() {
    return view.getGui();
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    Object chessObj = event.getSource();
    if (chessObj instanceof JButton) {
      JButton jButton = (JButton) chessObj;
      Color tileBGColor = jButton.getBackground();
      boolean isAllowedTile = (tileBGColor != Color.WHITE) && (tileBGColor != Color.BLACK);
      // Make a move is its allowed
      if (BoardModel.getCurrentPiece() != null && isAllowedTile) {
        Location newLocation = Location.toLocation(jButton.getActionCommand());
        updateModelView(newLocation);

        // TODO: execute the opponent move based on current move
        // FIXME: use the current move to get the background color of the tile.
        // If the tile was red, that means we killed an opponent piece
        executeOpponentMove(currentPieceLocation, newLocation, tileBGColor.equals(Color.RED));
        return;
      }
      // TODO: remove later
      System.out.println(jButton.getActionCommand());
      // TODO: clean this up a bit. I.e, reuse the variable than just leaving for debugging
      currentPieceLocation = Location.toLocation(jButton.getActionCommand());
      highlightLegalMoves(Location.toLocation(jButton.getActionCommand()));
    }
  }

  private void updateModelView(Location newLocation) {
    // update view
    view.updateBoard(BoardModel.getCurrentPiece(), newLocation);
    // update model
    model.updateCurrentPiece(newLocation);
    // update the bitboard models
    int pieceIndex = 8 * newLocation.row + newLocation.column;
    model.updateCurrentPieceIndex(pieceIndex);
  }

  private void highlightLegalMoves(Location location) {
    // recognize the piece using the chessBoard in the model
    BoardModel.setCurrentPiece(BoardModel.chessBoard[location.row][location.column]);
    // List<Location> moves = Piece.getMoves(BoardModel.chessBoard[location.row][location.column]);

    int pieceIndex = 8 * location.row + location.column;
    BoardModel.setCurrentPieceIndex(pieceIndex);
    long movesBoard = Piece.getMoves(pieceIndex);
    view.highlightTiles(movesBoard, pieceIndex);
    // highlight current piece as well
    // moves.add(location);
    System.out.println(pieceIndex);
    // view.highlightTiles(moves);

    // Extra: highlight opponent pieces as red
  }

  private void executeOpponentMove(Location currentPieceLocation, Location newLocation,
      boolean isCapturing) {
    // TODO: branch between MoveTree and Search option
    boolean isNextMoveReady = false;
    // create only one instance of the moveTree. when the tree is not usable
    // then switch to the search method
    if (movesTree.isTreeUsable()) {
      // TODO: update the moves tree using the current move
      short opponentMove = getMove(currentPieceLocation, newLocation, isCapturing);
      Node opponentMoveNode = movesTree.getAvailableMoves().stream()
          .filter(move -> (opponentMove == move.getMove())).findAny().orElse(null);

      // apply opponent move if possible
      if (opponentMoveNode != null) {
        movesTree.makeMove(opponentMoveNode);

        // execute a move using the tree
        List<Node> moves = movesTree.getAvailableMoves().stream()
            .sorted((n1, n2) -> Integer.compare(n2.getOccurrence(), n1.getOccurrence()))
            .collect(Collectors.toList());

        // apply move with the highest occurrence if available
        if (!moves.isEmpty()) {
          movesTree.makeMove(moves.get(0));
          
          // Update the view and the model
          int fromSqi = Move.getFromSqi(moves.get(0).getMove());
          int toSqi = Move.getToSqi(moves.get(0).getMove());
          Location fromLoc = getLocationFromIndex(fromSqi);
          Location toLoc = getLocationFromIndex(toSqi);
          BoardModel.setCurrentPiece(BoardModel.chessBoard[fromLoc.row][fromLoc.column]);
          int fromLocIndex = 8 * fromLoc.row + fromLoc.column;
          BoardModel.setCurrentPieceIndex(fromLocIndex);
          updateModelView(toLoc);
          isNextMoveReady = true;
        } else {
          movesTree.setTreeUsability(false);
        }
      } else {
        movesTree.setTreeUsability(false);
      }
      // loop through to get node with most occurrences
      // make that move
    }

    if (!isNextMoveReady) {
      // use search
    }

  }

  /**
   * TODO: refactor into helper class, along with some other functions Returns a short based on
   * fromSqi, toSqi and isCapturing. The from and to indexes need to be corrected first.
   * 
   * @param currentPieceLocation
   * @param newLocation
   * @param isCapturing
   * @return
   */
  private short getMove(Location currentPieceLocation, Location newLocation, boolean isCapturing) {
    Location fromSqLoc = new Location(Math.abs(currentPieceLocation.row - BoardModel.COLUMN_LIMIT),
        currentPieceLocation.column);
    Location toSqLoc =
        new Location(Math.abs(newLocation.row - BoardModel.COLUMN_LIMIT), newLocation.column);
    int fromSqi = 8 * fromSqLoc.row + fromSqLoc.column;
    int toSqi = 8 * toSqLoc.row + toSqLoc.column;
    return Move.getRegularMove(fromSqi, toSqi, isCapturing);
  }
  
  /**
   * Get Location object from the square index values obtained from the moves tree
   * 
   * @param squareIndex
   * @return
   */
  private Location getLocationFromIndex(int squareIndex){
    int fileIndex = squareIndex & 7;
    int toSqiNormalized = 63 - squareIndex + inversionOffset[fileIndex];
    int rankIndex = toSqiNormalized >> 3;
    return new Location(rankIndex, fileIndex);
  }
}
