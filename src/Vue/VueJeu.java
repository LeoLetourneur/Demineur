package Vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Commun.VarCommun;
import Controleur.CaseControleur;
import Modele.CaseModele;
import Modele.ModeleJeu;

import java.awt.Component;

public class VueJeu extends JFrame implements Observer {
	private static final long serialVersionUID = 3267840040749382412L;
	
	private JPanel container;
	private JPanel panelCases;
	private JPanel panelTexte;
	
	private JLabel bombeRestante;
	private JLabel temps;
	private JLabel btnTete;
	
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenu mnTheme;
	public JMenuItem mntmNouvellePartie;
	public JMenuItem mntmDecouvrir;
	public JMenuItem mntmParametres;
	public JMenuItem mntmQuitter;
	public JMenuItem mntmMario;
	public JMenuItem mntmCaisse;
	public JMenuItem mntmDisco;
	public JMenuItem mntmGolf;
	public JMenuItem mntmPacman;
	
	private Icon iconTete;
	private Icon iconPerdu;
	private Icon iconGagne;
	
	private ModeleJeu modele;
	
	
    public VueJeu(ModeleJeu p_model) {
        super();
        modele = p_model;
        modele.addObserver(this);
        
        loadIcons();
        buildFrame();
    }
    
    private void loadIcons() {
    	iconTete = new ImageIcon("sprite/"+modele.getThemeJeu()+"/tete.png");
        iconPerdu = new ImageIcon("sprite/"+modele.getThemeJeu()+"/perdu.png");
        iconGagne = new ImageIcon("sprite/"+modele.getThemeJeu()+"/gagne.png");
    }

	public void buildFrame() {
        
        setTitle("Démineur");
        setSize(800, 592);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	Font font = new Font("Courier New", Font.BOLD, 30);
    	
    	menuBar = new JMenuBar();
    	setJMenuBar(menuBar);
    	
    	mnMenu = new JMenu("Menu");
    	menuBar.add(mnMenu);
    	
    	mntmNouvellePartie = new JMenuItem("Nouvelle partie");
    	mnMenu.add(mntmNouvellePartie);
    	
    	mntmDecouvrir = new JMenuItem("Découvrir");
    	mnMenu.add(mntmDecouvrir);
    	
    	mntmParametres = new JMenuItem("Paramètres");
    	mnMenu.add(mntmParametres);
    	
    	mntmQuitter = new JMenuItem("Quitter");
    	mnMenu.add(mntmQuitter);
    	
    	mnTheme = new JMenu("Thème");
    	menuBar.add(mnTheme);
    	
    	mntmMario = new JMenuItem("Mario");
    	mnTheme.add(mntmMario);
    	
    	mntmCaisse = new JMenuItem("Caisse");
    	mnTheme.add(mntmCaisse);
    	
    	mntmDisco = new JMenuItem("Disco");
    	mnTheme.add(mntmDisco);
    	
    	mntmGolf = new JMenuItem("Golf");
    	mnTheme.add(mntmGolf);
    	
    	mntmPacman = new JMenuItem("Pacman");
    	mnTheme.add(mntmPacman);
    	bombeRestante = new JLabel(modele.getNbBombeRestante()+"");
    	bombeRestante.setFont(font);
    	bombeRestante.setHorizontalAlignment(SwingConstants.CENTER);
    	temps = new JLabel("0");
    	temps.setFont(font);
    	temps.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelTexte = new JPanel (new GridLayout(1, 3));
        panelTexte.setBounds(6, 10, 788, 64);
        
        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_2);
        panelTexte.add(bombeRestante);
        Component horizontalStrut = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut);
        
        btnTete = new JLabel();
        btnTete.setIcon(iconTete);
        btnTete.setHorizontalAlignment(SwingConstants.CENTER);
        panelTexte.add(btnTete);
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_1);
        panelTexte.add(temps);
        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_3);
        
        panelCases = new JPanel (new GridLayout(modele.getNbLigne(), modele.getNbColonne()));
        
        container = new JPanel();
        container.setLayout(null);
        container.add(panelCases);
        container.add(panelTexte);
        setContentPane(container);
        
        chargerJeu();
    }
	
	public void chargerJeu()
	{
		container.remove(panelCases);
		panelCases = new JPanel (new GridLayout(modele.getNbLigne(), modele.getNbColonne()));
        panelCases.setBounds((this.getWidth()/2-14 * modele.getNbColonne()), (this.getHeight()/2-14 * modele.getNbLigne()), 28 * modele.getNbColonne(), 28 * modele.getNbLigne());
        container.add(panelCases);
        
		btnTete.setIcon(iconTete);
        Font font = new Font("Courier New", Font.BOLD, 14);
        for(CaseModele caseM : modele.getListeCase())
        {
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleur caseControleur = new CaseControleur(caseM, caseVue);
            caseVue.setFont(font);
            caseVue.setForeground(Color.white);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && arg.equals("ChangeTheme")){
			loadIcons();
			if(modele.getEtat() == VarCommun.etatJeu.PERDU.value)
				btnTete.setIcon(iconPerdu);
			else if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value)
				btnTete.setIcon(iconGagne);
			else
				btnTete.setIcon(iconTete);
		}
		
		bombeRestante.setText(modele.getNbBombeRestante()+"");
		temps.setText(modele.getSecondes()+"");
		
		if(!modele.isFini()) {
			if(modele.getEtat() == VarCommun.etatJeu.PERDU.value) {
				modele.setFini(true);
				modele.getTimer().stop();
				btnTete.setIcon(iconPerdu);
				JOptionPane.showMessageDialog(null, "Vous avez perdu");
			}
			else if(modele.getEtat() == VarCommun.etatJeu.GAGNE.value) {
				modele.setFini(true);
				modele.getTimer().stop();
				btnTete.setIcon(iconGagne);
				JOptionPane.showMessageDialog(null, "Vous avez gagné");
			}
		}
	}
}
