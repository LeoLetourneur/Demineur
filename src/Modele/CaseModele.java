package Modele;
import java.util.ArrayList;
import java.util.Observable;

import Commun.VarCommun;

public class CaseModele extends Observable {

	private int index;
	private int valeur;
	private int etat;
	private int nbBombeVoisin;
	private ArrayList<CaseModele> caseVoisines;
	
	public CaseModele(int p_numero) {
		super();
		setIndex(p_numero);
		setNbBombeVoisin(0);
		setValeur(VarCommun.typeCase.EMPTY.value);
		setEtat(VarCommun.etatCase.COVER.value);
		caseVoisines= new ArrayList<CaseModele>();
	}
	
	public void sAjouterAuxVoisins(ArrayList<CaseModele> listeCase){
		if(index>=VarCommun.NB_CASE){
			caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE));
			listeCase.get(index-VarCommun.NB_CASE).addVoisine(this);
			
			if(index%VarCommun.NB_CASE<VarCommun.NB_CASE-1)
			{
				caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE+1));
				listeCase.get(index-VarCommun.NB_CASE+1).addVoisine(this);
			}
			
			if(index%VarCommun.NB_CASE>0)
			{
				caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE-1));
				listeCase.get(index-VarCommun.NB_CASE-1).addVoisine(this);
			}
		}
		if(index%VarCommun.NB_CASE>0)
		{
			caseVoisines.add(listeCase.get(index-1));
			listeCase.get(index-1).addVoisine(this);
		}
	}
	
	public void incrementerVoisin(int valeur)
	{
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getValeur() != 1)
				if(valeur>0||(valeur<0 && caseVoisine.getValeur()>0))
				caseVoisine.setNbBombeVoisin(caseVoisine.getNbBombeVoisin()+valeur);
		}
	}
	
	public void addVoisine(CaseModele caseAAjouter){
		caseVoisines.add(caseAAjouter);
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

	public ArrayList<CaseModele> getVoisins()
	{
		return caseVoisines;
	}
	
	public void retournerVoisin() {
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getNbBombeVoisin() == 0
			&& caseVoisine.getEtat() != VarCommun.etatCase.DISCOVER.value){
				caseVoisine.setEtat(VarCommun.etatCase.DISCOVER.value);
				caseVoisine.retournerVoisin();
			}
			else {
				caseVoisine.setEtat(VarCommun.etatCase.DISCOVER.value);
			}
		}
	}

}
