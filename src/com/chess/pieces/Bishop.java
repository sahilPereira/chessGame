package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.Location;

public class Bishop extends Piece{

	private Location location;
	
	public Bishop(int id, boolean isWhite) {
		super(id, isWhite);
		// TODO Auto-generated constructor stub
	}
	
	public Bishop(int id, boolean isWhite, Location location){
		super(id, isWhite);
		this.setLocation(location);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * The legal moves that this Bishop can take on the board. Returns an array of
	 * locations on the board
	 * 
	 * return Location[]
	 */
	public List<Location> getMoves() {
		List<Location> moves = new ArrayList<Location>();
		// back right
		int dx = 1;
		while(checkAndAdd(moves, -dx, dx)){
			dx++;
		}
		// back left
		dx = 1;
		while(checkAndAdd(moves, -dx, -dx)){
			dx++;
		}
		// forward right
		dx = 1;
		while(checkAndAdd(moves, dx, dx)){
			dx++;
		}
		// forward left
		dx = 1;
		while(checkAndAdd(moves, dx, -dx)){
			dx++;
		}
		return moves;
	}

	private boolean checkAndAdd(List<Location> moves, int dRow, int dCol) {
		Location newLocation = new Location(changeRow(dRow), location.column+dCol);
		if (!isLegalMove(newLocation)) {
			return false;
		}
		moves.add(newLocation);
		if(isOpponentPiece(newLocation)){
			return false;
		}
		return true;
	}

	private boolean isOpponentPiece(Location newLocation) {
		Piece pieceOnBoard = getPieceOnBoard(newLocation);
		return (pieceOnBoard != null) ? this.isWhite ^ pieceOnBoard.isWhite : false;			
	}

	private int changeRow(int dRow) {
		return isWhite ? location.row - dRow : location.row + dRow;
	}
	
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
