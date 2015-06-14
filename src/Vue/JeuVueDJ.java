package Vue;

import java.awt.Color;
import java.util.Observable;

import javax.swing.*;

import Commun.VarCommun;
import Controleur.CaseControleurDJ;
import Modele.CaseModele;
import Modele.JeuModeleDJ;

/**
 * Classe Vue du jeu pour deux joueurs.
 * 
 * @author LETOURNEUR Léo
 * @version 3.0
 */
public class JeuVueDJ extends JeuVue {
	private static final long serialVersionUID = 7563232298946244699L;

	/** 
	* Constructeur
	*
	*/
	public JeuVueDJ(JeuModeleDJ model) {
        super(model);
        
        changeLabel();
    }

	/** 
	* Changement des labels pour afficher les scores des deux joueurs
	*
	*/
	public void changeLabel() {
		
		//TODO Voir pour supprimer la méthode
		chargerIconeMilieu();
		
		if(((JeuModeleDJ)modele).getJoueurCourant() == ((JeuModeleDJ)modele).getJoueur1())
			labelGauche.setForeground(Color.red);
		else
			labelDroit.setForeground(Color.red);
    }
	
	/** 
	* Chargement des cases pour deux joueurs
	*
	*/
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
		//Changement de thème
		if(arg != null && arg.equals("ChangeTheme")){
			loadIcons();
			chargerIconeMilieu();
		}
		
		//Mise à jour des labels
		labelGauche.setText("J1 : "+(((JeuModeleDJ)modele).getJoueur1().getScore()));
		labelDroit.setText("J2 : "+(((JeuModeleDJ)modele).getJoueur2().getScore()));
		
		//Mise à jour graphique du joueur courant
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
	
		//Fin de partie
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
	
	public void chargerIconeMilieu() {
		super.chargerIconeMilieu();
		labelGauche.setText("J1 : "+(((JeuModeleDJ)modele).getJoueur1().getScore()));
		labelDroit.setText("J2 : "+(((JeuModeleDJ)modele).getJoueur2().getScore()));
	}
	
}
