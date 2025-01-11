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
        if (clickedPiece != null)
        	
        System.out.println("rowwwww" + clickedRow + " column " + clickedCol );
        showLegalMoves(clickedRow, clickedCol, clickedPiece);
        Iterator<int[]> iterator = this.legalMove.iterator();

        while (iterator.hasNext()) {
            int[] square = iterator.next();
            int row = square[0];
            int col = square[1];            
            // Remove the element after processing it
            chessView.updateSquareColor(row , col, Color.cyan); // we send it like that because that repaint take the first as x 					
            													// and the x is the column 
            iterator.remove();
        }
    }
    
    private void showLegalMoves(int row , int col , String clickedPiece) {
    	if (clickedPiece == "♟") {
    		this.legalMove.add(new int[]{row + 2 , col});

    	}
    }
    
    

}
