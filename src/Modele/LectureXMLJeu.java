package Modele;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class LectureXMLJeu {


	public static ArrayList<Partie> lectureXML(String nomFichier){
		
		ArrayList<Partie> listePartie= new ArrayList<Partie>();
		 try {
	            // instantiation du parser
	            InputStream in = new FileInputStream(nomFichier);
	            XMLInputFactory factory = XMLInputFactory.newInstance();
	            XMLStreamReader parser = factory.createXMLStreamReader(in);
	            
	            Partie partieCourante = null;
	            String objetCourant = "";
	            // lecture des evenements
	            for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next()) {
	                // traitement selon l'evenement
	            	System.out.println(objetCourant);
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
	                             partieCourante.setColonnes(Integer.parseInt(objetCourant));                     
	                        if (parser.getLocalName().equals("Lignes")) 
	                             partieCourante.setLignes(Integer.parseInt(objetCourant));        
	                        if (parser.getLocalName().equals("Bombes")) 
	                             partieCourante.setBombes(Integer.parseInt(objetCourant));      
	                        if (parser.getLocalName().equals("Secondes")) 
	                             partieCourante.setTemps(Integer.parseInt(objetCourant));
	                        if (parser.getLocalName().equals("Partie")) 
	                             listePartie.add(partieCourante);
	                        break;
	                    case XMLStreamConstants.CHARACTERS:
	                        objetCourant = parser.getText();
	                        break;
	                } // end switch
	            } // end while
	            parser.close();
	        } catch (XMLStreamException ex) {
	            System.out.println("Exception de type 'XMLStreamException' lors de la lecture du fichier : " + nomFichier);
	            System.out.println("Details :");
	            System.out.println(ex);
	        } catch (IOException ex) {
	            System.out.println("Exception de type 'IOException' lors de la lecture du fichier : " + nomFichier);
	            System.out.println("Verifier le chemin.");
	            System.out.println(ex.getMessage());
	        }
		
		
		
		return listePartie;
	}

}
