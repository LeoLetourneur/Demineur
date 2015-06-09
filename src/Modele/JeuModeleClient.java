package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class JeuModeleClient extends JeuModeleDJRes {

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
		} catch (IOException e) { e.printStackTrace(); }
	}
}
