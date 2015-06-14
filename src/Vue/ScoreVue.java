package Vue;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modele.Partie;

public class ScoreVue extends JDialog {
	private static final long serialVersionUID = 8874069533716005260L;
	private JTable table1J;
	private JTable table2J;
	public JTabbedPane tabbedPane;
	
	public ScoreVue() {
		setSize(450, 300);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Scores");
		setModal(true);
		getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 438, 266);
		getContentPane().add(tabbedPane);
		
		JPanel panelUnJ = new JPanel();
		tabbedPane.addTab("Un joueur", null, panelUnJ, null);
		panelUnJ.setLayout(null);
		
		String col[] = {"Lignes","Colonnes", "Bombes", "Temps"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		tableModel.addRow(col);
		ArrayList<Partie> liste = Partie.lectureXML("scoreXML.xml");
		for(Partie score : liste) {
			System.out.println(score.getTemps()+"");
			Object[] objs = {score.getLignes(), score.getColonnes(), score.getBombes(), score.getTemps()};
			tableModel.addRow(objs);
		}
		
		table1J = new JTable(tableModel);
		table1J.setBounds(6, 6, 405, 209);
		panelUnJ.add(table1J);
		
		tableModel = new DefaultTableModel(col, 0);
		tableModel.addRow(col);
		
		JPanel panel2J = new JPanel();
		tabbedPane.addTab("Deux joueurs", null, panel2J, null);
		panel2J.setLayout(null);
		
		table2J = new JTable(tableModel);
		table2J.setBounds(6, 6, 405, 208);
		panel2J.add(table2J);
		
	}
}
