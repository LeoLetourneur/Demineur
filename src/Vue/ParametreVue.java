package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;

import Commun.VarCommun;
import Modele.JeuModele;

public class ParametreVue extends JDialog implements ItemListener, ActionListener {
	private static final long serialVersionUID = 504012887671145390L;
	
	private JPanel contentPanel;
	private JPanel panelPerso;
	private JTabbedPane tabbedPane;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private JRadioButton rdbtnDebutant;
	private JRadioButton rdbtnIntermediaire;
	private JRadioButton rdbtnDifficile;
	private JRadioButton rdbtnPersonnalise;
	
	private JSpinner spinnerLigne;
	private JSpinner spinnerColonne;
	private JSpinner spinnerBombe;
	
	JCheckBox chckbxPlaySound;
	JCheckBox chckbxSaveQuitGame;
	JCheckBox chckbxUseQuestionMark;
	JCheckBox chckbxUseTime;
	
	private JeuModele modele;
	private boolean accept;

	public ParametreVue(JeuModele p_modele) {
		modele = p_modele;
		
		setModal(true);
		setAccept(false);
		setTitle("Paramètres");
		setLocationRelativeTo(null);
        setResizable(false);
		setSize(460, 342);
		getContentPane().setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(6, 10, 448, 265);
			contentPanel.add(tabbedPane);
			
			JPanel panelPartie = new JPanel();
			tabbedPane.addTab("Partie", null, panelPartie, null);
			panelPartie.setLayout(null);
			
			JPanel btnLayout = new JPanel();
			btnLayout.setBounds(6, 6, 415, 71);
			panelPartie.add(btnLayout);
			btnLayout.setLayout(new GridLayout(2, 2, 0, 0));
			
			ButtonGroup groupe = new ButtonGroup();
			
			rdbtnDebutant = new JRadioButton("Débutant (9,9,10)");
			rdbtnDebutant.setSelected(true);
			rdbtnDebutant.addItemListener(this);
			btnLayout.add(rdbtnDebutant);
			groupe.add(rdbtnDebutant);
			
			rdbtnIntermediaire = new JRadioButton("Intermédiaire (16,16,40)");
			rdbtnIntermediaire.addItemListener(this);
			btnLayout.add(rdbtnIntermediaire);
			groupe.add(rdbtnIntermediaire);
			
			rdbtnDifficile = new JRadioButton("Difficile (16,30,99)");
			rdbtnDifficile.addItemListener(this);
			btnLayout.add(rdbtnDifficile);
			groupe.add(rdbtnDifficile);
			
			rdbtnPersonnalise = new JRadioButton("Personnalisée");
			rdbtnPersonnalise.addItemListener(this);
			btnLayout.add(rdbtnPersonnalise);
			groupe.add(rdbtnPersonnalise);
			
			panelPerso = new JPanel();
			panelPerso.setBorder(new TitledBorder(null, "Personnalis\u00E9e", TitledBorder.LEFT, TitledBorder.TOP, null, null));
			panelPerso.setBounds(6, 87, 415, 126);
			panelPartie.add(panelPerso);
			panelPerso.setLayout(new GridLayout(3, 2, 0, 0));
			
			JLabel lblNombreDeLignes = new JLabel("Nombre de lignes");
			panelPerso.add(lblNombreDeLignes);
			
			SpinnerModel modelLigne = new SpinnerNumberModel(15, 3, 30, 1);
			SpinnerModel modelColonne = new SpinnerNumberModel(15, 3, 30, 1);
			SpinnerModel modelBombe = new SpinnerNumberModel(80, 2, 800, 1);
			
			spinnerLigne = new JSpinner();
			spinnerLigne.setModel(modelLigne);
			panelPerso.add(spinnerLigne);
			
			JLabel lblNombreDeColonnes = new JLabel("Nombre de colonnes");
			panelPerso.add(lblNombreDeColonnes);
			
			spinnerColonne = new JSpinner();
			spinnerColonne.setModel(modelColonne);
			panelPerso.add(spinnerColonne);
			
			JLabel lblNombreDeBombes = new JLabel("Nombre de bombes");
			panelPerso.add(lblNombreDeBombes);
			
			spinnerBombe = new JSpinner();
			spinnerBombe.setModel(modelBombe);
			panelPerso.add(spinnerBombe);
			
			setEnabledPerso(false);
			
			JPanel panelOptions = new JPanel();
			tabbedPane.addTab("Options", null, panelOptions, null);
			panelOptions.setLayout(new GridLayout(5, 0, 0, 0));
			
			chckbxPlaySound = new JCheckBox("Jouer les sons");
			panelOptions.add(chckbxPlaySound);
			
			chckbxSaveQuitGame = new JCheckBox("Sauvegarder les parties en quittant");
			panelOptions.add(chckbxSaveQuitGame);
			
			chckbxUseQuestionMark = new JCheckBox("Utiliser la point d'interrogation");
			chckbxUseQuestionMark.setSelected(true);
			panelOptions.add(chckbxUseQuestionMark);
			
			chckbxUseTime = new JCheckBox("Utiliser le temps");
			chckbxUseTime.setSelected(true);
			panelOptions.add(chckbxUseTime);
			
			JPanel panelTheme = new JPanel();
			tabbedPane.addTab("Thème", null, panelTheme, null);
			panelTheme.setLayout(new GridLayout(5, 6, 0, 0));
			
			JLabel label = new JLabel("Mario");
			panelTheme.add(label);
			JLabel marioCase = new JLabel(new ImageIcon("sprite/Mario/case.png"));
			panelTheme.add(marioCase);
			JLabel marioBombe = new JLabel(new ImageIcon("sprite/Mario/bombe.png"));
			panelTheme.add(marioBombe);
			JLabel marioFlag = new JLabel(new ImageIcon("sprite/Mario/drapeau.png"));
			panelTheme.add(marioFlag);
			JLabel marioQuestion = new JLabel(new ImageIcon("sprite/Mario/question.png"));
			panelTheme.add(marioQuestion);
			JLabel marioVide = new JLabel(new ImageIcon("sprite/Mario/vide.png"));
			panelTheme.add(marioVide);
			
			JLabel lblGolf = new JLabel("Golf");
			panelTheme.add(lblGolf);
			JLabel golfCase = new JLabel(new ImageIcon("sprite/Golf/case.png"));
			panelTheme.add(golfCase);
			JLabel golfBombe = new JLabel(new ImageIcon("sprite/Golf/bombe.png"));
			panelTheme.add(golfBombe);
			JLabel golfFlag = new JLabel(new ImageIcon("sprite/Golf/drapeau.png"));
			panelTheme.add(golfFlag);
			JLabel golfQuestion = new JLabel(new ImageIcon("sprite/Golf/question.png"));
			panelTheme.add(golfQuestion);
			JLabel golfVide = new JLabel(new ImageIcon("sprite/Golf/vide.png"));
			panelTheme.add(golfVide);
			
			
			JLabel lblCaisse = new JLabel("Caisse");
			panelTheme.add(lblCaisse);
			JLabel caisseCase = new JLabel(new ImageIcon("sprite/Caisse/case.png"));
			panelTheme.add(caisseCase);
			JLabel caisseBombe = new JLabel(new ImageIcon("sprite/Caisse/bombe.png"));
			panelTheme.add(caisseBombe);
			JLabel caisseFlag = new JLabel(new ImageIcon("sprite/Caisse/drapeau.png"));
			panelTheme.add(caisseFlag);
			JLabel caisseQuestion = new JLabel(new ImageIcon("sprite/Caisse/question.png"));
			panelTheme.add(caisseQuestion);
			JLabel caisseVide = new JLabel(new ImageIcon("sprite/Caisse/vide.png"));
			panelTheme.add(caisseVide);
			
			
			JLabel lblGolf_1 = new JLabel("Pacman");
			panelTheme.add(lblGolf_1);
			JLabel pacmanCase = new JLabel(new ImageIcon("sprite/Pacman/case.png"));
			panelTheme.add(pacmanCase);
			JLabel pacmanBombe = new JLabel(new ImageIcon("sprite/Pacman/bombe.png"));
			panelTheme.add(pacmanBombe);
			JLabel pacmanFlag = new JLabel(new ImageIcon("sprite/Pacman/drapeau.png"));
			panelTheme.add(pacmanFlag);
			JLabel pacmanQuestion = new JLabel(new ImageIcon("sprite/Pacman/question.png"));
			panelTheme.add(pacmanQuestion);
			JLabel pacmanVide = new JLabel(new ImageIcon("sprite/Pacman/vide.png"));
			panelTheme.add(pacmanVide);

			
			JLabel lblDisco = new JLabel("Disco");
			panelTheme.add(lblDisco);
			JLabel discoCase = new JLabel(new ImageIcon("sprite/Disco/case.png"));
			panelTheme.add(discoCase);
			JLabel discoBombe = new JLabel(new ImageIcon("sprite/Disco/bombe.png"));
			panelTheme.add(discoBombe);
			JLabel discoFlag = new JLabel(new ImageIcon("sprite/Disco/drapeau.png"));
			panelTheme.add(discoFlag);
			JLabel discoQuestion = new JLabel(new ImageIcon("sprite/Disco/question.png"));
			panelTheme.add(discoQuestion);
			JLabel discoVide = new JLabel(new ImageIcon("sprite/Disco/vide.png"));
			panelTheme.add(discoVide);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel label = new JLabel("Attention votre partie sera réinitialisée");
			buttonPane.add(label);
			
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(this);
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(this);
			buttonPane.add(cancelButton);
		}
	}
	
	private void setEnabledPerso(boolean coche) {
		panelPerso.setEnabled(coche);
		spinnerLigne.setEnabled(coche);
		spinnerColonne.setEnabled(coche);
		spinnerBombe.setEnabled(coche);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == rdbtnPersonnalise)
			setEnabledPerso(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(rdbtnDebutant.isSelected()) {
				modele.setNbLigne(VarCommun.nombreLCB.DEBUTANT.nbLigne);
				modele.setNbColonne(VarCommun.nombreLCB.DEBUTANT.nbColonne);
				modele.setNbBombe(VarCommun.nombreLCB.DEBUTANT.nbBombe);
			}
			else if(rdbtnIntermediaire.isSelected()) {
				modele.setNbLigne(VarCommun.nombreLCB.INTERMEDIAIRE.nbLigne);
				modele.setNbColonne(VarCommun.nombreLCB.INTERMEDIAIRE.nbColonne);
				modele.setNbBombe(VarCommun.nombreLCB.INTERMEDIAIRE.nbBombe);
			}
			else if(rdbtnDifficile.isSelected()) {
				modele.setNbLigne(VarCommun.nombreLCB.DIFFICILE.nbLigne);
				modele.setNbColonne(VarCommun.nombreLCB.DIFFICILE.nbColonne);
				modele.setNbBombe(VarCommun.nombreLCB.DIFFICILE.nbBombe);
			}
			else if(rdbtnPersonnalise.isSelected()) {
				modele.setNbLigne((Integer) spinnerLigne.getValue());
				modele.setNbColonne((Integer) spinnerColonne.getValue());
				modele.setNbBombe((Integer) spinnerBombe.getValue());
				
				if((Integer) spinnerLigne.getValue()*(Integer) spinnerColonne.getValue() < (Integer) spinnerBombe.getValue()) {
					spinnerBombe.setBorder(BorderFactory.createLineBorder(Color.red));
					tabbedPane.setSelectedIndex(0);
					return;
				}
			}
			
			modele.setAllowQuestion(this.chckbxUseQuestionMark.isSelected());
			modele.setAllowTime(this.chckbxUseTime.isSelected());
			this.setAccept(true);
		}
		else if(e.getSource() == cancelButton) {
			this.setAccept(false);
		}
		this.setVisible(false);
		
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}
}
