package Controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Commun.VarCommun;
import Modele.ModeleJeu;
import Vue.ParametreVue;
import Vue.VueJeu;


public class ControleurJeu implements ActionListener {

	ModeleJeu modele;
    VueJeu vue;
    
	public ControleurJeu(ModeleJeu p_model, VueJeu p_view) {
		modele = p_model;
		vue = p_view;
		
		vue.mntmNouvellePartie.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.mntmNouvellePartie) {
			System.out.println("Rejouer");
			modele.getTimer().stop();
			modele.construireCases();
			vue.chargerJeu();
		}
		else if(e.getSource() == vue.mntmDecouvrir) {
			System.out.println("Découvrir");
			modele.setFini(true);
			for(int i=0;i<(modele.getNbColonne()*modele.getNbLigne());i++)
			{
				modele.getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);
			}
		}
		else if(e.getSource() == vue.mntmQuitter) {
			System.out.println("Quitter");
			System.exit(0);
		}
		else if(e.getSource() == vue.mntmParametres) {
			System.out.println("Parametre");
			ParametreVue pv = new ParametreVue();
			pv.setVisible(true);
		}
		else if(e.getSource() == modele.getTimer()) {
			modele.setSecondes(modele.getSecondes()+1);
		}
		else if(e.getSource() == vue.mntmMario) 
			modele.setThemeJeu(VarCommun.themeJeu.Mario);
		else if(e.getSource() == vue.mntmCaisse)
			modele.setThemeJeu(VarCommun.themeJeu.Caisse);
		else if(e.getSource() == vue.mntmDisco)
			modele.setThemeJeu(VarCommun.themeJeu.Disco);
		else if(e.getSource() == vue.mntmGolf)
			modele.setThemeJeu(VarCommun.themeJeu.Golf);
		else if(e.getSource() == vue.mntmPacman)
			modele.setThemeJeu(VarCommun.themeJeu.Pacman);
	}

}
