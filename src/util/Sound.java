/** Fatma */
/** 
*/
package util;

import javax.sound.sampled.*;
import java.io.File;
import board.Board;
import java.io.File;
import java.io.IOException;

public class Sound {
	public Clip clip = null;
	public Board board;
    public void play(String sound)
    {
    	try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(sound)));
            clip.start();
        }
        catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public void bGMusic() {
        try{
        	File soundFile =new File ("background.wav");
        	AudioInputStream ais =AudioSystem.getAudioInputStream(soundFile);
        	AudioFormat format = ais.getFormat();
        	DataLine.Info info =new DataLine.Info(Clip.class, format);
        	clip =(Clip) AudioSystem.getLine(info);
        	clip.open(ais);
        	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	gainControl.setValue(-10);
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}
