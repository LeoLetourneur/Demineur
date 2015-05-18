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
        setSize(800, 615);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Quitter");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        /*menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem);*/
      
        JPanel container = new JPanel();
        container.setLayout(null);
        setContentPane(container);
        
        JPanel pan = new JPanel (new GridLayout(model.NB_CASE, model.NB_CASE));
        pan.setSize(570, 570);
        
        int[][] tableauBombe = model.construireGrille();
        
        Font font = new Font("Courier New", Font.BOLD, 14);
        
        listeCase = new ArrayList<Case>();
        for(int i = 0; i<Math.pow(model.NB_CASE, 2);i++){
        	
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
            pan.add(box);
        }
        container.add(pan);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof Case) {
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
				System.out.println("Index : "+index);
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
