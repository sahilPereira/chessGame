package com.chess.pieces;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Piece {

  public static final long NOT_FILE_A = new BigInteger("fefefefefefefefe", 16).longValue();
  public static final long NOT_FILE_H = new BigInteger("7f7f7f7f7f7f7f7f", 16).longValue();
  public static final long NORTH_MASK = new BigInteger("0101010101010100", 16).longValue();
  public static final long SOUTH_MASK = new BigInteger("0080808080808080", 16).longValue();

  public int id;
  public boolean isWhite;
  public Location location;

  public Piece(int id, boolean isWhite, Location location) {
    this.id = id;
    this.isWhite = isWhite;
    this.location = location;
  }

  @Deprecated
  public static List<Location> getMoves(Piece piece) {
    if (piece == null) {
      return new ArrayList<Location>();
    }
    switch (piece.id) {
      case BoardModel.ROOK:
        Rook rook = (Rook) piece;
        return rook.getMoves();
      case BoardModel.KNIGHT:
        Knight knight = (Knight) piece;
        return knight.getMoves();
      case BoardModel.BISHOP:
        Bishop bishop = (Bishop) piece;
        return bishop.getMoves();
      case BoardModel.KING:
        King king = (King) piece;
        return king.getMoves();
      case BoardModel.QUEEN:
        Queen queen = (Queen) piece;
        return queen.getMoves();
      case BoardModel.PAWN:
        Pawn pawn = (Pawn) piece;
        return pawn.getMoves();
    }
    // should never get here
    return null;
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
        // Bishop.getMoves(pieceIndex);
        break;
      case BoardModel.KING:
        return King.getMoves(pieceIndex);
      case BoardModel.QUEEN:
        // Queen.getMoves(pieceIndex);
        break;
      case BoardModel.PAWN:
        return Pawn.getMoves(pieceIndex);
    }
    // should never get here
    return 0L;
  }

  public Piece getPieceOnBoard(Location location) {
    if (isOnBoard(location)) {
      return BoardModel.chessBoard[location.row][location.column];
    }
    return null;
  }

  public boolean isOnBoard(Location location) {
    return (location.row >= 0 && location.row <= BoardModel.ROW_LIMIT)
        && (location.column >= 0 && location.column <= BoardModel.COLUMN_LIMIT);
  }

  public int changeRow(int dRow) {
    return this.isWhite ? this.location.row - dRow : this.location.row + dRow;
  }

  public boolean isLegalMove(Location newLocation) {
    if (newLocation == null) {
      return false;
    }
    if (isOnBoard(newLocation)) {
      Piece pieceOnBoard = getPieceOnBoard(newLocation);
      // "this" refers to the instantiated object, not necessarily this particular class
      return (pieceOnBoard == null) || this.isWhite ^ pieceOnBoard.isWhite;
    }
    return false;
  }

  public boolean isOpponentPiece(Location newLocation) {
    Piece pieceOnBoard = getPieceOnBoard(newLocation);
    return (pieceOnBoard != null) ? this.isWhite ^ pieceOnBoard.isWhite : false;
  }

  public boolean checkAndAdd(List<Location> moves, int dRow, int dCol) {
    Location newLocation = new Location(changeRow(dRow), location.column + dCol);
    if (!isLegalMove(newLocation)) {
      return false;
    }
    moves.add(newLocation);
    if (isOpponentPiece(newLocation)) {
      return false;
    }
    return true;
  }

  public static boolean isPieceWhite(int pieceIndex) {
    long bitBoardIndex = 1L << pieceIndex;
    long whiteBitBoard = BoardModel.bitBoards[BoardModel.WHT];
    return (whiteBitBoard & bitBoardIndex) != 0;
  }
  
  public static long eastMask(int pieceIndex){
    long mask = 2L*((1L << (pieceIndex|7)) - (1L << pieceIndex));
    System.out.println("East Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
  
  public static long northMask(int pieceIndex){
    long mask = NORTH_MASK << pieceIndex;
    System.out.println("North Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
  
  public static long southMask(int pieceIndex){
    long mask = SOUTH_MASK >>> (pieceIndex ^ 63);
    System.out.println("South Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
  
  public static long westMask(int pieceIndex){
    long mask = (1L << pieceIndex) - (1L << (pieceIndex&56));
    System.out.println("West Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
}
