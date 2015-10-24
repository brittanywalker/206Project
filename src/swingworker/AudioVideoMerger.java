package swingworker;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

	String audioDirectory;
	String videoDirectory;
	String saveFileAs;
	EmbeddedMediaPlayer mediaPlayer;
	JPanel panel;
	String saveDirectory;

	public AudioVideoMerger(String audioDirectory, String saveFileAs, String videoDirectory, EmbeddedMediaPlayer mediaPlayer, JPanel panel) {
		this.audioDirectory = audioDirectory;
		this.videoDirectory = videoDirectory;
		this.saveFileAs = saveFileAs;
		this.mediaPlayer = mediaPlayer;
		this.panel = panel;

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

		JFileChooser saveNewVideo = new JFileChooser();
		saveNewVideo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveNewVideo.setAcceptAllFileFilterUsed(false);
		
		if (saveNewVideo.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION) {
			saveDirectory = saveNewVideo.getSelectedFile().getAbsolutePath();
			try {
				
				String merge = "ffmpeg -i " + videoDirectory + " -i " + audioDirectory + " -c:v copy "
						+ "-c:a copy -map 0:v:0 -map 1:a:0 " + saveDirectory + "/" + saveFileAs + ".mp4";
				ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
				Process proc = pb.start();
				proc.waitFor();

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(panel, "Must select a directory to save to");
		}	
		return null;
	}

	public void done() {
		JOptionPane.showMessageDialog(null, "Your new video has been created");
		mediaPlayer.playMedia(saveDirectory + "/" + saveFileAs + ".mp4");
	}

}
