package Vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Controleur.CaseControleur;
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
		
		CaseControleur controleur = new CaseControleur(modele, this);
		addMouseListener(controleur);
		
		loadIcon();
		
		this.setIcon(iconCase);
        this.setIconTextGap( - iconCase.getIconWidth() );
        this.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
    private void loadIcon() {
		iconCase = new ImageIcon(ClassLoader.getSystemResource("case.png"));
		iconVide = new ImageIcon(ClassLoader.getSystemResource("vide.png"));
		iconBombe = new ImageIcon(ClassLoader.getSystemResource("bombe.png"));
		iconDrapeau = new ImageIcon(ClassLoader.getSystemResource("drapeau.png"));
		iconQuestion = new ImageIcon(ClassLoader.getSystemResource("question.png"));
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	@Override
	public void update(Observable o, Object arg) {
		switch(modele.getEtat()) {
			case 0 :
				this.setIcon(iconCase);
				break;
			case 1 :
				if(modele.getValeur() == ModeleJeu.typeCase.EMPTY.value) {
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
