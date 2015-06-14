package Vue;

import java.awt.Color;
import java.util.Observable;

import javax.swing.*;

import Commun.VarCommun;
import Controleur.CaseControleurDJ;
import Modele.CaseModele;
import Modele.JeuModeleDJ;

public class JeuVueDJ extends JeuVue {
	private static final long serialVersionUID = 7563232298946244699L;

	public JeuVueDJ(JeuModeleDJ model) {
        super(model);
        
        changeLabel();
    }

	public void changeLabel() {
		
		labelGauche.setText("J1 : "+((JeuModeleDJ)getModele()).getJoueur1().getScore());
		labelDroit.setText("J2 : "+((JeuModeleDJ)getModele()).getJoueur2().getScore());
		
		if(((JeuModeleDJ)modele).getJoueurCourant() == ((JeuModeleDJ)modele).getJoueur1())
			labelGauche.setForeground(Color.red);
		else
			labelDroit.setForeground(Color.red);
    }
	
	public void chargerCasesVueControleur() {
        for(CaseModele caseM : modele.getListeCase())
        {
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleurDJ caseControleur = new CaseControleurDJ(caseM, caseVue);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && arg.equals("ChangeTheme")){
			loadIcons();
			chargerIconeMilieu();
		}
		
		labelGauche.setText("J1 : "+(((JeuModeleDJ)modele).getJoueur1().getScore()));
		labelDroit.setText("J2 : "+(((JeuModeleDJ)modele).getJoueur2().getScore()));
		
		if(((JeuModeleDJ) modele).getJoueurCourant()==((JeuModeleDJ)modele).getJoueur1())
		{
			labelGauche.setForeground(Color.red);
			labelDroit.setForeground(Color.black);
		}
		else
		{
			labelDroit.setForeground(Color.red);
			labelGauche.setForeground(Color.black);
		}
	
		if(!modele.isFini()) {
			if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value) {
				modele.setFini(true);
				if(modele.isSauvegarde())
					modele.sauvegarde();
				iconeMilieu.setIcon(iconGagne);
				if(modele.isAllowSounds())
					modele.getSonWin().jouer();
				if(((JeuModeleDJ)modele).getJoueur1().getScore() > ((JeuModeleDJ)modele).getJoueur2().getScore())
					JOptionPane.showMessageDialog(null, "Le joueur 1 gagne");
				else if(((JeuModeleDJ)modele).getJoueur1().getScore() < ((JeuModeleDJ)modele).getJoueur2().getScore())
					JOptionPane.showMessageDialog(null, "Le joueur 2 gagne");
				else
					JOptionPane.showMessageDialog(null, "Egalité");
			}
		}
	}
	
	public void reinitialiser() {
		super.reinitialiser();
		labelGauche.setText("J1 : "+(((JeuModeleDJ)modele).getJoueur1().getScore()));
		labelDroit.setText("J2 : "+(((JeuModeleDJ)modele).getJoueur2().getScore()));
	}
	
}
