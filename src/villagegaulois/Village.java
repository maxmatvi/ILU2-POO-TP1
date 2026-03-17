package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		
		chaine.append(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append(".\n");
		
		//On cherche un étal libre
		int indiceEtalLibre = marche.trouverEtalLibre();
		
		if (indiceEtalLibre != -1) {

			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			
			
			chaine.append("Le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" vend des ");
			chaine.append(produit);
			chaine.append(" à l'étal n°");
			chaine.append(indiceEtalLibre + 1); 
			chaine.append(".\n");
		} else {
			chaine.append("Malheureusement, il n'y a plus d'étal libre pour ");
			chaine.append(vendeur.getNom());
			chaine.append(".\n");
		}
		
		return chaine.toString();
	}
	
	
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		
		// On récupère le tableau des étals qui vendent ce produit via la classe interne
		Etal[] etalsProduit = marche.trouverEtals(produit);
		
		if (etalsProduit == null || etalsProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
		} else if (etalsProduit.length == 1) {
			chaine.append("Seul le vendeur ");
			chaine.append(etalsProduit[0].getVendeur().getNom());
			chaine.append(" propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des ");
			chaine.append(produit);
			chaine.append(" sont :\n");
			for (int i = 0; i < etalsProduit.length; i++) {
				chaine.append("- ");
				chaine.append(etalsProduit[i].getVendeur().getNom());
				chaine.append("\n");
			}
		}
		
		return chaine.toString();
	}
	
	

	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etalsVendeur = marche.trouverVendeur(vendeur);
		
		return etalsVendeur;
	}
	
	
	
	public String partirVendeur(Gaulois vendeur) {
		
		Etal etalVendeur = rechercherEtal(vendeur);
		
		if (etalVendeur != null) {
			
			return etalVendeur.libererEtal();
		}
		
		return ""; 
	}
	
	
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"");
		chaine.append(nom);
		chaine.append("\" possède plusieurs étals :\n");
		
		// Ici, le Village demande au Marche de faire le travail !
		chaine.append(marche.afficherMarche()); 
		
		return chaine.toString();
	}
	
	
	
	
	
	
	
	
	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		
		if (chef == null) {
			throw new VillageSansChefException("Impossible d'afficher les villageois : le village n'a pas de chef !");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	 private static class Marche { //Class intern
		Etal etals[]; //Attribute
		
		private Marche(int nbEtals) { //Constructor
			etals = new Etal[nbEtals];
			for(int i = 0; i<etals.length; i++) {
			etals[i] = new Etal();
			}
		}
			
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbProduitsEtals = 0;
			//On compte combien d'étals vendent le produit
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbProduitsEtals++;
				}
			}
			
			//On crée le tableau à la bonne taille
			Etal[] etalsPr = new Etal[nbProduitsEtals];
			
			//On remplit le tableau
			int indiceTableauPr = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsPr[indiceTableauPr] = etals[i];
					indiceTableauPr++;
				}
			}
			
			return etalsPr;	
		}
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
		return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder(); // On prépare la chaîne
			int nbOccup = 0;
			
			for(int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					nbOccup++;
					// On AJOUTE le texte de l'étal à notre chaîne globale
					chaine.append(etals[i].afficherEtal());
				}
			}
			
			int nbEtalVide = etals.length - nbOccup;
			if(nbEtalVide > 0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
		
			return chaine.toString(); // On renvoie tout le bloc de texte
		}
		
		
		
		
		
		
		
		
		
  }
}