package Game;

public class Piece {
	protected Team team;
	int xPossition; 
	int yPossition;
	protected static final String[] PIECES = {
		    "♟",  // Black Pawn
		    "♜", // Black Rook
		    "♞", // Black Knight
		    "♝", // Black Bishop
		    "♛", // Black Queen
		    "♚", // Black King
		    "♝", // Black Bishop
		    "♞", // Black Knight
		    "♜", // Black Rook
		};
	String type = "";
	String Pawn = "♟";
	
	protected String pType (int type) {
		if (type >= 0 && type <= 9) {
			this.type = this.PIECES[type];	
		}
		return this.type;
	}
	
	public Piece() {}
}
