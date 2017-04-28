package com.chess.pieces;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class King extends Piece {

  public King(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
  
  // TODO: need to include logic to detect if we are allowed to move to a
  // "dangerous" position
//  @Override
//  public boolean isLegalMove(Location newLocation) {
//    if (newLocation == null) {
//      return false;
//    }
//    if (isOnBoard(newLocation)) {
//      Piece pieceOnBoard = getPieceOnBoard(newLocation);
//      return (pieceOnBoard == null) || this.isWhite ^ pieceOnBoard.isWhite;
//    }
//    return false;
//  }

  public static long getMoves(int pieceIndex) {

    // NOTE: based on the colour, we need to AND with A-File or H-File
    long bitBoardIndex = 1L << pieceIndex;
    long eastFilter = NOT_FILE_A;
    long westFilter = NOT_FILE_H;
    long moveMask = 0L;
    // get color mask
    boolean isPieceWhite = isPieceWhite(pieceIndex);
    long colorMask = (isPieceWhite) ? BoardModel.bitBoards[BoardModel.WHT]
        : BoardModel.bitBoards[BoardModel.BLK];

    // west side moves
    moveMask |= (bitBoardIndex << 9) & eastFilter;
    moveMask |= (bitBoardIndex << 1) & eastFilter;
    moveMask |= (bitBoardIndex >>> 7) & eastFilter;    
    // east side moves
    moveMask |= (bitBoardIndex >>> 9) & westFilter;
    moveMask |= (bitBoardIndex >>> 1) & westFilter;
    moveMask |= (bitBoardIndex << 7) & westFilter;
    // top and bottom moves
    moveMask |= bitBoardIndex >>> 8;
    moveMask |= bitBoardIndex << 8;
    // AND with the inverted color board so that own pieces are excluded
    moveMask &= ~colorMask;
    // add the current piece to highlight it
    moveMask |= bitBoardIndex;
    // TODO: remove println
//    System.out.println(Long.toBinaryString(moveMask));
    return moveMask;
  }
}
