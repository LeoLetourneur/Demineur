package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commun.VarCommun;

/**
 * Classe Mod�le du jeu pour deux joueurs en r�seau
 * 
 * @author COUTURIER Cyril
 * @since 4.0
 */
public abstract class JeuModeleDJRes extends JeuModeleDJ implements Serializable {
	private static final long serialVersionUID = 1L;
	
	transient private Socket flux;
	transient private ObjectInputStream entree;
	transient private ObjectOutputStream sortie;
	private int portServeur;
	private Joueur moi;
	
	/** 
	* Constructeur vide
	*
	*/
	public JeuModeleDJRes() {
		super();
		definirJoueurs();
	}
	
	/** 
	* Constructeur vide
	*
	*/
	public JeuModeleDJRes(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
		definirJoueurs();
	}
	
	/** 
	* D�finir quel joueur est celui qui utilise ce mod�le.
	*
	*/
	public void definirJoueurs() {
		if(this instanceof JeuModeleServeur)
			moi = getJoueur1();
		else
			moi = getJoueur2();
	}
	
	/** 
	* Connexion à travers TCP
	*
	*/
	public abstract void connexion();
	
	/** 
	* Gestion de la r�ception des messages
	*
	*/
	public boolean recevoir()
	{
		try {
			Object objet = entree.readObject();
			
			if(objet instanceof String) {
				traiterString((String)objet);
				return true;
			}
		
			int index = (Integer)objet;
			CaseModele caseM = getListeCase().get(index);
			caseM.setEtat(Commun.VarCommun.etatCase.DISCOVER.value);
			if(caseM.getValeur() == VarCommun.typeCase.BOMB.value) {
				incrementerScore();

			} else {
				if(caseM.getNbBombeVoisin() == 0)
					caseM.retournerVoisin();
				
				if(getJoueurCourant() ==  getJoueur1())
					setJoueurCourant(getJoueur2());
				else
					setJoueurCourant(getJoueur1());
			}
			return true;
		} catch (ClassNotFoundException e) { System.out.println("Classe inconnue");
		} catch (IOException e) { System.out.println("Vous avez �t� d�connect�"); }
		return false;
	}
	
	/** 
	* Traiter un message de l'application
	*
	*/
	private void traiterString(String objet) {
		if(objet.equals(VarCommun.MSG_NOUVELLE_PARTIE)) {
			JOptionPane.showMessageDialog(null, "Votre adversaire a relanc� une partie");
			initialiser();
			setJoueurCourant(moi);
			recevoirNouvellesCases();
		}
		else if(objet.equals(VarCommun.MSG_DECOUVRIR)) {
			for(int i=0;i<(getNbColonne()*getNbLigne());i++)
				getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);
			setFini(true);
			setEtat(VarCommun.etatJeu.GAGNE.value);
			JOptionPane.showMessageDialog(null, "Votre adversaire a d�couvert le jeu et a donc perdu");
		}
		else if(objet.equals(VarCommun.MSG_QUITTER)) {
			this.deconnexion();
			this.setFini(true);
			JOptionPane.showMessageDialog(null, "D�connexion de l'autre joueur");
		}
	}
	
	/** 
	* Envoyer une case sur laquelle on vient de cliquer.
	*
	*/
	public void envoyerCase(int index)
	{
		try
		{
			sortie.writeObject(index);
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez �t� d�connect�");}
	}
	
	/** 
	* Avertir l'autre joueur d'une nouvelle partie.
	*
	*/
	public void envoyerNouvellePartie()
	{
		try
		{
			sortie.writeObject("GAME_MSG_NEWGAME");
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez �t� d�connect�");}
	}
	
	/** 
	* Avertir l'autre joueur que l'on a d�couvert les cases.
	*
	*/
	public void envoyerDecouvrir()
	{
		try
		{
			sortie.writeObject("GAME_MSG_DISCOVER");
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez �t� d�connect�");}
	}
	
	/** 
	* Avertir l'autre joueur que je me suis d�connect�.
	*
	*/
	public void envoyerQuitter()
	{
		try
		{
			if(!flux.isClosed()) {
				sortie.writeObject("GAME_MSG_QUIT");
				sortie.flush();
				deconnexion();
			}
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez �t� d�connect�");}
	}
	
	/** 
	* Envoyer les dimensions de la grille et le nombre de bombe.
	*
	*/
	public void envoiDimensionsGrille() {
		int[] tab = { getNbLigne(), getNbColonne(), getNbBombe() };
		try {
			sortie.writeObject(tab);
			sortie.flush();
		} catch (IOException e) { System.out.println("Probl�me d'envoi de la grille"); }
	}
	
	/** 
	* Envoyer l'ensemble des cases et de leurs valeurs.
	*
	*/
	public void envoiEnsembleCases() {
		try {
			sortie.writeObject(((JeuModeleDJRes)this).getListeCase());
			sortie.flush();
			sortie.reset();
		} catch (IOException e) { System.out.println("Probl�me d'envoi de la liste de cases"); }
	}
	
	/** 
	* Reception des dimensions de la grille et du nombre de bombe.
	*
	*/
	public void recevoirPlateau() {
		try {
			int[] tab = (int[])getEntree().readObject();
			this.nbLigne = tab[0];
			this.nbColonne = tab[1];
			this.nbBombe = tab[2];
			
			setListeCase(new ArrayList<CaseModele>());
			for(int i = 0; i<nbColonne*nbLigne; i++) {
				CaseModele caseM = new CaseModele(i, this);
				caseM.sAjouterAuxVoisins(getListeCase());
				getListeCase().add(caseM);
			}
		} catch (IOException e) { System.out.println("Probl�me de r�ception du plateau"); 
		} catch (ClassNotFoundException e) { System.out.println("Probl�me de classe"); }
	}
	
	/** 
	* Recevoir une nouvelle liste de case lors d'une nouvelle partie.
	*
	*/
	@SuppressWarnings("unchecked")
	public void recevoirNouvellesCases() {
		try {
			ArrayList<CaseModele> al = (ArrayList<CaseModele>)getEntree().readObject();
			
			for(int i = 0; i<nbColonne*nbLigne; i++) {
				getListeCase().get(i).setNbBombeVoisin(al.get(i).getNbBombeVoisin());
				getListeCase().get(i).setValeur(al.get(i).getValeur());
				getListeCase().get(i).setEtat(VarCommun.etatCase.COVER.value);
			}
		} catch (ClassNotFoundException e) { System.out.println("Probl�me de case");
		} catch (IOException e) { System.out.println("Probl�me de r�ception des cases"); }
	}

	/** 
	* Deconnexion à travers TCP
	*
	*/
	public void deconnexion()
	{
		try {
			sortie.close();
			entree.close();
			flux.close(); 
		} catch (IOException e) { System.out.println("D�connexion"); }
		   
	}
	
	public Socket getFlux() {
		return flux;
	}

	public void setFlux(Socket flux) {
		this.flux = flux;
	}

	public ObjectInputStream getEntree() {
		return entree;
	}

	public void setEntree(ObjectInputStream entree) {
		this.entree = entree;
	}

	public ObjectOutputStream getSortie() {
		return sortie;
	}

	public void setSortie(ObjectOutputStream sortie) {
		this.sortie = sortie;
	}
	
	public int getPortServeur() {
		return portServeur;
	}

	public void setPortServeur(int portServeur) {
		this.portServeur = portServeur;
	}

	public Joueur getMoi() {
		return moi;
	}

	public void setMoi(Joueur moi) {
		this.moi = moi;
	}
}
