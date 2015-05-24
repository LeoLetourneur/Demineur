package Modele;
import java.util.Observable;

public class CaseModele extends Observable {

	private int index;
	private int valeur;
	private int etat;
	private int nbBombeVoisin;
	
	public CaseModele(int p_numero) {
		super();
		setIndex(p_numero);
		setValeur(0);
		setNbBombeVoisin(0);
		setEtat(ModeleJeu.etatCase.COVER.value);
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int numero) {
		this.index = numero;
	}

	public int getNbBombeVoisin() {
		return nbBombeVoisin;
	}

	public void setNbBombeVoisin(int nbBombeVoisin) {
		this.nbBombeVoisin = nbBombeVoisin;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
		setChanged();
		notifyObservers();
	}

}
