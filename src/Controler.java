import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controler implements ActionListener {

	Model model;
    View view;
    
	public Controler(Model p_model, View p_view) {
		model = p_model;
		view = p_view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
