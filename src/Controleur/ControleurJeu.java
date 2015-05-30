package Controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

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
		
		modele.setTimer(new Timer(1000, this));
		modele.getTimer().start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.mntmNouvellePartie) {
			System.out.println("Rejouer");
			modele.construireCases();
			vue.chargerJeu();
		}
		else if(e.getSource() == vue.mntmDecouvrir) {
			System.out.println("Découvrir");
			ParametreVue pv = new ParametreVue();
			pv.setVisible(true);
		}
		else if(e.getSource() == vue.mntmQuitter) {
			System.out.println("Quitter");
			System.exit(0);
		}
		else if(e.getSource() == vue.mntmParametres) {
			System.out.println("Parametre");
		}
		else if(e.getSource() == modele.getTimer()) {
			modele.setSecondes(modele.getSecondes()+1);
		}
	}

}
