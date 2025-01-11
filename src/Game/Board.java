package Game;

import java.util.ArrayList;

public class Board {
	protected String[][] board = {
		    {"♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"},
		    {"♟", "♟", "♟", "♟", "♟", "♟", "♟", "♟"},
		    {null, null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null, null},
		    {null, null, null, null, null, null, null, null},
		    {"♙", "♙", "♙", "♙", "♙", "♙", "♙", "♙"},
		    {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"}
		};
	Piece pice; 
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	
	
	public Board() {}
	
	public void boardPrinter() {
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					System.out.print(i + "" + j + " ");
				}
				System.out.println();
			}
	}	
	
	
	public void addPices() {
		for (int i=1; i<9; ++i) {
			Piece pWhite = new Piece();
			pWhite.xPossition = i;
			pWhite.yPossition = 1;
			pWhite.pType(i);
			pWhite.team = Team.WHITE;
			pieceList.add(pWhite);	
			
			Piece pBlack = new Piece();
			pBlack.xPossition = i;
			pBlack.yPossition = 8;
			pBlack.pType(i);
			pBlack.team = Team.BLACK;
			pieceList.add(pBlack);
			
			Piece pPawnW = new Piece();
			pPawnW.xPossition = i;
			pPawnW.yPossition = 2;
			pPawnW.pType(0);
			pPawnW.team = Team.WHITE;
			pieceList.add(pPawnW);	
			
			Piece pPawnB = new Piece();
			pPawnB.xPossition = i;
			pPawnB.yPossition = 7;
			pPawnB.pType(0);
			pPawnB.team = Team.BLACK;
			pieceList.add(pPawnB);
		}
	}
	
	
}
