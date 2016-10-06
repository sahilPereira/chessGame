package com.chess.pieces;

import com.chess.ui.Location;

public class Queen extends Piece{

	private Location location;
	
	public Queen(int id, boolean isWhite) {
		super(id, isWhite);
		// TODO Auto-generated constructor stub
	}
	
	public Queen(int id, boolean isWhite, Location location){
		super(id, isWhite);
		this.setLocation(location);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
