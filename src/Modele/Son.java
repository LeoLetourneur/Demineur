package Modele;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Son {
    private Clip audio;
    private AudioInputStream audioInput;
 
    public Son(String path) {
		try {
			audioInput = AudioSystem.getAudioInputStream(new File(path));
			audio = AudioSystem.getClip();
			audio.open(audioInput);
		} catch (UnsupportedAudioFileException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		} catch (LineUnavailableException e) { e.printStackTrace(); }
        
    }
    public void jouer() {
    	new Thread(new Runnable() {
    		public void run() {
    			audio.start();
    		}
    	}).start();
    }
}
