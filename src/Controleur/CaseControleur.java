package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Modele.CaseModele;
import Modele.ModeleJeu;
import Vue.CaseVue;

public class CaseControleur implements MouseListener {

	private CaseModele modele;
	private CaseVue vue;
	
	public CaseControleur(CaseModele p_modele, CaseVue p_vue) {
		setModele(p_modele);
		setVue(p_vue);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() == ModeleJeu.etatCase.COVER.value) {
				modele.setEtat(ModeleJeu.etatCase.DISCOVER.value);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			if(modele.getEtat() == ModeleJeu.etatCase.COVER.value)
				modele.setEtat(ModeleJeu.etatCase.FLAG.value);
			else if(modele.getEtat() == ModeleJeu.etatCase.FLAG.value)
				modele.setEtat(ModeleJeu.etatCase.QUESTION.value);
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

}
