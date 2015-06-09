package Modele;

public class RecevoirRes extends Thread{

	private boolean connecte;
	private JeuModeleDJRes modele;
	
	public RecevoirRes(JeuModeleDJRes p_modele) {
		modele = p_modele;
		connecte = true;
	}
	
	public void run() {
		while(connecte) {
			connecte = modele.recevoir();
		}
	}

}
