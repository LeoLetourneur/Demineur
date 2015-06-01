package Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import Commun.VarCommun;

public class ModeleJeu extends Observable {

	private int nbColonne;
	private int nbLigne;
	private int nbBombe;
	private Timer timer;
	private int secondes;
	private int nbBombeRestante;
	private int nbCasesRetournes;
	private int etat;
	private VarCommun.themeJeu themeJeu;
	private Boolean premierTour;
	private boolean fini;
	
	private ArrayList<CaseModele> listeCase;
	
	public ModeleJeu() {
		
		themeJeu = VarCommun.themeJeu.Mario;
		setNbColonne(10);
		setNbLigne(10);
		setNbBombe(10);
		construireCases();
	}
	
	public void initialiser() {
		
		setEtat(VarCommun.etatJeu.DEBUT.value);
		setNbCasesRetournees(0);
		setFini(false);
		setSecondes(0);
		setPremierTour(true);
		setNbBombeRestante(0);
		setListeCase(new ArrayList<CaseModele>());
	}

	public void construireCases() {
		
		initialiser();
		
		int nbCase = nbLigne*nbColonne;
		ArrayList<Integer> listeCaseVide = new ArrayList<Integer>();
		
		for(int i=0; i<nbCase; i++) {
			CaseModele caseModele = new CaseModele(i, this);
			listeCase.add(caseModele);
			caseModele.sAjouterAuxVoisins(listeCase);
			listeCaseVide.add(i);
		}
		
		Random rnd = new Random();
		int random;
		while(nbBombeRestante<nbBombe) {
			random = rnd.nextInt(listeCaseVide.size());
			if(listeCase.get(listeCaseVide.get(random)).getValeur() == 1)
				System.out.println("ERROR : Déjà à 1");
			listeCase.get(listeCaseVide.get(random)).setValeur(1);
			listeCase.get(listeCaseVide.get(random)).incrementerVoisin(1);
			listeCaseVide.remove(random);
			nbBombeRestante++;
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
	
	public int getNbBombeRestante() {
		return nbBombeRestante;
	}

	public void setNbBombeRestante(int nbBombe) {
		this.nbBombeRestante = nbBombe;
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

	public Boolean isPremierTour() {
		return premierTour;
	}

	public void setPremierTour(Boolean premierTour) {
		this.premierTour = premierTour;
		if(premierTour == false) {
			setEtat(VarCommun.etatJeu.ENJEU.value);
			timer.start();
		}
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
		if(etat == VarCommun.etatJeu.PERDU.value)
			retournerBombes();
		setChanged();
		notifyObservers();
	}

	public VarCommun.themeJeu getThemeJeu() {
		return themeJeu;
	}

	public void setThemeJeu(VarCommun.themeJeu themeJeu) {
		this.themeJeu = themeJeu;
		for(CaseModele caseMode : getListeCase()) {
			caseMode.setChangeTheme(true);
			caseMode.setEtat(caseMode.getEtat());
		}
		setChanged();
		notifyObservers("ChangeTheme");
	}
	
	public int getNbCasesRetournees() {
		return nbCasesRetournes;
	}

	public void setNbCasesRetournees(int p_nbCasesRetournes) {
		this.nbCasesRetournes = p_nbCasesRetournes;
		if(nbCasesRetournes == ( nbLigne * nbColonne ) - nbBombe)
			setEtat(VarCommun.etatJeu.GAGNE.value);
		setChanged();
		notifyObservers();
	}

	public boolean isFini() {
		return fini;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}
	
	public int getNbColonne() {
		return nbColonne;
	}

	public void setNbColonne(int nbLigne) {
		this.nbColonne = nbLigne;
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(int nbColonne) {
		this.nbLigne = nbColonne;
	}

	public int getNbBombe() {
		return nbBombe;
	}

	public void setNbBombe(int nbBombe) {
		this.nbBombe = nbBombe;
	}
}
