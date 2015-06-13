package Modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

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
		try {
			setFlux(new Socket(InetAddress.getByName(getIpServeur()), getPortServeur()));
			setSortie(new ObjectOutputStream(getFlux().getOutputStream()));
			setEntree(new ObjectInputStream(getFlux().getInputStream()));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public String getIpServeur() {
		return ipServeur;
	}

	public void setIpServeur(String ipServeur) {
		this.ipServeur = ipServeur;
	}
}
