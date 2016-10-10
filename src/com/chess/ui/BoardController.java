package com.chess.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.chess.pieces.Piece;

public class BoardController implements ActionListener {

	private static JButton oldBtnEvent = null;
	private static Color oldColor = null;
	private static BoardView view;
	private static BoardModel model;
	private Action newGameAction = new AbstractAction("New") {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			view.clearGame();
			view.setupNewGame();
		}
	};

	public BoardController() {
	}

	public void init() {
		model = new BoardModel();
		view = new BoardView(newGameAction);
		// init the UI (Just for visual and user input)
		view.initializeGui();
		// init the data model (for keeping track of pieces)
		model.initBoardModel();
	}
	
	public JComponent getGui(){
		return view.getGui();
	}

	public void actionPerformed(ActionEvent event) {
		Object chessObj = event.getSource();
		if (chessObj instanceof JButton) {
			JButton jButton = (JButton) chessObj;
			// TODO: remove later
			System.out.println(jButton.getName());
			System.out.println(jButton.getActionCommand());

			// save previous button information
			if (oldBtnEvent != null) {
				oldBtnEvent.setBackground(oldColor);
			}
			oldBtnEvent = jButton;
			oldColor = jButton.getBackground();

			// change color
			jButton.setBackground(Color.GREEN);
			highlightLegalMoves(Location.toLocation(jButton.getActionCommand()));
		}
	}

	private void highlightLegalMoves(Location location) {
		// recognize the piece using the chessBoard in the model
		List<Location> moves = Piece.getMoves(BoardModel.chessBoard[location.row][location.column]);
		System.out.println(moves.size());
		view.highlightTiles(moves);
//		for(Location local : moves){
//			System.out.println(Location.toString(local));			
//		}
		// call the getMoves for that particular piece
		// remove any moves that cannot be made referencing the current configuration
		// display possible move locations to user
		
		// Extra: highlight opponent pieces as red
	}
}
