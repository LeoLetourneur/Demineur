package Vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

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
	
	private ModeleJeu model;
	private ArrayList<CaseVue> listeCase;
	
    public VueJeu(ModeleJeu p_model) {
        super();
        model = p_model;
        model.addObserver(this);
        
        buildFrame();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
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
    	
        panelCases = new JPanel (new GridLayout(model.NB_CASE, model.NB_CASE));
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
		int[][] tableauBombe = model.construireGrille();
		bombeRestante.setText(model.getNbBombe()+"");
        Font font = new Font("Courier New", Font.BOLD, 14);
        
        panelCases.removeAll();
        listeCase = new ArrayList<CaseVue>();
        for(int i = 0; i<Math.pow(model.NB_CASE, 2);i++)
        {
        	CaseModele boxModele = new CaseModele(i);
        	CaseVue box = new CaseVue(boxModele);
        	boxModele.setValeur(tableauBombe[i][0]);
        	boxModele.setNbBombeVoisin(tableauBombe[i][1]);
            box.setFont(font);
            box.setForeground(Color.white);
            listeCase.add(box);
            panelCases.add(box);
        }
	}

	
	/*
	private void retournerVoisin(int numero) {
		
		int minI = 0;
		int maxI = 3;
		if(numero%model.NB_CASE < 1)
			minI = 1;
		else if(numero%model.NB_CASE > model.NB_CASE-2)
			maxI = 2;
		
		for(int i=minI;i<maxI;i++)
			for(int j=0;j<3;j++) {
				int index = numero+i-1+(j-1)*model.NB_CASE;
				if(index>=0 && index<Math.pow(model.NB_CASE,2)) {
					if(listeCase.get(index).getNbBombeVoisin() == 0
					&& !listeCase.get(index).isMarque()){
						listeCase.get(index).setMarque(true);
						retournerVoisin(index);
					}
					else {
						listeCase.get(index).setIcon(iconVide);
						listeCase.get(index).setText(listeCase.get(index).getNbBombeVoisin()+"");
					}
				}
			}
	}

	private void retournerBombe() {
		for(CaseVue box : listeCase) {
			if(box.getValeur() == Modele.Case.BOMB.value)
				box.setIcon(iconBombe);
		}
		//looseGame();
	}*/

	private void looseGame() {
		javax.swing.JOptionPane.showMessageDialog(null, "C'est perdu"); 
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO
		
	}

}
