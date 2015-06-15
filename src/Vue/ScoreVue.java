package Vue;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modele.Partie;
import Modele.PartieDJ;

import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Classe d'affichage des scores
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class ScoreVue extends JDialog {
	private static final long serialVersionUID = 8874069533716005260L;
	private JTable table1J;
	private JTable table2J;
	public JTabbedPane tabbedPane;
	
	public ScoreVue() {
		setSize(715, 360);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Scores");
		setModal(true);
		getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 700, 266);
		getContentPane().add(tabbedPane);
		
		JPanel panelUnJ = new JPanel();
		tabbedPane.addTab("Un joueur", null, panelUnJ, null);
		panelUnJ.setLayout(null);
		
		String col[] = {"Date","Lignes","Colonnes", "Bombes", "Temps"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		tableModel.addRow(col);
		ArrayList<Partie> liste = Partie.lectureXML("fichier/scoreXML.xml");
		for(Partie score : liste) {
			Object[] objs = {score.getDate(),score.getLignes(), score.getColonnes(), score.getBombes(), score.getTemps()};
			tableModel.addRow(objs);
		}
		
		table1J = new JTable(tableModel);
		table1J.setBounds(6, 6, 700, 209);
		panelUnJ.add(table1J);
		
		String col2[] = {"Date","Score joueur 1","Score joueur 2", "Bombes"};
		tableModel = new DefaultTableModel(col2, 0);
		tableModel.addRow(col2);
		ArrayList<PartieDJ> listeDJ = PartieDJ.lectureXML("fichier/scoreXMLDJ.xml");
		for(PartieDJ score : listeDJ) {
			Object[] objs = {score.getDate(),score.getScoreJ1(), score.getScoreJ2(), score.getBombes()};
			tableModel.addRow(objs);
		}
		
		JPanel panel2J = new JPanel();
		tabbedPane.addTab("Deux joueurs", null, panel2J, null);
		panel2J.setLayout(null);
		
		table2J = new JTable(tableModel);
		table2J.setBounds(6, 6, 700, 208);
		panel2J.add(table2J);
		
		JPanel panel = new JPanel();
		panel.setBounds(16, 268, 678, 64);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{208, 0, 208, 0};
		gbl_panel.rowHeights = new int[]{32, 32, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCouturierCyril = new JLabel("COUTURIER Cyril");
		GridBagConstraints gbc_lblCouturierCyril = new GridBagConstraints();
		gbc_lblCouturierCyril.fill = GridBagConstraints.BOTH;
		gbc_lblCouturierCyril.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouturierCyril.gridx = 0;
		gbc_lblCouturierCyril.gridy = 0;
		panel.add(lblCouturierCyril, gbc_lblCouturierCyril);
		
		JLabel lblVersion = new JLabel("Version 4.0");
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 0;
		panel.add(lblVersion, gbc_lblVersion);
		
		JLabel lblLetourneurLo = new JLabel("LETOURNEUR Léo");
		GridBagConstraints gbc_lblLetourneurLo = new GridBagConstraints();
		gbc_lblLetourneurLo.anchor = GridBagConstraints.EAST;
		gbc_lblLetourneurLo.fill = GridBagConstraints.VERTICAL;
		gbc_lblLetourneurLo.insets = new Insets(0, 0, 5, 0);
		gbc_lblLetourneurLo.gridx = 2;
		gbc_lblLetourneurLo.gridy = 0;
		panel.add(lblLetourneurLo, gbc_lblLetourneurLo);
		
		JLabel lblNewLabel = new JLabel("Polytech Lyon ©");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
	}
}
