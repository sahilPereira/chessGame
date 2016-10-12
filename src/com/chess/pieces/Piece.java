package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Piece {
	
	public int id;
	public boolean isWhite;
	public Location location;
	
	public Piece(int id, boolean isWhite, Location location){
		this.id = id;
		this.isWhite = isWhite;
		this.location = location;
	}
	
	public static List<Location> getMoves(Piece piece){
		if(piece == null){
			return new ArrayList<Location>();
		}
		switch (piece.id) {
		case BoardModel.ROOK:
			Rook rook = (Rook)piece;
			return rook.getMoves();
		case BoardModel.KNIGHT:
			Knight knight = (Knight)piece;
			return knight.getMoves();
		case BoardModel.BISHOP:
			Bishop bishop = (Bishop)piece;
			return bishop.getMoves();
		case BoardModel.KING:
			King king = (King)piece;
			return king.getMoves();
		case BoardModel.QUEEN:
			Queen queen = (Queen)piece;
			return queen.getMoves();
		case BoardModel.PAWN:
			Pawn pawn = (Pawn)piece;
			return pawn.getMoves();
		}
		// should never get here
		return null;
	}

	public Piece getPieceOnBoard(Location location){
		if(isOnBoard(location)){
			return BoardModel.chessBoard[location.row][location.column];
		}
		return null;
	}
	
	public boolean isOnBoard(Location location){
		return(location.row >= 0 && location.row <= BoardModel.ROW_LIMIT)
				&& (location.column >= 0 && location.column <= BoardModel.COLUMN_LIMIT);
	}

	public int changeRow(int dRow) {
		return this.isWhite ? this.location.row - dRow : this.location.row + dRow;
	}
	
	public boolean isLegalMove(Location newLocation) {
		if (newLocation == null) {
			return false;
		}
		if(isOnBoard(newLocation)){
			Piece pieceOnBoard = getPieceOnBoard(newLocation);
			// "this" refers to the instantiated object, not necessarily this particular class
			return (pieceOnBoard == null) || this.isWhite ^ pieceOnBoard.isWhite;
		}
		return false;
	}
	
	public boolean isOpponentPiece(Location newLocation) {
		Piece pieceOnBoard = getPieceOnBoard(newLocation);
		return (pieceOnBoard != null) ? this.isWhite ^ pieceOnBoard.isWhite : false;			
	}
	
	public boolean checkAndAdd(List<Location> moves, int dRow, int dCol) {
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
}
