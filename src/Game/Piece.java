package Game;

public class Piece {
	protected Team team;
	int xPossition; 
	int yPossition;
	PieceType piceType = new PieceType();
	String type = "";
	
	protected String pType (int type) {
		if (type >= 0 && type <= 9) {
			this.type = this.piceType.PIECES[type];	
		}
		return this.type;
	}
	
	public Piece() {}
}
