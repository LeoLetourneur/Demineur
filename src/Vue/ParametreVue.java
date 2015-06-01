package Vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class ParametreVue extends JDialog {
	private static final long serialVersionUID = 504012887671145390L;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ParametreVue dialog = new ParametreVue();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ParametreVue() {
		setBounds(100, 100, 460, 322);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(6, 10, 448, 245);
			contentPanel.add(tabbedPane);
			
			JPanel panelPartie = new JPanel();
			tabbedPane.addTab("Partie", null, panelPartie, null);
			
			JPanel panelGrille = new JPanel();
			tabbedPane.addTab("Grille", null, panelGrille, null);
			{
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
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
