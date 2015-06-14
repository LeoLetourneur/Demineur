package Modele;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import Commun.VarCommun;

/**
 * Classe Modèle du jeu.
 * 
 * @author COUTURIER Cyril
 * @since 1.0
 */
public class JeuModele extends Observable implements Serializable {
	private static final long serialVersionUID = 4260028170365630301L;
	
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
	private boolean allowTime;
	private boolean defiTemps;
	private int secondesDefi;
	private boolean saveBeforeQuit;
	private boolean sauvegarde;
	private boolean allowSounds;
	private ArrayList<CaseModele> listeCase;
	
	transient private Son sonBombe;
	transient private Son sonVide;
	transient private Son sonWin;
	
	/** 
	* Constructeur vide
	*
	*/
	public JeuModele() {
		
		this(9,9,10);
	}
	
	/** 
	* Constructeur
	* Initialisation des variables qui ne changent pas entre deux parties.
	*/
	public JeuModele(int nbLigne, int nbColonne, int nbBombe) {
		
		themeJeu = VarCommun.themeJeu.Mario;
		setSauvegarde(false);
		setSaveBeforeQuit(false);
		setAllowSounds(false);
		setDefiTemps(false);
		setSecondesDefi(0);
		setAllowQuestion(true);
		setAllowTime(true);
		setNbColonne(nbColonne);
		setNbLigne(nbLigne);
		setNbBombe(nbBombe);
	}
	
	/** 
	* Initialisation des variables qui changent entre chaque partie.
	*
	*/
	public void initialiser() {
		
		setEtat(VarCommun.etatJeu.DEBUT.value);
		setNbCasesRetournees(0);
		setFini(false);
		if(isDefiTemps())
			setSecondes(getSecondesDefi());
		else
			setSecondes(0);
		setPremierTour(true);
		setNbBombeRestante(0);
		
		setSonBombe(new Son("sons/bombe.wav"));
		setSonVide(new Son("sons/vide.wav"));
		setSonWin(new Son("sons/win.wav"));
	}

	/** 
	* Construction de toutes les cases modèles.
	*
	*/
	public void construireCases() {
		
		initialiser();
		listeCase = new ArrayList<CaseModele>();
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
	
	/** 
	* Répartir les bombes dans toutes les cases modèles au hasard.
	*
	*/
	public void repartirBombe(ArrayList<Integer> listeCaseVide) {
		Random rnd = new Random();
		int random;
		while(nbBombeRestante<nbBombe) {
			random = rnd.nextInt(listeCaseVide.size());
			listeCase.get(listeCaseVide.get(random)).setValeur(1);
			listeCase.get(listeCaseVide.get(random)).incrementerVoisin(1);
			listeCaseVide.remove(random);
			setNbBombeRestante(getNbBombeRestante()+1);
		}
	}
	
	/** 
	* Changer la valeur des cases pour relancer une nouvelle partie.
	*
	*/
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
	
	/** 
	* Retourner toutes les bombes lors d'une défaite.
	*
	*/
	public void retournerBombes() {
		for(CaseModele caseMod : listeCase) {
			if(caseMod.getValeur() == VarCommun.typeCase.BOMB.value)
				caseMod.setEtat(VarCommun.etatCase.DISCOVER.value);
		}
	}
	
	/** 
	* Sauvegarde de la partie dans un fichier binaire.
	*
	*/
	public void sauvegarde() {
		
		try {
			FileOutputStream fileStreamPartie = new FileOutputStream("partie.serial");
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
		JOptionPane.showMessageDialog(null, "Partie sauvegardée !");
	}
	
	/** 
	* Chargement d'une partie dans un fichier binaire.
	*
	*/
	public static JeuModele charger() {
		JeuModele partieCharger = null;
		try {
			FileInputStream fileStreamPartie = new FileInputStream("partie.serial");
			ObjectInputStream objetStreamPartie= new ObjectInputStream(fileStreamPartie);
			try {	
				partieCharger = (JeuModele) objetStreamPartie.readObject(); 
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
		return partieCharger;
	}

	public ArrayList<CaseModele> getListeCase() {
		return listeCase;
	}

	public void setListeCase(ArrayList<CaseModele> listeCase) {
		this.listeCase = listeCase;
		setChanged();
		notifyObservers();
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
		if(secondes < 0)
			setEtat(VarCommun.etatJeu.PERDU.value);
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
			if(isAllowTime())
				timer.start();
			if(isAllowSounds())
				getSonVide().jouer();
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

	public boolean isAllowTime() {
		return allowTime;
	}

	public void setAllowTime(boolean allowTime) {
		this.allowTime = allowTime;
	}

	public boolean isDefiTemps() {
		return defiTemps;
	}

	public void setDefiTemps(boolean defiTemps) {
		this.defiTemps = defiTemps;
	}

	public boolean isSauvegarde() {
		return sauvegarde;
	}

	public void setSauvegarde(boolean sauvegarde) {
		this.sauvegarde = sauvegarde;
	}

	public boolean isSaveBeforeQuit() {
		return saveBeforeQuit;
	}

	public void setSaveBeforeQuit(boolean saveBeforeQuit) {
		this.saveBeforeQuit = saveBeforeQuit;
	}

	public boolean isAllowSounds() {
		return allowSounds;
	}

	public void setAllowSounds(boolean allowSounds) {
		this.allowSounds = allowSounds;
	}

	public Son getSonBombe() {
		return sonBombe;
	}

	public void setSonBombe(Son sonBombe) {
		this.sonBombe = sonBombe;
	}

	public Son getSonVide() {
		return sonVide;
	}

	public void setSonVide(Son sonVide) {
		this.sonVide = sonVide;
	}

	public Son getSonWin() {
		return sonWin;
	}

	public void setSonWin(Son sonWin) {
		this.sonWin = sonWin;
	}

	public int getSecondesDefi() {
		return secondesDefi;
	}

	public void setSecondesDefi(int secondesDefi) {
		this.secondesDefi = secondesDefi;
	}
}
