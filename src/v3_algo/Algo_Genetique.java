package v3_algo;

import java.util.ArrayList;

import v3_window.Cell;

/**
 * L'Algorithme G�n�tique. <br>
 * 
 * 
 * @author Romain Duret
 * @version Build III -  v0.6
 * @since Build III -  v0.0
 */
public class Algo_Genetique {
 
	/*
	   _____       _ _   _       _ _		   _   _		     
	  |_   _|     (_) | (_)     | (_)		 | | (_)		    
	    | |  _ __  _| |_ _  __ _| |_ ___  __ _| |_ _  ___  _ __  
	    | | | '_ \| | __| |/ _` | | / __|/ _` | __| |/ _ \| '_ \ 
	   _| |_| | | | | |_| | (_| | | \__ \ (_| | |_| | (_) | | | |
	  |_____|_| |_|_|\__|_|\__,_|_|_|___/\__,_|\__|_|\___/|_| |_|
	*/														     
	    
	
    
    private static final double mutationRate = 0.05;
    private static final int tournamentSize = 3;

 
    /*
	  __  __      _   _		       _		   
	 |  \/  |    | | | |		     | |		  
	 | \  / | ___| |_| |__   ___   __| | ___  ___ 
	 | |\/| |/ _ \ __| '_ \ / _ \ / _` |/ _ \/ __|
	 | |  | |  __/ |_| | | | (_) | (_| |  __/\__ \
	 |_|  |_|\___|\__|_| |_|\___/ \__,_|\___||___/
											      
	 */	
    
    /**
     * Evoluer la population : <br>
     * On garde le meilleur Groupe, on cr�er un nouveau groupe de passager <br>
     * On croise
     * et on mute 
     * @param pop Population (voitures)
     * @return
     * @version Build III -  v0.6
     * @since Build III -  v0.0
     */
    public static Population evolvePopulation(Population pop, ArrayList<Cell> l_b) {
		Population newPopulation  = new Population(pop.getSize(), false, l_b);
 
		// Garder le meilleur PassagerParVoiture
		newPopulation.savePassagerOnVoiture(0, pop.getMoreCompetent());
		int elitismOffset = 1;

		// Loop over the population size and create new member with
		// Select new population
		for (int i = elitismOffset; i < pop.getSize(); i++) {
			PassagerParVoiture newPassagerOnVoiture = tournamentSelection(pop, l_b);
			newPopulation.savePassagerOnVoiture(i, newPassagerOnVoiture);
		}
		
		
		// Mutate population
		for (int i = elitismOffset; i < newPopulation.getSize(); i++) {
			mutation(newPopulation.getPassagerOnVoiture(i));
		}
		
		for (int i = elitismOffset; i < newPopulation.getSize(); i++) {
			newPopulation.getPassagerOnVoiture(i).attribuerPointsDePassage();
		}

		return newPopulation;
    }
    
 
    /**
     * D�clanche une mutation si le facteur random est d�clanch�. <br>
     * On teste s'il y a mutation autant de fois qu'il y a de place en tout dans le groupe de voiture/passager 
     * @version Build III -  v0.6
     * @since Build III -  v0.0
     */
    private static void mutation(PassagerParVoiture PassagerParVoiture) {
		for (int i = 0; i < PassagerParVoiture.getNbVoitures(); i++) {
			for (int j = 0; j < PassagerParVoiture.getNbPassagers(); j++){
				if (Math.random() <= mutationRate) {
					randomSwapPassagers(PassagerParVoiture, i);
				}
		    }
		}
    }
    
