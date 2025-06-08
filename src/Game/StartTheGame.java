package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartTheGame extends JFrame {
    public StartTheGame() {
        setTitle("Chess Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Chess", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setOpaque(true); // Make the background visible
        titleLabel.setBackground(Color.white); // Set background color
        titleLabel.setForeground(Color.BLACK); // Set text color
        add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 18));
        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this screen
                new ChessView(); // Open Chess Game
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StartTheGame(); // Start the game
    }
}