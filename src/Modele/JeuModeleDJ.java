package Modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

import Commun.VarCommun;

/**
 * Classe Modele du jeu pour deux joueurs
 * 
 * @author COUTURIER Cyril
 * @since 3.0
 */
public class JeuModeleDJ extends JeuModele implements Serializable {
	private static final long serialVersionUID = -5008464224374925137L;
	
	protected Joueur joueur1;
	protected Joueur joueur2;
	protected Joueur joueurCourant;
	
	/** 
	* Constructeur vide
	*
	*/
	public JeuModeleDJ() {
		this(9, 9, 10);
	}
	
	/** 
	* Constructeur
	*
	*/
	public JeuModeleDJ(int nbLigne, int nbColonne, int nbBombe) {
		
		super(nbLigne, nbColonne, nbBombe);
		joueur1 = new Joueur("Joueur1", 1);
		joueur2 = new Joueur("Joueur2", 2);
		
		setAllowQuestion(false);
		setAllowTime(false);
		setJoueurCourant(joueur1);
	}
	
	public void initialiser() {
		super.initialiser();
		getJoueur1().setScore(0);
		getJoueur2().setScore(0);
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
	
	public void setEtat(int etat) {
		this.etat = etat;
		
		if(!isFini()) {
			if(getEtat() == VarCommun.etatJeu.GAGNE.value) {
				if(isSauvegarde())
					sauvegarde();
				if(isAllowSounds())
					getSonWin().jouer();
				PartieDJ partie = new PartieDJ(getJoueur1().getScore(),getJoueur2().getScore(),getNbBombe(),
						new SimpleDateFormat("yyyy-MM-dd  HH:mm",Locale.FRANCE).format(new Date()).toString());
				PartieDJ.ecritureXML(partie, "fichier/scoreXMLDJ.xml");
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/** 
	* Sauvegarde de la partie deux joueurs dans un fichier binaire.
	*
	*/
	public void sauvegarde() {	
		try {
			FileOutputStream fileStreamPartie = new FileOutputStream("fichier/partieDJ.serial");
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
			
		} catch (FileNotFoundException e) { System.out.println("Problème de fichier");
		} catch (IOException e) { System.out.println("Problème de sérialisation");
		} catch (StackOverflowError e) { 
			JOptionPane.showMessageDialog(null, "Grille trop grosse pour la sauvegarde");
			File file = new File("fichier/partieDJ.serial"); 
			file.delete(); 
			return;
		}
		this.setSauvegarde(true);
		JOptionPane.showMessageDialog(null, "Partie sauvegardée !");
	}
	
	/** 
	* Chargement de la partie deux joueurs dans un fichier binaire.
	*
	*/
	public static JeuModeleDJ charger() {
		JeuModeleDJ partieCharger = null;
		try {
			FileInputStream fileStreamPartie = new FileInputStream("fichier/partieDJ.serial");
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
		} catch (FileNotFoundException e) { 
			JOptionPane.showMessageDialog(null, "Aucune partie sauvegardée");
			return null;
		} catch(IOException ioe) {
			System.out.println("Problème de sérialisation");
			return null;
		} catch(ClassNotFoundException cnfe) {
			System.out.println("Problème de classe");
			return null;
		}
		partieCharger.setSauvegarde(true);
		return partieCharger;
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
}
