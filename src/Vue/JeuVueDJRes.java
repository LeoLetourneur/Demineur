package Vue;

import java.util.Observable;

import Controleur.CaseControleurDJRes;
import Modele.CaseModele;
import Modele.JeuModeleDJ;

/**
 * Classe Vue du jeu pour deux joueurs en réseau.
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class JeuVueDJRes extends JeuVueDJ {
	private static final long serialVersionUID = 7563232298946244699L;

	/** 
	* Constructeur
	*
	*/
	public JeuVueDJRes(JeuModeleDJ model) {
		super(model);
		mntmSauvegarder.setEnabled(false);
		mntmParametres.setEnabled(false);
    }
	
	/** 
	* Chargement des cases pour deux joueurs en réseau
	*
	*/
	public void chargerCasesVueControleur() {
        for(CaseModele caseM : modele.getListeCase())
        {
        	caseM.setModeleJeu(modele); //FIX BUG
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleurDJRes caseControleur = new CaseControleurDJRes(caseM, caseVue);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}
	
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		if(modele.isFini())
			chargerIconeMilieu();
	}
}
