package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

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
	public Location[] getMoves(){
		// TODO: determine if there are other pieces on the position to move
		List<Location> moves = new ArrayList<Location>();
		if(!isMoved){
			moves.add(new Location(location.row, location.column+1));
			moves.add(new Location(location.row, location.column+2));
		} else{
			if(location.column < COLUMN_LIMIT){
				moves.add(new Location(location.row, location.column+1));
			}
			if(location.column > 0){
				moves.add(new Location(location.row, location.column+1));
			}
		}
		return null;
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

}
