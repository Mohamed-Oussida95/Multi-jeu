package tests;

import java.util.Scanner;

public class calculette {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Calculette ===");

        System.out.print("Entrez le premier nombre : ");
        double a = scanner.nextDouble();

        System.out.print("Entrez le deuxième nombre : ");
        double b = scanner.nextDouble();

        System.out.println("Choisissez une opération :");
        System.out.println("1 - Addition");
        System.out.println("2 - Soustraction");
        System.out.println("3 - Multiplication");
        System.out.println("4 - Division");
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();

        double resultat;

        switch (choix) {
            case 1:
                resultat = a + b;
                System.out.println("Résultat : " + resultat);
                break;
            case 2:
                resultat = a - b;
                System.out.println("Résultat : " + resultat);
                break;
            case 3:
                resultat = a * b;
                System.out.println("Résultat : " + resultat);
                break;
            case 4:
                if (b != 0) {
                    resultat = a / b;
                    System.out.println("Résultat : " + resultat);
                } else {
                    System.out.println("Erreur : division par zéro impossible.");
                }
                break;
            default:
                System.out.println("Choix invalide.");
        }

        scanner.close();
    }
}
