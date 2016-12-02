package com.chess.ui;

import java.math.BigInteger;

import com.chess.pieces.Bishop;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

public class BoardModel {
  public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5, BLK = 6,
      WHT = 7;
  public static final int[] STARTING_ROW =
      {ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK};
  public static final int BLACK = 0, WHITE = 1;
  public static final int ROW_LIMIT = 7, COLUMN_LIMIT = 7;

  // TODO: change to long[]
  public static Piece[][] chessBoard = null;
  public static long[] bitBoards = null;
  private static Piece currentPiece = null;
  private static long currentPieceIndex;

  public BoardModel() {
    chessBoard = new Piece[ROW_LIMIT + 1][COLUMN_LIMIT + 1];
    // 6 for pieces and 2 for color
    bitBoards = new long[8];
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

    // TODO: finalize the long values and remove the above code
    bitBoards[QUEEN] = 1L << 4 | 1L << 60;
    bitBoards[KING] = 1L << 3 | 1L << 59;
    bitBoards[ROOK] = 1L << 0 | 1L << 7 | 1L << 56 | 1L << 63;
    bitBoards[KNIGHT] = 1L << 1 | 1L << 6 | 1L << 57 | 1L << 62;
    bitBoards[BISHOP] = 1L << 2 | 1L << 5 | 1L << 58 | 1L << 61;
    // represent 0xFF and bit-shift to appropriate position
    long long_FF = new BigInteger("ff", 16).longValue();
    bitBoards[PAWN] = long_FF << 8 | long_FF << 48;
    // represent 0xFFFF and bit-shift for opponent pieces
    long long_FFFF = new BigInteger("ffff", 16).longValue();
    bitBoards[BLK] = long_FFFF;
    bitBoards[WHT] = long_FFFF << 48;
  }

  public void clearChessBoard() {
    chessBoard = new Piece[ROW_LIMIT + 1][COLUMN_LIMIT + 1];
    // not required but good to be sure
    bitBoards = new long[8];
  }

  // TODO: probably not needed
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

  // a little more refactoring.
  // most likely need to keep these since we we use to keep track of actual pieces.
  public static Piece getCurrentPiece() {
    return currentPiece;
  }

  public static long getCurrentPieceIndex() {
    return currentPieceIndex;
  }

  public static void setCurrentPiece(Piece currentPiece) {
    BoardModel.currentPiece = currentPiece;
  }

  public static void setCurrentPieceIndex(long currentIndex) {
    BoardModel.currentPieceIndex = currentIndex;
  }

  public void updateCurrentPiece(Location location) {
    Location oldLocation = currentPiece.location;
    chessBoard[oldLocation.row][oldLocation.column] = null;
    currentPiece.location = location;
    chessBoard[location.row][location.column] = currentPiece;

    if (currentPiece.id == PAWN && !oldLocation.equals(location)) {
      Pawn pawn = (Pawn) currentPiece;
      pawn.setIsMoved(true);
    }
    // reset for next selection
    currentPiece = null;
  }

  public void updateCurrentPieceIndex(long updatedPieceIndex) {
    // if new index is the same as the old index, no update needed
    if (updatedPieceIndex == currentPieceIndex) {
      return;
    }
    // TODO: need to update one of the 6 piece bitboards
    // Step 1: identify the bitboard requiring an update (bitwise & with old index)
    int bitBoardIndex = getBitBoardIndex(currentPieceIndex);
    int colorBoardIndex = getColorIndex(currentPieceIndex);
    long currentBitBoard = bitBoards[bitBoardIndex];
    long colourBoard = bitBoards[colorBoardIndex];
    // Step 2: set the old index value to zero in that particular bitboard
    currentBitBoard ^= (1 << currentPieceIndex);
    // Step 3: set the new index value to high in that particular bitboard
    currentBitBoard |= (1 << updatedPieceIndex);
    // Step 4: also update the appropriate color boards
    colourBoard ^= (1 << currentPieceIndex);
    colourBoard |= (1 << updatedPieceIndex);
    // Step 5: Update the actual board array
    bitBoards[bitBoardIndex] = currentBitBoard;
    bitBoards[colorBoardIndex] = colourBoard;

    // if(currentPiece.id == PAWN && !oldLocation.equals(location)){
    // Pawn pawn = (Pawn)currentPiece;
    // pawn.setIsMoved(true);
    // }
    // reset for next selection
    currentPiece = null;
  }

  public static int getBitBoardIndex(long currentIndex) {
    long currentPosition = 1L << currentIndex;
    for (int i = 0; i < bitBoards.length - 2; i++) {
      if ((bitBoards[i] & currentPosition) > 0) {
        return i;
      }
    }
    return -1;
  }

  public static int getColorIndex(long currentIndex) {
    long currentPosition = 1L << currentIndex;
    return ((bitBoards[BLK] & currentPosition) > 0) ? BLK : WHT;
  }
}
