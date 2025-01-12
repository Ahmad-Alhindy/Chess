package Game;
import java.awt.event.MouseAdapter;
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
					Color previousColor = previousColors.get(previousColors.size() - 1);
					// Repaint the square back to the previous color
					chessView.updateSquareColor(col, row, previousColor);

					// Remove the last stored color
					previousColors.remove(previousColors.size() - 1);
					iterator.remove();
				}
			}
			this.pieceIsSelected = false;
		}

		
		
		
	}

	private void showLegalMoves(int row , int col , String clickedPiece) {
		if (clickedPiece == "♟") {
			this.legalMove.add(new int[]{row + 1 , col});

		}
	}



}
