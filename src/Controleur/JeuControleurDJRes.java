package Controleur;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import Commun.VarCommun;
import Modele.JeuModeleDJ;
import Modele.JeuModeleDJRes;
import Vue.JeuVueDJRes;

/**
 * Classe Contrôleur du jeu à deux joueurs en réseau
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class JeuControleurDJRes extends JeuControleur {
    
	/**
	 * Constructeur
	 * 
	 */
	public JeuControleurDJRes(JeuModeleDJRes p_model, JeuVueDJRes p_view) {
		super(p_model, p_view);
	}

	/**
	 * Gestion des évènements
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.mntmNouvellePartie) {
			modele.reinitialiserCase();
			((JeuModeleDJRes)modele).envoyerNouvellePartie();
			((JeuModeleDJRes)modele).envoiEnsembleCases();
			vue.chargerIconeMilieu();
			
			if(((JeuModeleDJRes) modele).getMoi() == ((JeuModeleDJ) modele).getJoueur1())
				((JeuModeleDJRes) modele).setJoueurCourant(((JeuModeleDJRes) modele).getJoueur2());
			else
				((JeuModeleDJRes) modele).setJoueurCourant(((JeuModeleDJRes) modele).getJoueur1());
		}
		else if(e.getSource() == vue.mntmDecouvrir) {
			((JeuModeleDJRes)modele).envoyerDecouvrir();
			modele.setFini(true);
			modele.setEtat(VarCommun.etatJeu.PERDU.value);
			for(int i=0;i<(modele.getNbColonne()*modele.getNbLigne());i++)
				modele.getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);
		}
		else if(e.getSource() == vue.mntmQuitter) {
			((JeuModeleDJRes)modele).envoyerQuitter();
			vue.dispose();
		}
		//Tous les thèmes
		else if(e.getSource() instanceof JMenuItem)
			modele.setThemeJeu(VarCommun.themeJeu.valueOf(((JMenuItem)e.getSource()).getActionCommand()));
	}

}
