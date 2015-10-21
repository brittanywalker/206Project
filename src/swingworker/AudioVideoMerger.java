package swingworker;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import guicomponents.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/*
 * This class extends SwingWorker because it deals with the merging of the audio and video 
 * file chosen by the user. This process is fast for short videos however for longer ones
 * SwingWorker is needed so as to stop the GUI from freezing.
 */
public class AudioVideoMerger extends SwingWorker<Void, String> {

	String audio;
	String saveAs;
	EmbeddedMediaPlayer mediaPlayer;

	public AudioVideoMerger(String audio, String saveAs, EmbeddedMediaPlayer mediaPlayer) {
		this.audio = audio;
		this.saveAs = saveAs;
		this.mediaPlayer = mediaPlayer;

	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 * The doInBackground method deals with the heavy work, which is the merging of the two files 
	 * (or the replacing of the video's sound with the audio files sound).
	 * Once this is done, it will then play the new video.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		
		try {
			
			String merge = "ffmpeg -i " + MediaPlayer.videoDirectory + " -i "
					+ audio + " -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 " + home + "/BCVideo/" + saveAs + ".avi";
			//String merge = "ffmpeg -i " + videoplayback.mp4 -i videoplayback.m4a -c:v copy -c:a copy output.mp4";
			ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
			Process proc = pb.start();
			proc.waitFor();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Your new video was saved to the BCVideo directory, which can be found in your home directory.");
		return null;
	}

	public void done() {
		String home = System.getProperty("user.home");
		mediaPlayer.playMedia(home + "/BCVideo/" + saveAs + ".mp4");

	}

}
