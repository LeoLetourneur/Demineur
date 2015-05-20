import Modele.Modele;
import Vue.Vue;


public class Main {
	
	public static void main (String[] args){
	    System.out.println("Démineur par Léo Letourneur et Cyril Couturier");
	    Modele model = new Modele();
		Vue view = new Vue(model);
		Controleur controler =  new Controleur(model, view);
		view.setVisible(true);
	   }

}
