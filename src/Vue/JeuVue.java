package Vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Commun.VarCommun;
import Controleur.CaseControleur;
import Modele.CaseModele;
import Modele.JeuModele;
import Modele.JeuModeleDJ;
import Modele.Partie;

import java.awt.Component;

/**
 * Classe Vue du jeu.
 * 
 * @author LETOURNEUR Léo
 * @since 1.0
 */
public class JeuVue extends JFrame implements Observer {
	private static final long serialVersionUID = 3267840040749382412L;
	
	protected JPanel container;
	protected JPanel panelCases;
	protected JPanel panelTexte;
	JScrollPane scroller;
	
	protected JLabel labelGauche;
	protected JLabel labelDroit;
	protected JLabel iconeMilieu;
	
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenu mnTheme;
	public JMenuItem mntmNouvellePartie;
	public JMenuItem mntmSauvegarder;
	public JMenuItem mntmDecouvrir;
	public JMenuItem mntmParametres;
	public JMenuItem mntmQuitter;
	public JMenuItem mntmMario;
	public JMenuItem mntmCaisse;
	public JMenuItem mntmDisco;
	public JMenuItem mntmGolf;
	public JMenuItem mntmPacman;
	
	protected Icon iconTete;
	protected Icon iconPerdu;
	protected Icon iconGagne;
	
	protected JeuModele modele;

	public JeuVue(JeuModele p_model) {
        super();
        modele = p_model;
        modele.addObserver(this);
        
        loadIcons();
        buildFrame();
    }
    
	/** 
	* Chargement des icones en fonction du thème
	*
	*/
    protected void loadIcons() {
    	iconTete = new ImageIcon("sprite/"+modele.getThemeJeu()+"/tete.png");
        iconPerdu = new ImageIcon("sprite/"+modele.getThemeJeu()+"/perdu.png");
        iconGagne = new ImageIcon("sprite/"+modele.getThemeJeu()+"/gagne.png");
    }

