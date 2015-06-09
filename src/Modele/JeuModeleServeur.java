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
	}

	@Override
	public void connexion() {
		int[] tab = {nbLigne, nbColonne, nbBombe};
		try {
			socket = new ServerSocket(MON_PORT);
			
			setFlux(socket.accept());       
			setSortie(new ObjectOutputStream(getFlux().getOutputStream()));
			setEntree(new ObjectInputStream(getFlux().getInputStream()));
			getSortie().writeObject(tab);
			getSortie().flush();
			getSortie().writeObject(getListeCase());
			getSortie().flush();
		} catch (IOException e) { e.printStackTrace(); }

	}

}
