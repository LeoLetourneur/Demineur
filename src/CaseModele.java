import java.util.Observable;


public class CaseModele extends Observable {

	private int numero;
	private int valeur;
	private int nbBombeVoisin;
	private boolean marque;
	
	public CaseModele(int p_numero) {
		super();
		setNumero(p_numero);
		setValeur(0);
		setNbBombeVoisin(0);
		setMarque(false);
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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

	public boolean isMarque() {
		return marque;
	}

	public void setMarque(boolean marque) {
		this.marque = marque;
	}

}
