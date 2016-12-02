package com.chess.pieces;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
	
	public Queen(int id, boolean isWhite, Location location){
		super(id, isWhite, location);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * The legal moves that this Queen can take on the board. Returns an array of
	 * locations on the board
	 *
	 * return Location[]
	 */
	public List<Location> getMoves() {
		List<Location> moves = new ArrayList<Location>();
		// bottom
		for(int i = 1; i<= BoardModel.ROW_LIMIT; i++){
			Location newLocation = new Location(changeRow(i), location.column);
			if (!isLegalMove(newLocation)) {
				break;
			}
			moves.add(newLocation);
			if(isOpponentPiece(newLocation)){
				break;
			}
		}
		// top
		for(int i = 1; i<=BoardModel.ROW_LIMIT; i++){
			Location newLocation = new Location(changeRow(-i), location.column);
			if (!isLegalMove(newLocation)) {
				break;
			}
			moves.add(newLocation);
			if(isOpponentPiece(newLocation)){
				break;
			}
		}
		// right
		for(int i = location.column+1; i<=BoardModel.COLUMN_LIMIT; i++){
			Location newLocation = new Location(changeRow(0), i);
			if (!isLegalMove(newLocation)) {
				break;
			}
			moves.add(newLocation);
			if(isOpponentPiece(newLocation)){
				break;
			}
		}
		// left
		for(int i = location.column-1; i>=0; i--){
			Location newLocation = new Location(changeRow(0), i);
			if (!isLegalMove(newLocation)) {
				break;
			}
			moves.add(newLocation);
			if(isOpponentPiece(newLocation)){
				break;
			}
		}
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
