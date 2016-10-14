package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.Location;

public class Pawn extends Piece {

	private boolean isMoved;

	public Pawn(int id, boolean isWhite, Location location) {
		super(id, isWhite, location);
		this.isMoved = false;
	}

	/**
	 * The legal moves that this Pawn can take on the board. Returns an array of
	 * locations on the board
	 * 
	 * return Location[]
	 */
	public List<Location> getMoves() {
		// use the BoardModel to determine legal moves
		// TODO: determine if there are other pieces on the position to move
		List<Location> moves = new ArrayList<Location>();
		// piece ahead
		Location newLocation = new Location(changeRow(1), location.column);
		if (isLegalMove(newLocation)) {
			moves.add(newLocation);
		}
		// piece diagonal
		newLocation = new Location(changeRow(1), location.column + 1);
		if (isLegalMove(newLocation)) {
			moves.add(newLocation);
		}
		newLocation = new Location(changeRow(1), location.column - 1);
		if (isLegalMove(newLocation)) {
			moves.add(newLocation);
		}
		// extra move for first play
		if (!isMoved) {
			moves.add(new Location(changeRow(2), location.column));
		}
		return moves;
	}

	public boolean isMoved() {
		return isMoved;
	}

	public void setIsMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

	@Override
	public boolean isLegalMove(Location newLocation) {
		if (newLocation == null) {
			return false;
		}
		if (isOnBoard(newLocation)) {
			Piece pieceOnBoard = getPieceOnBoard(newLocation);
			int dRow = Math.abs(newLocation.row - location.row);
			int dColumn = Math.abs(newLocation.column - location.column);
			if (dColumn == 0) {
				return pieceOnBoard == null;
			} else if ((dRow + dColumn == 2) && pieceOnBoard != null) {
				return isWhite ^ pieceOnBoard.isWhite;
			}
		}
		return false;
	}

}
