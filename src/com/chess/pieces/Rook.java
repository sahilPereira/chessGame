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

  public static List<Integer> getMoves(int pieceIndex) {

    return null;
  }
}
