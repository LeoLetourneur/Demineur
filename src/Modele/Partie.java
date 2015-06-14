package Modele;

import java.util.Date;

public class Partie {
	
	String date;
	int colonnes;
	int lignes;
	int bombes;
	int temps;
	
	public Partie(String p_date,int p_colonnes,int p_lignes,int p_bombes,int p_temps){
		setDate(p_date);
		setColonnes(p_colonnes);
		setLignes(p_lignes);
		setBombes(p_bombes);
		setTemps(p_temps);
	}

	public Partie() {
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getColonnes() {
		return colonnes;
	}

	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}

	public int getLignes() {
		return lignes;
	}

	public void setLignes(int lignes) {
		this.lignes = lignes;
	}

	public int getBombes() {
		return bombes;
	}

	public void setBombes(int bombes) {
		this.bombes = bombes;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}
	
	
	

}
