package Game;


import Game.Piece.Direction;

public class Board {
	protected Piece[][] board = new Piece[8][8];  // 8x8 board to hold Piece objects
	
	
	public Board() {}
	
	public void boardPrinter() {
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					if (board[i][j] != null) {
					System.out.print(board[i][j].type + " ");
					}
					else {
						System.out.print("null");

					}
					
				}
				System.out.println();
			}
	}	
	
	
	public void addPices() {
		Direction downDirection = Direction.Down; 
		Direction uppDirection = Direction.UPP;
		for (int i=0; i<8; ++i) {
			Piece pWhite = new Piece(downDirection);
			pWhite.xPossition = i;
			pWhite.yPossition = 0;
			pWhite.pType(i);
			pWhite.team = Team.WHITE;
			board[pWhite.yPossition][pWhite.xPossition] = pWhite;	
			
		/*	Piece pPawnW = new Piece(downDirection);
			pPawnW.xPossition = i;
			pPawnW.yPossition = 1;
			pPawnW.type = "♟";
			pPawnW.team = Team.WHITE;
			board[pPawnW.yPossition][pPawnW.xPossition] = pPawnW;	*/

			
			Piece pBlack = new Piece(uppDirection);
			pBlack.xPossition = i;
			pBlack.yPossition = 7;
			pBlack.pType(i);
			pBlack.team = Team.BLACK;
			board[pBlack.yPossition][pBlack.xPossition] = pBlack;	
			
			
		/*	Piece pPawnB = new Piece(uppDirection);
			pPawnB.xPossition = i;
			pPawnB.yPossition = 6;
			pPawnB.type = "♟";
			pPawnB.team = Team.BLACK;
			board[pPawnB.yPossition][pPawnB.xPossition] = pPawnB;*/
		}
	    boardPrinter();
	}
	
	
}
