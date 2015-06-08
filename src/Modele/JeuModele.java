package Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import Commun.VarCommun;

public class JeuModele extends Observable {

	protected int nbColonne;
	protected int nbLigne;
	protected int nbBombe;
	private Timer timer;
	private int secondes;
	private int nbBombeRestante;
	protected int nbCasesRetournes;
	private int etat;
	private VarCommun.themeJeu themeJeu;
	private boolean premierTour;
	private boolean fini;
	private boolean allowQuestion;
	private boolean allowFlag;
	private boolean allowTime;
	private ArrayList<CaseModele> listeCase;
	
	
	public JeuModele() {
		
		themeJeu = VarCommun.themeJeu.Mario;
		setAllowFlag(true);
		setAllowQuestion(true);
		setAllowTime(true);
		setNbColonne(9);
		setNbLigne(9);
		setNbBombe(10);
		construireCases();
	}
	
	public JeuModele(int nbLigne, int nbColonne, int nbBombe) {
		
		themeJeu = VarCommun.themeJeu.Mario;
		setAllowFlag(true);
		setAllowQuestion(true);
		setAllowTime(true);
		setNbColonne(nbColonne);
		setNbLigne(nbLigne);
		setNbBombe(nbBombe);
		construireCases();
	}
	
	public void initialiser() {
		
		setEtat(VarCommun.etatJeu.DEBUT.value);
		setNbCasesRetournees(0);
		setFini(false);
		setSecondes(0);
		setPremierTour(true);
		setNbBombeRestante(0);
	}

	public void construireCases() {
		
		initialiser();
		setListeCase(new ArrayList<CaseModele>());
		int nbCase = nbLigne*nbColonne;
		ArrayList<Integer> listeCaseVide = new ArrayList<Integer>();
		for(int i=0; i<nbCase; i++) {
			CaseModele caseModele = new CaseModele(i, this);
			listeCase.add(caseModele);
			caseModele.sAjouterAuxVoisins(listeCase);
			listeCaseVide.add(i);
		}
		repartirBombe(listeCaseVide);
	}
	
	public void repartirBombe(ArrayList<Integer> listeCaseVide) {
		Random rnd = new Random();
		int random;
		while(nbBombeRestante<nbBombe) {
			random = rnd.nextInt(listeCaseVide.size());
			if(listeCase.get(listeCaseVide.get(random)).getValeur() == 1)
				System.out.println("ERROR : Déjà à 1");
			listeCase.get(listeCaseVide.get(random)).setValeur(1);
			listeCase.get(listeCaseVide.get(random)).incrementerVoisin(1);
			listeCaseVide.remove(random);
			setNbBombeRestante(getNbBombeRestante()+1);
		}
	}
	
	public void reinitialiserCase() {
		
		initialiser();
		
		ArrayList<Integer> listeCaseVide = new ArrayList<Integer>();
		for(CaseModele caseMod : listeCase) {
			caseMod.setNbBombeVoisin(0);
			caseMod.setValeur(VarCommun.typeCase.EMPTY.value);
			caseMod.setEtat(VarCommun.etatCase.COVER.value);
			listeCaseVide.add(caseMod.getIndex());
		}
		repartirBombe(listeCaseVide);
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
		if(premierTour == false && !(this instanceof JeuModeleDJ)) {
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

	public boolean isAllowQuestion() {
		return allowQuestion;
	}

	public void setAllowQuestion(boolean allowQuestion) {
		this.allowQuestion = allowQuestion;
	}

	public boolean isAllowFlag() {
		return allowFlag;
	}

	public void setAllowFlag(boolean allowFlag) {
		this.allowFlag = allowFlag;
	}

	public boolean isAllowTime() {
		return allowTime;
	}

	public void setAllowTime(boolean allowTime) {
		this.allowTime = allowTime;
	}
}
