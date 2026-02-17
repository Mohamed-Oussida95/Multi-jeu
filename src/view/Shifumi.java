package view;

import java.util.Random;
import java.util.Scanner;

public class Shifumi {

    public static void main(String[] args) {
    	// TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenue dans le jeu Shifumi ");
        System.out.println("Règles : Tapez 1 pour Pierre, 2 pour Papier, 3 pour Ciseaux.");

        String[] choices = {"Pierre", "Papier", "Ciseaux"};
        Random random = new Random();

        String rep = null;
		do {
            System.out.print("Faites votre choix (1, 2, 3 ) : ");
            int userChoice = sc.nextInt();

            if (userChoice < 1 || userChoice > 3) {
                System.out.println("Choix invalide. Réessayez.");
                continue;
            }

            int computerChoice = random.nextInt(3) + 1;

            System.out.println("Vous avez choisi : " + choices[userChoice - 1]);
            System.out.println("L'ordinateur a choisi : " + choices[computerChoice - 1]);

            if (userChoice == computerChoice) {
                System.out.println("Égalité !");
            } else if ((userChoice == 1 && computerChoice == 3) ||
                       (userChoice == 2 && computerChoice == 1) ||
                       (userChoice == 3 && computerChoice == 2)) {
                System.out.println("Vous avez gagné !");
            } else {
                System.out.println("L'ordinateur a gagné !");
            }
            System.out.println("Souhaitez-vous relancer le programme (O/N)?");
            rep = sc.next();
        } while (rep.equalsIgnoreCase("O"));
        }
    }


