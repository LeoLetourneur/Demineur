package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import Commun.VarCommun;
import Modele.CaseModele;
import Vue.CaseVue;

/**
 * Classe ContrÃ´leur d'une case
 * 
 * @author COUTURIER Cyril
 * @since 1.0
 */
public class CaseControleur implements MouseListener {

	private CaseModele modele;
	private CaseVue vue;
	
	/** 
	* Constructeur
	*
	*/
	public CaseControleur(CaseModele p_modele, CaseVue p_vue) {
		setModele(p_modele);
		setVue(p_vue);
	}

	/** 
	* Déplacement d'une bombe si l'on clique dessus au premier tour.
	*
	*/
	public void switchCaseBombe() {
		modele.getModeleJeu().setPremierTour(false);
		
		//Si il n'y Ã  que des bombes Ã  coté de la case, pas de switch
		if(modele.getNbBombeVoisin() == modele.getVoisins().size())
			return;
		
		ArrayList<CaseModele> caseNonBombe = new ArrayList<CaseModele>();
		for(CaseModele caseM : modele.getVoisins()) {
			if(caseM.getValeur() == VarCommun.typeCase.EMPTY.value)
				caseNonBombe.add(caseM);
		}
		
		int random;
		random=(int)(Math.random()*caseNonBombe.size());
		caseNonBombe.get(random).setValeur(VarCommun.typeCase.BOMB.value);
		caseNonBombe.get(random).incrementerVoisin(1);
		caseNonBombe.get(random).setNbBombeVoisin(0);
		
		int nbBombe = 0;
		for(CaseModele caseM : modele.getVoisins()) {
			if(caseM.getValeur() == VarCommun.typeCase.BOMB.value)
				nbBombe++;
		}
		modele.setValeur(VarCommun.typeCase.EMPTY.value);
		modele.incrementerVoisin(-1);
		modele.setNbBombeVoisin(nbBombe);
	}
	
	/** 
	* Gestion de la souris
	*
	*/
	public void mouseClicked(MouseEvent e) {
		if(modele.getModeleJeu().isFini())
			return;
		
		if (e.getClickCount() == 2 && !e.isConsumed() && e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() != VarCommun.etatCase.DISCOVER.value)
				return;
		     e.consume();
		     modele.retournerVoisin();
		}
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() != VarCommun.etatCase.COVER.value)
				return;
			
			if(modele.getModeleJeu().isPremierTour() 
			&& modele.getValeur() == VarCommun.typeCase.BOMB.value) {
				switchCaseBombe();
			}
			
			if(modele.getValeur() == VarCommun.typeCase.BOMB.value) {
				if(modele.getModeleJeu().isAllowSounds())
					modele.getModeleJeu().getSonBombe().jouer();
				modele.getModeleJeu().setEtat(VarCommun.etatJeu.PERDU.value);
			}
			else {
				modele.setEtat(VarCommun.etatCase.DISCOVER.value);
				if(modele.getNbBombeVoisin() == 0)
					modele.retournerVoisin();
				if(modele.getModeleJeu().isPremierTour())
					modele.getModeleJeu().setPremierTour(false);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			
			if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
				modele.setEtat(VarCommun.etatCase.FLAG.value);
				modele.getModeleJeu().setNbBombeRestante(modele.getModeleJeu().getNbBombeRestante()-1);
			}
			else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
				if(modele.getModeleJeu().isAllowQuestion())
					modele.setEtat(VarCommun.etatCase.QUESTION.value);
				else
					modele.setEtat(VarCommun.etatCase.COVER.value);
				modele.getModeleJeu().setNbBombeRestante(modele.getModeleJeu().getNbBombeRestante()+1);
			}
			else if(modele.getEtat() == VarCommun.etatCase.QUESTION.value)
				modele.setEtat(VarCommun.etatCase.COVER.value);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}
	
	public CaseVue getVue() {
		return vue;
	}

	public void setVue(CaseVue vue) {
		this.vue = vue;
	}
}
