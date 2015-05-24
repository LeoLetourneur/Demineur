import Controleur.ControleurJeu;
import Modele.ModeleJeu;
import Vue.VueJeu;


public class Main {
	
	public static void main (String[] args){
	    System.out.println("Démineur par Léo Letourneur et Cyril Couturier");
	    ModeleJeu model = new ModeleJeu();
		VueJeu view = new VueJeu(model);
		ControleurJeu controler =  new ControleurJeu(model, view);
		view.setVisible(true);
	   }

}
