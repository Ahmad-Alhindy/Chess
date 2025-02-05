package Game;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;


public class ChessView extends JFrame{    

	int Width = 0;
	int hight = 0;
	boolean listnerAdedd = false;
	protected Color[][] squareColors = new Color[8][8];

	public ChessView() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squareColors[i][j] = (i + j) % 2 == 0 ? Color.WHITE : Color.BLACK;
			}
		}
		setSize(400, 420);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure when the window is close to close the program

		add(new ChessBoard(this));

		setVisible(true);   
	}

	public void updateSquareColor(int row, int col, Color color) {
		//System.out.println("updateSquareColor Square at (" + row + ", " + col + ") updated to " + color);	
		if (color == Color.CYAN) {
			int x = col * this.Width; 
		    int y = row * this.hight; 
			squareColors[row][col] = color; // Update the color
			repaint();
			 System.out.println("color is " + color);
		}
		else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					squareColors[i][j] = (i + j) % 2 == 0 ? Color.WHITE : Color.BLACK;
				}
			}
			repaint();  
			System.out.println("repaint is done");
		}

	}







	protected static class ChessBoard extends JPanel { //ChessBord will draw the board
		private static final long serialVersionUID = 1L;
		Board board = new Board();
		ChessView chessView;
		boolean addpiece = false;


		ChessBoard(ChessView chessView) {
			this.chessView = chessView;
		}



		@Override
		protected void paintComponent(Graphics g) { 	// it used as the paint method
			System.out.println("We are paiting the starting position"); 
		

			super.paintComponent(g); // call the paint method form JPanel to ensure every thing is draw
			chessView.Width = getWidth() / 8; // calculate the width of one square
			chessView.hight = getHeight() / 8;

			if (chessView.listnerAdedd == false) {
				Move listener = new Move(board, chessView, chessView.Width, chessView.hight, this);
				addMouseListener(listener);
				chessView.listnerAdedd = true;
			}


			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					g.setColor(chessView.squareColors[i][j]);
					g.fillRect(i * chessView.Width, j * chessView.hight, chessView.Width, chessView.hight);
				}
			}

			if (addpiece == false) {  
				board.addPices();
				addpiece = true;
			}


			// this for loop to draw the pieces
			for (int row = 0; row < board.board.length; ++row) {
				if (board.board[row] == null) continue;
				for (int col = 0; col < board.board[row].length; ++col) {
					if (board.board[row][col]!= null) {
						Piece p = board.board[row][col]; 
						int xPos = p.xPossition * chessView.Width + 5;
						int yPos = p.yPossition * chessView.hight + 40 ;
						String pieceSymbol = p.type;
						g.setFont(new Font("TimesRoman", Font.PLAIN, (int) Math.floor(chessView.Width * 0.8)));
						g.setColor(p.team == Team.WHITE ? Color.blue : Color.RED); // Different color for teams      
						g.drawString(pieceSymbol, xPos, yPos);  // Adjust for centering
						//System.out.println("Piece: " + p.type + " at (x " + xPos + ", y " + yPos + ")");
						//System.out.println("Piece: " + p.type + "xPossition " + p.xPossition + "p.yPossition " + p.yPossition);
					}
					else {
						continue;
					}
				}
			}

		}

        


	}
}
