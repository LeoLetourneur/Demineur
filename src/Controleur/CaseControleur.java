package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Commun.VarCommun;
import Modele.CaseModele;
import Vue.CaseVue;

/**
 * Classe Contrôleur d'une case
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
			modele.cliqueGauche();
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			modele.cliqueDroit();
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
