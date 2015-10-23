package vidivox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import guicomponents.BottomPanel;
import swingworker.AudioVideoMerger;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ActionListeners {

	private AddCommentary window;
	BottomPanel bottompanel;
	JPanel panel;
	public static String currentVideoDirectory;
	String videoToMerge;
	String audioDirectory;
	public boolean mousePressedPlaying = false;
	EmbeddedMediaPlayer mediaPlayer;

	public ActionListeners(BottomPanel btmpanel, JPanel panel, EmbeddedMediaPlayer mediaPlayer) {
		this.bottompanel = btmpanel;
		this.panel = panel;
		this.mediaPlayer = mediaPlayer;
		createListeners();

	}

	public void createListeners() {

		// mute button - switches between muting and unmuting of the video,
		// changing icons also
		bottompanel.mute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isMute()) {
					bottompanel.mute.setIcon(bottompanel.muteImage);
					mediaPlayer.mute();
				} else {
					bottompanel.mute.setIcon(bottompanel.unmuteImage);
					mediaPlayer.mute();
				}
			}
		});

		// play button - plays or pauses the video depending on the state the
		// video is currently in
		// also switches between play and pause icons
		bottompanel.play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) { // playing
					bottompanel.play.setIcon(bottompanel.playImage);
					mediaPlayer.pause();
				} else {
					bottompanel.play.setIcon(bottompanel.pauseImage);
					mediaPlayer.play();
				}
				
			}
		});

		// full screen button - toggles in and out of full screen mode
		bottompanel.fullScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.toggleFullScreen();
			}
		});

		// volume slider - slides the volume of the audio up and down
		bottompanel.volume.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				mediaPlayer.setVolume(source.getValue());
			}
		});

		// open file button - lets the user open a avi file to play in the media
		// player
		// we have limited the files they can pick mp4 only
		bottompanel.openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter(".mp4", "mp4"));
				fc.showOpenDialog(panel);
				if (fc.getSelectedFile() != null) {
					currentVideoDirectory = fc.getSelectedFile().getAbsolutePath();
					mediaPlayer.playMedia(currentVideoDirectory);
				}
			}
		});

		// create commentary button - lets the user type in words to create new
		// voice overs, as well as test them before saving
		bottompanel.createCommentary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				window = new AddCommentary();
			}
		});

		// add commentary button - lets the user add an already created
		// commentary, or any mp3 and merge with the current playing video
		// creates an audio object (swing worker) and executes the swing worker
		bottompanel.addCommentary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				AudioVideoMerger addAudio;
				getAudioandVideoDirectory();
				String saveFileAs = JOptionPane.showInputDialog("What would you like to call your new video file? ");
				addAudio = new AudioVideoMerger(audioDirectory, saveFileAs, videoToMerge, mediaPlayer, panel);
				addAudio.execute();
			}
		});

		bottompanel.progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mousePosition = e.getX();
				int progressValue = (int) Math
						.round(((double) mousePosition / (double) bottompanel.progressBar.getWidth())
								* bottompanel.progressBar.getMaximum());
				long videoLength = Long.valueOf(progressValue);
				mediaPlayer.setTime(videoLength * 1000);
				bottompanel.progressBar.setValue(progressValue);
			}
		});

	}

	public void getAudioandVideoDirectory() {
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser audio = new JFileChooser();
		audio.setDialogTitle("Please choose an mp3 file to merge");
		audio.setFileFilter(new FileNameExtensionFilter(".mp3", "mp3"));
		audio.showOpenDialog(panel);
		if (audio.getSelectedFile() != null) {
			audioDirectory = audio.getSelectedFile().getAbsolutePath();

		}

		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser video = new JFileChooser();
		video.setDialogTitle("Please choose a video (mp4) file to merge");
		video.setFileFilter(new FileNameExtensionFilter(".mp4", "mp4"));
		video.showOpenDialog(panel);
		if (video.getSelectedFile() != null) {
			videoToMerge = video.getSelectedFile().getAbsolutePath();

		}
	}

}
