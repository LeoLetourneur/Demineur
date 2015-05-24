package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() == ModeleJeu.etatCase.COVER.value) {
				if(modele.getValeur() == ModeleJeu.typeCase.EMPTY.value) {
					modele.setEtat(ModeleJeu.etatCase.DISCOVER.value);
					if(modele.getNbBombeVoisin() == 0)
						modeleJeu.retournerVoisin(modele.getIndex());
				}
				else {
					modeleJeu.retournerBombes();
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			if(modele.getEtat() == ModeleJeu.etatCase.COVER.value) {
				modele.setEtat(ModeleJeu.etatCase.FLAG.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()-1);
			}
			else if(modele.getEtat() == ModeleJeu.etatCase.FLAG.value) {
				modele.setEtat(ModeleJeu.etatCase.QUESTION.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()+1);
			}
			else if(modele.getEtat() == ModeleJeu.etatCase.QUESTION.value)
				modele.setEtat(ModeleJeu.etatCase.COVER.value);
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
