package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class bataille_navale extends JFrame {

    // Etats des cases (TD)
    static final int VIDE = 0;       // ? (non découvert)
    static final int PION_CACHE = 1; // ? (non découvert)
    static final int PION_TOUCHE = 2;// o
    static final int RATE = 3;       // x

    private static final int NB_CASE = 5;
    private static final int NB_PIONS = 5;

    private int[][] tabJoueur = new int[NB_CASE][NB_CASE];
    private int[][] tabOrdi = new int[NB_CASE][NB_CASE];

    private int nbPionTrouveJoueur = 0;
    private int nbPionTrouveOrdi = 0;

    private JButton[][] btnJoueur = new JButton[NB_CASE][NB_CASE];
    private JButton[][] btnOrdi = new JButton[NB_CASE][NB_CASE];

    private JLabel lblInfo;
    private JButton btnNewGame;
    private JButton btnPlacementTermine;

    private boolean placementPhase = true; // d'abord placement joueur
    private int pionsPlaces = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            bataille_navale frame = new bataille_navale();
            frame.setVisible(true);
        });
    }

    public bataille_navale() {
        setTitle("Bataille Navale (5x5) - WindowBuilder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 520);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout(12, 12));
        setContentPane(contentPane);

        // Haut : infos + boutons
        JPanel top = new JPanel(new BorderLayout(12, 12));
        lblInfo = new JLabel("Phase placement : cliquez sur VOTRE grille pour placer 5 pions.");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        top.add(lblInfo, BorderLayout.CENTER);

        JPanel topBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNewGame = new JButton("Nouvelle partie");
        btnPlacementTermine = new JButton("Terminer placement");
        btnPlacementTermine.setEnabled(false);

        topBtns.add(btnPlacementTermine);
        topBtns.add(btnNewGame);
        top.add(topBtns, BorderLayout.EAST);

        contentPane.add(top, BorderLayout.NORTH);

        // Centre : 2 grilles
        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        contentPane.add(center, BorderLayout.CENTER);

        center.add(creerPanelGrilleJoueur());
        center.add(creerPanelGrilleOrdi());

        // events boutons
        btnNewGame.addActionListener(e -> resetGame());
        btnPlacementTermine.addActionListener(e -> terminerPlacement());

        // init
        resetGame();
    }

    private JPanel creerPanelGrilleJoueur() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        JLabel title = new JLabel("Votre plateau", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(NB_CASE, NB_CASE, 4, 4));
        for (int i = 0; i < NB_CASE; i++) {
            for (int j = 0; j < NB_CASE; j++) {
                JButton b = new JButton();
                b.setFont(new Font("Arial", Font.BOLD, 16));
                b.setFocusPainted(false);

                final int r = i, c = j;
                b.addActionListener(e -> clicJoueur(r, c));
                btnJoueur[i][j] = b;
                grid.add(b);
            }
        }
        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerPanelGrilleOrdi() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        JLabel title = new JLabel("Plateau ordinateur (cliquez pour tirer)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(NB_CASE, NB_CASE, 4, 4));
        for (int i = 0; i < NB_CASE; i++) {
            for (int j = 0; j < NB_CASE; j++) {
                JButton b = new JButton();
                b.setFont(new Font("Arial", Font.BOLD, 16));
                b.setFocusPainted(false);

                final int r = i, c = j;
                b.addActionListener(e -> clicOrdi(r, c));
                btnOrdi[i][j] = b;
                grid.add(b);
            }
        }
        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------
    // LOGIQUE
    // ---------------------------

    private void resetGame() {
        initTab(tabJoueur);
        initTab(tabOrdi);

        nbPionTrouveJoueur = 0;
        nbPionTrouveOrdi = 0;

        placementPhase = true;
        pionsPlaces = 0;

        // placement ordi immédiat (caché)
        placerPionsOrdi();

        btnPlacementTermine.setEnabled(false);
        lblInfo.setText("Phase placement : cliquez sur VOTRE grille pour placer 5 pions.");

        refreshUI();
    }

    private void initTab(int[][] tab) {
        for (int i = 0; i < NB_CASE; i++)
            for (int j = 0; j < NB_CASE; j++)
                tab[i][j] = VIDE;
    }

    private void placerPionsOrdi() {
        int places = 0;
        while (places < NB_PIONS) {
            int i = (int) (Math.random() * NB_CASE);
            int j = (int) (Math.random() * NB_CASE);
            if (tabOrdi[i][j] != PION_CACHE) {
                tabOrdi[i][j] = PION_CACHE;
                places++;
            }
        }
    }

    private void clicJoueur(int i, int j) {
        if (!placementPhase) return;

        if (tabJoueur[i][j] == PION_CACHE) {
            lblInfo.setText("Case déjà occupée. Choisissez une autre case.");
            return;
        }

        tabJoueur[i][j] = PION_CACHE;
        pionsPlaces++;

        if (pionsPlaces >= NB_PIONS) {
            btnPlacementTermine.setEnabled(true);
            lblInfo.setText("Vous avez placé 5 pions. Cliquez sur 'Terminer placement'.");
        } else {
            lblInfo.setText("Pion placé (" + pionsPlaces + "/" + NB_PIONS + ").");
        }

        refreshUI();
    }

    private void terminerPlacement() {
        if (pionsPlaces < NB_PIONS) return;

        placementPhase = false;
        btnPlacementTermine.setEnabled(false);
        lblInfo.setText("Jeu lancé ! Cliquez sur le plateau ordinateur pour tirer.");
        refreshUI();
    }

    private void clicOrdi(int i, int j) {
        if (placementPhase) {
            lblInfo.setText("Placez d'abord vos 5 pions sur votre plateau.");
            return;
        }

        // Tir joueur sur tabOrdi
        int val = tabOrdi[i][j];
        if (val == VIDE) {
            tabOrdi[i][j] = RATE;
            lblInfo.setText("Raté !");
        } else if (val == PION_CACHE) {
            tabOrdi[i][j] = PION_TOUCHE;
            nbPionTrouveJoueur++;
            lblInfo.setText("Touché ! (" + nbPionTrouveJoueur + "/" + NB_PIONS + ")");
        } else {
            lblInfo.setText("Tir à blanc ! (case déjà découverte)");
            refreshUI();
            return;
        }

        refreshUI();

        // Vérif victoire joueur
        if (nbPionTrouveJoueur >= NB_PIONS) {
            finPartie(true);
            return;
        }

        // Tour ordinateur après un petit délai visuel
        Timer t = new Timer(700, e -> {
            ((Timer) e.getSource()).stop();
            tourOrdi();
        });
        t.setRepeats(false);
        t.start();
    }

    private void tourOrdi() {
        // Choisir une case non découverte (0 ou 1)
        int i, j;
        while (true) {
            i = (int) (Math.random() * NB_CASE);
            j = (int) (Math.random() * NB_CASE);
            if (tabJoueur[i][j] == VIDE || tabJoueur[i][j] == PION_CACHE) break;
        }

        if (tabJoueur[i][j] == VIDE) {
            tabJoueur[i][j] = RATE;
            lblInfo.setText("Ordinateur : Raté ! (tir " + (i+1) + "," + (j+1) + ")");
        } else {
            tabJoueur[i][j] = PION_TOUCHE;
            nbPionTrouveOrdi++;
            lblInfo.setText("Ordinateur : Touché ! (" + nbPionTrouveOrdi + "/" + NB_PIONS + ") sur " + (i+1) + "," + (j+1));
        }

        refreshUI();

        // Vérif victoire ordi
        if (nbPionTrouveOrdi >= NB_PIONS) {
            finPartie(false);
        }
    }

    private void finPartie(boolean joueurGagne) {
        String msg = joueurGagne ? "Vous avez gagné !" : "L'ordinateur a gagné !";
        lblInfo.setText(msg);
        JOptionPane.showMessageDialog(this, msg, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------------------
    // AFFICHAGE (cache / visible)
    // ---------------------------
    private void refreshUI() {
        // plateau joueur : on peut montrer les pions pendant placement, puis en mode "cache" après
        for (int i = 0; i < NB_CASE; i++) {
            for (int j = 0; j < NB_CASE; j++) {
                JButton b = btnJoueur[i][j];
                if (placementPhase) {
                    // visible pendant placement : PION_CACHE -> o, sinon ~
                    setButtonStyleVisible(b, tabJoueur[i][j]);
                } else {
                    // après : affichage caché (0/1 -> ?, 2 -> o, 3 -> x)
                    setButtonStyleCache(b, tabJoueur[i][j]);
                }
            }
        }

        // plateau ordi : toujours caché (sauf cases découvertes)
        for (int i = 0; i < NB_CASE; i++) {
            for (int j = 0; j < NB_CASE; j++) {
                JButton b = btnOrdi[i][j];
                setButtonStyleCache(b, tabOrdi[i][j]);
            }
        }
    }

    // 0 -> ~ ; 1/2 -> o ; 3 -> x
    private void setButtonStyleVisible(JButton b, int val) {
        if (val == PION_CACHE || val == PION_TOUCHE) {
            b.setText("o");
            b.setBackground(new Color(230, 255, 230));
        } else if (val == RATE) {
            b.setText("x");
            b.setBackground(new Color(255, 230, 230));
        } else {
            b.setText("~");
            b.setBackground(new Color(230, 230, 255));
        }
    }

    // 0/1 -> ? ; 2 -> o ; 3 -> x
    private void setButtonStyleCache(JButton b, int val) {
        if (val == PION_TOUCHE) {
            b.setText("o");
            b.setBackground(new Color(230, 255, 230));
        } else if (val == RATE) {
            b.setText("x");
            b.setBackground(new Color(255, 230, 230));
        } else {
            b.setText("?");
            b.setBackground(new Color(240, 240, 240));
        }
    }
}
