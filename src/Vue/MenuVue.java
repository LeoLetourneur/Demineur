package Vue;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Commun.VarCommun;
import Controleur.JeuControleur;
import Controleur.JeuControleurDJRes;
import Modele.CaseModele;
import Modele.JeuModele;
import Modele.JeuModeleClient;
import Modele.JeuModeleDJ;
import Modele.JeuModeleServeur;
import Modele.RecevoirRes;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSlider;

/**
 * Classe de lancement du jeu.
 * Classe représentant la vue et le controleur du Menu.
 * 
 * @author LETOURNEUR Léo
 * @since 3.0
 */
public class MenuVue extends JFrame implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 4722648722327719604L;
	
	private JPanel contentPane;
	private JPanel panelNiveau;
	private JTabbedPane tpReseauClient;
	
	private JButton btn1Joueur;
	private JButton btnCharger;
	private JButton btnLocal;
	private JButton btnReseauServeur;
	private JButton btnReseauClient;
	private JButton btnCharger2JLocal;
	
	private JSpinner spinnerLigne;
	private JSpinner spinnerColonne;
	private JSpinner spinnerBombe;
	private JSpinner spinnerTemps;
	private JSlider slider;
	
	private JTextField txtPortServeur;
	private JTextField txtAdresseClient;
	private JTextField txtPortClient;
	
	private JCheckBox cbDefiTemps;
	private JCheckBox cbUseInterrogation;
	private JCheckBox cbUseTemps;
	private JCheckBox cbUseSounds;
	private JCheckBox cbSaveGameBefore;
	private JCheckBox cbUseSoundsDJ;
	private JCheckBox cbSaveBeforeDJ;

	/** Lancement du jeu.
    * 
    * @param args
    *            Paramêtres de la fonction main.
    */
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

	/** 
	* Constructeur du menu
	*
	*/
	public MenuVue() {
		setTitle("Menu");
		
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(644, 391);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		SpinnerModel modelLigne = new SpinnerNumberModel(9, 3, 30, 1);
		SpinnerModel modelColonne = new SpinnerNumberModel(9, 3, 30, 1);
		SpinnerNumberModel modelBombe = new SpinnerNumberModel();
		modelBombe.setValue(10);
		modelBombe.setMinimum(2);
		modelBombe.setStepSize(1);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(43, 118, 360, 230);
		contentPane.add(tabbedPane);
		
		JPanel panelUnJoueur = new JPanel();
		tabbedPane.addTab("Un joueur", null, panelUnJoueur, null);
		panelUnJoueur.setLayout(null);
		
		btn1Joueur = new JButton("Jouer");
		btn1Joueur.setBounds(204, 135, 112, 43);
		btn1Joueur.addActionListener(this);
		panelUnJoueur.add(btn1Joueur);
		
		cbUseTemps = new JCheckBox("Utiliser le temps");
		cbUseTemps.setSelected(true);
		cbUseTemps.addActionListener(this);
		cbUseTemps.setBounds(6, 30, 327, 23);
		panelUnJoueur.add(cbUseTemps);
		
		cbUseInterrogation = new JCheckBox("Utiliser le point d'interrogation");
		cbUseInterrogation.setSelected(true);
		cbUseInterrogation.setBounds(6, 6, 327, 23);
		panelUnJoueur.add(cbUseInterrogation);
		
		cbDefiTemps = new JCheckBox("Défi temps (secondes)");
		cbDefiTemps.setBounds(6, 54, 178, 23);
		cbDefiTemps.addActionListener(this);
		panelUnJoueur.add(cbDefiTemps);
		
		SpinnerNumberModel modelTemps = new SpinnerNumberModel(120, 10, 9999999, 1);
		
		spinnerTemps = new JSpinner();
		spinnerTemps.setBounds(196, 52, 120, 28);
		spinnerTemps.setModel(modelTemps);
		spinnerTemps.setEnabled(false);
		panelUnJoueur.add(spinnerTemps);
		
		btnCharger = new JButton("Charger");
		btnCharger.setBounds(20, 135, 112, 43);
		btnCharger.addActionListener(this);
		panelUnJoueur.add(btnCharger);
		
		cbUseSounds = new JCheckBox("Utiliser les sons");
		cbUseSounds.setBounds(6, 78, 327, 23);
		panelUnJoueur.add(cbUseSounds);
		
		cbSaveGameBefore = new JCheckBox("Sauvegarder avant de quitter");
		cbSaveGameBefore.setBounds(6, 102, 327, 23);
		panelUnJoueur.add(cbSaveGameBefore);
		
		JPanel panelDJ = new JPanel();
		tabbedPane.addTab("Deux joueurs", null, panelDJ, null);
		panelDJ.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(6, 6, 327, 172);
		panelDJ.add(tabbedPane_2);
		
		JPanel panelLocal = new JPanel();
		tabbedPane_2.addTab("Local", null, panelLocal, null);
		panelLocal.setLayout(null);
		
		btnLocal = new JButton("Jouer");
		btnLocal.setBounds(188, 77, 112, 43);
		btnLocal.addActionListener(this);
		panelLocal.add(btnLocal);
		
		btnCharger2JLocal = new JButton("Charger");
		btnCharger2JLocal.setBounds(6, 77, 112, 43);
		btnCharger2JLocal.addActionListener(this);
		panelLocal.add(btnCharger2JLocal);
		
		cbUseSoundsDJ = new JCheckBox("Utiliser les sons");
		cbUseSoundsDJ.setBounds(6, 0, 294, 35);
		panelLocal.add(cbUseSoundsDJ);
		
		cbSaveBeforeDJ = new JCheckBox("Sauvegarder avant de quitter");
		cbSaveBeforeDJ.setBounds(6, 30, 294, 35);
		panelLocal.add(cbSaveBeforeDJ);
		
		JPanel panelReseau = new JPanel();
		tabbedPane_2.addTab("Réseau", null, panelReseau, null);
		panelReseau.setLayout(null);
		
		panelNiveau = new JPanel();
		panelNiveau.setBorder(null);
		panelNiveau.setBounds(436, 118, 159, 230);
		contentPane.add(panelNiveau);
		panelNiveau.setLayout(null);
		
		tpReseauClient = new JTabbedPane(JTabbedPane.TOP);
		tpReseauClient.addChangeListener(this);
		tpReseauClient.setBounds(6, 6, 294, 114);
		panelReseau.add(tpReseauClient);
		
		JPanel panelReseauServeur = new JPanel();
		tpReseauClient.addTab("Serveur", null, panelReseauServeur, null);
		panelReseauServeur.setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(6, 30, 38, 16);
		panelReseauServeur.add(lblPort);
		
		txtPortServeur = new JTextField("2015");
		txtPortServeur.setBounds(56, 24, 113, 28);
		panelReseauServeur.add(txtPortServeur);
		txtPortServeur.setColumns(10);
		
		btnReseauServeur = new JButton("Jouer");
		btnReseauServeur.setBounds(179, 25, 88, 29);
		btnReseauServeur.addActionListener(this);
		panelReseauServeur.add(btnReseauServeur);
		
		JPanel panelReseauClient = new JPanel();
		tpReseauClient.addTab("Client", null, panelReseauClient, null);
		panelReseauClient.setLayout(null);
		
		String monAdresse = "127.0.0.1";
		try { monAdresse = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) { e.printStackTrace(); }
		
		txtAdresseClient = new JTextField(monAdresse);
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
		
		txtPortClient = new JTextField("2015");
		txtPortClient.setColumns(10);
		txtPortClient.setBounds(66, 32, 113, 28);
		panelReseauClient.add(txtPortClient);
		
		JLabel label_1 = new JLabel("Port");
		label_1.setBounds(6, 38, 38, 16);
		panelReseauClient.add(label_1);
		
		JLabel panelTitre = new JLabel(new ImageIcon("sprite/titre.png"));
		panelTitre.setBounds(6, 19, 632, 87);
		contentPane.add(panelTitre);
		
		JLabel lblNiveau = new JLabel("Niveau");
		lblNiveau.setBounds(6, 6, 61, 16);
		panelNiveau.add(lblNiveau);
		
		slider = new JSlider();
		slider.setValue(1);
		slider.setMinimum(1);
		slider.setMaximum(3);
		slider.addChangeListener(this);
		slider.setBounds(6, 29, 147, 29);
		panelNiveau.add(slider);
		
		JLabel lblNombreDeLignes = new JLabel("Nombre de lignes");
		lblNombreDeLignes.setBounds(6, 57, 147, 30);
		panelNiveau.add(lblNombreDeLignes);
		
		spinnerLigne = new JSpinner();
		spinnerLigne.setBounds(6, 84, 147, 30);
		panelNiveau.add(spinnerLigne);
		spinnerLigne.setModel(modelLigne);
		
		JLabel lblNombreDeColonnes = new JLabel("Nombre de colonnes");
		lblNombreDeColonnes.setBounds(6, 112, 147, 30);
		panelNiveau.add(lblNombreDeColonnes);
		
		spinnerColonne = new JSpinner();
		spinnerColonne.setBounds(6, 138, 147, 30);
		panelNiveau.add(spinnerColonne);
		spinnerColonne.setModel(modelColonne);
		
		JLabel lblNombreDeBombes = new JLabel("Nombre de bombes");
		lblNombreDeBombes.setBounds(6, 165, 147, 30);
		panelNiveau.add(lblNombreDeBombes);
		
		spinnerBombe = new JSpinner();
		spinnerBombe.setBounds(6, 191, 147, 30);
		panelNiveau.add(spinnerBombe);
		spinnerBombe.setModel(modelBombe);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Vérification des checkbox useTemps et DefiTemps qui sont liés
		if(e.getSource() == cbUseTemps) {
			if(cbDefiTemps.isSelected())
				cbUseTemps.setSelected(true);
		}
		if(e.getSource() == cbDefiTemps) {
			spinnerTemps.setEnabled(cbDefiTemps.isSelected());
			if(cbDefiTemps.isSelected())
				cbUseTemps.setSelected(true);
		}

		//Vérification du nombre de bombes
		int lignes = (Integer) spinnerLigne.getValue();
		int colonnes = (Integer) spinnerColonne.getValue();
		int bombes = (Integer) spinnerBombe.getValue();
		if(lignes*colonnes < bombes) {
			spinnerBombe.setBorder(BorderFactory.createLineBorder(Color.red));
			return;
		}
		
		if(e.getSource() == btnCharger) {
		    JeuModele model = JeuModele.charger();
			JeuVue view = new JeuVue(model);
			new JeuControleur(model, view);
			for(CaseModele caseM : model.getListeCase())
			{
				caseM.setModeleJeu(model);
				caseM.setEtat(caseM.getEtat());
			}	
			view.setVisible(true);
			if(!model.isFini())
				model.getTimer().start();
		}
		else if(e.getSource() == btnCharger2JLocal) {
			 	JeuModeleDJ model = JeuModeleDJ.charger();
				JeuVueDJ view = new JeuVueDJ(model);
				new JeuControleur(model, view);
				for(CaseModele caseM : model.getListeCase())
				{
					caseM.setModeleJeu(model);
					caseM.setEtat(caseM.getEtat());
				}	
				view.setVisible(true);
		}
		else if(e.getSource() == btn1Joueur) {
		    JeuModele model = new JeuModele(lignes, colonnes, bombes);
		    model.construireCases();
		    if(cbDefiTemps.isSelected()) {
		    	model.setSecondes((Integer) spinnerTemps.getValue());
		    	model.setDefiTemps(true);
		    }
		    model.setAllowQuestion(cbUseInterrogation.isSelected());
		    model.setAllowTime(cbUseTemps.isSelected());
		    model.setAllowSounds(cbUseSounds.isSelected());
		    model.setSaveBeforeQuit(cbSaveGameBefore.isSelected());
			JeuVue view = new JeuVue(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
		else if(e.getSource() == btnLocal) {
		    JeuModeleDJ model = new JeuModeleDJ(lignes, colonnes, bombes);
		    model.construireCases();
		    model.setAllowSounds(cbUseSoundsDJ.isSelected());
		    model.setSaveBeforeQuit(cbSaveBeforeDJ.isSelected());
			JeuVueDJ view = new JeuVueDJ(model);
			new JeuControleur(model, view);
			view.setVisible(true);
		}
		else if(e.getSource() == btnReseauServeur) {
		    JeuModeleServeur model = new JeuModeleServeur(lignes, colonnes, bombes);
		    
		    if(!testInputServeur())
		    	return;
		    model.setPortServeur(Integer.parseInt(txtPortServeur.getText()));
		    model.construireCases();
		    model.connexion();
		    model.envoiDimensionsGrille();
		    model.envoiEnsembleCases();
		    
			JeuVueDJRes view = new JeuVueDJRes(model);
			new JeuControleurDJRes(model, view);
			view.setVisible(true);
			
			RecevoirRes rs = new RecevoirRes(model);
			rs.start();
		}
		else if(e.getSource() == btnReseauClient) {
		    JeuModeleClient model = new JeuModeleClient();
		    
		    if(!testInputClient())
		    	return;
		    model.setPortServeur(Integer.parseInt(txtPortClient.getText()));
		    model.setIpServeur(txtAdresseClient.getText());
		    model.connexion();
		    model.recevoirPlateau();
			model.recevoirNouvellesCases();
		    
		    JeuVueDJRes view = new JeuVueDJRes(model);
			new JeuControleurDJRes(model, view);
			view.setVisible(true);
			
			RecevoirRes rs = new RecevoirRes(model);
			rs.start();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == tpReseauClient) {
			panelNiveau.setVisible(((JTabbedPane)tpReseauClient).getSelectedIndex() == 0);
			return;
		}
		
		//Changement du niveau de la partie
		if(((JSlider)e.getSource()).getValue() == 1) {
			spinnerLigne.setValue(VarCommun.nombreLCB.DEBUTANT.nbLigne);
			spinnerColonne.setValue(VarCommun.nombreLCB.DEBUTANT.nbColonne);
			spinnerBombe.setValue(VarCommun.nombreLCB.DEBUTANT.nbBombe);
		}
		else if(((JSlider)e.getSource()).getValue() == 2) {
			spinnerLigne.setValue(VarCommun.nombreLCB.INTERMEDIAIRE.nbLigne);
			spinnerColonne.setValue(VarCommun.nombreLCB.INTERMEDIAIRE.nbColonne);
			spinnerBombe.setValue(VarCommun.nombreLCB.INTERMEDIAIRE.nbBombe);
		}
		else if(((JSlider)e.getSource()).getValue() == 3) {
			spinnerLigne.setValue(VarCommun.nombreLCB.DIFFICILE.nbLigne);
			spinnerColonne.setValue(VarCommun.nombreLCB.DIFFICILE.nbColonne);
			spinnerBombe.setValue(VarCommun.nombreLCB.DIFFICILE.nbBombe);
		}
	}

	/** 
	* Test du champs port pour le lancement du serveur
	*
	*/
	private boolean testInputServeur() {
		if(txtPortServeur.getText() == "") {
	    	JOptionPane.showMessageDialog(null, "Le port est invalide");
	    	return false;
	    }
	    try{
	        Integer.parseInt(txtPortServeur.getText());
	    }catch(NumberFormatException e1){
	    	JOptionPane.showMessageDialog(null, "Le port est invalide");
	    	return false;
	    }
		return true;
	}
	
	/** 
	* Test du champs port et adresse pour le lancement du client
	*
	*/
	private boolean testInputClient() {
		if(txtAdresseClient.getText() == "" || !txtAdresseClient.getText().matches(
				"(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))")) {
	    	JOptionPane.showMessageDialog(null, "L'adresse ip est invalide");
	    	return false;
	    }
		if(txtPortClient.getText() == "") {
	    	JOptionPane.showMessageDialog(null, "Le port est invalide");
	    	return false;
	    }
	    try{
	        Integer.parseInt(txtPortClient.getText());
	    }catch(NumberFormatException e1){
	    	JOptionPane.showMessageDialog(null, "Le port est invalide");
	    	return false;
	    }
		return true;
	}
}
