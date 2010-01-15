/*
 * AudioPlayer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
import java.util.Vector;



public class AudioPlayer {
	
	
	byte buffer[];
	
	
	
	/**
	 * Creates a new %AudioPlayer.
	 */
	public AudioPlayer() {
		
		buffer = new byte[10000];
	}
	
	
	
	/**
	 * Plays a file.
	 */
	public void play(File file)
	                 throws Exception {
		
		AudioFormat format;
		AudioInputStream stream;
		DataLine.Info info;
		int count;
		SourceDataLine line;
		
		// Open file as stream
		stream = AudioSystem.getAudioInputStream(file);
		format = stream.getFormat();
		
		// Open line
		info = new DataLine.Info(SourceDataLine.class, format);
		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);
		
		// Play stream into line
		line.start();
		while ((count = stream.read(buffer, 0, buffer.length)) != -1)
			if (count > 0)
				line.write(buffer, 0, count);
		
		// Close line
		line.drain();
		line.close();
	}
	
	
	
	/**
	 * Plays a file.
	 */
	public void play(String filename)
	                 throws Exception {
		
		play(new File(filename));
	}
	
	
	
	/**
	 * Test for %AudioPlayer.
	 */
	public static void main(String[] args) {
		
		AudioPlayer audioPlayer;
		String filename;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("AudioPlayer");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		filename = "/home/andy/computing/programs/dalmatian/audio/welcome.wav";
		try {
			audioPlayer = new AudioPlayer();
			audioPlayer.play(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("AudioPlayer");
		System.out.println("****************************************");
		System.out.println();
	}
}

