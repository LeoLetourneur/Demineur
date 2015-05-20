package Vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Controleur.CaseControleur;
import Modele.CaseModele;


public class CaseVue extends JButton implements Observer {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private Icon iconCase;
	private Icon iconVide;
	private Icon iconBombe;
	private Icon iconDrapeau;
	
	private CaseModele modele;
	
	public CaseVue(CaseModele p_modele) {
		super();
		setModele(p_modele);
		modele.addObserver(this);
		
		CaseControleur controleur = new CaseControleur(modele, this);
		addMouseListener(controleur);
		
		iconCase = new ImageIcon(ClassLoader.getSystemResource("case.png"));
		iconVide = new ImageIcon(ClassLoader.getSystemResource("vide.png"));
		iconBombe = new ImageIcon(ClassLoader.getSystemResource("bombe.png"));
		iconDrapeau = new ImageIcon(ClassLoader.getSystemResource("drapeau.png"));
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(modele.isMarque())
			this.setIcon(iconDrapeau);
		else {
			this.setIcon(iconCase);
		}	
	}
}
