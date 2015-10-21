package videvox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import guicomponents.BottomPanel;
import swingworker.AudioVideoMerger;
import swingworker.Rewind;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ActionListeners {
	
	private AddCommentary window;
	BottomPanel bottompanel;
	JPanel panel;
	String videoDirectory;
	String audioDirectory;
	public Rewind goBack = null;
	EmbeddedMediaPlayer mediaPlayer;
	
	public ActionListeners(BottomPanel btmpanel, JPanel panel, EmbeddedMediaPlayer mediaPlayer) {
		this.bottompanel = btmpanel;
		this.panel = panel;
		this.mediaPlayer = mediaPlayer;
		createListeners();
	
	}

	public void createListeners() {

		// mute button - switches between muting and unmuting of the video, changing icons also
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

		// play button - plays or pauses the video depending on the state the video is currently in
		// also switches between play and pause icons
		bottompanel.play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.getRate() == 1f && mediaPlayer.isPlaying()) { // playing or rewinding
					if (goBack != null) { // if it is rewinding 
						goBack.cancel(true);
						bottompanel.play.setIcon(bottompanel.pauseImage);
						goBack = null;
					} else { // if it is playing 
						bottompanel.play.setIcon(bottompanel.playImage);
						mediaPlayer.pause();
					}
				} else if (mediaPlayer.getRate() == 10f) { // fast forwarding
					mediaPlayer.setRate(1f);
					bottompanel.play.setIcon(bottompanel.pauseImage);					
				} else {  //everything else - paused 
					bottompanel.play.setIcon(bottompanel.pauseImage);
					mediaPlayer.play();
				}
			}
		});

		// fast forward button - sets the rate of the video to fast forward 
		bottompanel.fwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.setRate(10f);
				bottompanel.play.setIcon(bottompanel.playImage);
			}
		});

		// rewind button - creates a rewind object (swing worker) to rewind 
		bottompanel.rewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bottompanel.play.setIcon(bottompanel.playImage);
				goBack = new Rewind(mediaPlayer);
				goBack.execute();
			}
		});

		// skip forward button - skips once 
		bottompanel.skipfwd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.skip(5000);
			}
		});

		// skip backward - skips back once
		bottompanel.skipback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.skip(-5000);
			}
		});

		// full screen button  - toggles in and out of full screen mode
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

		// open file button - lets the user open a avi file to play in the media player 
		// we have limited the files they can pick to avi only 
		bottompanel.openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter(".mp4", "mp4"));
				fc.showOpenDialog(panel);
				if (fc.getSelectedFile() != null) {
					videoDirectory = fc.getSelectedFile().getAbsolutePath();
					mediaPlayer.playMedia(videoDirectory);
				}
			}
		});

		// create commentary button - lets the user type in words to create new voice overs, as well as test them before saving
		bottompanel.createCommentary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				window = new AddCommentary();
			}
		});
		
		// add commentary button - lets the user add an already created commentary, or any mp3 and merge with the current playing video
		// creates an audio object (swing worker) and executes the swing worker 
		bottompanel.addCommentary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (videoDirectory == null) {
					JOptionPane.showMessageDialog(panel,
							"Please choose a video first.");
				} else {
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
					}
					AudioVideoMerger addAudio;
					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter(".mp3", "mp3"));
					fc.showOpenDialog(panel);
					if (fc.getSelectedFile() != null) {
						audioDirectory = fc.getSelectedFile().getAbsolutePath();
						String saveAs = JOptionPane
								.showInputDialog("Save file as: ");
						addAudio = new AudioVideoMerger(audioDirectory, saveAs, mediaPlayer);
						addAudio.execute();

					}
				}
			}
		});

	}

}
