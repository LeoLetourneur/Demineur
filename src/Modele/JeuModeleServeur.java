package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class JeuModeleServeur extends JeuModeleDJRes {

	private ServerSocket socket;
	
	public JeuModeleServeur() {
		super();
	}

	public JeuModeleServeur(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
		connexion();
	}

	@Override
	public void connexion() {
		try {
			socket = new ServerSocket(MON_PORT);
			
			flux = socket.accept();       
			sortie = new ObjectOutputStream(flux.getOutputStream());
			entree = new ObjectInputStream(flux.getInputStream());
			sortie.flush();
		} catch (IOException e) { e.printStackTrace(); }

	}

}
