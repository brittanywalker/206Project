package guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class BottomPanel{
	
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
	public JButton fwd;
	public JButton rewind;
	public JButton skipfwd;
	public JButton skipback;
	public JButton play;
	public JButton fullScreen;
	public JSlider volume;
	public JButton openFile;
	public JButton createCommentary;
	public JButton addCommentary;
	public JProgressBar progressBar;
	
	public BottomPanel(JPanel panel, EmbeddedMediaPlayer mediaplayer) {
		this.panel = panel;
		this.mediaPlayer = mediaplayer;
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		createPanels();
		bottomPanel.add(positionPanel, BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void createPanels() {
		buttonPanel = new JPanel();	
		createButtonPanel();
		positionPanel = new JPanel();	
		createPositionPanel();
	}
	
	public void createButtonPanel() {
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.WHITE);
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
		ImageIcon fastForwardImage = new ImageIcon("buttons/fastforward.png");
		fwd = new JButton(fastForwardImage);
		ImageIcon rewindImage = new ImageIcon("buttons/rewind.png");
		rewind = new JButton(rewindImage);
		ImageIcon skipforwardImage = new ImageIcon("buttons/skipfwd.png");
		skipfwd = new JButton(skipforwardImage);
		ImageIcon skipBackImage = new ImageIcon("buttons/skipback.png");
		skipback = new JButton(skipBackImage);
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
	}
	
	public void buttonPanelCharacteristics() {
		
		mute.setPreferredSize(new Dimension(50, 50));
		mute.setToolTipText("Mute");		
		fwd.setPreferredSize(new Dimension(50, 50));
		fwd.setToolTipText("Fast Forward");
		rewind.setPreferredSize(new Dimension(50, 50));
		rewind.setToolTipText("Rewind");		
		skipfwd.setPreferredSize(new Dimension(50, 50));
		skipfwd.setToolTipText("Skip Forward");
		skipback.setPreferredSize(new Dimension(50, 50));
		skipback.setToolTipText("Skip Back");
		play.setPreferredSize(new Dimension(50, 50));
		play.setToolTipText("Play/Pause");
		fullScreen.setPreferredSize(new Dimension(50, 50));
		fullScreen.setToolTipText("FullScreen");	

		volume.setOrientation(JSlider.HORIZONTAL);
		volume.setMinimum(LibVlcConst.MIN_VOLUME);
		volume.setMaximum(LibVlcConst.MAX_VOLUME);
		volume.setPreferredSize(new Dimension(100, 40));
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
		progressBar.setMaximum(1000);
		progressBar.setValue(0);
	}
	
	public void buttonPanelLayout() {

		buttonPanel.add(mute);
		buttonPanel.add(volume);
		buttonPanel.add(rewind);
		buttonPanel.add(skipback);
		buttonPanel.add(play);
		buttonPanel.add(skipfwd);
		buttonPanel.add(fwd);
		buttonPanel.add(fullScreen);
		buttonPanel.add(openFile);
		buttonPanel.add(createCommentary);
		buttonPanel.add(addCommentary);	
	}
	
	public void positionPanelLayout() {
		positionPanel.add(progressBar, BorderLayout.CENTER);
	}

}
