package Game;

public enum Team {
	WHITE {
		@Override public String toString() { return "Blue"; }
	},
	BLACK {
		@Override public String toString() { return "Red"; }
	}
}
