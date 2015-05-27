package Vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.ModeleJeu;


public class CaseVue extends JButton implements Observer {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private Icon iconCase;
	private Icon iconVide;
	private Icon iconBombe;
	private Icon iconDrapeau;
	private Icon iconQuestion;
	
	private CaseModele modele;
	
	public CaseVue(CaseModele p_modele) {
		super();
		setModele(p_modele);
		modele.addObserver(this);
		
		loadIcon();
		
		this.setIcon(iconCase);
        this.setIconTextGap( - iconCase.getIconWidth() );
        this.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
    private void loadIcon() {
		iconCase = new ImageIcon("sprite/theme4/case.png");
		iconVide = new ImageIcon("sprite/theme4/vide.png");
		iconBombe = new ImageIcon("sprite/theme4/bombe.png");
		iconDrapeau = new ImageIcon("sprite/theme4/drapeau.png");
		iconQuestion = new ImageIcon("sprite/theme4/question.png");
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	public void update(Observable o, Object arg) {
		switch(modele.getEtat()) {
			case 0 :
				this.setIcon(iconCase);
				break;
			case 1 :
				if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
					this.setIcon(iconVide);
					if(modele.getNbBombeVoisin() != 0)
						this.setText(modele.getNbBombeVoisin()+"");
				}
				else
					this.setIcon(iconBombe);
			    break;
			case 2 :
				this.setIcon(iconDrapeau);
			    break;
			case 3 :
				this.setIcon(iconQuestion);
			    break;
		  default:
		    	break;
		}
		
	}
}
