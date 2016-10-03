package com.chess.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

public class BoardController implements ActionListener {

	private static JButton oldBtnEvent = null;
	private static Color oldColor = null;
	private BoardView view = null;
	private Action newGameAction = new AbstractAction("New") {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearGame();
			view.setupNewGame();
		}
	};

	public BoardController() {
	}

	public void init() {
		view = new BoardView(newGameAction);
		view.initializeGui();
	}
	
	public JComponent getGui(){
		return view.getGui();
	}

	@Override
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
			highlightLegalMoves();
			jButton.setBackground(Color.GREEN);
		}
	}

	private void highlightLegalMoves() {
		// TODO Auto-generated method stub

	}
}
