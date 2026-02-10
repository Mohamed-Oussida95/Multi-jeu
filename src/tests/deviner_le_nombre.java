package tests;
import java.util.Scanner;
public class deviner_le_nombre {
	




		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			Scanner sc = new Scanner (System.in);
			int nb, nbuser, rep;
			do
			{
	nb=(int)(Math.random()*(100-1))+1;
	do	
	{
		System.out.println("Merci de saisir un nombre à deviner entre 1 et 100");
		nbuser=sc.nextInt();
		if(nbuser>nb)
		{
		System.out.println("nombre trop grand");
		}
		if(nbuser<nb)
		{
		System.out.println("nombre trop petit");
		}
	}while(nbuser!=nb);
	System.out.println("bravo tu as gagné");
	System.out.println("veux tu recommancer ? (1:OUI 2:NON)");
	rep=sc.nextInt();
		}while(rep==1);
			System.out.println("A bientôt ! ");
	}
	}