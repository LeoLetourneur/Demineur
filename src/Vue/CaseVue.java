package Vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import Commun.VarCommun;
import Modele.CaseModele;

/**
 * Classe Vue de la case.
 * 
 * @author COUTURIER Cyril
 * @since 1.0
 */
public class CaseVue extends JButton implements Observer {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private CaseModele modele;
	
	/** 
	* Constructeur
	*
	*/
	public CaseVue(CaseModele p_modele) {
		super();
		setModele(p_modele);
		modele.addObserver(this);
		
		this.setFont(new Font("Courier New", Font.BOLD, 14));
        this.setForeground(Color.white);
		this.setIcon(modele.getModeleJeu().iconCase);
        this.setIconTextGap( - modele.getModeleJeu().iconCase.getIconWidth() );
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setMargin(new Insets(0,0,0,0));
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	public void update(Observable o, Object arg) {
		
		//Réinitialisation (Couvrir la case)
		if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
			this.setIcon(modele.getModeleJeu().iconCase);
			this.setText("");
		} 
		//Découvrir la case
		else if(modele.getEtat() == VarCommun.etatCase.DISCOVER.value) {
			//Case vide
			if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
				this.setIcon(modele.getModeleJeu().iconVide);
				if(modele.getNbBombeVoisin() != 0)
					this.setText(modele.getNbBombeVoisin()+"");
			}
			else //Case contenant une bombe
				this.setIcon(modele.getModeleJeu().iconBombe);
		}
		//Poser un drapeau
		else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
			this.setIcon(modele.getModeleJeu().iconDrapeau);
		}
		//Poser un point d'interrogation
		else if(modele.getEtat() == VarCommun.etatCase.QUESTION.value) {
			this.setIcon(modele.getModeleJeu().iconQuestion);
		}
	}
}
