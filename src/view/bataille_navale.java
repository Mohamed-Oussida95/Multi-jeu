package view;

import java.util.Scanner;

public class bataille_navale {

    // Etats des cases (selon le TD)
    // 0 = aucune action, pas de pion (inconnu)
    // 1 = pion non découvert
    // 2 = pion découvert
    // 3 = case découverte sans pion
    static final int VIDE = 0;
    static final int PION_CACHE = 1;
    static final int PION_TOUCHE = 2;
    static final int RATE = 3;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        final int nbCase = 5;   // plateau 5x5 (TD)
        final int nbPions = 5;  // 5 bateaux/pions (TD)

        boolean rejouer = true;

        while (rejouer) {
            int[][] tabjoueur = new int[nbCase][nbCase];
            int[][] tabordi = new int[nbCase][nbCase];

            // Etape 1 : initialisation implicite à 0 (Java met déjà 0 partout)
            // mais on garde la méthode pour être clair TD
            initialiser(tabjoueur);
            initialiser(tabordi);

            // Compteurs de pions trouvés
            int nbPionTrouveJoueur = 0;
            int nbPionTrouveOrdi = 0;

            // Etape 2 : placement joueur
            System.out.println("=== Placement de vos 5 pions ===");
            placerPionsJoueur(sc, tabjoueur, nbCase, nbPions);

            System.out.println("\nVotre champ de bataille (pions visibles) :");
            affichageTab(tabjoueur, nbCase);

            // Etape 3 : placement ordinateur
            placerPionsOrdi(tabordi, nbCase, nbPions);
            System.out.println("\nL'ordinateur a placé ses pions.");

            // Etape 8 : boucle de jeu jusqu'à victoire
            while (nbPionTrouveJoueur < nbPions && nbPionTrouveOrdi < nbPions) {

                // Etape 6 : tour joueur
                System.out.println("\n==============================");
                System.out.println("A vous de jouer !");
                nbPionTrouveJoueur += tourJoueur(sc, tabordi, nbCase);

                System.out.println("\nTableau de l'ordinateur (cache) :");
                affichageTabCache(tabordi, nbCase);

                if (nbPionTrouveJoueur >= nbPions) {
                    break; // joueur gagne
                }

                // Etape 7 : tour ordinateur
                System.out.println("\n==============================");
                System.out.println("Tour de l'ordinateur...");
                nbPionTrouveOrdi += tourOrdi(tabjoueur, nbCase);

                System.out.println("\nVotre tableau (cache) :");
                affichageTabCache(tabjoueur, nbCase);
            }

            // Etape 8.2 : vainqueur
            System.out.println("\n==============================");
            if (nbPionTrouveJoueur >= nbPions) {
                System.out.println("✅ Bravo ! Vous avez gagné !");
            } else {
                System.out.println("❌ L'ordinateur a gagné !");
            }

            // Etape 8.3 : rejouer
            rejouer = demanderRejouer(sc);
        }

