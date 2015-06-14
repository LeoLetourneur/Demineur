package Modele;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Représentation d'une partie pour la sauvegarde XML des scores.
 * 
 * @author COUTURIER Cyril
 * @since 4.0
 */
public class Partie {
	
	String date;
	int colonnes;
	int lignes;
	int bombes;
	int temps;
	
	/** 
	* Constructeur
	*
	*/
	public Partie(String p_date,int p_colonnes,int p_lignes,int p_bombes,int p_temps){
		setDate(p_date);
		setColonnes(p_colonnes);
		setLignes(p_lignes);
		setBombes(p_bombes);
		setTemps(p_temps);
	}
	
	/** 
	* Constructeur vide
	*
	*/
	public Partie() {}

	/** 
	* Lecture des scores en XML
	*
	*/
	public static ArrayList<Partie> lectureXML(String nomFichier) {

		ArrayList<Partie> listePartie = new ArrayList<Partie>();
		try {
			// instantiation du parser
			InputStream in = new FileInputStream(nomFichier);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);

			Partie partieCourante = null;
			String objetCourant = "";
			// lecture des evenements
			for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser
					.next()) {
				// traitement selon l'evenement
				
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if (parser.getLocalName().equals("Partie")) {
						partieCourante = new Partie();
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (parser.getLocalName().equals("Date"))
						partieCourante.setDate(objetCourant);
					if (parser.getLocalName().equals("Colonnes"))
						partieCourante.setColonnes(Integer
								.parseInt(objetCourant));
					if (parser.getLocalName().equals("Lignes"))
						partieCourante
								.setLignes(Integer.parseInt(objetCourant));
					if (parser.getLocalName().equals("Bombes"))
						partieCourante
								.setBombes(Integer.parseInt(objetCourant));
					if (parser.getLocalName().equals("Secondes"))
						partieCourante.setTemps(Integer.parseInt(objetCourant));
					if (parser.getLocalName().equals("Partie"))
						listePartie.add(partieCourante);
					break;
				case XMLStreamConstants.CHARACTERS:
					objetCourant = parser.getText();
					break;
				}
			}
			parser.close();
		} catch (XMLStreamException ex) {
			System.out
					.println("Exception de type 'XMLStreamException' lors de la lecture du fichier : "
							+ nomFichier);
			System.out.println("Details :");
			System.out.println(ex);
		} catch (IOException ex) {
			System.out
					.println("Exception de type 'IOException' lors de la lecture du fichier : "
							+ nomFichier);
			System.out.println("Verifier le chemin.");
			System.out.println(ex.getMessage());
		}
		return listePartie;
	}

	/** 
	* Sauvegarde des scores en XML
	*
	*/
	public static void ecritureXML(JeuModele model, String nomFichier) {
		ArrayList<Partie> listePartie = Partie.lectureXML(nomFichier);
		FileWriter fileXML;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		try {
			fileXML = new FileWriter(nomFichier);
			XMLStreamWriter writer = factory.createXMLStreamWriter(fileXML);
			writer.writeStartDocument();
			writer.writeStartElement("Parties");
			writer.writeStartElement("Partie");
			writer.writeStartElement("Date");
			writer.writeCharacters(new Date().toString());
			writer.writeEndElement();
			writer.writeStartElement("Colonnes");
			writer.writeCharacters(Integer.toString(model.getNbColonne()));
			writer.writeEndElement();
			writer.writeStartElement("Lignes");
			writer.writeCharacters(Integer.toString(model.getNbLigne()));
			writer.writeEndElement();
			writer.writeStartElement("Bombes");
			writer.writeCharacters(Integer.toString(model.getNbBombe()));
			writer.writeEndElement();
			writer.writeStartElement("Temps");
			writer.writeCharacters(Integer.toString(model.getSecondes()));
			writer.writeEndElement();
			writer.writeEndElement();
			for (int i = 0; i < listePartie.size(); i++) {
				writer.writeStartElement("Partie");
				writer.writeStartElement("Date");
				writer.writeCharacters(listePartie.get(i).getDate().toString());
				writer.writeEndElement();
				writer.writeStartElement("Colonnes");
				writer.writeCharacters(Integer.toString(listePartie.get(i)
						.getColonnes()));
				writer.writeEndElement();
				writer.writeStartElement("Lignes");
				writer.writeCharacters(Integer.toString(listePartie.get(i)
						.getLignes()));
				writer.writeEndElement();
				writer.writeStartElement("Bombes");
				writer.writeCharacters(Integer.toString(listePartie.get(i)
						.getBombes()));
				writer.writeEndElement();
				writer.writeStartElement("Temps");
				writer.writeCharacters(Integer.toString(listePartie.get(i)
						.getTemps()));
				writer.writeEndElement();
				writer.writeEndElement();
			}
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();

		} catch (XMLStreamException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getColonnes() {
		return colonnes;
	}

	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}

	public int getLignes() {
		return lignes;
	}

	public void setLignes(int lignes) {
		this.lignes = lignes;
	}

	public int getBombes() {
		return bombes;
	}

	public void setBombes(int bombes) {
		this.bombes = bombes;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}
}
