package com.chess.pieces;

import java.util.List;

import com.chess.ui.BoardModel;
import com.chess.ui.Location;

public class Piece {
	
	public int id;
	public boolean isWhite;
	
	public Piece(int id, boolean isWhite){
		this.id = id;
		this.isWhite = isWhite;
	}
	
	public static List<Location> getMoves(Piece piece){
		switch (piece.id) {
		case BoardModel.ROOK:
			Rook rook = (Rook)piece;
//			return rook.getMoves()
			break;
		case BoardModel.KNIGHT:
			Knight knight = (Knight)piece;
			return knight.getMoves();
		case BoardModel.BISHOP:
			Bishop bishop = (Bishop)piece;
			break;
		case BoardModel.KING:
			King king = (King)piece;
			break;
		case BoardModel.QUEEN:
			Queen queen = (Queen)piece;
			break;
		case BoardModel.PAWN:
			Pawn pawn = (Pawn)piece;
			return pawn.getMoves();
		}
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
}
