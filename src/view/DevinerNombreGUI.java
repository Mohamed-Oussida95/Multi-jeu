import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DevinerNombreGUI extends JFrame {

    private int nombreSecret;
    private JTextField champ;
    private JLabel message;

    public DevinerNombreGUI() {
        setTitle("Deviner le nombre");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        genererNombre();


        champ = new JTextField();
        champ.setFont(new Font("Arial", Font.BOLD, 20));
        champ.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnValider = new JButton("Valider");
        JButton btnRejouer = new JButton("Rejouer");

        message = new JLabel("Devine un nombre entre 1 et 100");
        message.setHorizontalAlignment(SwingConstants.CENTER);

        
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.add(message);
        panel.add(champ);
        panel.add(btnValider);
        panel.add(btnRejouer);

        add(panel);

        
        btnValider.addActionListener(e -> verifier());
        btnRejouer.addActionListener(e -> rejouer());
    }

    private void genererNombre() {
        nombreSecret = (int)(Math.random() * 100) + 1;
    }

    private void verifier() {
        try {
            int nbUser = Integer.parseInt(champ.getText());

            if (nbUser > nombreSecret) {
                message.setText("Trop grand !");
            } else if (nbUser < nombreSecret) {
                message.setText("Trop petit !");
            } else {
                message.setText("Bravo, tu as trouvé !");
            }

        } catch (NumberFormatException ex) {
            message.setText("Entre un nombre valide !");
        }
    }

    private void rejouer() {
        genererNombre();
        champ.setText("");
        message.setText("Nouveau nombre généré !");
    }

    public static void main(String[] args) {
        new DevinerNombreGUI().setVisible(true);
    }
}
