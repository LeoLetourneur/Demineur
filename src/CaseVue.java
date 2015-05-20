import javax.swing.JButton;


public class CaseVue extends JButton {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private int numero;
	private int valeur;
	private int nbBombeVoisin;
	private boolean marque;
	
	private CaseModele modele;
	
	public CaseVue(int p_numero) {
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
