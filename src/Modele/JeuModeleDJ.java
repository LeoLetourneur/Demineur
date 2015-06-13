package Modele;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Commun.VarCommun;

public class JeuModeleDJ extends JeuModele implements Serializable {
	private static final long serialVersionUID = -5008464224374925137L;
	
	protected Joueur joueur1;
	protected Joueur joueur2;
	protected Joueur joueurCourant;
	
	public JeuModeleDJ() {
		this(9, 9, 10);
	}
	
	public JeuModeleDJ(int nbLigne, int nbColonne, int nbBombe) {
		
		super(nbLigne, nbColonne, nbBombe);
		joueur1 = new Joueur("Joueur1", 1);
		joueur2 = new Joueur("Joueur2", 2);
		
		setAllowQuestion(false);
		setPremierTour(false);
		setJoueurCourant(joueur1);
	}
	
	public void initialiser() {
		super.initialiser();
		setPremierTour(false);
		getJoueur1().setScore(0);
		getJoueur2().setScore(0);
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
	
	public void sauvegarde() {	
		try {
			FileOutputStream fileStreamPartie = new FileOutputStream("partieDJ.serial");
			ObjectOutputStream objetStreamPartie= new ObjectOutputStream(fileStreamPartie);
			try{
				objetStreamPartie.writeObject(this);
				objetStreamPartie.flush();
			} finally {
				try {
						objetStreamPartie.close();
					} finally {
						fileStreamPartie.close();
					}
			}
			
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		}
		this.setSauvegarde(true);
		System.out.println("Sauvegardé");
	}
	
	public static JeuModeleDJ charger() {
		JeuModeleDJ partieCharger = null;
		try {
			FileInputStream fileStreamPartie = new FileInputStream("partieDJ.serial");
			ObjectInputStream objetStreamPartie= new ObjectInputStream(fileStreamPartie);
			try {	
				partieCharger = (JeuModeleDJ) objetStreamPartie.readObject(); 
			} finally {
				try {
					objetStreamPartie.close();
				} finally {
					objetStreamPartie.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		partieCharger.setSauvegarde(true);
		System.out.println("Chargé");
		return partieCharger;
	}
}
