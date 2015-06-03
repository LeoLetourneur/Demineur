package Controleur;

import java.awt.event.MouseEvent;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.JeuModeleDJ;
import Vue.CaseVue;

public class CaseControleurDJ extends CaseControleur {


	public CaseControleurDJ(CaseModele p_modele, CaseVue p_vue) {
		super(p_modele, p_vue);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(getModele().getModeleJeu().isFini())
			return;
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(getModele().getEtat() != VarCommun.etatCase.COVER.value)
				return;
			
			getModele().setEtat(VarCommun.etatCase.DISCOVER.value);
			if(getModele().getValeur() == VarCommun.typeCase.BOMB.value) {
				((JeuModeleDJ)getModele().getModeleJeu()).incrementerScore();
			}
			else {
				if(getModele().getNbBombeVoisin() == 0)
					getModele().retournerVoisin();
				
				JeuModeleDJ mod = ((JeuModeleDJ)getModele().getModeleJeu());
				if(mod.getJoueurCourant() ==  mod.getJoueur1())
					mod.setJoueurCourant(mod.getJoueur2());
				else
					mod.setJoueurCourant(mod.getJoueur1());
			}
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
}
