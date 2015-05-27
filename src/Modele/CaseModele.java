package Modele;
import java.util.ArrayList;
import java.util.Observable;

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
		setValeur(ModeleJeu.typeCase.EMPTY.value);
		setEtat(ModeleJeu.etatCase.COVER.value);
		caseVoisines= new ArrayList<CaseModele>();
		
	}
	
	public void sAjouterAuxVoisins(ArrayList<CaseModele> listeCase){
		if(index>=ModeleJeu.NB_CASE){
			caseVoisines.add(listeCase.get(index-ModeleJeu.NB_CASE));
			listeCase.get(index-ModeleJeu.NB_CASE).addVoisine(this);
			
			if(index%ModeleJeu.NB_CASE<ModeleJeu.NB_CASE-1)
			{
				caseVoisines.add(listeCase.get(index-ModeleJeu.NB_CASE+1));
				listeCase.get(index-ModeleJeu.NB_CASE+1).addVoisine(this);
			}
			
			if(index%ModeleJeu.NB_CASE>0)
			{
				caseVoisines.add(listeCase.get(index-ModeleJeu.NB_CASE-1));
				listeCase.get(index-ModeleJeu.NB_CASE-1).addVoisine(this);
			}
		}
		if(index%ModeleJeu.NB_CASE>0)
		{
			caseVoisines.add(listeCase.get(index-1));
			listeCase.get(index-1).addVoisine(this);
		}
	}
	
	public void incrementerVoisin()
	{
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getValeur() != 1)
				caseVoisine.setNbBombeVoisin(caseVoisine.getNbBombeVoisin()+1);
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

	public void retournerVoisin() {
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getNbBombeVoisin() == 0
			&& caseVoisine.getEtat() != ModeleJeu.etatCase.DISCOVER.value){
				caseVoisine.setEtat(ModeleJeu.etatCase.DISCOVER.value);
				caseVoisine.retournerVoisin();
			}
			else {
				caseVoisine.setEtat(ModeleJeu.etatCase.DISCOVER.value);
			}
		}
	}

}
