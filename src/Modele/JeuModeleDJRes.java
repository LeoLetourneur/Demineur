package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

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
			int index = (Integer)entree.readObject();
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
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { System.out.println("Vous avez été déconnecté"); }
		return false;
	}
	
	public void envoyer(int index)
	{
		try
		{
			sortie.writeObject(index);
			sortie.flush();
		}
		catch(IOException io)
		{JOptionPane.showMessageDialog(null, "Vous avez été déconnecté");}
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
