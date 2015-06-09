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
			setFlux(new Socket(InetAddress.getByName(this.ipServeur), MON_PORT));
			setSortie(new ObjectOutputStream(getFlux().getOutputStream()));
			setEntree(new ObjectInputStream(getFlux().getInputStream()));
			
			recevoirPlateau();
			recevoirCases();
		} catch (IOException e) { e.printStackTrace(); }
	}

	private void recevoirPlateau() {
		try {
			int[] tab = (int[])getEntree().readObject();
			this.nbLigne = tab[0];
			this.nbColonne = tab[1];
			this.nbBombe = tab[2];
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
		
	}
	
	@SuppressWarnings("unchecked")
	private void recevoirCases() {
		try {
			Object al = getEntree().readObject();
			setListeCase((ArrayList<CaseModele>)al);
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
}
