package Vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Commun.VarCommun;
import Modele.CaseModele;


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
		
		this.setFont(new Font("Courier New", Font.BOLD, 14));
        this.setForeground(Color.white);
		this.setIcon(iconCase);
        this.setIconTextGap( - iconCase.getIconWidth() );
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setMargin(new Insets(0,0,0,0));
	}
	
    private void loadIcon() {
		iconCase = new ImageIcon("sprite/"+modele.getModeleJeu().getThemeJeu()+"/case.png");
		iconVide = new ImageIcon("sprite/"+modele.getModeleJeu().getThemeJeu()+"/vide.png");
		iconBombe = new ImageIcon("sprite/"+modele.getModeleJeu().getThemeJeu()+"/bombe.png");
		iconDrapeau = new ImageIcon("sprite/"+modele.getModeleJeu().getThemeJeu()+"/drapeau.png");
		iconQuestion = new ImageIcon("sprite/"+modele.getModeleJeu().getThemeJeu()+"/question.png");
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	public void update(Observable o, Object arg) {
		if(modele.isChangeTheme()) {
			loadIcon();
			modele.setChangeTheme(false);
		}
		
		if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
			this.setIcon(iconCase);
			this.setText("");
		}
		else if(modele.getEtat() == VarCommun.etatCase.DISCOVER.value) {
			if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
				this.setIcon(iconVide);
				if(modele.getNbBombeVoisin() != 0)
					this.setText(modele.getNbBombeVoisin()+"");
			}
			else
				this.setIcon(iconBombe);
		}
		else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
			this.setIcon(iconDrapeau);
		}
		else if(modele.getEtat() == VarCommun.etatCase.QUESTION.value) {
			this.setIcon(iconQuestion);
		}
	}
}
