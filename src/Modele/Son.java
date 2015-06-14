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

public class Son implements LineListener {
    private Clip audio;
    private AudioInputStream audioInput;
 
    public Son(String path) {
		try {
			audioInput = AudioSystem.getAudioInputStream(new File(path));
			audio = AudioSystem.getClip();
			audio.addLineListener( this );
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
	@Override
	public void update(LineEvent lineEvent) {
		if (lineEvent.getType() == LineEvent.Type.STOP){
			audio.setMicrosecondPosition(0);
		}
	}
}
