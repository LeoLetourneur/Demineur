package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;

public class JeuModeleServeur extends JeuModeleDJRes implements Serializable {
	private static final long serialVersionUID = -6946234147530306744L;
	
	transient private ServerSocket socket;
	
	public JeuModeleServeur() {
		super();
	}

	public JeuModeleServeur(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
		connexion();
	}

	@Override
	public void connexion() {
		int[] tab = {nbLigne, nbColonne, nbBombe};
		try {
			socket = new ServerSocket(MON_PORT);
			
			flux = socket.accept();       
			sortie = new ObjectOutputStream(flux.getOutputStream());
			entree = new ObjectInputStream(flux.getInputStream());
			sortie.writeObject(tab);
			sortie.flush();
			sortie.writeObject(getListeCase());
			sortie.flush();
		} catch (IOException e) { e.printStackTrace(); }

	}

}
