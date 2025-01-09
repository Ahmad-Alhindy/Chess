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
	
	
    public ChessView() {
        setSize(400, 420);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure when the window is close to close the program
                
        add(new ChessBoard());
    
        setVisible(true);

    }
    
	private static class ChessBoard extends JPanel { //ChessBord will draw the board
		Board board = new Board();
        @Override
		protected void paintComponent(Graphics g) { // it used as the paint method 
           
        	super.paintComponent(g); // call the paint method form JPanel to ensure every thing is draw

           
            int Width = getWidth() / 8; // calculate the width of one square
            int hight = getHeight() / 8;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 == 0) {
                        g.setColor(Color.WHITE);	// Even squares are white
                    }
                    else {
                        g.setColor(Color.BLACK);	// Odd squares are black
                    }
                    
                    g.fillRect(i * Width, j * hight, Width, hight);
                }
            }
          board.addPices();
          for (Piece p : board.pieceList) {
        	  int xPos = p.xPossition * Width - Width + 3;
        	  int yPos = p.yPossition * hight - 5 ;
              String pieceSymbol = p.type;
        	  g.setFont(new Font("TimesRoman", Font.PLAIN, (int) Math.floor(Width * 0.8)));
              g.setColor(p.team == Team.WHITE ? Color.blue : Color.RED); // Different color for teams      
              g.drawString(pieceSymbol, xPos, yPos);  // Adjust for centering
              System.out.println("Piece: " + p.type + " at (x" + xPos + ", y" + yPos + ")");
              System.out.println("Piece: " + p.type + "xPossition " + p.xPossition + "p.yPossition " + p.yPossition);
            }
            
        }
              	
	}
}
