package Commun;

import java.awt.EventQueue;

import Vue.MenuVue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Démineur par Léo Letourneur et Cyril Couturier");
					MenuVue frame = new MenuVue();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Lancement du jeu impossible");
				}
			}
		});
	}

}
