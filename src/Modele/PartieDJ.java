package Modele;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Représentation d'une partie pour la sauvegarde XML des scores d'une partie 2 joueurs.
 * 
 * @author COUTURIER Cyril
 * @since 4.0
 */
public class PartieDJ {
	
	String date;
	int scoreJ1;
	int scoreJ2;
	int bombes;
	/** 
	* Constructeur
	*
	*/
	public PartieDJ(int p_scoreJ1 , int p_scoreJ2,int p_bombes , String p_date){
		setScoreJ1(p_scoreJ1);
		setScoreJ2(p_scoreJ2);
		setBombes(p_bombes);
		setDate(p_date);
	}
	
	/** 
	* Constructeur vide
	*
	*/
	public PartieDJ() {}

	/** 
	* Lecture des scores en XML
	*
	*/
	@SuppressWarnings("unchecked")
	public static ArrayList<PartieDJ> lectureXML(String nomFichier) {

		ArrayList<PartieDJ> listePartie = new ArrayList<PartieDJ>();
		    // ouverture de decodeur
		    XMLDecoder decoder;
			try {
				decoder = new XMLDecoder(new FileInputStream(nomFichier));
				try {
			        // deserialisation de l'objet
			        listePartie = (ArrayList<PartieDJ>) decoder.readObject();
			    } finally {
			        // fermeture du decodeur
			        decoder.close();
			    }
			} catch (FileNotFoundException e) {
				System.out.println("pas de fichier");
			}
		    
		return listePartie;
	}

	/** 
	* Sauvegarde des scores en XML
	*
	*/
	public static void ecritureXML(PartieDJ partie, String nomFichier) {
		ArrayList<PartieDJ> listeSauve = new ArrayList<PartieDJ>();
		listeSauve.add(partie);
		ArrayList<PartieDJ> listePartie = PartieDJ.lectureXML(nomFichier);
		listeSauve.addAll(listePartie);
		XMLEncoder encoder;
		try {
			encoder = new XMLEncoder(new FileOutputStream(nomFichier));
			  try {
		            // serialisation de l'objet
		            encoder.writeObject(listeSauve);
		            encoder.flush();
		        } finally {
		            // fermeture de l'encodeur
		            encoder.close();
		        }
		} catch (FileNotFoundException e) {
			System.out.println("Pas de fichier");
		}
	      
	}

	public int getScoreJ1() {
		return scoreJ1;
	}

	public void setScoreJ1(int scoreJ1) {
		this.scoreJ1 = scoreJ1;
	}

	public int getScoreJ2() {
		return scoreJ2;
	}

	public void setScoreJ2(int scoreJ2) {
		this.scoreJ2 = scoreJ2;
	}

	public int getBombes() {
		return bombes;
	}

	public void setBombes(int bombes) {
		this.bombes = bombes;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
