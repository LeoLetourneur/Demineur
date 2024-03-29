package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;

/**
 * Classe Modele du serveur
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class JeuModeleServeur extends JeuModeleDJRes implements Serializable {
	private static final long serialVersionUID = -6946234147530306744L;
	
	transient private ServerSocket socket;
	
	/** 
	* Constructeur vide
	*
	*/
	public JeuModeleServeur() {
		super();
	}

	/** 
	* Constructeur
	*
	*/
	public JeuModeleServeur(int nbLigne, int nbColonne, int nbBombe) {
		super(nbLigne, nbColonne, nbBombe);
	}

	@Override
	public void connexion() {
		try {
			socket = new ServerSocket(getPortServeur());
			
			setFlux(socket.accept());       
			setSortie(new ObjectOutputStream(getFlux().getOutputStream()));
			setEntree(new ObjectInputStream(getFlux().getInputStream()));
			
		} catch (IOException e) { System.out.println("Problème de connexion"); }
	}
}
