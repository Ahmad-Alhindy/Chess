package Game;

/*import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rollengine {
	private Board board;

	List<int[]> pawnMoves = new ArrayList<>();
	List<int[]> rookMoves = new ArrayList<>();
	List<int[]> bishopMoves = new ArrayList<>();
	List<int[]> knightMoves = new ArrayList<>();
	List<int[]> queenMoves = new ArrayList<>();
	List<int[]> kingMoves = new ArrayList<>();


	public Rollengine() {


	}

	boolean checkingGameState(Board board, Piece selectedPiece) {

		for (int x = 0; x < board.board.length; x++) {
			for (int y = 0; y < board.board[x].length; y++) {
				Piece piece = board.board[x][y];

				if (piece != null && piece.team != selectedPiece.team) {
					switch (piece.type) {
					case "♟":
						showLegalMoves(x, y, piece, pawnMoves);
						break;
					case "♜":
						showLegalMoves(x, y, piece, rookMoves);
						break;
					case "♝":
						showLegalMoves(x, y, piece, bishopMoves);
						break;
					case "♞":
						showLegalMoves(x, y, piece, knightMoves);
						break;
					case "♛":
						showLegalMoves(x, y, piece, queenMoves);
						break;
					case "♚":
						showLegalMoves(x, y, piece, kingMoves);
						break;
					}
				}
			}
		}
	}
	
	private void knight (int row, int col, List<int[]> legalMove) {
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
		legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;
			if (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length) {
				Piece piece = board.board[x][y];
				if (piece != null && selectedPiece.team != piece.team) {
					legalMove.add(new int[] {x, y});
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.team == selectedPiece.team) {
					continue;
				}
			}

		}
	}
	private void rock( int row, int col, List<int[]> legalMove) {
		int[][] directions = {
				{0, 1},  // Right
				{0, -1}, // Left
				{1, 0},  // Down
				{-1, 0}  // Up
		};
		legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;
			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length) {
				Piece piece = board.board[x][y];
				if (piece != null && selectedPiece.team != piece.team) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.team == selectedPiece.team) {
					break;
				}
				x += dx; // Move further in the same direction
				y += dy;	
			}
		}
	}
	private void bishop(int row, int col, List<int[]> legalMove) {
		int[][] directions = {
				{1, 1},  
				{-1, -1}, 
				{1, -1},  
				{-1, 1}  
		};
		legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;
			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length) {
				Piece piece = board.board[x][y];
				if (piece != null && selectedPiece.team != piece.team) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.team == selectedPiece.team) {
					break;
				}
				x += dx; // Move further in the same direction
				y += dy;

			}
		}
	}




	private boolean isUnderAttack(int x, int y, List<List<int[]>> attackLists) {
	    for (List<int[]> attackList : attackLists) {
	        for (int[] move : attackList) {
	            if (move[0] == x && move[1] == y) {
	                return true;
	            }
	        }
	    }
	    return false;
	}



	private void king(int row, int col, List<int[]> legalMove) {
		if (board.board[row][col].type == "♚" && board.board[row][col].team == selectedPiece.team) {
			checkTheKing();
		}
		int[][] directions = {
				{0, 1}, {0, -1}, // Right, Left
				{-1, 0}, {1, 0}, // Up, Down
				{1, 1}, {-1, -1}, // Diagonal Down-Right, Up-Left
				{1, -1}, {-1, 1}  // Diagonal Down-Left, Up-Right
		};

		for (int[] dir : directions) {
			int x = row + dir[0];
			int y = col + dir[1];

			if (x >= 0 && x < board.board.length && y >= 0 && y < board.board[0].length) {
				Piece piece = board.board[x][y];
				List<List<int[]>> allMoves = Arrays.asList(pawnMoves, rookMoves, bishopMoves, knightMoves, queenMoves, kingMoves);
				underAttack = isUnderAttack(x, y, allMoves);
				// Now validate the move
				if (!underAttack) {
					if (piece == null || piece.team != selectedPiece.team) { 
						// Allow move if it's an empty square or capturing an opponent piece
						legalMove.add(new int[]{x, y});
					}
				
				}
			}
		}
	}


	






	private void queen(int row, int col, List<int[]> legalMove) {
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
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;

			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length) {
				//board.boardPrinter();
				Piece piece = board.board[x][y];
				if (piece != null && selectedPiece.team != piece.team) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.team == selectedPiece.team) {
					break;
				}
				x += dx; // Move further in the same direction
				y += dy;
			}
		}
	}
	private void pawn(int row, int col, List<int[]> legalMove) {
		legalMove.add(new int[] {row , col});
		int direction = (selectedPiece.team == Team.WHITE) ? 1 : -1;
		if (board.board[row + direction][col] == null) {
			legalMove.add(new int[]{row + direction, col});

			// Double move on first move
			int startRow = (selectedPiece.team == Team.WHITE) ? 1 : board.board.length - 2;
			if (row == startRow && board.board[row + 2 * direction][col] == null) {
				legalMove.add(new int[]{row + 2 * direction, col});
			}
		}

		// Capture moves (diagonal)
		for (int d : new int[]{-1, 1}) {
			int x = row + direction;
			int y = col + d;
			if (x >= 0 && x < board.board.length && y >= 0 && y < board.board[0].length) {
				Piece piece = board.board[x][y];
				if (piece != null && piece.team != selectedPiece.team) {
					legalMove.add(new int[]{x, y});
				}
			}
		}
	}



	
	private void showLegalMoves(int row , int col , Piece clickedPiece , List<int[]> legalMove) {
		if (clickedPiece == null ) {
			return;
		}
		if (clickedPiece.type == "♟") {
			pawn(row, col, legalMove);
		}


		if (clickedPiece.type == "♜") {
			rock(row, col, legalMove);

		}
		if ("♝".equals(clickedPiece.type)) {
			bishop(row, col, legalMove);
		}
		if ("♞".equals(clickedPiece.type)) {
			knight(row, col, legalMove);
		}
		if ("♛".equals(clickedPiece.type)) {
			queen(row, col, legalMove);
		}

		if ("♚".equals(clickedPiece.type)) {
			king(row, col, legalMove);
		}
		//System.out.println("Legal moves for " + clickedPiece + ":");
		//for (int[] move : this.legalMove) {
		//   System.out.println("Row: " + move[0] + ", Col: " + move[1]);
		//}
	}

}*/
