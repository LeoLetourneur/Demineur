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
	public JMenuItem mntmBase;
	public JMenuItem mntmCaisse;
	public JMenuItem mntmDisco;
	public JMenuItem mntmGolf;
	public JMenuItem mntmPacman;
	
	private Icon iconTete;
	private Icon iconPerdu;
	
	private ModeleJeu modele;
	
	
    public VueJeu(ModeleJeu p_model) {
        super();
        modele = p_model;
        modele.addObserver(this);
        
        iconTete = new ImageIcon("sprite/theme1/tete.png");
        iconPerdu = new ImageIcon("sprite/theme1/perdu.png");
		
        buildFrame();
    }

	public void buildFrame() {
        
        setTitle("Démineur");
        setSize(800, 592);
        setLocationRelativeTo(null);
        setResizable(false);
        
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
    	
    	mntmBase = new JMenuItem("Base");
    	mnTheme.add(mntmBase);
    	
    	mntmCaisse = new JMenuItem("Caisse");
    	mnTheme.add(mntmCaisse);
    	
    	mntmDisco = new JMenuItem("Disco");
    	mnTheme.add(mntmDisco);
    	
    	mntmGolf = new JMenuItem("Golf");
    	mnTheme.add(mntmGolf);
    	
    	mntmPacman = new JMenuItem("Pacman");
    	mnTheme.add(mntmPacman);
    	bombeRestante = new JLabel("80");
    	bombeRestante.setFont(font);
    	bombeRestante.setHorizontalAlignment(SwingConstants.CENTER);
    	temps = new JLabel("0");
    	temps.setFont(font);
    	temps.setHorizontalAlignment(SwingConstants.CENTER);
    	
        panelCases = new JPanel (new GridLayout(VarCommun.NB_CASE, VarCommun.NB_CASE));
        panelCases.setBounds(192, 86, 28 * VarCommun.NB_CASE, 28 * VarCommun.NB_CASE);
        
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
        
        container = new JPanel();
        container.setLayout(null);
        container.add(panelCases);
        container.add(panelTexte);
        
        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        panelTexte.add(horizontalStrut_3);
        setContentPane(container);
        
        chargerJeu();
    }
	
	public void chargerJeu()
	{
		bombeRestante.setText(modele.getNbBombe()+"");
        Font font = new Font("Courier New", Font.BOLD, 14);
        
        panelCases.removeAll();
        for(CaseModele caseM : modele.getListeCase())
        {
        	CaseVue caseVue = new CaseVue(caseM);
        	CaseControleur caseControleur = new CaseControleur(caseM, caseVue, modele);
            caseVue.setFont(font);
            caseVue.setForeground(Color.white);
            caseVue.addMouseListener(caseControleur);
            panelCases.add(caseVue);
        }
	}

	@Override
	public void update(Observable o, Object arg) {
		if(modele.getEtat() != VarCommun.etatJeu.PERDU.value) {
			bombeRestante.setText(modele.getNbBombe()+"");
			temps.setText(modele.getSecondes()+"");
		}
		else {
			modele.getTimer().stop();
			btnTete.setIcon(iconPerdu);
			JOptionPane.showMessageDialog(null, "Vous avez perdu");
		}
	}
}
