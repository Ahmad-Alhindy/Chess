package Game;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;


public class ChessView extends JFrame{    
	
	int Width = 0;
	int hight = 0;
	boolean listnerAdedd = false;
	protected Color[][] squareColors = new Color[8][8];
	
    public ChessView() {
    	
    	   for (int i = 0; i < 8; i++) {
               for (int j = 0; j < 8; j++) {
               	squareColors[i][j] = (i + j) % 2 == 0 ? Color.WHITE : Color.BLACK;
               }
           }
        setSize(400, 420);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure when the window is close to close the program
                
        add(new ChessBoard(this));
    
        setVisible(true);   
    }
    
    public void updateSquareColor(int row, int col, Color color) {
    	System.out.println("updateSquareColor Square at (" + row + ", " + col + ") updated to " + color);
        squareColors[row][col] = color; // Update the color
        repaint(row , col , this.Width, this.hight);	    	
    }	
    
    
    
	protected static class ChessBoard extends JPanel { //ChessBord will draw the board
	    private static final long serialVersionUID = 1L;
		Board board = new Board();
		ChessView chessView;
		
		
		ChessBoard(ChessView chessView) {
		    this.chessView = chessView;
		}
		
		
		
        @Override
		protected void paintComponent(Graphics g) { // it used as the paint method 
           
        	super.paintComponent(g); // call the paint method form JPanel to ensure every thing is draw
        	chessView.Width = getWidth() / 8; // calculate the width of one square
        	chessView.hight = getHeight() / 8;
            
        	if (chessView.listnerAdedd == false) {
            Move listener = new Move(board, chessView, chessView.Width, chessView.hight);
            addMouseListener(listener);
            chessView.listnerAdedd = true;
        	}
            

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	g.setColor(chessView.squareColors[i][j]);
                    g.fillRect(i * chessView.Width, j * chessView.hight, chessView.Width, chessView.hight);
                    repaint();
                }
            }
            
            
          board.addPices();
          
          for (Piece p : board.pieceList) { // this for loop to draw the pieces
        	  int xPos = p.xPossition * chessView.Width - chessView.Width + 3;
        	  int yPos = p.yPossition * chessView.hight - 5 ;
              String pieceSymbol = p.type;
        	  g.setFont(new Font("TimesRoman", Font.PLAIN, (int) Math.floor(chessView.Width * 0.8)));
              g.setColor(p.team == Team.WHITE ? Color.blue : Color.RED); // Different color for teams      
              g.drawString(pieceSymbol, xPos, yPos);  // Adjust for centering
             // System.out.println("Piece: " + p.type + " at (x" + xPos + ", y" + yPos + ")");
             // System.out.println("Piece: " + p.type + "xPossition " + p.xPossition + "p.yPossition " + p.yPossition);
            }
            
        }
        
              	
	}
}
