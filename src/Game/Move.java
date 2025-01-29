package Game;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Move extends MouseAdapter {

	private Board board;
	private int width, height;
	private ChessView chessView; // Reference to ChessView
	private boolean pieceIsSelected = false;
	List<int[]> legalMove = new ArrayList<>(); 

	public Move(Board board, ChessView chessView ,int width, int height) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.chessView = chessView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickedRow = e.getY() / height;
		int clickedCol = e.getX() / width;

		String clickedPiece = this.board.board[clickedRow][clickedCol];
		System.out.println("this is the click " + clickedPiece);
		if (clickedPiece != null && this.pieceIsSelected == false) {
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
			chessView.updateSquareColor(0, 0, Color.red); //just putting som random color but it will never color in red
			this.pieceIsSelected = false;
			}
		else if (clickedPiece == null && this.pieceIsSelected == true) {
				Color c = chessView.squareColors[clickedRow][clickedCol];
				if (c == Color.CYAN) {
					System.out.println("this work" + clickedRow + clickedCol);
				}
			
		}
	}
			/*
				while (legalMove.size() > 0) {
					int[] square = legalMove.get(0); // Get the first element
					legalMove.remove(0);
					int row = square[0];
					int col = square[1];    
					Color previousColor = squareColorMap.get(row + "," + col);
					// Repaint the square back to the previous color
					chessView.updateSquareColor(col, row, previousColor);
					//System.out.println("square to repain row" + row + "column" + col);
					// Remove the last stored color
					squareColorMap.remove(row + "," + col);
				}
				*/
			
	private void showLegalMoves(int row , int col , String clickedPiece) {
		if (clickedPiece == "♟") {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row + 1 , col});
			if (row == 1) {
				this.legalMove.add(new int[]{row + 2 , col});
			}
		}
		if (clickedPiece == "♙") {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row - 1 , col});
			if (row == 6) {
				this.legalMove.add(new int[]{row - 2 , col});
			}
		}
		if ("♜".equals(clickedPiece) || "♖".equals(clickedPiece)) {

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


		if ("♝".equals(clickedPiece) || "♗".equals(clickedPiece)) {
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


		if ("♞".equals(clickedPiece) || "♘".equals(clickedPiece)) {
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
		if ("♛".equals(clickedPiece) || "♕".equals(clickedPiece)) {
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
		
		if ("♔".equals(clickedPiece) || "♚".equals(clickedPiece)) {
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
				if (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length 
						&& this.board.board[x][y] == null) {
					this.legalMove.add(new int[] {x, y});
					x += dx; // Move further in the same direction
					y += dy;
				}
			}
			//System.out.println("Legal moves for " + clickedPiece + ":");
			//for (int[] move : this.legalMove) {
			 //   System.out.println("Row: " + move[0] + ", Col: " + move[1]);
			//}
		}



	}



}










/*public class Move extends MouseAdapter {

	private Board board;
	private int width, height;
	private ChessView chessView; // Reference to ChessView
	private boolean pieceIsSelected = false;
	List<int[]> legalMove = new ArrayList<>(); 
	List<Color> previousColors = new ArrayList<>();

	public Move(Board board, ChessView chessView ,int width, int height) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.chessView = chessView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickedRow = e.getY() / height;
		int clickedCol = e.getX() / width;

		String clickedPiece = this.board.board[clickedRow][clickedCol];

		if (clickedPiece != null && this.pieceIsSelected == false) {
			System.out.println("clicked piece is not null row" + clickedRow + " column " + clickedCol );

			showLegalMoves(clickedRow, clickedCol, clickedPiece); 

			Iterator<int[]> iterator = this.legalMove.iterator();

			while (iterator.hasNext()) {
				int[] square = iterator.next();
				int row = square[0];
				int col = square[1];      
				Color currentColor = this.chessView.squareColors[row][col]; // Get the current color before updating
				// Add the current color to the list
				previousColors.add(currentColor);

				chessView.updateSquareColor(col , row, Color.cyan); // we send it like that because that repaint take the first as x 					
				// and the x is the column 
				// Remove the element after processing it
			}
			this.pieceIsSelected = true ;

		}
		else  if (clickedPiece != null && this.pieceIsSelected == true){
			// Get the previous color from the list (if exists)
			if (!previousColors.isEmpty()) {
				Iterator<int[]> iterator = this.legalMove.iterator();
				while (iterator.hasNext()) {
					int[] square = iterator.next();
					int row = square[0];
					int col = square[1];    
					Color previousColor = previousColors.get(0);
					// Repaint the square back to the previous color
					chessView.updateSquareColor(col, row, previousColor);

					// Remove the last stored color
					previousColors.remove(0);
					iterator.remove();
				}
				
			}
			this.pieceIsSelected = false;
		}




	}

	private void showLegalMoves(int row , int col , String clickedPiece) {
		if (clickedPiece == "♟") {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row + 1 , col});
			if (row == 1) {
				this.legalMove.add(new int[]{row + 2 , col});
			}
		}
		if (clickedPiece == "♙") {
			this.legalMove.add(new int[]{row , col});
			this.legalMove.add(new int[]{row - 1 , col});
			if (row == 6) {
				this.legalMove.add(new int[]{row - 2 , col});
			}
		}
		if ("♜".equals(clickedPiece) || "♖".equals(clickedPiece)) {

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


		if ("♝".equals(clickedPiece) || "♗".equals(clickedPiece)) {
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


		if ("♞".equals(clickedPiece) || "♘".equals(clickedPiece)) {
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
		if ("♛".equals(clickedPiece) || "♕".equals(clickedPiece)) {
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



	}



}*/