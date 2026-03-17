package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		
		Etal etal = new Etal();
		Gaulois acheteurTest = new Gaulois("Astérix", 8);
		
		etal.libererEtal();
		System.out.println("Fin du test #1");

		
		// Test avec un acheteur null
		System.out.println("Test de l'acheteur null :");
		etal.occuperEtal(new Gaulois("Obélix", 25), "menhirs", 10);
		etal.acheterProduit(5, null); 
		System.out.println("\n");
		
		// Test avec une quantité négative
		System.out.println("Test de la quantité négative :");
		try {
			etal.acheterProduit(-2, acheteurTest);
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur capturée : " + e.getMessage());
		}
		System.out.println("\n");
		
		// Test avec un étal vide
		System.out.println("Test de l'étal vide :");
		etal.libererEtal();
		try {
			etal.acheterProduit(5, acheteurTest);
		} catch (IllegalStateException e) {
			System.out.println("Erreur capturée : " + e.getMessage());
		}
		
		System.out.println("\nFin du test");
	}
}