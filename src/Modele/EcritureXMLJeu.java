package Modele;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class EcritureXMLJeu {

	
	public EcritureXMLJeu(){
		
	}
	
		public static void ecritureXML(JeuModele model,String nomFichier)
		{	ArrayList<Partie> listePartie= LectureXMLJeu.lectureXML(nomFichier);
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
				for(int i=0;i<listePartie.size();i++)
					{
					System.out.println("test");
						writer.writeStartElement("Partie");
							writer.writeStartElement("Date");
								writer.writeCharacters(listePartie.get(i).getDate().toString());
							writer.writeEndElement();
							writer.writeStartElement("Colonnes");
								writer.writeCharacters(Integer.toString(listePartie.get(i).getColonnes()));
							writer.writeEndElement();
							writer.writeStartElement("Lignes");
								writer.writeCharacters(Integer.toString(listePartie.get(i).getLignes()));
							writer.writeEndElement();
							writer.writeStartElement("Bombes");
								writer.writeCharacters(Integer.toString(listePartie.get(i).getBombes()));
							writer.writeEndElement();
							writer.writeStartElement("Temps");
								writer.writeCharacters(Integer.toString(listePartie.get(i).getTemps()));
							writer.writeEndElement();
						writer.writeEndElement();
					}
				writer.writeEndElement();
				writer.writeEndDocument();
				writer.flush();
                writer.close();
				
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
