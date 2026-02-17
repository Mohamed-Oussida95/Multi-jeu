package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.io.IOException;

public class Vue_accueil {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Vue_accueil window = new Vue_accueil();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Vue_accueil() {
        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 790, 519);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("PLATEFORME MULTIJEUX");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel.setBounds(210, 26, 379, 93);
        frame.getContentPane().add(lblNewLabel);

        // 🔥 Récupère classpath Eclipse automatiquement
        String cp = System.getProperty("java.class.path");

        // ================= CALCULETTE =================
        JButton btnCalculette = new JButton("calculette");
        btnCalculette.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCalculette.setBounds(95, 159, 203, 93);

        btnCalculette.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", cp, "view.View_calculette");
                pb.inheritIO();
                pb.start();
                System.out.println("🧮 Calculette lancée !");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.getContentPane().add(btnCalculette);

        // ================= DEVINER =================
        JButton btnDeviner = new JButton("deviner le nombre");
        btnDeviner.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeviner.setBounds(491, 159, 203, 93);

        btnDeviner.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", cp, "view.DevinerNombre");
                pb.inheritIO();
                pb.start();
                System.out.println("🎯 Deviner lancée !");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.getContentPane().add(btnDeviner);

        // ================= BATAILLE =================
        JButton btnBataille = new JButton("bataille navale");
        btnBataille.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBataille.setBounds(491, 319, 203, 93);

        btnBataille.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", cp, "view.bataille_navale");
                pb.inheritIO();
                pb.start();
                System.out.println("⚓ Bataille navale lancée !");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.getContentPane().add(btnBataille);

        // ================= SHIFUMI =================
        JButton btnShifumi = new JButton("shifumi");
        btnShifumi.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnShifumi.setBounds(95, 319, 203, 93);

        btnShifumi.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", cp, "view.Shifumi");
                pb.inheritIO();
                pb.start();
                System.out.println("✂️ Shifumi lancée !");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.getContentPane().add(btnShifumi);
    }
}
