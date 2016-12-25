package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Knight extends Piece {

  @Deprecated
  public Knight(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }

  /**
   * The legal moves that this Knight can take on the board. Returns an array of locations on the
   * board
   * 
   * return Location[]
   */
  @Deprecated
  public List<Location> getMoves() {
    List<Location> moves = new ArrayList<Location>();
    List<Location> possibleMoves = new ArrayList<Location>();
    // all positions a Knight can move
    possibleMoves.add(new Location(changeRow(-2), location.column - 1));
    possibleMoves.add(new Location(changeRow(-2), location.column + 1));
    possibleMoves.add(new Location(changeRow(-1), location.column - 2));
    possibleMoves.add(new Location(changeRow(-1), location.column + 2));
    possibleMoves.add(new Location(changeRow(1), location.column - 2));
    possibleMoves.add(new Location(changeRow(1), location.column + 2));
    possibleMoves.add(new Location(changeRow(2), location.column - 1));
    possibleMoves.add(new Location(changeRow(2), location.column + 1));
    for (Location possibleMove : possibleMoves) {
      if (isLegalMove(possibleMove)) {
        moves.add(possibleMove);
      }
    }
    return moves;
  }

  public static long getMoves(int pieceIndex) {

    // NOTE: based on the colour, we need to AND with A-File or H-File
    long bitBoardIndex = 1L << pieceIndex;
    System.out.println(Long.toBinaryString(bitBoardIndex));
    long eastFilter = NOT_FILE_A;
    long westFilter = NOT_FILE_H;
    long moveMask = 0L;
    // get color mask
    boolean isPieceWhite = isPieceWhite(pieceIndex);
    long colorMask = (isPieceWhite) ? BoardModel.bitBoards[BoardModel.WHT]
        : BoardModel.bitBoards[BoardModel.BLK];

    // west side moves
    moveMask |= (bitBoardIndex << 17) & eastFilter;
    moveMask |= (bitBoardIndex << 10) & eastFilter;
    moveMask |= (bitBoardIndex >> 6) & eastFilter;
    moveMask |= (bitBoardIndex >> 15) & eastFilter;
    // east side moves
    moveMask |= (bitBoardIndex << 15) & westFilter;
    moveMask |= (bitBoardIndex << 6) & westFilter;
    moveMask |= (bitBoardIndex >> 10) & westFilter;
    moveMask |= (bitBoardIndex >> 17) & westFilter;
    // AND with the inverted color board so that own pieces are excluded
    moveMask &= ~colorMask;
    // add the current piece to highlight it
    moveMask |= bitBoardIndex;
    // TODO: remove println
    System.out.println(Long.toBinaryString(moveMask));
    return moveMask;
  }
}
