package Modele;

public class Joueur {

	private String name;
	private int numero;
	private int score;
	
	public Joueur(String p_name, int p_numero) {
		setName(p_name);
		setNumero(p_numero);
		setScore(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void incrementerScore() {
		score++;
		
	}
}
