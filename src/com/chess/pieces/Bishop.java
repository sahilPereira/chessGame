package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.Location;

public class Bishop extends Piece{
	
	@Deprecated
	public Bishop(int id, boolean isWhite, Location location){
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
	 * The legal moves that this Bishop can take on the board. Returns an array of
	 * locations on the board
	 * 
	 * return Location[]
	 */
	@Deprecated
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
	
	public static List<Integer> getMoves(int pieceIndex) {
		return null;
	}
}
