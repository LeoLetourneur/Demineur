package Vue;

import Controleur.CaseControleurDJRes;
import Modele.CaseModele;
import Modele.JeuModeleDJ;

public class JeuVueDJRes extends JeuVueDJ {
	private static final long serialVersionUID = 7563232298946244699L;

	public JeuVueDJRes(JeuModeleDJ model) {
		super(model);
    }
	
	public void chargerCase() {
        for(CaseModele caseM : modele.getListeCase())
        {
        	caseM.setModeleJeu(modele); //FIX BUG
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleurDJRes caseControleur = new CaseControleurDJRes(caseM, caseVue);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}
}