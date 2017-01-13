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
    long westFilter = 0L;
    long eastFilter = 0L;
    long eastMoves = 0L, westMoves = 0L, moveMask = 0L;
    
    northMask(pieceIndex);
    southMask(pieceIndex);
    eastMask(pieceIndex);
    westMask(pieceIndex);
    
//    // get color mask
//    boolean isPieceWhite = isPieceWhite(pieceIndex);
//    long colorMask = (isPieceWhite) ? BoardModel.bitBoards[BoardModel.WHT]
//        : BoardModel.bitBoards[BoardModel.BLK];
//    // east side moves
//    eastMoves |= (bitBoardIndex << 17);
//    eastMoves |= (bitBoardIndex << 10);
//    eastMoves |= (bitBoardIndex >>> 6);
//    eastMoves |= (bitBoardIndex >>> 15);
//    System.out.println("East Moves Incomplete");
//    System.out.println(Long.toBinaryString(eastMoves));
//    // if I am on the EAST side of the board, then clear any moves to the WEST side since those are
//    // caused by wrapping of the numbers during bit shifting
//    if ((bitBoardIndex & eastFilter) == 0) {
//      eastMoves &= westFilter;
//    }
//    System.out.println("East Moves");
//    System.out.println(Long.toBinaryString(eastMoves));
//    
//    // west side moves
//    westMoves |= (bitBoardIndex << 15);
//    westMoves |= (bitBoardIndex << 6);
//    westMoves |= (bitBoardIndex >>> 10);
//    westMoves |= (bitBoardIndex >>> 17);
//    System.out.println("West Moves Incomplete");
//    System.out.println(Long.toBinaryString(westMoves));
//    // if I am on the WEST side of the board, then clear any moves to the EAST side since those are
//    // caused by wrapping of the numbers during bit shifting
//    if ((bitBoardIndex & westFilter) == 0) {
//      westMoves &= eastFilter;
//    }
//    System.out.println("West Moves");
//    System.out.println(Long.toBinaryString(westMoves));
//    
//    // combine the east and west moves
//    moveMask = eastMoves | westMoves;
//    System.out.println("All Piece Moves, before colorMask");
//    System.out.println(Long.toBinaryString(moveMask));
//    // AND with the inverted color board so that own pieces are excluded
//    moveMask &= ~colorMask;
//    // add the current piece to highlight it
//    moveMask |= bitBoardIndex;
//    System.out.println("Piece Moves");
//    System.out.println(Long.toBinaryString(moveMask));
    return northMask(pieceIndex);
  }
}
