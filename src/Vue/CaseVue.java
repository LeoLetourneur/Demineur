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

/**
 * Classe Vue de la case.
 * 
 * @author COUTURIER Cyril
 * @version 1.0
 */
public class CaseVue extends JButton implements Observer {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private Icon iconCase;
	private Icon iconVide;
	private Icon iconBombe;
	private Icon iconDrapeau;
	private Icon iconQuestion;
	
	private CaseModele modele;
	
	/** 
	* Constructeur
	*
	*/
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
	
	/** 
	* Chargement des icones en fonction du thème
	*
	*/
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
		//Changement de thème
		if(modele.isChangeTheme()) {
			loadIcon();
			modele.setChangeTheme(false);
		}
		
		//Réinitialisation (Couvrir la case)
		if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
			this.setIcon(iconCase);
			this.setText("");
		} 
		//Découvrir la case
		else if(modele.getEtat() == VarCommun.etatCase.DISCOVER.value) {
			//Case vide
			if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
				this.setIcon(iconVide);
				if(modele.getNbBombeVoisin() != 0)
					this.setText(modele.getNbBombeVoisin()+"");
			}
			else //Case contenant une bombe
				this.setIcon(iconBombe);
		}
		//Poser un drapeau
		else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
			this.setIcon(iconDrapeau);
		}
		//Poser un point d'interrogation
		else if(modele.getEtat() == VarCommun.etatCase.QUESTION.value) {
			this.setIcon(iconQuestion);
		}
	}
}
