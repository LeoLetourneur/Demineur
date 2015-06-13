package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commun.VarCommun;

public abstract class JeuModeleDJRes extends JeuModeleDJ implements Serializable {
	private static final long serialVersionUID = 1L;
	
	transient private Socket flux;
	transient private ObjectInputStream entree;
	transient private ObjectOutputStream sortie;
	private int portServeur;
	private Joueur moi;
	
	public JeuModeleDJRes() {
		super();
		if(this instanceof JeuModeleServeur)
			moi = getJoueur1();
		else
			moi = getJoueur2();
	}
	
	public JeuModeleDJRes(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
		if(this instanceof JeuModeleServeur)
			moi = getJoueur1();
		else
			moi = getJoueur2();
	}
	
	public abstract void connexion();
	
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
		} catch (IOException e) { System.out.println("Vous avez été déconnecté"); }
		return false;
	}
	
	private void traiterString(String objet) {
		if(objet.equals(VarCommun.MSG_NOUVELLE_PARTIE)) {
			JOptionPane.showMessageDialog(null, "Votre adversaire a relancé une partie");
			initialiser();
			setJoueurCourant(moi);
			recevoirNouvellesCases();
		}
		else if(objet.equals(VarCommun.MSG_DECOUVRIR)) {
			for(int i=0;i<(getNbColonne()*getNbLigne());i++)
				getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);
			setFini(true);
			setEtat(VarCommun.etatJeu.GAGNE.value);
			JOptionPane.showMessageDialog(null, "Votre adversaire a découvert le jeu et a donc perdu");
		}
		else if(objet.equals(VarCommun.MSG_QUITTER)) {
			this.deconnexion();
			this.setFini(true);
			JOptionPane.showMessageDialog(null, "Déconnexion de l'autre joueur");
		}
	}
	
	public void envoyerCase(int index)
	{
		try
		{
			sortie.writeObject(index);
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez été déconnecté");}
	}
	
	public void envoyerNouvellePartie()
	{
		try
		{
			sortie.writeObject("GAME_MSG_NEWGAME");
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez été déconnecté");}
	}
	
	public void envoyerDecouvrir()
	{
		try
		{
			sortie.writeObject("GAME_MSG_DISCOVER");
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez été déconnecté");}
	}
	
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
		{JOptionPane.showMessageDialog(null, "Vous avez été déconnecté");}
	}
	
	public void envoiDimensionsGrille() {
		int[] tab = { getNbLigne(), getNbColonne(), getNbBombe() };
		try {
			sortie.writeObject(tab);
			sortie.flush();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void envoiEnsembleCases() {
		try {
			sortie.writeObject(((JeuModeleDJRes)this).getListeCase());
			sortie.flush();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
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
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	@SuppressWarnings("unchecked")
	public void recevoirNouvellesCases() {
		try {
			ArrayList<CaseModele> al = (ArrayList<CaseModele>)getEntree().readObject();
			
			for(int i = 0; i<nbColonne*nbLigne; i++) {
				getListeCase().get(i).setNbBombeVoisin(al.get(i).getNbBombeVoisin());
				getListeCase().get(i).setValeur(al.get(i).getValeur());
				getListeCase().get(i).setEtat(VarCommun.etatCase.COVER.value);
			}
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void deconnexion()
	{
		try {
			sortie.close();
			entree.close();
			flux.close(); 
		} catch (IOException e) { e.printStackTrace(); }
		   
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
