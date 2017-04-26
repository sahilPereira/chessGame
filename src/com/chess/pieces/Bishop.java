package com.chess.pieces;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Bishop extends Piece {

  private static long NORTH_EAST_MASK = new BigInteger("8040201008040201", 16).longValue();
  private static long NORTH_WEST_MASK = new BigInteger("0102040810204080", 16).longValue();
  
  @Deprecated
  public Bishop(int id, boolean isWhite, Location location) {
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
   * The legal moves that this Bishop can take on the board. Returns an array of locations on the
   * board
   * 
   * return Location[]
   */
  @Deprecated
  public List<Location> getMoves() {
    List<Location> moves = new ArrayList<Location>();
    // back right
    int dx = 1;
    while (checkAndAdd(moves, -dx, dx)) {
      dx++;
    }
    // back left
    dx = 1;
    while (checkAndAdd(moves, -dx, -dx)) {
      dx++;
    }
    // forward right
    dx = 1;
    while (checkAndAdd(moves, dx, dx)) {
      dx++;
    }
    // forward left
    dx = 1;
    while (checkAndAdd(moves, dx, -dx)) {
      dx++;
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
    moveMask = getForwardMoves(bitBoardIndex, colorMask, opponentColorMask, northEastMask(pieceIndex));
    moveMask |= getForwardMoves(bitBoardIndex, colorMask, opponentColorMask, northWestMask(pieceIndex));
    moveMask |= getReverseMoves(bitBoardIndex, colorMask, opponentColorMask, northEastMask(pieceIndex));
    moveMask |= getReverseMoves(bitBoardIndex, colorMask, opponentColorMask, northWestMask(pieceIndex));

    return moveMask;
  }

  private static long getForwardMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & (rayDirMask - bbIndex);
    System.out.println("Occupied bits in diagonal Mask");
    System.out.println(Long.toBinaryString(occupied));
    // long flippedBits = occupied ^ (occupied - (bbIndex << 1));
    // No bit shift required since the rayDirMask clears the sliding bit
    long flippedBits = occupied ^ (occupied - bbIndex);
    System.out.println("Flipped bits for diagonal Mask");
    System.out.println(Long.toBinaryString(flippedBits));
    // clear extra bits, friendly bits and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }

  private static long getReverseMoves(long bbIndex, long colorMask, long oppColorMask,
      long rayDirMask) {
    long occupied = (colorMask | oppColorMask) & (rayDirMask - bbIndex);
    System.out.println("Occupied bits in diagonal Mask");
    System.out.println(Long.toBinaryString(occupied));
    // we cannot use LSH 1, since at index 63, we roll back to 0
    // long flippedBits = occupied
    // ^ Long.reverse((Long.reverse(occupied) - Long.reverse(bbIndex) - Long.reverse(bbIndex)));
    long flippedBits = occupied ^ Long.reverse((Long.reverse(occupied) - Long.reverse(bbIndex)));
    System.out.println("Flipped bits for diagonal Mask");
    System.out.println(Long.toBinaryString(flippedBits));
    // clear extra bits, and add the current piece index
    return ((flippedBits & rayDirMask) & (~colorMask)) | bbIndex;
  }
  
  private static long northEastMask(int pieceIndex) {
    long diag = 8 * (pieceIndex & 7) - (pieceIndex & 56);
    long nort = -diag & (diag >>> 31);
    long sout = diag & (-diag >>> 31);
    long mask = (NORTH_EAST_MASK >>> sout) << nort;
    System.out.println("North East Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }

  private static long northWestMask(int pieceIndex) {
    long diag = 56 - 8 * (pieceIndex & 7) - (pieceIndex & 56);
    long nort = -diag & (diag >>> 31);
    long sout = diag & (-diag >>> 31);
    long mask = (NORTH_WEST_MASK >>> sout) << nort;
    System.out.println("North West Mask");
    System.out.println(Long.toBinaryString(mask));
    return mask;
  }
}
