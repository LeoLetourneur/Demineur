package Modele;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import Commun.VarCommun;

public class CaseModele extends Observable implements Serializable {
	private static final long serialVersionUID = -2292570373610324561L;
	
	private int index;
	private int valeur;
	private int etat;
	private int nbBombeVoisin;
	private boolean changeTheme;
	private ArrayList<CaseModele> caseVoisines;
	
	private JeuModele modeleJeu;

	public CaseModele(int p_numero, JeuModele p_modeleJeu) {
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
		if(index>=modeleJeu.getNbColonne()){
			caseVoisines.add(listeCase.get(index-modeleJeu.getNbColonne()));
			listeCase.get(index-modeleJeu.getNbColonne()).addVoisin(this);
			
			if(index%modeleJeu.getNbColonne()<modeleJeu.getNbColonne()-1)
			{
				caseVoisines.add(listeCase.get(index-modeleJeu.getNbColonne()+1));
				listeCase.get(index-modeleJeu.getNbColonne()+1).addVoisin(this);
			}
			
			if(index%modeleJeu.getNbColonne()>0)
			{
				caseVoisines.add(listeCase.get(index-modeleJeu.getNbColonne()-1));
				listeCase.get(index-modeleJeu.getNbColonne()-1).addVoisin(this);
			}
		}
		if(index%modeleJeu.getNbColonne()>0)
		{
			caseVoisines.add(listeCase.get(index-1));
			listeCase.get(index-1).addVoisin(this);
		}
	}
	
	public void incrementerVoisin(int valeur)
	{
		for(CaseModele caseVoisine : caseVoisines) {
			if( valeur>0 || ( valeur<0 && caseVoisine.getNbBombeVoisin()>0 ))
				caseVoisine.setNbBombeVoisin(caseVoisine.getNbBombeVoisin()+valeur);
		}
	}
	
	public void retournerVoisin() {
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getEtat() != VarCommun.etatCase.DISCOVER.value) {
				if(caseVoisine.getEtat() != VarCommun.etatCase.QUESTION.value
				&& caseVoisine.getEtat() != VarCommun.etatCase.FLAG.value)
				{
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
			
		if(etat!=this.etat && etat == VarCommun.etatCase.DISCOVER.value && valeur == VarCommun.typeCase.EMPTY.value )
			{
				this.etat = etat;
				setChanged();
				notifyObservers();
				modeleJeu.setNbCasesRetournees(modeleJeu.getNbCasesRetournees()+1);
			}
		this.etat=etat;
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

	public JeuModele getModeleJeu() {
		return modeleJeu;
	}

	public void setModeleJeu(JeuModele modeleJeu) {
		this.modeleJeu = modeleJeu;
	}
}
