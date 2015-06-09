package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JOptionPane;

public abstract class JeuModeleDJRes extends JeuModeleDJ implements Serializable {
	private static final long serialVersionUID = 1L;
	
	transient protected Socket flux;
	transient protected ObjectInputStream entree;
	transient protected ObjectOutputStream sortie;
	public static final int MON_PORT = 2015;
	
	public JeuModeleDJRes() {
		super();
	}
	
	public JeuModeleDJRes(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
	}
	
	public abstract void connexion();
	
	public void recevoir()
	{
		try {
			int index = (Integer)entree.readObject();
			getListeCase().get(index).setEtat(Commun.VarCommun.etatCase.DISCOVER.value);
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
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
}
