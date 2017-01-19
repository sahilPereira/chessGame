package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Rook extends Piece {

  @Deprecated
  public Rook(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }

  @Deprecated
  public Location getLocation() {
    return location;
  }

  @Deprecated
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * The legal moves that this Rook can take on the board. Returns an array of locations on the
   * board
   * 
   * return Location[]
   */
  @Deprecated
  public List<Location> getMoves() {
    List<Location> moves = new ArrayList<Location>();
    // bottom
    for (int i = 1; i <= BoardModel.ROW_LIMIT; i++) {
      Location newLocation = new Location(changeRow(i), location.column);
      if (!isLegalMove(newLocation)) {
        break;
      }
      moves.add(newLocation);
      if (isOpponentPiece(newLocation)) {
        break;
      }
    }
    // top
    for (int i = 1; i <= BoardModel.ROW_LIMIT; i++) {
      Location newLocation = new Location(changeRow(-i), location.column);
      if (!isLegalMove(newLocation)) {
        break;
      }
      moves.add(newLocation);
      if (isOpponentPiece(newLocation)) {
        break;
      }
    }
    // right
    for (int i = location.column + 1; i <= BoardModel.COLUMN_LIMIT; i++) {
      Location newLocation = new Location(changeRow(0), i);
      if (!isLegalMove(newLocation)) {
        break;
      }
      moves.add(newLocation);
      if (isOpponentPiece(newLocation)) {
        break;
      }
    }
    // left
    for (int i = location.column - 1; i >= 0; i--) {
      Location newLocation = new Location(changeRow(0), i);
      if (!isLegalMove(newLocation)) {
        break;
      }
      moves.add(newLocation);
      if (isOpponentPiece(newLocation)) {
        break;
      }
    }
    return moves;
  }

  public static long getMoves(int pieceIndex) {
    long bitBoardIndex = 1L << pieceIndex;
    System.out.println("Bit Board Index of Piece");
    System.out.println(Long.toBinaryString(bitBoardIndex));
    long moveMask = 0L;
    long colorMask = 0L, opponentColorMask = 0L;
    boolean isPieceWhite = isPieceWhite(pieceIndex);
    if (isPieceWhite) {
      opponentColorMask = BoardModel.bitBoards[BoardModel.BLK];
      colorMask = BoardModel.bitBoards[BoardModel.WHT];
    } else {
      opponentColorMask = BoardModel.bitBoards[BoardModel.WHT];
      colorMask = BoardModel.bitBoards[BoardModel.BLK];
    }
    moveMask = getForwardMoves(bitBoardIndex, colorMask, opponentColorMask, northMask(pieceIndex));
    moveMask |= getForwardMoves(bitBoardIndex, colorMask, opponentColorMask, eastMask(pieceIndex));
    moveMask |= getReverseMoves(bitBoardIndex, colorMask, opponentColorMask, westMask(pieceIndex));
    moveMask |= getReverseMoves(bitBoardIndex, colorMask, opponentColorMask, southMask(pieceIndex));

    return moveMask;
  }

  private static long getForwardMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & rayDirMask;
    // long flippedBits = occupied ^ (occupied - (bbIndex << 1));
    // No bit shift required sine the rayDirMask clears the sliding bit
    long flippedBits = occupied ^ (occupied - bbIndex);
    // clear extra bits, and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }

  private static long getReverseMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & rayDirMask;
    // we cannot use LSH 1, since at index 63, we roll back to 0
    // long flippedBits = occupied
    // ^ Long.reverse((Long.reverse(occupied) - Long.reverse(bbIndex) - Long.reverse(bbIndex)));
    long flippedBits = occupied ^ Long.reverse((Long.reverse(occupied) - Long.reverse(bbIndex)));
    // clear extra bits, and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }
}
