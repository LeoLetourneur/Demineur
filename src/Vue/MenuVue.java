package Vue;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import Modele.JeuModeleClient;
import Modele.JeuModeleDJ;
import Modele.JeuModeleServeur;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class MenuVue extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4722648722327719604L;
	
	private JPanel contentPane;
	private JPanel panel;
	
	private JButton btn1Joueur;
	private JButton btnLocal;
	private JButton btnReseauServeur;
	private JButton btnReseauClient;
	
	private JSpinner spinnerLigne;
	private JSpinner spinnerColonne;
	private JSpinner spinnerBombe;
	
	private JTextField txtPortServeur;
	private JTextField txtAdresseClient;
	private JTextField txtPortClient;

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
		setSize(530, 430);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		SpinnerModel modelLigne = new SpinnerNumberModel(15, 3, 30, 1);
		SpinnerModel modelColonne = new SpinnerNumberModel(15, 3, 30, 1);
		SpinnerNumberModel modelBombe = new SpinnerNumberModel();
		modelBombe.setValue(80);
		modelBombe.setMinimum(2);
		modelBombe.setStepSize(1);
		contentPane.setLayout(null);
		
		JLabel lblNombreDeLignes = new JLabel("Nombre de lignes");
		lblNombreDeLignes.setBounds(85, 35, 147, 30);
		contentPane.add(lblNombreDeLignes);
		
		spinnerLigne = new JSpinner();
		spinnerLigne.setBounds(298, 35, 147, 30);
		spinnerLigne.setModel(modelLigne);
		contentPane.add(spinnerLigne);
		
		JLabel lblNombreDeColonnes = new JLabel("Nombre de colonnes");
		lblNombreDeColonnes.setBounds(85, 77, 147, 30);
		contentPane.add(lblNombreDeColonnes);
		
		spinnerColonne = new JSpinner();
		spinnerColonne.setBounds(298, 77, 147, 30);
		spinnerColonne.setModel(modelColonne);
		contentPane.add(spinnerColonne);
		
		JLabel lblNombreDeBombes = new JLabel("Nombre de bombes");
		lblNombreDeBombes.setBounds(85, 119, 147, 30);
		contentPane.add(lblNombreDeBombes);
		
		spinnerBombe = new JSpinner();
		spinnerBombe.setBounds(298, 119, 147, 30);
		spinnerBombe.setModel(modelBombe);
		contentPane.add(spinnerBombe);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(85, 172, 360, 230);
		contentPane.add(tabbedPane);
		
		JPanel panelUnJoueur = new JPanel();
		tabbedPane.addTab("Un joueur", null, panelUnJoueur, null);
		panelUnJoueur.setLayout(null);
		
		btn1Joueur = new JButton("Jouer");
		btn1Joueur.setBounds(110, 121, 112, 43);
		btn1Joueur.addActionListener(this);
		panelUnJoueur.add(btn1Joueur);
		
		panel = new JPanel();
		tabbedPane.addTab("Deux joueurs", null, panel, null);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(6, 6, 327, 172);
		panel.add(tabbedPane_2);
		
		JPanel panelLocal = new JPanel();
		tabbedPane_2.addTab("Local", null, panelLocal, null);
		panelLocal.setLayout(null);
		
		btnLocal = new JButton("Jouer");
		btnLocal.setBounds(104, 77, 112, 43);
		btnLocal.addActionListener(this);
		panelLocal.add(btnLocal);
		
		JPanel panelReseau = new JPanel();
		tabbedPane_2.addTab("Réseau", null, panelReseau, null);
		panelReseau.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(6, 6, 294, 114);
		panelReseau.add(tabbedPane_1);
		
		JPanel panelReseauServeur = new JPanel();
		tabbedPane_1.addTab("Serveur", null, panelReseauServeur, null);
		panelReseauServeur.setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(6, 30, 38, 16);
		panelReseauServeur.add(lblPort);
		
		txtPortServeur = new JTextField();
		txtPortServeur.setBounds(56, 24, 113, 28);
		panelReseauServeur.add(txtPortServeur);
		txtPortServeur.setColumns(10);
		
		btnReseauServeur = new JButton("Jouer");
		btnReseauServeur.setBounds(179, 25, 88, 29);
		btnReseauServeur.addActionListener(this);
		panelReseauServeur.add(btnReseauServeur);
		
		JPanel panelReseauClient = new JPanel();
		tabbedPane_1.addTab("Client", null, panelReseauClient, null);
		panelReseauClient.setLayout(null);
		
		txtAdresseClient = new JTextField();
		txtAdresseClient.setColumns(10);
		txtAdresseClient.setBounds(66, 6, 113, 28);
		panelReseauClient.add(txtAdresseClient);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setBounds(6, 12, 58, 16);
		panelReseauClient.add(lblAdresse);
		
		btnReseauClient = new JButton("Jouer");
		btnReseauClient.setBounds(179, 33, 88, 29);
		btnReseauClient.addActionListener(this);
		panelReseauClient.add(btnReseauClient);
		
		txtPortClient = new JTextField();
		txtPortClient.setColumns(10);
		txtPortClient.setBounds(66, 32, 113, 28);
		panelReseauClient.add(txtPortClient);
		
		JLabel label_1 = new JLabel("Port");
		label_1.setBounds(6, 38, 38, 16);
		panelReseauClient.add(label_1);
		
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
		else if(e.getSource() == btnLocal) {
		    JeuModeleDJ model = new JeuModeleDJ(lignes, colonnes, bombes);
			JeuVueDJ view = new JeuVueDJ(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
		else if(e.getSource() == btnReseauServeur) {
		    JeuModeleServeur model = new JeuModeleServeur(lignes, colonnes, bombes);
			JeuVueDJ view = new JeuVueDJ(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
		else if(e.getSource() == btnReseauClient) {
		    JeuModeleClient model = new JeuModeleClient();
		    model.connexion();
			JeuVueDJ view = new JeuVueDJ(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
	}
}
