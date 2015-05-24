package Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class ModeleJeu extends Observable {

	public final int NB_CASE = 20;
	public final int NB_BOMBE = 80;
	
	public static enum typeCase { 
		EMPTY("empty",0), BOMB("bombe",1);
		public final String name;
		public final int value;
		  
		typeCase(String name, int valeur){
		    this.name = name;
		    this.value = valeur;
		}
	}
	
	public static enum etatCase { 
		COVER("cover",0), DISCOVER("discover",1), FLAG("flag",2), QUESTION("question",3);
		public final String name;
		public final int value;
		  
		etatCase(String name, int valeur){
		    this.name = name;
		    this.value = valeur;
		}
	}
	

	private ArrayList<CaseModele> listeCase;
	private int nbBombe;
	
	public ModeleJeu() {
		listeCase = new ArrayList<CaseModele>();
	}

	public int[][] construireGrille() {
		
		int nbCase = (int) Math.pow(NB_CASE, 2);
		nbBombe = 0;
		
		ArrayList<Integer> listeCaseVide = new ArrayList<Integer>();
		int[][] tableauValeur = new int[nbCase][2];
		for(int i=0; i<nbCase; i++) {
			listeCaseVide.add(i);
			tableauValeur[i][0] = 0;
			tableauValeur[i][1] = 0;
		}
		
		Random rnd = new Random();
		int random;
		while(nbBombe<NB_BOMBE) {
			random = rnd.nextInt(listeCaseVide.size());
			if(tableauValeur[listeCaseVide.get(random)][0] == 1)
				System.out.println("ERROR : Déjà à 1");
			tableauValeur[listeCaseVide.get(random)][0] = 1;
			tableauValeur = incrementerVoisin(listeCaseVide.get(random), tableauValeur,1);
			listeCaseVide.remove(random);
			nbBombe++;
		}
		return tableauValeur;
	}

	private int[][] incrementerVoisin(int index, int[][] tableauValeur, int modificateur)
	{
		int minI = 0;
		int maxI = 3;
		if(index%NB_CASE < 1)
			minI = 1;
		else if(index%NB_CASE > NB_CASE-2)
			maxI = 2;
		
		for(int i=minI;i<maxI;i++)
			for(int j=0;j<3;j++) {
				if(index+i-1+(j-1)*NB_CASE>=0 && index+i-1+(j-1)*NB_CASE<Math.pow(NB_CASE,2)
				&& tableauValeur[index+i-1+(j-1)*NB_CASE][0] != 1)
					tableauValeur[index+i-1+(j-1)*NB_CASE][1] += modificateur;
			}
		return tableauValeur;
	}
	
	public void retournerVoisin(int numero) {
		int minI = 0;
		int maxI = 3;
		if(numero%NB_CASE < 1)
			minI = 1;
		else if(numero%NB_CASE > NB_CASE-2)
			maxI = 2;
		
		for(int i=minI;i<maxI;i++)
			for(int j=0;j<3;j++) {
				int index = numero+i-1+(j-1)*NB_CASE;
				if(index>=0 && index<Math.pow(NB_CASE,2)) {
					if(listeCase.get(index).getNbBombeVoisin() == 0
					&& listeCase.get(index).getEtat() != ModeleJeu.etatCase.DISCOVER.value){
						listeCase.get(index).setEtat(ModeleJeu.etatCase.DISCOVER.value);
						retournerVoisin(index);
					}
					else {
						listeCase.get(index).setEtat(ModeleJeu.etatCase.DISCOVER.value);
					}
				}
			}
	}
	
	
	public void retournerBombes() {
		for(CaseModele caseMod : listeCase) {
			if(caseMod.getValeur() == ModeleJeu.typeCase.BOMB.value)
				caseMod.setEtat(ModeleJeu.etatCase.DISCOVER.value);
		}
	}

	public ArrayList<CaseModele> getListeCase() {
		return listeCase;
	}

	public void setListeCase(ArrayList<CaseModele> listeCase) {
		this.listeCase = listeCase;
	}
	
	public int getNbBombe() {
		return nbBombe;
	}

	public void setNbBombe(int nbBombe) {
		this.nbBombe = nbBombe;
		setChanged();
		notifyObservers();
	}

}
