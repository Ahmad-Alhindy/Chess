package Game;

public class Piece {
	protected Team team;
	int xPossition; 
	int yPossition;
	protected static final String[] PIECES = {
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
	Direction direction;
	
	public enum Direction {
	    UPP,
	    Down
	}

	
	protected String pType (int type) {
		if (type >= 0 && type < 10) {
			this.type = this.PIECES[type];	
		}
		return this.type;
	}
	
	public Piece(Direction direc) {
		this.direction = direc;
	}
}
