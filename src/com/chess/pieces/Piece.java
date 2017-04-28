package com.chess.pieces;

import java.math.BigInteger;
import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Piece {

  public static final long NOT_FILE_A = new BigInteger("fefefefefefefefe", 16).longValue();
  public static final long NOT_FILE_H = new BigInteger("7f7f7f7f7f7f7f7f", 16).longValue();
  public int id;
  public boolean isWhite;
  public Location location;

  public Piece(int id, boolean isWhite, Location location) {
    this.id = id;
    this.isWhite = isWhite;
    this.location = location;
  }

  // TODO: locations will be inferred from the index
  public static long getMoves(int pieceIndex) {
    if (pieceIndex < 0 || pieceIndex > 63) {
      return 0L;
    }
    switch (BoardModel.getBitBoardIndex(pieceIndex)) {
      case BoardModel.ROOK:
        return Rook.getMoves(pieceIndex);
      case BoardModel.KNIGHT:
        return Knight.getMoves(pieceIndex);
      case BoardModel.BISHOP:
        return Bishop.getMoves(pieceIndex);
      case BoardModel.KING:
        return King.getMoves(pieceIndex);
      case BoardModel.QUEEN:
        return Queen.getMoves(pieceIndex);
      case BoardModel.PAWN:
        return Pawn.getMoves(pieceIndex);
    }
    // should never get here
    return 0L;
  }

//  public Piece getPieceOnBoard(Location location) {
//    if (isOnBoard(location)) {
//      return BoardModel.chessBoard[location.row][location.column];
//    }
//    return null;
//  }

  public boolean isOnBoard(Location location) {
    return (location.row >= 0 && location.row <= BoardModel.ROW_LIMIT)
        && (location.column >= 0 && location.column <= BoardModel.COLUMN_LIMIT);
  }

  public int changeRow(int dRow) {
    return this.isWhite ? this.location.row - dRow : this.location.row + dRow;
  }

//  public boolean isLegalMove(Location newLocation) {
//    if (newLocation == null) {
//      return false;
//    }
//    if (isOnBoard(newLocation)) {
//      Piece pieceOnBoard = getPieceOnBoard(newLocation);
//      // "this" refers to the instantiated object, not necessarily this particular class
//      return (pieceOnBoard == null) || this.isWhite ^ pieceOnBoard.isWhite;
//    }
//    return false;
//  }

//  public boolean isOpponentPiece(Location newLocation) {
//    Piece pieceOnBoard = getPieceOnBoard(newLocation);
//    return (pieceOnBoard != null) ? this.isWhite ^ pieceOnBoard.isWhite : false;
//  }

//  public boolean checkAndAdd(List<Location> moves, int dRow, int dCol) {
//    Location newLocation = new Location(changeRow(dRow), location.column + dCol);
//    if (!isLegalMove(newLocation)) {
//      return false;
//    }
//    moves.add(newLocation);
//    if (isOpponentPiece(newLocation)) {
//      return false;
//    }
//    return true;
//  }

  public static boolean isPieceWhite(int pieceIndex) {
    long bitBoardIndex = 1L << pieceIndex;
    long whiteBitBoard = BoardModel.bitBoards[BoardModel.WHT];
    return (whiteBitBoard & bitBoardIndex) != 0;
  }
}
