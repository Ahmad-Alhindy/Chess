package Game;

import java.util.ArrayList;

public class Board {
	int[][] board = new int [8][8];
	Piece pice; 
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	
	
	public Board() {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				board[i][j] = j;
			}
		}
	}
	
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
			Piece p = new Piece();
			p.xPossition = i;
			p.yPossition = 1;
			p.pType(i);
			p.team = Team.WHITE;
			pieceList.add(p);	
		}
		for (int i=1; i<9; ++i) {
			Piece p = new Piece();
			p.xPossition = i;
			p.yPossition = 8;
			p.pType(i);
			p.team = Team.BLACK;
			pieceList.add(p);	
		}
		for (int i=1; i<9; ++i) {
			Piece p = new Piece();
			p.xPossition = i;
			p.yPossition = 2;
			p.type = p.Pawn;
			p.team = Team.WHITE;
			pieceList.add(p);	
		}
		for (int i=1; i<9; ++i) {
			Piece p = new Piece();
			p.xPossition = i;
			p.yPossition = 7;
			p.type = p.Pawn;
			p.team = Team.BLACK;
			pieceList.add(p);	
		}
	}
}
