package com.chess.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.chess.pieces.Piece;

public class BoardController implements ActionListener {

//  private static JButton oldBtnEvent = null;
//  private static Color oldColor = null;
  private static BoardView view;
  private static BoardModel model;
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
    }
  };

  public BoardController() {}

  public void init() {
    model = new BoardModel();
    view = new BoardView(newGameAction);
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
      if (BoardModel.getCurrentPiece() != null && isAllowedTile) {
        Location newLocation = Location.toLocation(jButton.getActionCommand());
        // update view
        view.updateBoard(BoardModel.getCurrentPiece(), newLocation);
        // update model
        model.updateCurrentPiece(newLocation);
        // update the bitboard models
        int pieceIndex = 8*newLocation.row + newLocation.column;
        model.updateCurrentPieceIndex(pieceIndex);
        return;
      }
      // TODO: remove later
      System.out.println(jButton.getActionCommand());
      highlightLegalMoves(Location.toLocation(jButton.getActionCommand()));
    }
  }

  private void highlightLegalMoves(Location location) {
    // recognize the piece using the chessBoard in the model
    BoardModel.setCurrentPiece(BoardModel.chessBoard[location.row][location.column]);
//    List<Location> moves = Piece.getMoves(BoardModel.chessBoard[location.row][location.column]);
    
    int pieceIndex = 8*location.row + location.column;
    BoardModel.setCurrentPieceIndex(pieceIndex);
    long movesBoard = Piece.getMoves(pieceIndex);
    view.highlightTiles(movesBoard, pieceIndex);
    // highlight current piece as well
//    moves.add(location);
    System.out.println(pieceIndex);
//    view.highlightTiles(moves);

    // Extra: highlight opponent pieces as red
  }
}
