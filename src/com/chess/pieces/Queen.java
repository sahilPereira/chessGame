package com.chess.pieces;

import com.chess.ui.Location;

public class Queen extends Piece {

  public Queen(int id, boolean isWhite, Location location) {
    super(id, isWhite, location);
  }
  
  public static long getMoves(int pieceIndex) {
    // Get Rook moves
    long moveMask = Rook.getMoves(pieceIndex);
    // Get Bishop moves and OR with rook moves
    moveMask |= Bishop.getMoves(pieceIndex);
    return moveMask;
  }
}
