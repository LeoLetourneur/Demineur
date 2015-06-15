package Modele;

/**
 * Gestion de la r�ception de message TCP.
 * Extends Thread pour que la r�ception soit dissoci�e du reste.
 * 
 * @author LETOURNEUR L�o
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
	* Lancement de la r�ception
	*
	*/
	public void run() {
		while(connecte) {
			connecte = modele.recevoir();
		}
	}

}
