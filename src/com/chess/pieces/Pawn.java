package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Pawn extends Piece{

	private Location location;
	private boolean isMoved;
	
	public Pawn(int id, boolean isWhite, Location location){
		super(id, isWhite);
		this.setLocation(location);
		this.isMoved = false;
	}
	
	public Pawn(int id, boolean isWhite) {
		super(id, isWhite);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The legal moves that this Pawn can take on the board.
	 * Returns an array of locations on the board
	 * 
	 * return Location[]
	 */
	public List<Location> getMoves(){
		// use the BoardModel to determine legal moves
		// TODO: determine if there are other pieces on the position to move
		List<Location> moves = new ArrayList<Location>();
		if(!isMoved){
			moves.add(new Location(changeRow(1), location.column));
			moves.add(new Location(changeRow(2), location.column));
		} else{
			// piece ahead
			Location newLocation = new Location(changeRow(1), location.column);
			if(isLegalMove(newLocation)){
				moves.add(newLocation);
			}
			// piece diagonal
			newLocation = new Location(changeRow(1), location.column+1);
			if(isLegalMove(newLocation)){
				moves.add(newLocation);
			}
			newLocation = new Location(changeRow(1), location.column-1);
			if(isLegalMove(newLocation)){
				moves.add(newLocation);
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

	public boolean isMoved() {
		return isMoved;
	}
	
	private boolean isLegalMove(Location newLocation){
		if(newLocation == null){
			return false;
		}
		Piece pieceOnBoard = getPieceOnBoard(newLocation);
		int dRow = newLocation.row - this.location.row;
		int dColumn = Math.abs(newLocation.column - this.location.column);
		if((dColumn + dRow) == 1){
			return pieceOnBoard == null;
		} else if((dColumn + dRow) == 2){
			return this.isWhite^pieceOnBoard.isWhite;
		}
		return false;
	}

}
