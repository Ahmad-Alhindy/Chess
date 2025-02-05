package Game;
import java.awt.event.MouseAdapter;
import java.awt.Graphics;

import java.util.HashMap;
import java.util.Map;

import javax.swing.border.Border;

import Game.ChessView.ChessBoard;
import Game.Piece.Direction;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;

public class Move extends MouseAdapter {

	private Board board;
	private int width, height;
	private ChessView chessView; // Reference to ChessView
	private ChessBoard chessBoard;
	private boolean pieceIsSelected = false;
	Direction direction;
	private Piece selectedPiece = new Piece(direction);
	List<int[]> legalMove = new ArrayList<>(); 

	public Move(Board board, ChessView chessView ,int width, int height, ChessBoard chessBoard) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.chessView = chessView;
		this.chessBoard = chessBoard;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickedRow = e.getY() / height;
		int clickedCol = e.getX() / width;
		Piece clickedPiece = this.board.board[clickedRow][clickedCol]; 
		if (clickedPiece != null) {
		System.out.println("this is the click " + clickedPiece.type);
		}
		else {
			System.out.println("this piece is null");
		}
		if (clickedPiece != null && this.pieceIsSelected == false) {
			selectedPiece = board.board[clickedRow][clickedCol];
			selectedPiece.type = clickedPiece.type;
			selectedPiece.direction = clickedPiece.direction;
			
			/*System.out.println("clicked piece is not null row" + clickedRow + " column " + clickedCol );*/
			showLegalMoves(clickedRow, clickedCol, clickedPiece); 

			while (legalMove.size() > 0) {
				int[] square = legalMove.get(0);
				legalMove.remove(0);
				int row = square[0];
				int col = square[1];
				chessView.updateSquareColor(col , row, Color.cyan); // we send it like that because that repaint take the first as x  and the x is the column														
			}
			this.pieceIsSelected = true ;

		}
		else  if (clickedPiece != null && this.pieceIsSelected == true){
			chessView.updateSquareColor(0, 0, Color.red); //just putting some random color but it will never color in red
			this.pieceIsSelected = false;
			}
		
		else if (clickedPiece == null && this.pieceIsSelected == true) {
				Color c = chessView.squareColors[clickedCol][clickedRow];
				movingPiece(clickedRow, clickedCol);	
				System.out.println(c);
				chessView.updateSquareColor(0, 0, Color.red);
				this.pieceIsSelected = false;		
		}
	}
	
	
	private void movingPiece(int row, int col) {
		Color color = chessView.squareColors[col][row];
		if (color == Color.CYAN) {
			board.board[row][col] = selectedPiece;
			board.board[selectedPiece.yPossition][selectedPiece.xPossition] = null;
			//clickedPiece = null;
			selectedPiece.yPossition = row;
			selectedPiece.xPossition = col;
			board.boardPrinter();
			Graphics g = chessBoard.getGraphics();
			chessBoard.paintComponent(g);
		}	
		System.out.println("this work" + col + row + color + selectedPiece.type);
	}
	
		
	
	
	
	private void rock( int row, int col ) {
		int[][] directions = {
				{0, 1},  // Right
				{0, -1}, // Left
				{1, 0},  // Down
				{-1, 0}  // Up
		};
		this.legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;

			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
					&& this.board.board[x][y] == null) {
				this.legalMove.add(new int[] {x, y});
				x += dx; // Move further in the same direction
				y += dy;
			}
		}
	}
	private void bishop(int row, int col ) {
		int[][] directions = {
				{1, 1},  
				{-1, -1}, 
				{1, -1},  
				{-1, 1}  
		};
		this.legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;
			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
					&& this.board.board[x][y] == null) {
				this.legalMove.add(new int[] {x, y});
				x += dx; // Move further in the same direction
				y += dy;
			}
		}
	}
	private void knight (int row, int col ) {
		int[][] directions = {
				{1, 2},  
				{-1, 2},  
				{2, 1},  
				{2, -1},
				{-2, -1},  
				{-2, 1},
				{1, -2},  
				{-1, -2}
		};
		this.legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;
			if (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
					&& this.board.board[x][y] == null) {
			this.legalMove.add(new int[] {x, y});
			}
			x += dx; // Move further in the same direction
			y += dy;
		}
	}
	private void king (int row, int col) {
		int[][] directions = {
				{0, 1},  
				{0, -1}, 
				{1, 0},  
				{-1, 0}, 
				{1, 1},  
				{-1, -1}, 
				{1, -1},  
				{-1, 1}  
		};
		this.legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;

			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
					&& this.board.board[x][y] == null) {
				this.legalMove.add(new int[] {x, y});
				x += dx; // Move further in the same direction
				y += dy;
			}
		}
	}
	private void queen(int row, int col) {
		int[][] directions = {
				{0, 1},  
				{0, -1}, 
				{1, 0},  
				{-1, 0}, 
				{1, 1},  
				{-1, -1}, 
				{1, -1},  
				{-1, 1}  
		};
		this.legalMove.add(new int[] {row , col});
		System.out.println("row: " + row +" col: " +col + " " + board.board[row][col]);

		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			System.out.println("dx: " + dx +"dy: " + dy);
			int x = row + dx;
			int y = col + dy;
			System.out.println("x: " + x +"y: " + y);


			// Loop until out of bounds or blocked
			if (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
					&& this.board.board[x][y] == null) {
				this.legalMove.add(new int[] {x, y});
				x += dx; // Move further in the same direction
				y += dy;
			}
		}
	}
	
	
	
	
	
	private void showLegalMoves(int row , int col , Piece clickedPiece) {
		if (clickedPiece == null ) {
			return;
		}
		
		if (clickedPiece.type == "♟" && clickedPiece.direction == Direction.Down) {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row + 1 , col});
			
			if (row == 1) {
				this.legalMove.add(new int[]{row + 2 , col});
			}
		}
		if (clickedPiece.type == "♟" && clickedPiece.direction == Direction.UPP ) {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row - 1 , col});
			if (row == 6) {
				this.legalMove.add(new int[]{row - 2 , col});
			}
		}
		if (clickedPiece.type == "♜") {
			rock(row, col);
		}
		if ("♝".equals(clickedPiece.type)) {
			bishop(row, col);
		}
		if ("♞".equals(clickedPiece.type)) {
			knight(row, col);
		}
		if ("♛".equals(clickedPiece.type)) {
			king(row, col);
		}
		
		if ("♚".equals(clickedPiece.type)) {
			queen(row, col);
			}
			//System.out.println("Legal moves for " + clickedPiece + ":");
			//for (int[] move : this.legalMove) {
			 //   System.out.println("Row: " + move[0] + ", Col: " + move[1]);
			//}
	}	
}

