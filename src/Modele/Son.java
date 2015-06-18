package Modele;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Classe de gestion des sons.
 * 
 * @author COUTURIER Cyril
 * @since 4.0
 */
public class Son implements LineListener {
	
    private Clip audio;
    private AudioInputStream audioInput;
 
    /** 
	* Constructeur
	*
	*/
    public Son(String path) {
		try {
			audioInput = AudioSystem.getAudioInputStream(new File(path));
			audio = AudioSystem.getClip();
			audio.addLineListener( this );
			audio.open(audioInput);
		} catch (UnsupportedAudioFileException e) { System.out.println("Problème de fichier son");
		} catch (IOException e) { System.out.println("Problème de flux");
		} catch (LineUnavailableException e) { System.out.println("Problème de fichier"); }
        
    }
    
    /** 
	* Méthode pour jouer le son
	*
	*/
    public void jouer() {
    	new Thread(new Runnable() {
    		public void run() {
    			audio.start();
    		}
    	}).start();
    }
    
    /** 
	* Reset du son lorsqu'il est terminé
	*
	*/
	@Override
	public void update(LineEvent lineEvent) {
		if (lineEvent.getType() == LineEvent.Type.STOP){
			audio.setMicrosecondPosition(0);
		}
	}
}
