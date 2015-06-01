package Modele;
import java.util.ArrayList;
import java.util.Observable;

import Commun.VarCommun;

public class CaseModele extends Observable {

	private int index;
	private int valeur;
	private int etat;
	private int nbBombeVoisin;
	private boolean changeTheme;
	private ArrayList<CaseModele> caseVoisines;
	
	private ModeleJeu modeleJeu;

	public CaseModele(int p_numero, ModeleJeu p_modeleJeu) {
		super();
		setChangeTheme(false);
		setIndex(p_numero);
		setNbBombeVoisin(0);
		setValeur(VarCommun.typeCase.EMPTY.value);
		setEtat(VarCommun.etatCase.COVER.value);
		setVoisins(new ArrayList<CaseModele>());
		setModeleJeu(p_modeleJeu);
	}
	
	public void sAjouterAuxVoisins(ArrayList<CaseModele> listeCase){
		if(index>=VarCommun.NB_CASE){
			caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE));
			listeCase.get(index-VarCommun.NB_CASE).addVoisin(this);
			
			if(index%VarCommun.NB_CASE<VarCommun.NB_CASE-1)
			{
				caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE+1));
				listeCase.get(index-VarCommun.NB_CASE+1).addVoisin(this);
			}
			
			if(index%VarCommun.NB_CASE>0)
			{
				caseVoisines.add(listeCase.get(index-VarCommun.NB_CASE-1));
				listeCase.get(index-VarCommun.NB_CASE-1).addVoisin(this);
			}
		}
		if(index%VarCommun.NB_CASE>0)
		{
			caseVoisines.add(listeCase.get(index-1));
			listeCase.get(index-1).addVoisin(this);
		}
	}
	
	public void incrementerVoisin(int valeur)
	{
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getValeur() != VarCommun.typeCase.BOMB.value)
				if( valeur>0 || ( valeur<0 && caseVoisine.getNbBombeVoisin()>0 ))
					caseVoisine.setNbBombeVoisin(caseVoisine.getNbBombeVoisin()+valeur);
		}
	}
	
	public void retournerVoisin() {
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getEtat() != VarCommun.etatCase.DISCOVER.value) {
				if(caseVoisine.getEtat() == VarCommun.etatCase.QUESTION.value
				|| caseVoisine.getEtat() == VarCommun.etatCase.FLAG.value)
					break;
				caseVoisine.setEtat(VarCommun.etatCase.DISCOVER.value);
				if(caseVoisine.getValeur() == VarCommun.typeCase.BOMB.value) {
					getModeleJeu().setEtat(VarCommun.etatJeu.PERDU.value);
					return;
				}
				if(caseVoisine.getNbBombeVoisin() == 0)
					caseVoisine.retournerVoisin();
			}
		}
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
		
		if( etat == VarCommun.etatCase.DISCOVER.value && valeur == VarCommun.typeCase.EMPTY.value )
			modeleJeu.setNbCasesRetournees(modeleJeu.getNbCasesRetournees()+1);
		
		setChanged();
		notifyObservers();
	}

	public ArrayList<CaseModele> getVoisins()
	{
		return caseVoisines;
	}

	public void setVoisins(ArrayList<CaseModele> caseVoisines) {
		this.caseVoisines = caseVoisines;
	}
	
	public void addVoisin(CaseModele caseAAjouter){
		this.caseVoisines.add(caseAAjouter);
	}

	public boolean isChangeTheme() {
		return changeTheme;
	}

	public void setChangeTheme(boolean changeTheme) {
		this.changeTheme = changeTheme;
	}

	public ModeleJeu getModeleJeu() {
		return modeleJeu;
	}

	public void setModeleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}
}
