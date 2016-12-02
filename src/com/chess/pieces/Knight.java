package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.Location;

public class Knight extends Piece {

	@Deprecated
	public Knight(int id, boolean isWhite, Location location) {
		super(id, isWhite, location);
	}

	/**
	 * The legal moves that this Knight can take on the board. Returns an array of
	 * locations on the board
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
	
	public static List<Integer> getMoves(int pieceIndex) {
		
		return null;
	}
}
