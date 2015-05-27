package Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import Commun.VarCommun;

public class ModeleJeu extends Observable {

	private ArrayList<CaseModele> listeCase;
	private int nbBombe;
	private Timer timer;
	private int secondes;
	
	public ModeleJeu() {
		
		setSecondes(0);
		construireCases();
	}

	public void construireCases() {
		int nbCase = (int) Math.pow(VarCommun.NB_CASE, 2);
		nbBombe = 0;
		
		ArrayList<Integer> listeCaseVide = new ArrayList<Integer>();
		listeCase = new ArrayList<CaseModele>();
		
		for(int i=0; i<nbCase; i++) {
			CaseModele caseModele = new CaseModele(i);
			listeCase.add(caseModele);
			caseModele.sAjouterAuxVoisins(listeCase);
			listeCaseVide.add(i);
		}
		
		Random rnd = new Random();
		int random;
		while(nbBombe<VarCommun.NB_BOMBE) {
			random = rnd.nextInt(listeCaseVide.size());
			if(listeCase.get(listeCaseVide.get(random)).getValeur() == 1)
				System.out.println("ERROR : Déjà à 1");
			listeCase.get(listeCaseVide.get(random)).setValeur(1);
			listeCase.get(listeCaseVide.get(random)).incrementerVoisin();
			listeCaseVide.remove(random);
			nbBombe++;
		}
	}
	
	public void retournerBombes() {
		for(CaseModele caseMod : listeCase) {
			if(caseMod.getValeur() == VarCommun.typeCase.BOMB.value)
				caseMod.setEtat(VarCommun.etatCase.DISCOVER.value);
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

	public int getSecondes() {
		return secondes;
	}

	public void setSecondes(int secondes) {
		this.secondes = secondes;
		setChanged();
		notifyObservers();
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

}
