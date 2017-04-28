package com.chess.pieces;

import java.math.BigInteger;
import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Pawn extends Piece {

  private static final long blackRank = new BigInteger("ff", 16).longValue() << 8;
  private static final long whiteRank = new BigInteger("ff", 16).longValue() << 48;

  private boolean isMoved;

  public Pawn(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
    this.isMoved = false;
  }

  public boolean isMoved() {
    return isMoved;
  }

  public void setIsMoved(boolean isMoved) {
    this.isMoved = isMoved;
  }

//  @Override
//  public boolean isLegalMove(Location newLocation) {
//    if (newLocation == null) {
//      return false;
//    }
//    if (isOnBoard(newLocation)) {
//      Piece pieceOnBoard = getPieceOnBoard(newLocation);
//      int dRow = Math.abs(newLocation.row - location.row);
//      int dColumn = Math.abs(newLocation.column - location.column);
//      if (dColumn == 0) {
//        return pieceOnBoard == null;
//      } else if ((dRow + dColumn == 2) && pieceOnBoard != null) {
//        return isWhite ^ pieceOnBoard.isWhite;
//      }
//    }
//    return false;
//  }

  public static long getMoves(int pieceIndex) {
    long bitBoardIndex = 1L << pieceIndex;
    long moveMask = 0L, colorMask = 0L, opponentColorMask = 0L, allPiecesMask = 0L;
    boolean isPieceWhite = isPieceWhite(pieceIndex);

    if (isPieceWhite) {
      colorMask = BoardModel.bitBoards[BoardModel.WHT];
      opponentColorMask = BoardModel.bitBoards[BoardModel.BLK];

      moveMask |= (bitBoardIndex >>> 9) & NOT_FILE_H;
      moveMask |= (bitBoardIndex >>> 7) & NOT_FILE_A;
      moveMask &= opponentColorMask;
      // get all positions
      allPiecesMask = colorMask | opponentColorMask;
      // forward move
      moveMask |= ~allPiecesMask & (bitBoardIndex >>> 8);
      boolean hasExtraMove =
          ((whiteRank & bitBoardIndex) != 0) & ((allPiecesMask & (bitBoardIndex >>> 8)) == 0);
      if (hasExtraMove) {
        moveMask |= ~allPiecesMask & (bitBoardIndex >>> 16);
      }
    } else {
      colorMask = BoardModel.bitBoards[BoardModel.BLK];
      opponentColorMask = BoardModel.bitBoards[BoardModel.WHT];

      moveMask |= (bitBoardIndex << 9) & NOT_FILE_A;
      moveMask |= (bitBoardIndex << 7) & NOT_FILE_H;
      moveMask &= opponentColorMask;
      // get all positions
      allPiecesMask = colorMask | opponentColorMask;
      // forward move
      moveMask |= ~allPiecesMask & (bitBoardIndex << 8);
      boolean hasExtraMove =
          ((blackRank & bitBoardIndex) != 0) & ((allPiecesMask & (bitBoardIndex << 8)) == 0);
      if (hasExtraMove) {
        moveMask |= ~allPiecesMask & (bitBoardIndex << 16);
      }
    }
    // AND with the inverted color board so that own pieces are excluded
    // moveMask &= ~colorMask;
    // add the current piece to highlight it
    moveMask |= bitBoardIndex;
    // TODO: remove println
//    System.out.println(Long.toBinaryString(moveMask));
    return moveMask;
  }
}
