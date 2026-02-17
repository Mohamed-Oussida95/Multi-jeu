package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class View_calculette extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ecran;  // L'écran
    private double valeur1 = 0, valeur2 = 0;
    private String operateur = "";
    private boolean nouvelleEntree = true;

    public View_calculette() {
        setTitle("Calculette");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 400));

        // Écran
        ecran = new JTextField("0");
        ecran.setFont(new Font("Arial", Font.BOLD, 30));
        ecran.setHorizontalAlignment(JTextField.RIGHT);
        ecran.setEditable(false);
        ecran.setPreferredSize(new Dimension(280, 80));
        add(ecran, BorderLayout.NORTH);

        // Panneau boutons
        JPanel boutons = new JPanel(new GridLayout(5, 4, 5, 5));
        boutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Boutons
        String[][] labels = {
            {"C", "±", "%", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"0", ".", "=", ""}
        };

        for (String[] ligne : labels) {
            for (String label : ligne) {
                JButton btn = new JButton(label);
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.setFocusPainted(false);
                boutons.add(btn);

                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        touchePressee(label);
                    }
                });
            }
        }

        add(boutons, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);  // Centre la fenêtre
    }

    private void touchePressee(String touche) {
        switch (touche) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
            case ".":
                if (nouvelleEntree) {
                    ecran.setText("");
                    nouvelleEntree = false;
                }
                ecran.setText(ecran.getText() + touche);
                break;

            case "+": case "-": case "×": case "÷":
                valeur1 = Double.parseDouble(ecran.getText());
                operateur = touche;
                nouvelleEntree = true;
                break;

            case "=":
                if (!operateur.isEmpty()) {
                    valeur2 = Double.parseDouble(ecran.getText());
                    double res = calculer(valeur1, valeur2, operateur);
                    ecran.setText(String.valueOf(res));
                    nouvelleEntree = true;
                    operateur = "";
                }
                break;

            case "C":
                ecran.setText("0");
                valeur1 = 0;
                valeur2 = 0;
                operateur = "";
                nouvelleEntree = true;
                break;

            case "±":
                double val = Double.parseDouble(ecran.getText());
                ecran.setText(String.valueOf(-val));
                break;

            case "%":
                double v = Double.parseDouble(ecran.getText());
                ecran.setText(String.valueOf(v / 100));
                break;
        }
    }

    private double calculer(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "×": return a * b;
            case "÷":
                if (b != 0) return a / b;
                else {
                    JOptionPane.showMessageDialog(this, "Division par zéro !");
                    return 0;
                }
            default: return 0;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new View_calculette().setVisible(true);
        });
    }
}
