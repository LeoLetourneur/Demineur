import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;


public class CaseVue extends JButton implements Observer {
	private static final long serialVersionUID = 3969339859887656340L;
	
	private CaseModele modele;
	
	public CaseVue(CaseModele p_modele) {
		super();
		setModele(p_modele);
		modele.addObserver(this);
	}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}

	

}
