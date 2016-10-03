package com.chess.pieces;

public class Piece {
	
	public int id;
	public boolean isWhite;
	public static final int ROW_LIMIT = 7, COLUMN_LIMIT = 7;
	
	public Piece(int id, boolean isWhite){
		this.id = id;
		this.isWhite = isWhite;
	}

}
