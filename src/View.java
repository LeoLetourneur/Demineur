import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class View extends JFrame implements ActionListener, MouseListener, Observer {
	private static final long serialVersionUID = 3267840040749382412L;
	
	private Icon iconCase;
	private Icon iconVide;
	private Icon iconBombe;
	private Icon iconDrapeau;
	
	private JPanel container;
	private JPanel panelCases;
	private JPanel panelBouton;
	private JPanel panelTexte;
	
	private JButton btnQuitter;
	private JButton btnRejouer;
	private JButton btnDecouvrir;
	
	private JLabel bombeRestante;
	private JLabel temps;
	
	private Model model;
	private ArrayList<Case> listeCase;
	
    public View(Model p_model) {
        super();
        model = p_model;
        model.addObserver(this);
        
        loadIcon();
        buildFrame();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }
    
    private void loadIcon() {
		iconCase = new ImageIcon(ClassLoader.getSystemResource("case.png"));
		iconVide = new ImageIcon(ClassLoader.getSystemResource("vide.png"));
		iconBombe = new ImageIcon(ClassLoader.getSystemResource("bombe.png"));
		iconDrapeau = new ImageIcon(ClassLoader.getSystemResource("drapeau.png"));
	}

	public void buildFrame() {
        
        setTitle("Démineur");
        setSize(800, 592);
        setLocationRelativeTo(null);
        setResizable(false);
        
        btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(this);
        
    	btnRejouer = new JButton("Rejouer");
    	btnRejouer.addActionListener(this);
    	
    	btnDecouvrir = new JButton("Découvrir");
    	btnDecouvrir.addActionListener(this);
        
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
        listeCase = new ArrayList<Case>();
        for(int i = 0; i<Math.pow(model.NB_CASE, 2);i++)
        {
        	Case box = new Case(i);
        	box.setValeur(tableauBombe[i][0]);
        	box.setNbBombeVoisin(tableauBombe[i][1]);
        	box.setIcon(iconCase);
            box.setIconTextGap( - iconCase.getIconWidth() );
            box.setHorizontalTextPosition(SwingConstants.CENTER);
            box.setFont(font);
            box.setForeground(Color.white);
            box.addActionListener(this);
            box.addMouseListener(this);
            listeCase.add(box);
            panelCases.add(box);
        }
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnRejouer)
			rejouer();
		else if(e.getSource() == btnQuitter)
			this.dispose();
		
		else if(e.getSource() instanceof Case) {
			Case button = (Case)e.getSource();
			int command = button.getValeur();
			if(command == Model.Case.EMPTY.value) {
				button.setIcon(iconVide);
				button.setMarque(true);
				if(button.getNbBombeVoisin() != 0)
					button.setText(button.getNbBombeVoisin()+"");
				else {
					retournerVoisin(button.getNumero());
				}
			}
			else {
				button.setIcon(iconBombe);
				retournerBombe();
			}
		}	
	}
	
	private void rejouer() {
		chargerJeu();
	}

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
						if(listeCase.get(index).getNbBombeVoisin() != 0)
							listeCase.get(index).setText(listeCase.get(index).getNbBombeVoisin()+"");
					}
				}
			}
	}

	private void retournerBombe() {
		for(Case box : listeCase) {
			if(box.getValeur() == Model.Case.BOMB.value)
				box.setIcon(iconBombe);
		}
		looseGame();
	}

	private void looseGame() {
		javax.swing.JOptionPane.showMessageDialog(null, "C'est perdu"); 
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) {
            if(e.getComponent() instanceof Case) {
            	((Case)e.getComponent()).setIcon(iconDrapeau);
            	model.setNbBombe(model.getNbBombe()-1);
            	bombeRestante.setText(model.getNbBombe()+"");
            }
        }
    }

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
