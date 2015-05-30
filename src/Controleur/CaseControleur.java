package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.ModeleJeu;
import Vue.CaseVue;

public class CaseControleur implements MouseListener {

	private CaseModele modele;
	private CaseVue vue;
	private ModeleJeu modeleJeu;
	
	public CaseControleur(CaseModele p_modele, CaseVue p_vue, ModeleJeu p_modeleJeu) {
		setModele(p_modele);
		setVue(p_vue);
		setModeleJeu(p_modeleJeu);
	}

	public void switchCaseBombe() {
		System.out.println("Case bombe switchée");
		modeleJeu.setPremierTour(false);
		
		//Si il n'y à que des bombes à coté de la case, pas de switch
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
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() == VarCommun.etatCase.COVER.value)
			{
				
				if(modeleJeu.isPremierTour() 
				&& modele.getValeur() == VarCommun.typeCase.BOMB.value) {
					switchCaseBombe();
				}
				
				if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
					modele.setEtat(VarCommun.etatCase.DISCOVER.value);
					if(modele.getNbBombeVoisin() == 0)
						modele.retournerVoisin();
					if(modeleJeu.isPremierTour())
						modeleJeu.setPremierTour(false);
						
				}
				else
					modeleJeu.setEtat(VarCommun.etatJeu.PERDU.value);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
				modele.setEtat(VarCommun.etatCase.FLAG.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()-1);
			}
			else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
				modele.setEtat(VarCommun.etatCase.QUESTION.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()+1);
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

	public ModeleJeu getModeleJeu() {
		return modeleJeu;
	}

	public void setModeleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}

}
