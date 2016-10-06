package com.chess.ui;

import com.chess.pieces.Bishop;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

public class BoardModel {
	public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
	public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };
	public static final int BLACK = 0, WHITE = 1;
	public static final int ROW_LIMIT = 7, COLUMN_LIMIT = 7;

	public static Piece[][] chessBoard = null;

	public BoardModel() {
		chessBoard = new Piece[ROW_LIMIT + 1][COLUMN_LIMIT + 1];
	}

	public void initBoardModel() {
		// setup black pieces
		for (int i = 0; i < STARTING_ROW.length; i++) {
			Location location = new Location(0, i);
			fillChessBoard(0, i, STARTING_ROW[i], false, location);
		}
		for (int i = 0; i < BoardModel.STARTING_ROW.length; i++) {
			Location location = new Location(1, i);
			fillChessBoard(1, i, PAWN, false, location);
		}
		// set up the white pieces
		for (int i = 0; i < BoardModel.STARTING_ROW.length; i++) {
			Location location = new Location(6, i);
			fillChessBoard(6, i, PAWN, true, location);
		}
		for (int i = 0; i < BoardModel.STARTING_ROW.length; i++) {
			Location location = new Location(7, i);
			fillChessBoard(7, i, STARTING_ROW[i], true, location);
		}
	}

	private void fillChessBoard(int row, int col, int id, boolean isWhite, Location location) {
		switch (id) {
		case ROOK:
			chessBoard[row][col] = new Rook(ROOK, isWhite, location);
			break;
		case KNIGHT:
			chessBoard[row][col] = new Knight(KNIGHT, isWhite, location);
			break;
		case BISHOP:
			chessBoard[row][col] = new Bishop(BISHOP, isWhite, location);
			break;
		case KING:
			chessBoard[row][col] = new King(KING, isWhite, location);
			break;
		case QUEEN:
			chessBoard[row][col] = new Queen(QUEEN, isWhite, location);
			break;
		case PAWN:
			chessBoard[row][col] = new Pawn(PAWN, isWhite, location);
			break;
		}
	}
}
