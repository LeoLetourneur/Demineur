import javax.swing.JButton;


public class Case extends JButton {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private int numero;
	private int valeur;
	private int nbBombeVoisin;
	
	public Case(int p_numero) {
		super();
		setNumero(p_numero);
		setValeur(0);
		setNbBombeVoisin(0);
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

}
