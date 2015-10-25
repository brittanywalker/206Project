package swingworker;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import actionlisteners.AudioEditorActions;
import guicomponents.AudioEditor;
import guicomponents.BottomPanel;
import guicomponents.LoadingBar;
import guicomponents.MediaPlayer;
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
	JFrame frame;
	String saveDirectory;
	String outputVideo;
	JFrame loading;

	public AudioVideoMerger(ArrayList<AudioFile> audioFiles, String saveFileAs, String videoDirectory,
			String saveDirectory, EmbeddedMediaPlayer mediaPlayer, JFrame frame) {
		this.audioFiles = audioFiles;
		this.videoDirectory = videoDirectory;
		this.saveFileAs = saveFileAs;
		this.saveDirectory = saveDirectory;
		this.mediaPlayer = mediaPlayer;
		this.frame = frame;

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
		loading = new LoadingBar();
		loading.setVisible(true);
		
		outputVideo = saveDirectory + "/" + saveFileAs;
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
							+ outputVideo + i + ".mp4";
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
					String merge = "ffmpeg -y -i " + outputVideo + (i-1) + ".mp4 -i " + audioFiles.get(i).getAbsPath() + 
							" -filter_complex \"[1:a]adelay=" + milliseconds + "[aud1];[aud1][0:a] amix=inputs=2\" "
							+ outputVideo + i + ".mp4";
					ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
					Process proc = pb.start();
					proc.waitFor();
					String remove = "rm " + outputVideo + (i-1) + ".mp4";
					ProcessBuilder rm = new ProcessBuilder("/bin/bash", "-c", remove);
					Process delete = rm.start();
					delete.waitFor();
				}
			} else {
				for (int i = 0; i < 1; i++) {
					int seconds = Integer.parseInt(audioFiles.get(i).getStartSecs());
					int mins = Integer.parseInt(audioFiles.get(i).getStartMins());
					int milliseconds = seconds*1000 + mins*10000;
					if (!(milliseconds > 0)) {
						milliseconds = 15;
					}
					String merge = "ffmpeg -y -i " + videoDirectory + " -itsoffset " + (milliseconds/1000) + " -i " 
					+ audioFiles.get(i).getAbsPath() + " -map 0:0 -map 1:a -c:v copy -async 1 " + outputVideo + i + ".mp4";
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
					String merge = "ffmpeg -y -i " + outputVideo + (i-1) + ".mp4 -i " + audioFiles.get(i).getAbsPath() + 
							" -filter_complex \"[1:a]adelay=" + milliseconds + "[aud1];[aud1][0:a] amix=inputs=2\" "
							+ outputVideo + i + ".mp4";
					System.out.println(merge);
					ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", merge);
					Process proc = pb.start();
					proc.waitFor();
					String remove = "rm " + outputVideo + (i-1) + ".mp4";
					ProcessBuilder rm = new ProcessBuilder("/bin/bash", "-c", remove);
					Process delete = rm.start();
					delete.waitFor();
				}
				
			}
			String rename = "mv " + outputVideo + (audioFiles.size()-1) + ".mp4 " + outputVideo + ".mp4";
			ProcessBuilder change = new ProcessBuilder("/bin/bash", "-c", rename);
			Process p = change.start();
			p.waitFor();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void done() {
		loading.setVisible(false);
		int option = JOptionPane.showConfirmDialog(null, "Your new video has been created.\nWould you like to play it now?", 
				"Saved", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			MediaPlayer.btmpanel.timer();
			mediaPlayer.playMedia(outputVideo + ".mp4");
		}	
	}
}
