package Controleur;

import java.awt.event.MouseEvent;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.JeuModeleDJ;
import Modele.JeuModeleDJRes;
import Vue.CaseVue;

/**
 * Classe Contrôleur d'une case à deux joueurs en réseau
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class CaseControleurDJRes extends CaseControleurDJ {

	/** 
	* Constructeur
	*
	*/
	public CaseControleurDJRes(CaseModele p_modele, CaseVue p_vue) {
		super(p_modele, p_vue);
	}
	
	/** 
	* Gestion de la souris
	*
	*/
	public void mouseClicked(MouseEvent e) {
		if(getModele().getModeleJeu().isFini())
			return;
		
		if(((JeuModeleDJ)getModele().getModeleJeu()).getJoueurCourant() 
				!= ((JeuModeleDJRes)getModele().getModeleJeu()).getMoi())
			return;
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(getModele().getEtat() != VarCommun.etatCase.COVER.value)
				return;
			
			getModele().setEtat(VarCommun.etatCase.DISCOVER.value);
			if(getModele().getValeur() == VarCommun.typeCase.BOMB.value) {
				((JeuModeleDJRes)getModele().getModeleJeu()).envoyerCase(getModele().getIndex());
				((JeuModeleDJRes)getModele().getModeleJeu()).incrementerScore();
			}
			else {
				if(getModele().getNbBombeVoisin() == 0)
					getModele().retournerVoisin();
				
				JeuModeleDJRes mod = ((JeuModeleDJRes)getModele().getModeleJeu());
				mod.envoyerCase(getModele().getIndex());
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
