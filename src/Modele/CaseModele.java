package Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import Commun.VarCommun;

/**
 * Classe Modele de la case.
 * 
 * @author LETOURNEUR Léo
 * @since 1.0
 */
public class CaseModele extends Observable implements Serializable {
	private static final long serialVersionUID = -2292570373610324561L;
	
	private int index;
	private int valeur;
	private int etat;
	private int nbBombeVoisin;
	private ArrayList<CaseModele> caseVoisines;
	private JeuModele modeleJeu;

	/** 
	* Constructeur
	*
	*/
	public CaseModele(int p_numero, JeuModele p_modeleJeu) {
		super();
		setIndex(p_numero);
		setNbBombeVoisin(0);
		setValeur(VarCommun.typeCase.EMPTY.value);
		setEtat(VarCommun.etatCase.COVER.value);
		setVoisins(new ArrayList<CaseModele>());
		setModeleJeu(p_modeleJeu);
	}
	
	/** 
	* Méthode qui permet de se faire connaitre de ses voisins,
	* et ainsi contruire les ArrayList de voisin de chaque case.
	*
	*/
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
	
	/** 
	* Dire à son voisin que la case est une bombe
	*
	*/
	public void incrementerVoisin(int valeur)
	{
		for(CaseModele caseVoisine : caseVoisines) {
			if( valeur>0 || ( valeur<0 && caseVoisine.getNbBombeVoisin()>0 ))
				caseVoisine.setNbBombeVoisin(caseVoisine.getNbBombeVoisin()+valeur);
		}
	}
	
	/** 
	* Demander à son voisin de se retourner
	*
	*/
	public void retournerVoisin() {
		for(CaseModele caseVoisine : caseVoisines) {
			if(caseVoisine.getEtat() != VarCommun.etatCase.DISCOVER.value) {
				if(caseVoisine.getEtat() != VarCommun.etatCase.QUESTION.value
				&& caseVoisine.getEtat() != VarCommun.etatCase.FLAG.value)
				{
					caseVoisine.setEtat(VarCommun.etatCase.DISCOVER.value);
					//Seulement à 1J pour le double clique
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
	
	/** 
	* Déplacement d'une bombe si l'on clique dessus au premier tour.
	*
	*/
	public void switchCaseBombe() {
		getModeleJeu().setPremierTour(false);
		
		//Si il n'y à que des bombes à coté de la case, pas de switch
		if(getNbBombeVoisin() == getVoisins().size())
			return;
		
		ArrayList<CaseModele> caseNonBombe = new ArrayList<CaseModele>();
		for(CaseModele caseM : getVoisins()) {
			if(caseM.getValeur() == VarCommun.typeCase.EMPTY.value)
				caseNonBombe.add(caseM);
		}
		
		int random;
		random=(int)(Math.random()*caseNonBombe.size());
		caseNonBombe.get(random).setValeur(VarCommun.typeCase.BOMB.value);
		caseNonBombe.get(random).incrementerVoisin(1);
		caseNonBombe.get(random).setNbBombeVoisin(0);
		
		int nbBombe = 0;
		for(CaseModele caseM : getVoisins()) {
			if(caseM.getValeur() == VarCommun.typeCase.BOMB.value)
				nbBombe++;
		}
		setValeur(VarCommun.typeCase.EMPTY.value);
		incrementerVoisin(-1);
		setNbBombeVoisin(nbBombe);
	}
	
	/** 
	* Clique droit sur la case
	*
	*/
	public void cliqueDroit() {
		if(getEtat() == VarCommun.etatCase.COVER.value) {
			setEtat(VarCommun.etatCase.FLAG.value);
			getModeleJeu().setNbBombeRestante(getModeleJeu().getNbBombeRestante()-1);
		}
		else if(getEtat() == VarCommun.etatCase.FLAG.value) {
			if(getModeleJeu().isAllowQuestion())
				setEtat(VarCommun.etatCase.QUESTION.value);
			else
				setEtat(VarCommun.etatCase.COVER.value);
			getModeleJeu().setNbBombeRestante(getModeleJeu().getNbBombeRestante()+1);
		}
		else if(getEtat() == VarCommun.etatCase.QUESTION.value)
			setEtat(VarCommun.etatCase.COVER.value);
	}

	/** 
	* Clique gauche sur la case
	*
	*/
	public void cliqueGauche() {
		if(getEtat() != VarCommun.etatCase.COVER.value)
			return;
		
		if(getModeleJeu().isPremierTour() 
		&& getValeur() == VarCommun.typeCase.BOMB.value) {
			switchCaseBombe();
		}
		
		if(getValeur() == VarCommun.typeCase.BOMB.value) {
			if(getModeleJeu().isAllowSounds())
				getModeleJeu().getSonBombe().jouer();
			getModeleJeu().setEtat(VarCommun.etatJeu.PERDU.value);
		}
		else {
			setEtat(VarCommun.etatCase.DISCOVER.value);
			if(getNbBombeVoisin() == 0)
				retournerVoisin();
			if(getModeleJeu().isPremierTour())
				getModeleJeu().setPremierTour(false);
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
		int etatPrecedant = this.etat;
		
		this.etat=etat;
		setChanged();
		notifyObservers();
		
		//Incrementer la variable avec laquelle on connait le nombre de cases vides déjà retournées.
		if(etat!=etatPrecedant && etat == VarCommun.etatCase.DISCOVER.value && valeur == VarCommun.typeCase.EMPTY.value )
			modeleJeu.setNbCasesRetournees(modeleJeu.getNbCasesRetournees()+1);
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

	public JeuModele getModeleJeu() {
		return modeleJeu;
	}

	public void setModeleJeu(JeuModele modeleJeu) {
		this.modeleJeu = modeleJeu;
	}
	
	public String toString() {
		return "Index :"+getIndex()+" / valeur :"+getValeur()+" / nbBombe :"+nbBombeVoisin+"\n";
	}
}
