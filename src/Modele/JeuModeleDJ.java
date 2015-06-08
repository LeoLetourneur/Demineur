package Modele;

import Commun.VarCommun;

public class JeuModeleDJ extends JeuModele {

	private Joueur joueur1;
	private Joueur joueur2;
	private Joueur joueurCourant;
	
	public JeuModeleDJ() {
		super();
		joueur1 = new Joueur("Joueur1", 1);
		joueur2 = new Joueur("Joueur2", 2);
		
		setAllowFlag(false);
		setAllowQuestion(false);
		setPremierTour(false);
		setJoueurCourant(joueur1);
	}
	
	public JeuModeleDJ(int nbLigne, int nbColonne, int nbBombe) {
		
		super(nbLigne, nbColonne, nbBombe);
		joueur1 = new Joueur("Joueur1", 1);
		joueur2 = new Joueur("Joueur2", 2);
		
		setAllowFlag(false);
		setAllowQuestion(false);
		setPremierTour(false);
		setJoueurCourant(joueur1);
	}

	public Joueur getJoueur1() {
		return joueur1;
	}

	public void setJoueur1(Joueur joueur1) {
		this.joueur1 = joueur1;
	}

	public Joueur getJoueur2() {
		return joueur2;
	}

	public void setJoueur2(Joueur joueur2) {
		this.joueur2 = joueur2;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public void setJoueurCourant(Joueur joueurCourant) {
		this.joueurCourant = joueurCourant;
		setChanged();
		notifyObservers();
	}
	
	public void incrementerScore() {
		getJoueurCourant().incrementerScore();
		if(getNbBombe() == getJoueur1().getScore()+getJoueur2().getScore())
			setEtat(VarCommun.etatJeu.GAGNE.value);
		setChanged();
		notifyObservers();
	}
	
	public void reinitialiserCase() {
		super.reinitialiserCase();
		getJoueur1().setScore(0);
		getJoueur2().setScore(0);
		setJoueurCourant(getJoueur1());
		
	}
	
	public void setNbCasesRetournees(int p_nbCasesRetournes) {
		this.nbCasesRetournes = p_nbCasesRetournes;
		if(nbCasesRetournes == ( nbLigne * nbColonne ))
			setEtat(VarCommun.etatJeu.GAGNE.value);
		setChanged();
		notifyObservers();
		
	}
	
}
