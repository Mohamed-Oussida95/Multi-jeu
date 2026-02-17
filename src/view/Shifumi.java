package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Shifumi {

    JFrame frame;
    private JLabel lblResult;
    private JLabel lblComputerChoice;
    private JLabel lblScore;

    private JButton btnPierre;
    private JButton btnPapier;
    private JButton btnCiseaux;

    private Random random = new Random();
    private String[] choices = {"Pierre", "Papier", "Ciseaux"};

    private int playerScore = 0;
    private int computerScore = 0;
    private final int MAX_SCORE = 5;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Shifumi window = new Shifumi(); 
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Shifumi() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Jeu Shifumi - Partie en 5 points");
        frame.setBounds(100, 100, 500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("SHIFUMI - 5 POINTS");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(80, 10, 320, 40);
        frame.getContentPane().add(lblTitle);

        lblScore = new JLabel("Score : Joueur 0 - 0 Ordinateur");
        lblScore.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setBounds(100, 60, 300, 30);
        frame.getContentPane().add(lblScore);

        btnPierre = new JButton("Pierre");
        btnPierre.setBounds(40, 120, 120, 40);
        frame.getContentPane().add(btnPierre);

        btnPapier = new JButton("Papier");
        btnPapier.setBounds(180, 120, 120, 40);
        frame.getContentPane().add(btnPapier);

        btnCiseaux = new JButton("Ciseaux");
        btnCiseaux.setBounds(320, 120, 120, 40);
        frame.getContentPane().add(btnCiseaux);

        lblComputerChoice = new JLabel("Choix ordinateur : ");
        lblComputerChoice.setBounds(120, 180, 300, 25);
        frame.getContentPane().add(lblComputerChoice);

        lblResult = new JLabel("Résultat : ");
        lblResult.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblResult.setBounds(120, 220, 300, 25);
        frame.getContentPane().add(lblResult);

        // Actions boutons
        btnPierre.addActionListener(e -> play(1));
        btnPapier.addActionListener(e -> play(2));
        btnCiseaux.addActionListener(e -> play(3));
    }

    private void play(int userChoice) {

        if (playerScore >= MAX_SCORE || computerScore >= MAX_SCORE) {
            return;
        }

        int computerChoice = random.nextInt(3) + 1;

        lblComputerChoice.setText("Choix ordinateur : " + choices[computerChoice - 1]);

        if (userChoice == computerChoice) {
            lblResult.setText("Résultat : Égalité !");
        } 
        else if ((userChoice == 1 && computerChoice == 3) ||
                 (userChoice == 2 && computerChoice == 1) ||
                 (userChoice == 3 && computerChoice == 2)) {

            playerScore++;
            lblResult.setText("Résultat : Vous avez gagné !");
        } 
        else {
            computerScore++;
            lblResult.setText("Résultat : L'ordinateur a gagné !");
        }

        updateScore();

        if (playerScore == MAX_SCORE || computerScore == MAX_SCORE) {
            endGame();
        }
    }

    private void updateScore() {
        lblScore.setText("Score : Joueur " + playerScore + 
                         " - " + computerScore + " Ordinateur");
    }

    private void endGame() {

        if (playerScore == MAX_SCORE) {
            lblResult.setText("Vous avez gagné la partie !");
        } else {
            lblResult.setText("L'ordinateur a gagné la partie !");
        }

        btnPierre.setEnabled(false);
        btnPapier.setEnabled(false);
        btnCiseaux.setEnabled(false);
    }
}
