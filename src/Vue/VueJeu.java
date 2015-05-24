package Vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controleur.CaseControleur;
import Modele.CaseModele;
import Modele.ModeleJeu;

public class VueJeu extends JFrame implements Observer {
	private static final long serialVersionUID = 3267840040749382412L;
	
	private JPanel container;
	private JPanel panelCases;
	private JPanel panelBouton;
	private JPanel panelTexte;
	
	public JButton btnQuitter;
	public JButton btnRejouer;
	public JButton btnDecouvrir;
	
	private JLabel bombeRestante;
	private JLabel temps;
	
	private ModeleJeu modele;
	
    public VueJeu(ModeleJeu p_model) {
        super();
        modele = p_model;
        modele.addObserver(this);
        
        buildFrame();
    }

	public void buildFrame() {
        
        setTitle("Démineur");
        setSize(800, 592);
        setLocationRelativeTo(null);
        setResizable(false);
        
        btnQuitter = new JButton("Quitter");
    	btnRejouer = new JButton("Rejouer");
    	btnDecouvrir = new JButton("Découvrir");
        
    	Font font = new Font("Courier New", Font.BOLD, 30);
    	
    	JLabel lblBombe = new JLabel("BombeRestante");
    	lblBombe.setHorizontalAlignment(SwingConstants.CENTER);
    	bombeRestante = new JLabel("80");
    	bombeRestante.setFont(font);
    	bombeRestante.setHorizontalAlignment(SwingConstants.CENTER);
    	
    	JLabel lblTemps = new JLabel("Temps");
    	lblTemps.setHorizontalAlignment(SwingConstants.CENTER);
    	temps = new JLabel("0");
    	temps.setFont(font);
    	temps.setHorizontalAlignment(SwingConstants.CENTER);
    	
        panelCases = new JPanel (new GridLayout(modele.NB_CASE, modele.NB_CASE));
        panelCases.setBounds(0, 0, 570, 570);
        chargerJeu();
        
        panelBouton = new JPanel (new GridLayout(2, 2));
        panelBouton.setBounds(580, 360, 210, 200);
        panelBouton.add(btnRejouer);
        panelBouton.add(btnQuitter);
        panelBouton.add(btnDecouvrir);
        
        panelTexte = new JPanel (new GridLayout(4, 1));
        panelTexte.setBounds(580, 10, 210, 300);
        panelTexte.add(lblBombe);
        panelTexte.add(bombeRestante);
        panelTexte.add(lblTemps);
        panelTexte.add(temps);
        
        container = new JPanel();
        container.setLayout(null);
        container.add(panelCases);
        container.add(panelBouton);
        container.add(panelTexte);
        setContentPane(container);
    }
	
	public void chargerJeu()
	{
		int[][] tableauBombe = modele.construireGrille();
		bombeRestante.setText(modele.getNbBombe()+"");
        Font font = new Font("Courier New", Font.BOLD, 14);
        
        panelCases.removeAll();
        
        for(int i = 0; i<Math.pow(modele.NB_CASE, 2);i++)
        {
        	CaseModele caseModele = new CaseModele(i);
        	CaseVue caseVue = new CaseVue(caseModele);
        	CaseControleur caseControleur = new CaseControleur(caseModele, caseVue, modele);
        	caseModele.setValeur(tableauBombe[i][0]);
        	caseModele.setNbBombeVoisin(tableauBombe[i][1]);
            caseVue.setFont(font);
            caseVue.setForeground(Color.white);
            caseVue.addMouseListener(caseControleur);
            modele.getListeCase().add(caseModele);
            panelCases.add(caseVue);
        }
	}

	@Override
	public void update(Observable o, Object arg) {
		bombeRestante.setText(modele.getNbBombe()+"");
		temps.setText(modele.getSecondes()+"");
	}

}
