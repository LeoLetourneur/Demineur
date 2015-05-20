import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Modele;
import Vue.Vue;


public class Controleur implements ActionListener {

	Modele model;
    Vue view;
    
	public Controleur(Modele p_model, Vue p_view) {
		model = p_model;
		view = p_view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
