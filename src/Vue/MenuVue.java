package Vue;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Controleur.JeuControleur;
import Modele.JeuModele;
import Modele.JeuModeleDJ;

public class MenuVue extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4722648722327719604L;
	
	private JPanel contentPane;
	private JButton btn1Joueur;
	private JButton btn2Joueurs;
	private JSpinner spinnerLigne;
	private JSpinner spinnerColonne;
	private JSpinner spinnerBombe;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Démineur par Léo Letourneur et Cyril Couturier");
					MenuVue frame = new MenuVue();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuVue() {
		setTitle("Menu");
		
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(370, 230);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 2, 0, 0));
		
		SpinnerModel modelLigne = new SpinnerNumberModel(15, 3, 30, 1);
		SpinnerModel modelColonne = new SpinnerNumberModel(15, 3, 30, 1);
		SpinnerModel modelBombe = new SpinnerNumberModel(80, 2, 800, 1);
		
		JLabel lblNombreDeLignes = new JLabel("Nombre de lignes");
		contentPane.add(lblNombreDeLignes);
		
		spinnerLigne = new JSpinner();
		spinnerLigne.setModel(modelLigne);
		contentPane.add(spinnerLigne);
		
		JLabel lblNombreDeColonnes = new JLabel("Nombre de colonnes");
		contentPane.add(lblNombreDeColonnes);
		
		spinnerColonne = new JSpinner();
		spinnerColonne.setModel(modelColonne);
		contentPane.add(spinnerColonne);
		
		JLabel lblNombreDeBombes = new JLabel("Nombre de bombes");
		contentPane.add(lblNombreDeBombes);
		
		spinnerBombe = new JSpinner();
		spinnerBombe.setModel(modelBombe);
		contentPane.add(spinnerBombe);
		
		btn1Joueur = new JButton("1 Joueur");
		btn1Joueur.addActionListener(this);
		contentPane.add(btn1Joueur);
		
		btn2Joueurs = new JButton("2 Joueurs");
		btn2Joueurs.addActionListener(this);
		contentPane.add(btn2Joueurs);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int lignes = (Integer) spinnerLigne.getValue();
		int colonnes = (Integer) spinnerColonne.getValue();
		int bombes = (Integer) spinnerBombe.getValue();
		
		if(lignes*colonnes < bombes) {
			spinnerBombe.setBorder(BorderFactory.createLineBorder(Color.red));
			return;
		}
		
		if(e.getSource() == btn1Joueur) {
		    JeuModele model = new JeuModele(lignes, colonnes, bombes);
			JeuVue view = new JeuVue(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
		else if(e.getSource() == btn2Joueurs) {
		    JeuModeleDJ model = new JeuModeleDJ(lignes, colonnes, bombes);
			JeuVueDJ view = new JeuVueDJ(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
	}

}