    /**
	 * Evoluer la population : <br>
	 * On garde le meilleur Groupe, on cr�er un nouveau groupe de passager <br>
     * On croise et on mute 
     * @param pop Population (voitures)
     * @return
     * @version Build III -  v0.6
     * @since Build III -  v0.0
     */
	private static void randomSwapPassagers(PassagerParVoiture PassagerParVoiture, int nVoiture){
		//On g�n�re 2 endroits al�atoire non identiques
		int placeTotal = Execut_Algo_Genetique.nbPlaceVoiture* Execut_Algo_Genetique.nbVoiture;
		int place_1 = (int)(Math.random() * placeTotal);
		int place_2 = (int)(Math.random() * placeTotal);

		while(place_1==place_2) {
			place_2 = (int)(Math.random() * placeTotal);
		}
		
		//On cr�� une m�moire provisoire : 
		int[] emplacement_1 = new int[3];
		int[] emplacement_2 = new int[3];
		
		//Pour chaque place, on diminue place_1/place_2.
		//Si l'un des deux = 0, on sauvegarde l'emplacement et le type (0 si vide 1 si plein)
		
		for(int i=0;i<Execut_Algo_Genetique.nbVoiture;i++) {
			for(int j=0;j<Execut_Algo_Genetique.nbPlaceVoiture;j++) {
				if(place_1==0 || place_2 == 0) {
					if(PassagerParVoiture.nbPassagerParVoiture[i]>j) {
						if(place_1==0) {
							emplacement_1[0] = 1;
							emplacement_1[1] = i;
							emplacement_1[2] = j;
						} else if (place_2==0) {
							emplacement_2[0] = 1;
							emplacement_2[1] = i;
							emplacement_2[2] = j;
						}
					} else {
						if(place_1==0) {
							emplacement_1[0] = 0;
							emplacement_1[1] = i;
							emplacement_1[2] = -1;
						} else if (place_2==0) {
							emplacement_2[0] = 0;
							emplacement_2[1] = i;
							emplacement_2[2] = -1;
						}
					}
				}
				place_1--; place_2--;
			}
		}

		//Ensuite, on croise :
		Passager PassagerTmps = null;
		if(emplacement_1[0]==1 && emplacement_2[0]==1) {
			//Croisement simple
			PassagerTmps = PassagerParVoiture.passagersOrdonnes[emplacement_1[1]][emplacement_1[2]];
			PassagerParVoiture.passagersOrdonnes[emplacement_1[1]][emplacement_1[2]] = PassagerParVoiture.passagersOrdonnes[emplacement_2[1]][emplacement_2[2]];
			PassagerParVoiture.passagersOrdonnes[emplacement_2[1]][emplacement_2[2]] = PassagerTmps;
		} else if (emplacement_2[0]==1) {
			
		} else if (emplacement_1[0]==1) {
			//Croisement avec un bloc vide
			
		} else {
			//rien
		}
		
    }
    
    /**
     * S�lection en roue de la fortune. <br>
     * Pour chaque "�tape", on s�lectionne 4 passagers tir�s au sort. On r�cup�re la taille. <br>
     * Cela donne un poids (50% meilleur, 10% pour le reste, 20% pour un nouveau g�n�r� al�atoirement) <br>
     * On tire au sort et on renvoie.
     * @param pop Population (voitures)
     * @return
     * @version Build III -  v0.6
     * @since Build III -  v0.0
     */
    private static PassagerParVoiture tournamentSelection(Population pop, ArrayList<Cell> l_b) {
		Population creation = new Population(4, false, l_b);
		for (int i = 0; i < 4; i++) {
		    int randomId = (int) (Math.random() * pop.getSize());
		    creation.savePassagerOnVoiture(i, pop.getPassagerOnVoiture(randomId));
		}
		Population tournament = new Population(4, false, l_b);
		tournament.savePassagerOnVoiture(0, creation.getMoreCompetent());
		int t = 1;
		for (int i = 0; i < tournamentSize; i++) {
			if(!(tournament.getPassagerOnVoiture(0)==creation.getPassagerOnVoiture(i))) {
				tournament.savePassagerOnVoiture(t, creation.getPassagerOnVoiture(i));
				t++;
			}
		}
		int select = (int) Math.random();
		if(select <0.5) {
			return tournament.getPassagerOnVoiture(0);
		} else if(select < 0.6) {
			return tournament.getPassagerOnVoiture(0);
		} else if(select < 0.7) {
			return tournament.getPassagerOnVoiture(0);
		} else if(select < 0.8) {
			return tournament.getPassagerOnVoiture(0);
		} else {
			return new PassagerParVoiture();
		}
    }
}