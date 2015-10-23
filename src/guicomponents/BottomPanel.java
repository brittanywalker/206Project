package guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Timer;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import vidivox.ActionListeners;

public class BottomPanel {

	JPanel panel;
	JPanel bottomPanel;
	JPanel buttonPanel;
	JPanel positionPanel;
	EmbeddedMediaPlayer mediaPlayer;

	public ImageIcon playImage;
	public ImageIcon pauseImage;
	public ImageIcon unmuteImage;
	public ImageIcon muteImage;
	public JButton mute;
	public JButton play;
	public JButton fullScreen;
	public JSlider volume;
	public JButton openFile;
	public JButton createCommentary;
	public JButton addCommentary;
	public JProgressBar progressBar;
	public JLabel currentTime;
	public JLabel endTime;
	Timer time;

	public BottomPanel(JPanel panel, EmbeddedMediaPlayer mediaplayer) {
		this.panel = panel;
		this.mediaPlayer = mediaplayer;
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		createPanels();
		bottomPanel.add(positionPanel, BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(bottomPanel, BorderLayout.SOUTH);

		time = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				beginProgress();
			}
		});
		time.start();
	}

	public void createPanels() {
		buttonPanel = new JPanel();
		createButtonPanel();
		positionPanel = new JPanel();
		createPositionPanel();
	}

	public void createButtonPanel() {
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.darkGray);
		buttonPanelComponents();
		buttonPanelCharacteristics();
		buttonPanelLayout();
	}

	public void createPositionPanel() {
		positionPanel.setLayout(new BorderLayout());
		positionPanelComponents();
		positionPanelCharacteristics();
		positionPanelLayout();
	}

	public void buttonPanelComponents() {

		muteImage = new ImageIcon("buttons/unmute.png");
		unmuteImage = new ImageIcon("buttons/mute.png");
		mute = new JButton(muteImage);
		pauseImage = new ImageIcon("buttons/pause.png");
		playImage = new ImageIcon("buttons/play.png");
		play = new JButton(pauseImage);
		ImageIcon fullScreenImage = new ImageIcon("buttons/fullscreen.png");
		fullScreen = new JButton(fullScreenImage);
		volume = new JSlider();

		ImageIcon openImage = new ImageIcon("buttons/Open.png");
		openFile = new JButton(openImage);
		ImageIcon createImage = new ImageIcon("buttons/Create.png");
		createCommentary = new JButton(createImage);
		ImageIcon addImage = new ImageIcon("buttons/Add.png");
		addCommentary = new JButton(addImage);

	}

	public void positionPanelComponents() {
		progressBar = new JProgressBar();
		currentTime = new JLabel("--:--  ");
		endTime = new JLabel("  --:--");
	}

	public void buttonPanelCharacteristics() {

		mute.setPreferredSize(new Dimension(50, 50));
		mute.setToolTipText("Mute");
		play.setPreferredSize(new Dimension(50, 50));
		play.setToolTipText("Play/Pause");
		fullScreen.setPreferredSize(new Dimension(50, 50));
		fullScreen.setToolTipText("FullScreen");

		volume.setOrientation(JSlider.HORIZONTAL);
		volume.setMinimum(LibVlcConst.MIN_VOLUME);
		volume.setMaximum(LibVlcConst.MAX_VOLUME);
		volume.setPreferredSize(new Dimension(100, 50));
		volume.setBackground(Color.WHITE);
		volume.setToolTipText("Change Volume");

		openFile.setToolTipText("Choose a video to play :D");
		openFile.setPreferredSize(new Dimension(50, 50));
		createCommentary.setToolTipText("Create a spoken commentary");
		createCommentary.setPreferredSize(new Dimension(50, 50));
		addCommentary.setToolTipText("Add a commentary to your video");
		addCommentary.setPreferredSize(new Dimension(50, 50));

	}

	public void positionPanelCharacteristics() {

		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setBackground(Color.lightGray);
		currentTime.setBackground(Color.darkGray);
		endTime.setBackground(Color.darkGray);
	}

	public void buttonPanelLayout() {

		buttonPanel.add(play);
		buttonPanel.add(fullScreen);
		buttonPanel.add(mute);
		buttonPanel.add(volume);
		buttonPanel.add(openFile);
		buttonPanel.add(createCommentary);
		buttonPanel.add(addCommentary);
	}

	public void positionPanelLayout() {
		positionPanel.add(progressBar, BorderLayout.CENTER);
		positionPanel.add(currentTime, BorderLayout.WEST);
		positionPanel.add(endTime, BorderLayout.EAST);
	}

	public void beginProgress() {

		if (ActionListeners.currentVideoDirectory == null) {
			progressBar.setValue(0);
		} else {
			long videoLength = mediaPlayer.getLength();
			long currentVideoTime = mediaPlayer.getTime();
			int videoInSeconds = (int) videoLength / 1000;
			progressBar.setValue((int) currentVideoTime / 1000);
			progressBar.setMaximum(videoInSeconds);
			
			 String cT = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(currentVideoTime), TimeUnit.MILLISECONDS.toSeconds(currentVideoTime) 
					 - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentVideoTime)));
			currentTime.setText(cT);
			String eT = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(videoLength), TimeUnit.MILLISECONDS.toSeconds(videoLength) 
					 - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(videoLength)));
			endTime.setText(eT);
		}
	}
}