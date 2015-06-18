package Controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.Timer;

import Commun.VarCommun;
import Modele.JeuModele;
import Modele.JeuModeleDJRes;
import Vue.ParametreVue;
import Vue.JeuVue;

/**
 * Classe Contrôleur du jeu
 * 
 * @author LETOURNEUR Léo
 * @since 2.0
 */
public class JeuControleur implements ActionListener {

	JeuModele modele;
    JeuVue vue;
    
	public JeuControleur(JeuModele p_model, JeuVue p_view) {
		modele = p_model;
		vue = p_view;
		
		vue.mntmNouvellePartie.addActionListener(this);
		vue.mntmSauvegarder.addActionListener(this);
		vue.mntmDecouvrir.addActionListener(this);
		vue.mntmParametres.addActionListener(this);
		vue.mntmQuitter.addActionListener(this);
		
		vue.mntmMario.addActionListener(this);
		vue.mntmCaisse.addActionListener(this);
		vue.mntmDisco.addActionListener(this);
		vue.mntmGolf.addActionListener(this);
		vue.mntmPacman.addActionListener(this);
		
		modele.setTimer(new Timer(1000, this));
	}

	/**
	 * Gestion des événements
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.mntmNouvellePartie) {
			modele.getTimer().stop();
			modele.reinitialiserCase();
			vue.chargerIconeMilieu();
		}
		else if(e.getSource() == vue.mntmSauvegarder) {
			modele.sauvegarde();
		}
		else if(e.getSource() == vue.mntmDecouvrir) {
			modele.setEtat(VarCommun.etatJeu.PERDU.value);
			for(int i=0;i<(modele.getNbColonne()*modele.getNbLigne());i++)
				modele.getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);
		}
		else if(e.getSource() == vue.mntmQuitter) {
			if(modele.isSaveBeforeQuit() && !(modele instanceof JeuModeleDJRes))
				modele.sauvegarde();
			vue.dispose();
		}
		else if(e.getSource() == vue.mntmParametres) {
			modele.getTimer().stop();
			ParametreVue pv = new ParametreVue(modele);
			pv.setVisible(true);
			if(pv.isAccept()) {
				modele.construireCases();
				vue.chargerJeu();
				vue.chargerCasesVueControleur();
				vue.repaint();
			} else
				modele.getTimer().start();
			pv.dispose();
		}
		else if(e.getSource() instanceof Timer) {
			if(modele.isDefiTemps())
				modele.setSecondes(modele.getSecondes()-1);
			else
				modele.setSecondes(modele.getSecondes()+1);
		}
		else
			modele.setThemeJeu(VarCommun.themeJeu.valueOf(((JMenuItem)e.getSource()).getActionCommand()));
	}

}
