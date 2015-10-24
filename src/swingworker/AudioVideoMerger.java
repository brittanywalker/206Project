package swingworker;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import actionlisteners.AudioEditorActions;
import guicomponents.AudioEditor;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import vidivox.AudioFile;

/*
 * This class extends SwingWorker because it deals with the merging of the audio and video 
 * file chosen by the user. This process is fast for short videos however for longer ones
 * SwingWorker is needed so as to stop the GUI from freezing.
 */
public class AudioVideoMerger extends SwingWorker<Void, String> {

	ArrayList<AudioFile> audioFiles;
	String videoDirectory;
	String saveFileAs;
	EmbeddedMediaPlayer mediaPlayer;
	JPanel panel;
	String saveDirectory;

	public AudioVideoMerger(ArrayList<AudioFile> audioFiles, String saveFileAs, String videoDirectory,
			String saveDirectory, EmbeddedMediaPlayer mediaPlayer, JPanel panel) {
		this.audioFiles = audioFiles;
		this.videoDirectory = videoDirectory;
		this.saveFileAs = saveFileAs;
		this.saveDirectory = saveDirectory;
		this.mediaPlayer = mediaPlayer;
		this.panel = panel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#doInBackground() The doInBackground method
	 * deals with the heavy work, which is the merging of the two files (or the
	 * replacing of the video's sound with the audio files sound). Once this is
	 * done, it will then play the new video.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		String outputVideo = saveDirectory + "/" + saveFileAs + ".mp4";
		System.out.println(outputVideo);
		try {
			
			if (AudioEditor.keepYes.isSelected()) {
				for (int i = 0; i < 1; i++) {
					int seconds = Integer.parseInt(audioFiles.get(i).getStartSecs());
					int mins = Integer.parseInt(audioFiles.get(i).getStartMins());
					int milliseconds = seconds*1000 + mins*10000;
					if (!(milliseconds > 0)) {
						milliseconds = 15;
					}
					String merge = "ffmpeg -y -i " + videoDirectory + " -i " + audioFiles.get(i).getAbsPath() + 
							" -filter_complex \"[1:a]adelay=" + milliseconds + "[aud1];[aud1][0:a] amix=inputs=2\" "
							+ outputVideo;
					ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
					Process proc = pb.start();
					proc.waitFor();
				}
				for (int i = 1; i < audioFiles.size(); i++) {
					int seconds = Integer.parseInt(audioFiles.get(i).getStartSecs());
					int mins = Integer.parseInt(audioFiles.get(i).getStartMins());
					int milliseconds = seconds*1000 + mins*10000;
					if (!(milliseconds > 0)) {
						milliseconds = 15;
					}
					String merge = "ffmpeg -y -i " + outputVideo + " -i " + audioFiles.get(i).getAbsPath() + 
							" -filter_complex \"[1:a]adelay=" + milliseconds + "[aud1];[aud1][0:a] amix=inputs=2\" "
							+ outputVideo;
					ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
					Process proc = pb.start();
					proc.waitFor();
				}
			} else {
				String strip = "ffmpeg -i " + videoDirectory + " -vcodec copy -an " + outputVideo;
				ProcessBuilder p = new ProcessBuilder("/bin/bash", "-c", strip);
				Process process = p.start();
				process.waitFor();
				for (int i = 0; i < audioFiles.size(); i++) {
					int seconds = Integer.parseInt(audioFiles.get(i).getStartSecs());
					int mins = Integer.parseInt(audioFiles.get(i).getStartMins());
					int milliseconds = seconds*1000 + mins*10000;
					if (!(milliseconds > 0)) {
						milliseconds = 15;
					}
					String merge = "ffmpeg -y -i " + outputVideo + " -i " + audioFiles.get(i).getAbsPath() + 
							" -filter_complex \"[1:a]adelay=" + milliseconds + "[aud1];[aud1][0:a] amix=inputs=2\" "
							+ outputVideo;
					ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
					Process proc = pb.start();
					proc.waitFor();
				}
				
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void done() {
		JOptionPane.showMessageDialog(null, "Your new video has been created");
		mediaPlayer.playMedia(saveDirectory + "/" + saveFileAs + ".mp4");
	}

}
