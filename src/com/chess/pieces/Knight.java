package com.chess.pieces;

import java.math.BigInteger;
import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Knight extends Piece {

  public static final long NOT_FILE_AB = new BigInteger("fcfcfcfcfcfcfcfc", 16).longValue();
  public static final long NOT_FILE_GH = new BigInteger("3f3f3f3f3f3f3f3f", 16).longValue();
  
  public Knight(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }
  
  public static long getMoves(int pieceIndex) {
    
    long bitBoardIndex = 1L << pieceIndex;
//    System.out.println("Bit Board Index of Piece");
//    System.out.println(Long.toBinaryString(bitBoardIndex));
    long westFilter = NOT_FILE_AB;
    long eastFilter = NOT_FILE_GH;
    long eastMoves = 0L, westMoves = 0L, moveMask = 0L;
    
    // get color mask
    boolean isPieceWhite = isPieceWhite(pieceIndex);
    long colorMask = (isPieceWhite) ? BoardModel.bitBoards[BoardModel.WHT]
        : BoardModel.bitBoards[BoardModel.BLK];
    // east side moves
    eastMoves |= (bitBoardIndex << 17);
    eastMoves |= (bitBoardIndex << 10);
    eastMoves |= (bitBoardIndex >>> 6);
    eastMoves |= (bitBoardIndex >>> 15);
//    System.out.println("East Moves Incomplete");
//    System.out.println(Long.toBinaryString(eastMoves));
    // if I am on the EAST side of the board, then clear any moves to the WEST side since those are
    // caused by wrapping of the numbers during bit shifting
    if ((bitBoardIndex & eastFilter) == 0) {
      eastMoves &= westFilter;
    }
//    System.out.println("East Moves");
//    System.out.println(Long.toBinaryString(eastMoves));
    
    // west side moves
    westMoves |= (bitBoardIndex << 15);
    westMoves |= (bitBoardIndex << 6);
    westMoves |= (bitBoardIndex >>> 10);
    westMoves |= (bitBoardIndex >>> 17);
//    System.out.println("West Moves Incomplete");
//    System.out.println(Long.toBinaryString(westMoves));
    // if I am on the WEST side of the board, then clear any moves to the EAST side since those are
    // caused by wrapping of the numbers during bit shifting
    if ((bitBoardIndex & westFilter) == 0) {
      westMoves &= eastFilter;
    }
//    System.out.println("West Moves");
//    System.out.println(Long.toBinaryString(westMoves));
    
    // combine the east and west moves
    moveMask = eastMoves | westMoves;
//    System.out.println("All Piece Moves, before colorMask");
//    System.out.println(Long.toBinaryString(moveMask));
    // AND with the inverted color board so that own pieces are excluded
    moveMask &= ~colorMask;
    // add the current piece to highlight it
    moveMask |= bitBoardIndex;
//    System.out.println("Piece Moves");
//    System.out.println(Long.toBinaryString(moveMask));
    return moveMask;
  }
}
