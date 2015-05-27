package Controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import Commun.VarCommun;
import Modele.CaseModele;
import Modele.ModeleJeu;
import Vue.CaseVue;

public class CaseControleur implements MouseListener {

	private CaseModele modele;
	private CaseVue vue;
	private ModeleJeu modeleJeu;
	
	public CaseControleur(CaseModele p_modele, CaseVue p_vue, ModeleJeu p_modeleJeu) {
		setModele(p_modele);
		setVue(p_vue);
		setModeleJeu(p_modeleJeu);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
				if(modele.getValeur() == VarCommun.typeCase.EMPTY.value) {
					if(modeleJeu.getPremierTour())
						{
							modeleJeu.setPremierTour(false);
						}
					modele.setEtat(VarCommun.etatCase.DISCOVER.value);
					if(modele.getNbBombeVoisin() == 0)
						modele.retournerVoisin();
						
				}
				else {
					if(!modeleJeu.getPremierTour())
						{
							modeleJeu.retournerBombes();
						}
					else
						{
						System.out.println("test");
							modeleJeu.setPremierTour(false);
							modele.setValeur(VarCommun.typeCase.EMPTY.value);
							Boolean test = true;
							while(test)
								{
									int random2;
									random2=(int)(Math.random()*modele.getVoisins().size());
									if(modele.getVoisins().get(random2).getValeur()!=1)
										{
											modele.getVoisins().get(random2).setValeur(1);
											modele.incrementerVoisin(-1);
											modele.getVoisins().get(random2).incrementerVoisin(1);
											test=false;
										}
								}
							modele.setEtat(VarCommun.etatCase.DISCOVER.value);
							if(modele.getNbBombeVoisin() == 0)
								modele.retournerVoisin();
							
						}
					
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			if(modele.getEtat() == VarCommun.etatCase.COVER.value) {
				modele.setEtat(VarCommun.etatCase.FLAG.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()-1);
			}
			else if(modele.getEtat() == VarCommun.etatCase.FLAG.value) {
				modele.setEtat(VarCommun.etatCase.QUESTION.value);
				modeleJeu.setNbBombe(modeleJeu.getNbBombe()+1);
			}
			else if(modele.getEtat() == VarCommun.etatCase.QUESTION.value)
				modele.setEtat(VarCommun.etatCase.COVER.value);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	public CaseModele getModele() {
		return modele;
	}

	public void setModele(CaseModele modele) {
		this.modele = modele;
	}
	
	public CaseVue getVue() {
		return vue;
	}

	public void setVue(CaseVue vue) {
		this.vue = vue;
	}

	public ModeleJeu getModeleJeu() {
		return modeleJeu;
	}

	public void setModeleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}

}
