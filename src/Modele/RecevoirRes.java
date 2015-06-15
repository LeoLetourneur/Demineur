package Modele;

/**
 * Gestion de la réception de message TCP.
 * Extends Thread pour que la réception soit dissociée du reste.
 * 
 * @author LETOURNEUR Léo
 * @since 4.0
 */
public class RecevoirRes extends Thread{

	private boolean connecte;
	private JeuModeleDJRes modele;
	
	/** 
	* Constructeur
	*
	*/
	public RecevoirRes(JeuModeleDJRes p_modele) {
		modele = p_modele;
		connecte = true;
	}
	
	/** 
	* Lancement de la réception
	*
	*/
	public void run() {
		while(connecte) {
			connecte = modele.recevoir();
		}
	}

}
