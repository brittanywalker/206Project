package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import guicomponents.AddCommentary;
import guicomponents.AudioEditor;
import guicomponents.BottomPanel;
import guicomponents.EditingTable;
import guicomponents.MediaPlayer;
import swingworker.AudioVideoMerger;
import tablemodel.AudioFileTableModel;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MediaPlayerActions {

	private AddCommentary window;
	BottomPanel bottompanel;
	JPanel panel;
	public static String currentVideoDirectory;
	String videoToMerge;
	String audioDirectory;
	public boolean mousePressedPlaying = false;
	EmbeddedMediaPlayer mediaPlayer;
	AudioEditor editAudio;

	public MediaPlayerActions(BottomPanel btmpanel, JPanel panel, EmbeddedMediaPlayer mediaPlayer) {
		this.bottompanel = btmpanel;
		this.panel = panel;
		this.mediaPlayer = mediaPlayer;
		bottomPanelListeners();
		topPanelListeners();

	}

	public void bottomPanelListeners() {

		// mute button - switches between muting and unmuting of the video,
		// changing icons also
		bottompanel.mute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bottompanel.mute.getIcon().equals(bottompanel.muteImage)) {
					bottompanel.mute.setIcon(bottompanel.unmuteImage);
				} else {
					bottompanel.mute.setIcon(bottompanel.muteImage);
				}
				mediaPlayer.mute();
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
				if (mediaPlayer.isFullScreen()) {
					mediaPlayer.toggleFullScreen();
					MediaPlayer.window.setBounds(500, 100, 850, 550);
				} else {
					mediaPlayer.toggleFullScreen();
				}
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

		// add commentary button - lets the user add an already created
		// commentary, or any mp3 and merge with the current playing video
		// creates an audio object (swing worker) and executes the swing worker
		bottompanel.openAudioEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				if (bottompanel.openAudioEditor.getText().equals("Open Audio Editor")) {
					editAudio = new AudioEditor();
					bottompanel.openAudioEditor.setText("Close Audio Editor");
				} else {
					int option = JOptionPane.showConfirmDialog(panel, "Please make sure you have saved your\nvideo "
							+ "otherwise your work will be lost.\n Do you want to continue?", "Are you sure you want to continue?",
							JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						editAudio.audioEditor.setVisible(false);
						EditingTable.editingPanel.setVisible(false);
						bottompanel.openAudioEditor.setText("Open Audio Editor");
					}	
				}
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

	public void topPanelListeners() {

		guicomponents.MediaPlayer.open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
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
		guicomponents.MediaPlayer.txtSpeech.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				window = new AddCommentary();
			}
		});

	}

}