    /** 
	* Construction de la fenêtre
	*
	*/
	public void buildFrame() {
        
        setTitle("Démineur");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    	Font font = new Font("Courier New", Font.BOLD, 30);
    	
    	menuBar = new JMenuBar();
    	setJMenuBar(menuBar);
    	
    	mnMenu = new JMenu("Menu");
    	menuBar.add(mnMenu);
    	
    	mntmNouvellePartie = new JMenuItem("Nouvelle partie");
    	mnMenu.add(mntmNouvellePartie);
    	
    	mntmSauvegarder = new JMenuItem("Sauvegarder");
    	mnMenu.add(mntmSauvegarder);
    	
    	mntmDecouvrir = new JMenuItem("Découvrir");
    	mnMenu.add(mntmDecouvrir);
    	
    	mntmParametres = new JMenuItem("Paramètres");
    	mnMenu.add(mntmParametres);
    	
    	mntmQuitter = new JMenuItem("Quitter");
    	mnMenu.add(mntmQuitter);
    	
    	mnTheme = new JMenu("Thème");
    	menuBar.add(mnTheme);
    	
    	mntmMario = new JMenuItem("Mario");
    	mntmMario.setActionCommand("Mario");
    	mnTheme.add(mntmMario);
    	
    	mntmCaisse = new JMenuItem("Caisse");
    	mntmCaisse.setActionCommand("Caisse");
    	mnTheme.add(mntmCaisse);
    	
    	mntmDisco = new JMenuItem("Disco");
    	mntmDisco.setActionCommand("Disco");
    	mnTheme.add(mntmDisco);
    	
    	mntmGolf = new JMenuItem("Golf");
    	mntmGolf.setActionCommand("Golf");
    	mnTheme.add(mntmGolf);
    	
    	mntmPacman = new JMenuItem("Pacman");
    	mntmPacman.setActionCommand("Pacman");
    	mnTheme.add(mntmPacman);
    	labelGauche = new JLabel(modele.getNbBombeRestante()+"");
    	labelGauche.setFont(font);
    	labelGauche.setHorizontalAlignment(SwingConstants.CENTER);
    	labelDroit = new JLabel(modele.getSecondes()+"");
    	labelDroit.setFont(font);
    	labelDroit.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelTexte = new JPanel (new GridLayout(1, 3));
        panelTexte.setBounds(6, 10, 888, 50);
        
        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_2);
        panelTexte.add(labelGauche);
        Component horizontalStrut = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut);
        
        iconeMilieu = new JLabel();
        iconeMilieu.setIcon(iconTete);
        iconeMilieu.setHorizontalAlignment(SwingConstants.CENTER);
        panelTexte.add(iconeMilieu);
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_1);
        panelTexte.add(labelDroit);
        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_3);
        
        panelCases = new JPanel (new GridLayout(modele.getNbLigne(), modele.getNbColonne()));
        scroller = new JScrollPane(panelCases);
        scroller.setSize(modele.getNbColonne()*28+4, modele.getNbLigne()*28+5);
        
        container = new JPanel();
        container.setLayout(null);
        //container.add(panelCases);
        container.add(scroller);
        container.add(panelTexte);
        setContentPane(container);
        
        chargerJeu();
        chargerCasesVueControleur();
    }
	
	/** 
	* Chargement de la Vue et du controleur des cases en fonction des modèles.
	*
	*/
	public void chargerCasesVueControleur() {
        for(CaseModele caseM : modele.getListeCase())
        {
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleur caseControleur = new CaseControleur(caseM, caseVue);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}

	/** 
	* Réinitialisation du panel de case
	*
	*/
	public void chargerJeu()
	{
		getLabelDroit().setVisible((modele.isAllowTime() || modele instanceof JeuModeleDJ));
		
		container.remove(scroller);
		panelCases = new JPanel (new GridLayout(modele.getNbLigne(), modele.getNbColonne()));
		scroller = new JScrollPane(panelCases);
        container.add(scroller);
        
        chargerIconeMilieu();
        
        resize();
	}
	
	private void resize() {
		
		int col = modele.getNbColonne();
		int ligne = modele.getNbLigne();
		if(col > 30) col = 30;
		if(ligne > 16) ligne = 16;
		
		setSize(col*28 + 100, ligne*28 + 120);
		panelTexte.setBounds(5, 5, getSize().width-10, 50);
		panelCases.setPreferredSize(new Dimension(28 * modele.getNbColonne(), 28 * modele.getNbLigne()));
		scroller.setBounds((this.getWidth()/2-14 * col), 60, 28 * col+4, 28 * ligne+5);
		
	}

	/** 
	* Charger l'icone du milieu en fonction de l'état du jeu
	*
	*/
	public void chargerIconeMilieu() {
		if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value)
        	iconeMilieu.setIcon(iconGagne);
        else if(modele.getEtat() == VarCommun.etatJeu.PERDU.value)
        	iconeMilieu.setIcon(iconPerdu);
        else
        	iconeMilieu.setIcon(iconTete);
	}

	@Override
	public void update(Observable o, Object arg) {
		//Changement de thème
		if(arg != null && arg.equals("ChangeTheme")){
			loadIcons();
			chargerIconeMilieu();
		}
		
		//Mise à jour des labels
		labelGauche.setText(modele.getNbBombeRestante()+"");
		labelDroit.setText(modele.getSecondes()+"");
		
		//Fin de partie
		if(!modele.isFini()) {
			if(modele.getEtat() == VarCommun.etatJeu.PERDU.value) {
				modele.setFini(true);
				modele.getTimer().stop();
				iconeMilieu.setIcon(iconPerdu);
				if(modele.isSauvegarde())
					modele.sauvegarde();
				JOptionPane.showMessageDialog(null, "Vous avez perdu");
			}
			else if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value) {
				modele.setFini(true);
				modele.getTimer().stop();
				iconeMilieu.setIcon(iconGagne);
				if(modele.isDefiTemps())
					modele.setSecondes(modele.getSecondesDefi()-modele.getSecondes());
				if(modele.isSauvegarde())
					modele.sauvegarde();
				Partie.ecritureXML(modele,"fichier/scoreXML.xml");
				if(modele.isAllowSounds())
					modele.getSonWin().jouer();
				JOptionPane.showMessageDialog(null, "Vous avez gagné");
			}
		}
			
	}
	
	 public JeuModele getModele() {
		return modele;
	}

	public void setModele(JeuModele modele) {
		this.modele = modele;
	}
	
	public JLabel getLabelDroit() {
		return labelDroit;
	}

	public void setLabelDroit(JLabel labelDroit) {
		this.labelDroit = labelDroit;
	}
}
