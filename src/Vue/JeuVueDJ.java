package Vue;

import java.awt.Color;
import java.awt.Font;
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
        
        buildFrame();
    }

	public void buildFrame() {
        
        super.buildFrame();
        
		labelGauche.setText("J1 : "+((JeuModeleDJ)getModele()).getJoueur1().getScore());
		labelGauche.setForeground(Color.red);
		labelDroit.setText("J2 : "+((JeuModeleDJ)getModele()).getJoueur2().getScore());
    }
	
	public void chargerCase() {
		Font font = new Font("Courier New", Font.BOLD, 14);
        for(CaseModele caseM : modele.getListeCase())
        {
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleurDJ caseControleur = new CaseControleurDJ(caseM, caseVue);
            caseVue.setFont(font);
            caseVue.setForeground(Color.white);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && arg.equals("ChangeTheme")){
			loadIcons();
			if(modele.getEtat() == VarCommun.etatJeu.PERDU.value)
				iconeMilieu.setIcon(iconPerdu);
			else if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value)
				iconeMilieu.setIcon(iconGagne);
			else
				iconeMilieu.setIcon(iconTete);
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
				iconeMilieu.setIcon(iconGagne);
				
				if(((JeuModeleDJ)modele).getJoueur1().getScore() > ((JeuModeleDJ)modele).getJoueur2().getScore())
					JOptionPane.showMessageDialog(null, "Le joueur 1 gagne");
				else if(((JeuModeleDJ)modele).getJoueur1().getScore() < ((JeuModeleDJ)modele).getJoueur2().getScore())
					JOptionPane.showMessageDialog(null, "Le joueur 2 gagne");
				else
					JOptionPane.showMessageDialog(null, "Egalité");
			}
		}
	}
}
