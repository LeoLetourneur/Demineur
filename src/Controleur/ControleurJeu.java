package Controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.ModeleJeu;
import Vue.VueJeu;


public class ControleurJeu implements ActionListener {

	ModeleJeu modele;
    VueJeu vue;
    
	public ControleurJeu(ModeleJeu p_model, VueJeu p_view) {
		modele = p_model;
		vue = p_view;
		
		vue.btnDecouvrir.addActionListener(this);
		vue.btnQuitter.addActionListener(this);
		vue.btnRejouer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == vue.btnRejouer)
			System.out.println("Rejouer");
		else if(e.getSource() == vue.btnQuitter)
			System.out.println("Quitter");
	}

}
