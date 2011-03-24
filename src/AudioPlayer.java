/*
 * AudioPlayer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;


public class AudioPlayer extends Thread {
	
   private final List<ActionListener> listeners;
   private final String filename;
	private final byte buffer[];
	private boolean shouldStop;
	
	/**
	 * Creates a new AudioPlayer.
	 */
	public AudioPlayer(String filename) {
	   this.listeners = new ArrayList<ActionListener>();
	   this.filename = filename;
		this.buffer = new byte[256];
		this.shouldStop = false;
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	public void fireActionEvent(String command) {
		
		ActionEvent event;
		
		if (listeners.size() == 0)
			return;
		
		event = new ActionEvent(this, 0, command);
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	/**
	 * Plays a file.
	 */
	public void run() {
		try {
			fireActionEvent("AUDIOPLAYER_PLAYING");
			play(filename);
			fireActionEvent("AUDIOPLAYER_STOPPED");
		} catch (Exception e) {
			System.err.println("[AudioPlayer] " + e.getMessage());
		}
	}
	
	private void play(String filename) throws Exception {
		
		AudioFormat format;
		AudioInputStream stream;
		DataLine.Info info;
		int count;
		SourceDataLine line;
		
		// Open file as stream
		stream = AudioSystem.getAudioInputStream(new File(filename));
		format = stream.getFormat();
		
		// Open line
		info = new DataLine.Info(SourceDataLine.class, format);
		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);
		
		// Play stream into line
		line.start();
		while ((count = stream.read(buffer, 0, buffer.length)) != -1) {
			if (count > 0) {
				line.write(buffer, 0, count);
			}
			if (shouldStop) {
				break;
			}
		}
		
		// Close line
		if (shouldStop) {
			line.flush();
		} else {
			line.drain();
		}
		line.close();
	}
	
	public void stopPlaying() {
		shouldStop = true;
	}
	
	/**
	 * Test for %AudioPlayer.
	 */
	public static void main(String[] args) {
		
		AudioPlayer audioPlayer;
		String folder, filename;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("AudioPlayer");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		folder = "/home/andy/computing/programs/dalmatian/audio/";
		filename = "that-word.wav";
		try {
			audioPlayer = new AudioPlayer(folder + filename);
			audioPlayer.start();
			audioPlayer.join();
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

