package com.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.chess.pieces.Piece;

public class BoardView extends JPanel {

  private static final long serialVersionUID = 1L;
  private final JPanel gui = new JPanel(new BorderLayout(3, 3));
  private JButton[][] chessBoardSquares = new JButton[8][8];
  private Image[][] chessPieceImages = new Image[2][6];
  private JPanel chessBoard;
  private final JLabel message = new JLabel("Chess Champ is ready to play!");
  private static final String COLS = "ABCDEFGH";
  private Action newGameAction = null;
  private final BoardController controller = new BoardController();
  private List<Location> litLocations;

  // public BoardView() {
  // initializeGui();
  // }

  public BoardView(Action newGameAction) {
    this.newGameAction = newGameAction;
    this.setLitLocations(new ArrayList<Location>());
  }

  // TODO: might refactor the functional buttons out to BoardController
  public final void initializeGui() {
    // create the images for the chess pieces
    createImages();

    // set up the main GUI
    gui.setBorder(new EmptyBorder(5, 5, 5, 5));
    JToolBar tools = new JToolBar();
    tools.setFloatable(false);
    gui.add(tools, BorderLayout.PAGE_START);
    // Action newGameAction = new AbstractAction("New") {
    //
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // setupNewGame();
    // }
    // };
    tools.add(newGameAction);
    tools.add(new JButton("Save")); // TODO - add functionality!
    tools.add(new JButton("Restore")); // TODO - add functionality!
    tools.addSeparator();
    tools.add(new JButton("Resign")); // TODO - add functionality!
    tools.addSeparator();
    tools.add(message);

    gui.add(new JLabel("?"), BorderLayout.LINE_START);

    chessBoard = new JPanel(new GridLayout(0, 9)) {

      private static final long serialVersionUID = 1L;

      /**
       * Override the preferred size to return the largest it can, in a square shape. Must (must,
       * must) be added to a GridBagLayout as the only component (it uses the parent as a guide to
       * size) with no GridBagConstaint (so it is centered).
       */
      @Override
      public final Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Dimension prefSize = null;
        Component c = getParent();
        if (c == null) {
          prefSize = new Dimension((int) d.getWidth(), (int) d.getHeight());
        } else if (c != null && c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
          prefSize = c.getSize();
        } else {
          prefSize = d;
        }
        int w = (int) prefSize.getWidth();
        int h = (int) prefSize.getHeight();
        // the smaller of the two sizes
        int s = (w > h ? h : w);
        return new Dimension(s, s);
      }
    };
    chessBoard
        .setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new LineBorder(Color.BLACK)));
    // Set the BG to be ochre
    Color ochre = new Color(204, 119, 34);
    chessBoard.setBackground(ochre);
    JPanel boardConstrain = new JPanel(new GridBagLayout());
    boardConstrain.setBackground(ochre);
    boardConstrain.add(chessBoard);
    gui.add(boardConstrain);

    // create the chess board squares
    Insets buttonMargin = new Insets(0, 0, 0, 0);
    for (int ii = 0; ii < chessBoardSquares.length; ii++) {
      for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
        JButton b = new JButton();
        b.setMargin(buttonMargin);
        // our chess pieces are 64x64 px in size, so we'll
        // 'fill this in' using a transparent icon..
        ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        b.setIcon(icon);
        if (isBackgroundWhite(ii, jj)) {
          b.setBackground(Color.WHITE);
        } else {
          b.setBackground(Color.BLACK);
        }
        // chessBoardSquares[jj][ii] = b;
        chessBoardSquares[ii][jj] = b;
      }
    }

    /*
     * fill the chess board
     */
    chessBoard.add(new JLabel(""));
    // fill the top row
    for (int ii = 0; ii < 8; ii++) {
      chessBoard.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
    }
    // fill the black non-pawn piece row
    for (int ii = 0; ii < 8; ii++) {
      for (int jj = 0; jj < 8; jj++) {
        switch (jj) {
          case 0:
            chessBoard.add(new JLabel("" + (9 - (ii + 1)), SwingConstants.CENTER));
          default:
            // chessBoard.add(chessBoardSquares[jj][ii]);
            chessBoard.add(chessBoardSquares[ii][jj]);
        }
      }
    }
  }

  private boolean isBackgroundWhite(int rowIndex, int colIndex) {
    return (colIndex % 2 == 1 && rowIndex % 2 == 1) || (colIndex % 2 == 0 && rowIndex % 2 == 0);
  }

  public final JComponent getGui() {
    return gui;
  }

  private final void createImages() {
    try {
      File image = new File("src/resources/chessPieces.png");// ("http://i.stack.imgur.com/memI0.png");
      BufferedImage bi = ImageIO.read(image);
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 6; j++) {
          chessPieceImages[i][j] = bi.getSubimage(j * 64, i * 64, 64, 64);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Initializes the icons of the initial chess board piece places
   */
  public final void setupNewGame() {
    message.setText("Make your move!");
    // set up pieces
    for (int i = 0; i < BoardModel.STARTING_ROW.length; i++) {
      for (int j = 0; j < BoardModel.STARTING_ROW.length; j++) {
        switch (i) {
          case 0:
            chessBoardSquares[i][j].setIcon(
                new ImageIcon(chessPieceImages[BoardModel.BLACK][BoardModel.STARTING_ROW[j]]));
            break;
          case 1:
            chessBoardSquares[i][j]
                .setIcon(new ImageIcon(chessPieceImages[BoardModel.BLACK][BoardModel.PAWN]));
            break;
          case 6:
            chessBoardSquares[i][j]
                .setIcon(new ImageIcon(chessPieceImages[BoardModel.WHITE][BoardModel.PAWN]));
            break;
          case 7:
            chessBoardSquares[i][j].setIcon(
                new ImageIcon(chessPieceImages[BoardModel.WHITE][BoardModel.STARTING_ROW[j]]));
            break;
        }
        chessBoardSquares[i][j].setActionCommand(Location.toString(new Location(i, j)));
        chessBoardSquares[i][j].addActionListener(controller);
      }
    }
  }

  public void clearGame() {
    for (int i = 0; i < chessBoardSquares.length; i++) {
      for (int j = 0; j < chessBoardSquares[0].length; j++) {
        chessBoardSquares[i][j].setIcon(null);
        chessBoardSquares[i][j].setName(null);
        chessBoardSquares[i][j].setActionCommand(null);
        chessBoardSquares[i][j].removeActionListener(controller);
        if (isBackgroundWhite(j, i)) {
          chessBoardSquares[i][j].setBackground(Color.WHITE);
        } else {
          chessBoardSquares[i][j].setBackground(Color.BLACK);
        }
      }
    }
  }

  public void highlightTiles(List<Location> locations) {
    // unhighlight old buttons
    unhighlightLocations();
    litLocations.clear();
    for (Location local : locations) {
      JButton button = chessBoardSquares[local.row][local.column];
      button.setBackground(Color.GREEN);
      litLocations.add(local);
    }
  }
  
  public void highlightTiles(long movesBitBoard) {

    List<Integer> movesIndex = new ArrayList<>();
    while(movesBitBoard != 0){
      movesIndex.add(Long.numberOfTrailingZeros(movesBitBoard));
      movesBitBoard &= movesBitBoard - 1;
    }
//     unhighlight old buttons
    unhighlightLocations();
    litLocations.clear();
    for (Integer moveIndex : movesIndex) {
      int rankIndex = moveIndex >> 3;
      int fileIndex = moveIndex & 7;
      JButton button = chessBoardSquares[rankIndex][fileIndex];
      button.setBackground(Color.GREEN);
      litLocations.add(new Location(rankIndex, fileIndex));
    }
  }

  private void unhighlightLocations() {
    for (Location local : litLocations) {
      if (isBackgroundWhite(local.row, local.column)) {
        chessBoardSquares[local.row][local.column].setBackground(Color.WHITE);
      } else {
        chessBoardSquares[local.row][local.column].setBackground(Color.BLACK);
      }
    }
  }

  public List<Location> getLitLocations() {
    return litLocations;
  }

  public void setLitLocations(List<Location> litLocations) {
    this.litLocations = litLocations;
  }

  public void updateBoard(Piece currentPiece, Location newLocation) {
    // remove the piece image from old spot
    Location oldLocation = currentPiece.location;
    chessBoardSquares[oldLocation.row][oldLocation.column].setIcon(null);
    unhighlightLocations();
    // unhighlightLocation(oldLocation);

    // add piece image to new location
    int color = (currentPiece.isWhite) ? 1 : 0;
    chessBoardSquares[newLocation.row][newLocation.column]
        .setIcon(new ImageIcon(chessPieceImages[color][currentPiece.id]));
    // unhighlightLocation(newLocation);
  }
}
