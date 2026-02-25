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
		chaine.append("Le vendeur ");
		chaine.append(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		
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

	public String afficherVillageois() {
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
			for(int i = 0; i<=etals.length; i++) {
				if(etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbProduitsEtals = 0;
			for(int i = 0; i<=etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					nbProduitsEtals++;
				}
			}
			
			Etal[] etalsPr = new Etal[nbProduitsEtals];
			
			for(int i = 0; i<=etals.length; i++) {
				for(int j = 0; j<=etalsPr.length; j++) {
				   if(etals[i].contientProduit(produit)) {
					etalsPr[j] = etals[i] ;
				   }
			    }
			}
			
		return etalsPr;	
		}
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i<=etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
		return null;
		}
		
		private String afficherMarche() {
			int nbOccup = 0;
			for(int i = 0; i<=etals.length; i++) {
			  if (etals[i].isEtalOccupe() == true) {
				  nbOccup++;
				  etals[i].afficherEtal();
			  }
			}
			int nbEtalVide = etals.length - nbOccup;
			if(nbOccup != etals.length) {
				return " Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			}
		
		return "";
		}
		
		
		
		
		
		
		
		
		
  }
}