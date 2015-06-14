package Vue;

import java.util.Observable;

import Controleur.CaseControleurDJRes;
import Modele.CaseModele;
import Modele.JeuModeleDJ;

public class JeuVueDJRes extends JeuVueDJ {
	private static final long serialVersionUID = 7563232298946244699L;

	public JeuVueDJRes(JeuModeleDJ model) {
		super(model);
		mntmSauvegarder.setEnabled(false);
		mntmParametres.setEnabled(false);
    }
	
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
