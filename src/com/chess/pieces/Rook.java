package com.chess.pieces;

import java.math.BigInteger;
import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Rook extends Piece {

  private static long NORTH_MASK = new BigInteger("0101010101010100", 16).longValue();
  private static long SOUTH_MASK = new BigInteger("0080808080808080", 16).longValue();
  
  public Rook(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }

  public static long getMoves(int pieceIndex) {
    long bitBoardIndex = 1L << pieceIndex;
//    System.out.println("Bit Board Index of Piece");
//    System.out.println(Long.toBinaryString(bitBoardIndex));
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

  private static long eastMask(int pieceIndex) {
    long mask = 2L * ((1L << (pieceIndex | 7)) - (1L << pieceIndex));
//    System.out.println("East Mask");
//    System.out.println(Long.toBinaryString(mask));
    return mask;
  }

  private static long northMask(int pieceIndex) {
    long mask = NORTH_MASK << pieceIndex;
//    System.out.println("North Mask");
//    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
  
  private static long southMask(int pieceIndex) {
    long mask = SOUTH_MASK >>> (pieceIndex ^ 63);
//    System.out.println("South Mask");
//    System.out.println(Long.toBinaryString(mask));
    return mask;
  }

  private static long westMask(int pieceIndex) {
    long mask = (1L << pieceIndex) - (1L << (pieceIndex & 56));
//    System.out.println("West Mask");
//    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
  private static long getForwardMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & rayDirMask;
    // No bit shift required since the rayDirMask clears the sliding bit
    long flippedBits = occupied ^ (occupied - bbIndex);
    // clear extra bits, friendly bits and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }

  private static long getReverseMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & rayDirMask;
    // we cannot use LSH 1, since at index 63, we roll back to 0
    long flippedBits = occupied ^ Long.reverse((Long.reverse(occupied) - Long.reverse(bbIndex)));
    // clear extra bits, and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }
}
