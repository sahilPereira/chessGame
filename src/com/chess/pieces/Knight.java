package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.Location;

public class Knight extends Piece {

	private Location location;

	public Knight(int id, boolean isWhite) {
		super(id, isWhite);
	}

	public Knight(int id, boolean isWhite, Location location) {
		super(id, isWhite);
		this.setLocation(location);
	}

	/**
	 * The legal moves that this Pawn can take on the board. Returns an array of
	 * locations on the board
	 * 
	 * return Location[]
	 */
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

	private int changeRow(int dRow) {
		return isWhite ? location.row - dRow : location.row + dRow;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	// TODO change
	private boolean isLegalMove(Location newLocation) {
		if (newLocation == null) {
			return false;
		}
		if(isOnBoard(newLocation)){
			Piece pieceOnBoard = getPieceOnBoard(newLocation);
			return (pieceOnBoard == null) || this.isWhite ^ pieceOnBoard.isWhite;			
		}
		return false;
	}

}
