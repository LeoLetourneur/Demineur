package Controleur;

import java.awt.event.MouseEvent;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.JeuModeleDJ;
import Vue.CaseVue;

/**
 * Classe Contrôleur d'une case à deux joueurs
 * 
 * @author LETOURNEUR Léo
 * @since 3.0
 */
public class CaseControleurDJ extends CaseControleur {

	/** 
	* Constructeur
	*
	*/
	public CaseControleurDJ(CaseModele p_modele, CaseVue p_vue) {
		super(p_modele, p_vue);
	}
	
	/** 
	* Gestion de la souris
	*
	*/
	public void mouseClicked(MouseEvent e) {
		if(getModele().getModeleJeu().isFini())
			return;
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(getModele().getEtat() != VarCommun.etatCase.COVER.value)
				return;
			
			if(getModele().getModeleJeu().isPremierTour())
				getModele().getModeleJeu().setPremierTour(false);
			
			getModele().setEtat(VarCommun.etatCase.DISCOVER.value);
			if(getModele().getValeur() == VarCommun.typeCase.BOMB.value) {
				if(getModele().getModeleJeu().isAllowSounds())
					getModele().getModeleJeu().getSonBombe().jouer();
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
