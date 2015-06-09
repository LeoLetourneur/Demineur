package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class JeuModeleClient extends JeuModeleDJRes implements Serializable {

	private static final long serialVersionUID = 6353635343291120470L;
	
	private String ipServeur;
	
	public JeuModeleClient() {
		super();
	}

	public JeuModeleClient(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
	}

	@Override
	public void connexion() {
		ipServeur = "127.0.0.1";
		try {
			this.flux = new Socket(InetAddress.getByName(this.ipServeur), MON_PORT);
			this.sortie = new ObjectOutputStream(this.flux.getOutputStream());
			this.entree = new ObjectInputStream(this.flux.getInputStream());
			this.sortie.flush();
			
			recevoirPlateau();
			setListeCase(recevoirCases());
		} catch (IOException e) { e.printStackTrace(); }
	}

	private void recevoirPlateau() {
		try {
			int[] tab = (int[])entree.readObject();
			this.nbLigne = tab[0];
			this.nbColonne = tab[1];
			this.nbBombe = tab[2];
			System.out.println("Nb ok");
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
		
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<CaseModele> recevoirCases() {
		try {
			Object al = entree.readObject();
			System.out.println("Cases ok");
			return (ArrayList<CaseModele>)al;
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		return null;
	}
}
