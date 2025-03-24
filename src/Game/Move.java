package Game;
import java.awt.event.MouseAdapter;
import java.awt.Graphics;




import Game.ChessView.ChessBoard;
import Game.Piece.Direction;

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
	Direction direction;
	Team team = Team.WHITE;
	int teamTurn = 1;
	protected Piece selectedPiece = new Piece(direction);
	List<int[]> legalMove = new ArrayList<>(); 
	List<int[]> legalMoveForAllPieces = new ArrayList<>(); 
	
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
		if (clickedPiece != null) {
			System.out.println("this is the click " + clickedPiece.type);
		}
		else {
			System.out.println("this piece is null");
		}
		if (clickedPiece != null && this.pieceIsSelected == false && clickedPiece.team == team) {
			selectedPiece = board.board[clickedRow][clickedCol];
			selectedPiece.type = clickedPiece.type;
			selectedPiece.direction = clickedPiece.direction;

			/*System.out.println("clicked piece is not null row" + clickedRow + " column " + clickedCol );*/
			showLegalMoves(clickedRow, clickedCol, clickedPiece, this.legalMove); 

			while (legalMove.size() > 0) {
				int[] square = legalMove.get(0);
				legalMove.remove(0);
				int row = square[0];
				int col = square[1];
				chessView.updateSquareColor(col , row, Color.cyan); // we send it like that because that repaint take the first as x  and the x is the column														
			}
			this.pieceIsSelected = true ;
			this.legalMoveForAllPieces.clear();
		}
		else  if (clickedPiece != null && this.pieceIsSelected == true && clickedPiece.direction == selectedPiece.direction){
			chessView.isUpdaiting = false;
			Graphics g = chessBoard.getGraphics();
			chessBoard.paintComponent(g);
			this.pieceIsSelected = false;
		}

		else if (clickedPiece == null && this.pieceIsSelected == true) {
			chessView.isUpdaiting = false;
			movingPiece(clickedRow, clickedCol);	
			this.pieceIsSelected = false;		
		}
		else if(clickedPiece != null && this.pieceIsSelected == true && clickedPiece.direction != selectedPiece.direction && clickedPiece.team != team) {
			chessView.isUpdaiting = false;
			movingPiece(clickedRow, clickedCol);
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
			board.boardPrinter();
			Graphics g = chessBoard.getGraphics();
			chessBoard.paintComponent(g);
		}
		teamTurn += 1 ;
	}

	private void checkTheKing() {
		for (int x = 0; x < this.board.board.length; x++) {
			for (int y = 0; y < this.board.board[x].length; y++) {
				if (board.board[x][y] != null && board.board[x][y].direction != selectedPiece.direction) {
					showLegalMoves(x, y, board.board[x][y], legalMoveForAllPieces);
					//System.out.println("We are adding now legal move for " + board.board[x][y].type);
				}
			}
		}
		System.out.println("now we done");

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
				if (piece != null && selectedPiece.direction != piece.direction) {
					legalMove.add(new int[] {x, y});
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.direction == selectedPiece.direction) {
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
				if (piece != null && selectedPiece.direction != piece.direction) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.direction == selectedPiece.direction) {
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
				if (piece != null && selectedPiece.direction != piece.direction) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.direction == selectedPiece.direction) {
					break;
				}
				x += dx; // Move further in the same direction
				y += dy;

			}
		}
	}
	private void king(int row, int col, List<int[]> legalMove) {
	    if (board.board[row][col].type.equals("♚") && board.board[row][col].direction == selectedPiece.direction) {
	        checkTheKing();
	    }

	    legalMove.add(new int[] { row, col });

	    int[][] horizontal = { {0, 1}, {0, -1} };    // Right and Left  
	    int[][] vertical   = { {-1, 0}, {1, 0} };      // Up and Down  
	    int[][] diagonal1  = { {1, 1}, {-1, -1} };      // Down-Right & Up-Left  
	    int[][] diagonal2  = { {1, -1}, {-1, 1} };      // Down-Left & Up-Right  
	    int[][][] directions = { horizontal, vertical, diagonal1, diagonal2 };

	    for (int[][] group : directions) {
	        boolean groupBlocked = false;
	        boolean grouBisok = false;

	        // First pass: Check if any move in the group is blocked.
	        // Block the group only if the empty square is under attack.
	        for (int[] dir : group) {
	            int dx = dir[0];
	            int dy = dir[1];
	            int x = row + dx;
	            int y = col + dy;
	            if (x >= 0 && x < board.board.length && y >= 0 && y < board.board[0].length) {
	                Piece piece = board.board[x][y];
	                boolean underAttack = legalMoveForAllPieces.stream().anyMatch(arr -> Arrays.equals(arr, new int[]{x, y}));
	                // Only block the group if the square is empty and under attack.
	                if (piece == null && underAttack) {
	                    groupBlocked = true;
	                    break;
	                }
	                else if (piece != null && underAttack) {
	                	grouBisok = true;
	                	groupBlocked = true;
	                	break;
	                }
	            }
	        }

	        // If the group is blocked, skip processing all directions in the group.
	        if (groupBlocked && !grouBisok) {
	            continue;
	        }
	        if (grouBisok) {
	            for (int[] dir : group) {
		            int dx = dir[0];
		            int dy = dir[1];
		            int x = row + dx;
		            int y = col + dy;
		            if (x >= 0 && x < board.board.length && y >= 0 && y < board.board[0].length) {
		                Piece piece = board.board[x][y];
		                // If there's an enemy piece, add the move regardless of attack status.
		                if (piece != null && piece.direction != selectedPiece.direction) {
		                    legalMove.add(new int[] { x, y });
		                }		          
		            }
		        }
	        }
	        if (groupBlocked) {
	            continue;
	        }
	        // Second pass: Evaluate each direction in the group.
	        for (int[] dir : group) {
	            int dx = dir[0];
	            int dy = dir[1];
	            int x = row + dx;
	            int y = col + dy;
	            if (x >= 0 && x < board.board.length && y >= 0 && y < board.board[0].length) {
	                Piece piece = board.board[x][y];
	                // If there's an enemy piece, add the move regardless of attack status.
	                if (piece != null && piece.direction != selectedPiece.direction) {
	                    legalMove.add(new int[] { x, y });
	                }
	                // If the square is empty, add it only if it's not under attack.
	                else if (piece == null) {
	                    boolean underAttack = legalMoveForAllPieces
	                            .stream()
	                            .anyMatch(arr -> Arrays.equals(arr, new int[]{x, y}));
	                    if (!underAttack) {
	                        legalMove.add(new int[] { x, y });
	                    }
	                }
	                // Friendly piece: do nothing.
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
		legalMove.add(new int[] {row , col});
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int x = row + dx;
			int y = col + dy;

			// Loop until out of bounds or blocked
			while (x >= 0 && x < this.board.board.length && y >= 0 && y < this.board.board[0].length) {
				Piece piece = board.board[x][y];
				if (piece != null && selectedPiece.direction != piece.direction) {
					legalMove.add(new int[] {x, y});
					break;
				}
				if (piece == null) {
					legalMove.add(new int[] {x, y});
				}
				if (piece != null && piece.direction == selectedPiece.direction) {
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
}

