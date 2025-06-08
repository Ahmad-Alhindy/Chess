package Game;
import java.awt.event.MouseAdapter;
import java.awt.Graphics;
import Game.ChessView.ChessBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Move extends MouseAdapter {

	private Board board;
	private int width, height;
	private ChessView chessView; // Reference to ChessView
	private ChessBoard chessBoard;
	private boolean pieceIsSelected = false;
	Team team = Team.WHITE;
	private boolean underAttack  = false;
	private boolean checkingTheKing = false;
	int teamTurn = 1;
	protected Piece selectedPiece = new Piece();
	List<int[]> legalMove = new ArrayList<>(); 

	List<int[]> pawnMoves = new ArrayList<>();
	List<int[]> rookMoves = new ArrayList<>();
	List<int[]> bishopMoves = new ArrayList<>();
	List<int[]> knightMoves = new ArrayList<>();
	List<int[]> queenMoves = new ArrayList<>();
	List<int[]> kingMoves = new ArrayList<>();

	public Move(Board board, ChessView chessView ,int width, int height, ChessBoard chessBoard) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.chessView = chessView;
		this.chessBoard = chessBoard;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		team = (teamTurn % 2 == 0) ? Team.WHITE : Team.BLACK;
		int clickedRow = e.getY() / height;
		int clickedCol = e.getX() / width;
		Piece clickedPiece = this.board.board[clickedRow][clickedCol]; 
		Team t1 = Team.BLACK;
		if (clickedPiece != null) {
			System.out.println("this is the click " + clickedPiece.type);		
			Team t = board.board[clickedRow][clickedCol].team;
			t = (t == Team.WHITE) ? Team.BLACK : Team.WHITE;
			t1 = t;
		}
		else {
			System.out.println("this piece is null");
		}
		boolean kingInCheck = checkkingStatuse();
		int[] kingPos = getKingPosition(team);
		Piece p = new Piece();
		p.type = "♚";
		List<int[]> filteredMoves = new ArrayList<>(kingMoves);
		filteredMoves.removeIf(move -> !moveSavesKing(move[0], move[1]));
		kingMoves.clear();
		kingMoves.addAll(filteredMoves);
		boolean kingCanMove = !kingMoves.isEmpty();

		if (!kingCanMove) {
			chessView.showWinner(t1.toString());
		}
		if (clickedPiece != null && this.pieceIsSelected == false && clickedPiece.team == team) {
			selectedPiece = clickedPiece;
			/*System.out.println("clicked piece is not null row" + clickedRow + " column " + clickedCol );*/
			showLegalMoves(clickedRow, clickedCol, clickedPiece, this.legalMove); 
			
			legalMove.removeIf(move -> !moveSavesKing(move[0], move[1]));
			

			for (int[] square : legalMove) {
				chessView.updateSquareColor(square[1], square[0], Color.cyan);
			}

			legalMove.clear();
			this.pieceIsSelected = true ;
			//this.legalMoveForAllPieces.clear();
		}
		else  if (clickedPiece != null && this.pieceIsSelected == true && clickedPiece.team == selectedPiece.team){
			chessView.isUpdaiting = false;
			Graphics g = chessBoard.getGraphics();
			chessBoard.paintComponent(g);
			this.pieceIsSelected = false;
		}

		else if (clickedPiece == null && this.pieceIsSelected) {

			if (!kingInCheck || moveSavesKing(clickedRow, clickedCol)) {
				chessView.isUpdaiting = false;
				movingPiece(clickedRow, clickedCol);
			}
			pieceIsSelected = false;
		}
		else if(clickedPiece != null && this.pieceIsSelected && clickedPiece.team != selectedPiece.team && clickedPiece.team != team) {

			if (!kingInCheck || moveSavesKing(clickedRow, clickedCol)) {
				chessView.isUpdaiting = false;
				movingPiece(clickedRow, clickedCol);
			}
			this.pieceIsSelected = false;
		}
	}


	private void movingPiece(int row, int col) {
		Color color = chessView.squareColors[col][row];
		if (color == Color.CYAN) {
			board.board[row][col] = selectedPiece;
			board.board[selectedPiece.yPossition][selectedPiece.xPossition] = null;
			selectedPiece.yPossition = row;
			selectedPiece.xPossition = col;
			//board.boardPrinter();
			Graphics g = chessBoard.getGraphics();
			chessBoard.paintComponent(g);
			this.pieceIsSelected = false;		
		}
		else {
			return;
		}
		teamTurn += 1 ;
	}

	private void checkTheKing() {
		pawnMoves.clear();
		pawnMoves.clear();
		rookMoves.clear();
		bishopMoves.clear();
		knightMoves.clear();
		queenMoves.clear();
		kingMoves.clear();
		for (int x = 0; x < this.board.board.length; x++) {
			for (int y = 0; y < this.board.board[x].length; y++) {
				Piece piece = board.board[x][y];
				//board.boardPrinter();
				if (piece != null && piece.team != selectedPiece.team) {
					Piece temp = selectedPiece; // Save current selectedPiece
					selectedPiece = piece; // Temporarily set selectedPiece
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
						this.checkingTheKing = true;
						showLegalMoves(x, y, piece, kingMoves);
						break;
					}
					selectedPiece = temp; // Restore selectedPiece
				}
			}
		}
		//System.out.println("now we done");

	}




	private void king1(int row, int col, List<int[]> legalMove) {

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
				//List<List<int[]>> allMoves = Arrays.asList(pawnMoves, rookMoves, bishopMoves, knightMoves, queenMoves, kingMoves);
				//underAttack = isUnderAttack(x, y, allMoves);
				// Now validate the move

				if (piece == null || piece.team != selectedPiece.team) { 
					// Allow move if it's an empty square or capturing an opponent piece
					legalMove.add(new int[]{x, y});	
				}
			}
		}
	}


	private void king(int row, int col, List<int[]> legalMove) {
		if (board.board[row][col].type.equals("♚") && board.board[row][col].team == selectedPiece.team) {
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
				Piece originalPiece = board.board[x][y]; // Save original piece (could be null)
				int oldX = selectedPiece.xPossition;
				int oldY = selectedPiece.yPossition;

				// Simulate move
				board.board[x][y] = selectedPiece;
				board.board[oldY][oldX] = null;
				selectedPiece.xPossition = x;
				selectedPiece.yPossition = y;

				checkTheKing();
				List<List<int[]>> allMoves = Arrays.asList(pawnMoves, rookMoves, bishopMoves, knightMoves, queenMoves, kingMoves);
				underAttack = isUnderAttack(x, y, allMoves);
				// Restore board state
				board.board[oldY][oldX] = selectedPiece;
				board.board[x][y] = originalPiece;
				selectedPiece.xPossition = oldX;
				selectedPiece.yPossition = oldY;
				
				if (!underAttack) { 
					// Add move only if it's safe
					if (originalPiece == null || originalPiece.team != selectedPiece.team) {
						legalMove.add(new int[]{x, y});
						
					}
				}
				
			}
		}
		Team t = board.board[row][col].team;
		t = (t == Team.WHITE) ? Team.BLACK : Team.WHITE;
		if (legalMove.isEmpty()) {
			chessView.showWinner(t.toString());
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

	
	
	// check if the move save the king if not will be deleted from the place this function is called 
	private boolean moveSavesKing(int row, int col) {
		Piece originalPiece = board.board[row][col];
		int oldX = selectedPiece.xPossition;
		int oldY = selectedPiece.yPossition;

		board.board[row][col] = selectedPiece;
		board.board[oldY][oldX] = null;
		selectedPiece.xPossition = row;
		selectedPiece.yPossition = col;

		boolean stillInCheck = checkkingStatuse();

		board.board[oldY][oldX] = selectedPiece;
		board.board[row][col] = originalPiece;
		selectedPiece.xPossition = oldX;
		selectedPiece.yPossition = oldY;

		return !stillInCheck;
	}

	// get the king position 
	private int[] getKingPosition(Team team) {
		for (int x = 0; x < board.board.length; x++) {
			for (int y = 0; y < board.board[x].length; y++) {
				Piece piece = board.board[x][y];
				if (piece != null && piece.type.equals("♚") && piece.team == team) {
					return new int[]{x, y};
				}
			}
		}
		return null; // Should never happen unless king is missing
	}
	
	
	//this function will be called when any other piece than the king is clicked 
	public boolean checkkingStatuse() {
		int[] kingPos = getKingPosition(team); 
		if (kingPos == null) return false; // Safety check

		int kingX = kingPos[0];
		int kingY = kingPos[1];
		checkTheKing();
		List<List<int[]>> allMoves = Arrays.asList(pawnMoves, rookMoves, bishopMoves, knightMoves, queenMoves, kingMoves);

		return isUnderAttack(kingX, kingY, allMoves);
	}

	
	
	
	//this check if the king is under attacked
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
					//System.out.println("we adding " + x + " and "+ y);
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
					//System.out.println("we adding " + x + " and "+ y);

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
			if (!checkingTheKing) {
				king(row, col, legalMove);
			}
			else {
				king1(row, col, legalMove);
				checkingTheKing = false;
			}
		}
		//System.out.println("Legal moves for " + clickedPiece + ":");
		//for (int[] move : this.legalMove) {
		//   System.out.println("Row: " + move[0] + ", Col: " + move[1]);
		//}
	}	
}