        System.out.println("Fin du jeu. A bientôt !");
        sc.close();
    }

    // -----------------------------
    // Etape 1 : init
    // -----------------------------
    static void initialiser(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = VIDE;
            }
        }
    }

    // -----------------------------
    // Etape 2 : placement joueur
    // -----------------------------
    static void placerPionsJoueur(Scanner sc, int[][] tabjoueur, int nbCase, int nbPions) {
        int places = 0;

        while (places < nbPions) {
            System.out.println("\nPion " + (places + 1) + "/" + nbPions);

            int ligne = demanderNombre(sc, "Choisissez une ligne (1-" + nbCase + ") : ");
            int col = demanderNombre(sc, "Choisissez une colonne (1-" + nbCase + ") : ");

            // convertir en index 0-based
            int i = ligne - 1;
            int j = col - 1;

            // contrôle périmètre
            if (!dansPlateau(i, j, nbCase)) {
                System.out.println("⛔ Hors du plateau. Réessayez.");
                continue;
            }

            // contrôle déjà pris
            if (tabjoueur[i][j] == PION_CACHE) {
                System.out.println("⛔ Case déjà occupée par un pion. Réessayez.");
                continue;
            }

            tabjoueur[i][j] = PION_CACHE;
            places++;
            System.out.println("✅ Pion placé !");
        }
    }

    // -----------------------------
    // Etape 3 : placement ordi aléatoire
    // -----------------------------
    static void placerPionsOrdi(int[][] tabordi, int nbCase, int nbPions) {
        int places = 0;
        while (places < nbPions) {
            int ligne = random1N(nbCase) - 1; // 0..nbCase-1
            int col = random1N(nbCase) - 1;

            if (tabordi[ligne][col] == PION_CACHE) {
                continue; // déjà un pion -> on recommence
            }
            tabordi[ligne][col] = PION_CACHE;
            places++;
        }
    }

    // -----------------------------
    // Etape 4 : affichage normal (pions visibles)
    // 0 -> ~
    // 1 -> o
    // 2 -> o (touché, reste un pion)
    // 3 -> x (raté)
    // -----------------------------
    static void affichageTab(int[][] tab, int nbCase) {
        // entête colonnes
        System.out.print("   ");
        for (int c = 1; c <= nbCase; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < nbCase; i++) {
            // numéro de ligne
            System.out.print((i + 1) + "  ");

            for (int j = 0; j < nbCase; j++) {
                char ch;
                if (tab[i][j] == PION_CACHE || tab[i][j] == PION_TOUCHE) {
                    ch = 'o';
                } else if (tab[i][j] == RATE) {
                    ch = 'x';
                } else {
                    ch = '~';
                }
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    // -----------------------------
    // Etape 5 : affichage caché (états TD)
    // 0 ou 1 -> ?
    // 2 -> o
    // 3 -> x
    // -----------------------------
    static void affichageTabCache(int[][] tab, int nbCase) {
        // entête colonnes
        System.out.print("   ");
        for (int c = 1; c <= nbCase; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < nbCase; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < nbCase; j++) {
                char ch;
                if (tab[i][j] == PION_TOUCHE) {
                    ch = 'o';
                } else if (tab[i][j] == RATE) {
                    ch = 'x';
                } else {
                    ch = '?';
                }
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    // -----------------------------
    // Etape 6 : tour joueur (retourne 1 si touché sinon 0)
    // - si 0 -> passe à 3 (raté)
    // - si 1 -> passe à 2 (touché)
    // - sinon -> tir à blanc
    // -----------------------------
    static int tourJoueur(Scanner sc, int[][] tabOrdi, int nbCase) throws InterruptedException {
        int ligne = demanderNombre(sc, "Ligne à découvrir (1-" + nbCase + ") : ");
        int col = demanderNombre(sc, "Colonne à découvrir (1-" + nbCase + ") : ");

        int i = ligne - 1;
        int j = col - 1;

        if (!dansPlateau(i, j, nbCase)) {
            System.out.println("⛔ Hors du plateau. Tir annulé.");
            return 0;
        }

        System.out.print("Tir en cours");
        pauseTir();

        int val = tabOrdi[i][j];

        if (val == VIDE) {
            tabOrdi[i][j] = RATE;
            System.out.println(" => Raté !");
            return 0;
        } else if (val == PION_CACHE) {
            tabOrdi[i][j] = PION_TOUCHE;
            System.out.println(" => Touché !");
            return 1;
        } else {
            System.out.println(" => Tir à blanc ! (case déjà découverte)");
            return 0;
        }
    }

    // -----------------------------
    // Etape 7 : tour ordi (retourne 1 si touché sinon 0)
    // - choisit une case dont la valeur est 0 ou 1
    // -----------------------------
    static int tourOrdi(int[][] tabJoueur, int nbCase) throws InterruptedException {
        int i, j;

        // choisir une case non découverte (0 ou 1)
        while (true) {
            i = random1N(nbCase) - 1;
            j = random1N(nbCase) - 1;
            if (tabJoueur[i][j] == VIDE || tabJoueur[i][j] == PION_CACHE) {
                break;
            }
        }

        System.out.println("L'ordinateur tire sur : ligne " + (i + 1) + ", colonne " + (j + 1));
        System.out.print("Tir en cours");
        pauseTir();

        if (tabJoueur[i][j] == VIDE) {
            tabJoueur[i][j] = RATE;
            System.out.println(" => Raté !");
            return 0;
        } else { // PION_CACHE
            tabJoueur[i][j] = PION_TOUCHE;
            System.out.println(" => Touché !");
            return 1;
        }
    }

    // -----------------------------
    // Utils
    // -----------------------------
    static boolean demanderRejouer(Scanner sc) {
        while (true) {
            System.out.print("\nVoulez-vous rejouer ? (o/n) : ");
            String rep = sc.next().trim().toLowerCase();
            if (rep.equals("o") || rep.equals("oui")) return true;
            if (rep.equals("n") || rep.equals("non")) return false;
            System.out.println("Réponse invalide. Tapez o/n.");
        }
    }

    static int demanderNombre(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                sc.next(); // consommer le mauvais token
                System.out.println("⛔ Entrez un nombre.");
            }
        }
    }

    static boolean dansPlateau(int i, int j, int nbCase) {
        return i >= 0 && i < nbCase && j >= 0 && j < nbCase;
    }

    static int random1N(int n) {
        return (int) (Math.random() * n) + 1;
    }

    static void pauseTir() throws InterruptedException {
        // petit effet "..."
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(500); // total ~2s
    }
}