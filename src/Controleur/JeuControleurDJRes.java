package Controleur;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import Commun.VarCommun;
import Modele.JeuModele;
import Vue.ParametreVue;
import Vue.JeuVue;

public class JeuControleurDJRes extends JeuControleur {
    
	public JeuControleurDJRes(JeuModele p_model, JeuVue p_view) {
		super(p_model, p_view);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.mntmNouvellePartie) {
			//TODO
			/*modele.getTimer().stop();
			modele.reinitialiserCase();
			vue.reinitialiser();*/
		}
		else if(e.getSource() == vue.mntmDecouvrir) {
			//TODO
			/*modele.setEtat(VarCommun.etatJeu.PERDU.value);
			for(int i=0;i<(modele.getNbColonne()*modele.getNbLigne());i++)
				modele.getListeCase().get(i).setEtat(VarCommun.etatCase.DISCOVER.value);*/
		}
		else if(e.getSource() == vue.mntmQuitter) {
			//TODO
			vue.dispose();
		}
		else if(e.getSource() == vue.mntmParametres) {
			//TODO
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
		//Tous les thèmes
		else if(e.getSource() instanceof JMenuItem)
			modele.setThemeJeu(VarCommun.themeJeu.valueOf(((JMenuItem)e.getSource()).getActionCommand()));
	}

}
