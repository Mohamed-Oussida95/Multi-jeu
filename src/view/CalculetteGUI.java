import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculetteGUI extends JFrame {

    private JTextField ecran;
    private double premierNombre = 0;
    private String operateur = "";
    private boolean nouveauCalcul = false;

    public CalculetteGUI() {
        setTitle("Calculette");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ecran = new JTextField();
        ecran.setEditable(false);
        ecran.setFont(new Font("Arial", Font.BOLD, 24));
        ecran.setHorizontalAlignment(SwingConstants.RIGHT);
        add(ecran, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] touches = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String t : touches) {
            JButton btn = new JButton(t);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(new BoutonListener());
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    private class BoutonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String touche = e.getActionCommand();

            if (touche.matches("[0-9]")) {
                if (nouveauCalcul) {
                    ecran.setText("");
                    nouveauCalcul = false;
                }
                ecran.setText(ecran.getText() + touche);
            }
            else if (touche.matches("[+\\-*/]")) {
                premierNombre = Double.parseDouble(ecran.getText());
                operateur = touche;
                ecran.setText("");
            }
            else if (touche.equals("=")) {
                double secondNombre = Double.parseDouble(ecran.getText());
                double resultat = 0;

                switch (operateur) {
                    case "+": resultat = premierNombre + secondNombre; break;
                    case "-": resultat = premierNombre - secondNombre; break;
                    case "*": resultat = premierNombre * secondNombre; break;
                    case "/":
                        if (secondNombre == 0) {
                            ecran.setText("Erreur");
                            return;
                        }
                        resultat = premierNombre / secondNombre;
                        break;
                }

                ecran.setText("" + resultat);
                nouveauCalcul = true;
            }
            else if (touche.equals("C")) {
                ecran.setText("");
                premierNombre = 0;
                operateur = "";
            }
        }
    }

    public static void main(String[] args) {
        new CalculetteGUI().setVisible(true);
    }
}
