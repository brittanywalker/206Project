package guicomponents;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import vidivox.ActionListeners;

public class MediaPlayer {

	private static final long serialVersionUID = 1L;
	private MediaPlayerFactory mediaPlayerFactory;
	FullScreenStrategy fullScreenStrategy;
	public EmbeddedMediaPlayer mediaPlayer;
	JFrame window;

	private JPanel panel;
	private Canvas canvas;
	ActionListeners action;
	BottomPanel btmpanel;

	public static String videoDirectory;

	/* contstruct the media player, setting the frame, title, panels and attaching a surface for the video to play.
	 * call methods to create buttons
	 * , actionlisteners and layout. 
	 * play the media if it a media has been selected. 
	 */
	public MediaPlayer() {

		window = new JFrame();
		window.setTitle("Vidivox");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(500, 200, 853, 637);
		window.setBackground(Color.darkGray);
		
		initialisePlayer();
		createPanel();
		action = new ActionListeners(btmpanel, panel, mediaPlayer);
		
		window.add(panel);
		window.setVisible(true);
	}
	
	public void initialisePlayer() {
		
		canvas = new Canvas();
		//canvas.setBounds(0, 0, 853, 600);
		canvas.setBackground(Color.darkGray);

		mediaPlayerFactory = new MediaPlayerFactory();
		fullScreenStrategy = new DefaultFullScreenStrategy(window);
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);
	}
	
	public void createPanel() {
		
		panel = new JPanel();
		panel.setBackground(Color.darkGray);
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.setLayout(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);

		btmpanel = new BottomPanel(panel, mediaPlayer);
		addTopPanel();
	}
	
	public void addTopPanel() {
		JMenuBar menu = new JMenuBar();
		JMenu open = new JMenu("Open");
		JMenu festival = new JMenu("Create");
		JMenuItem txtSpeech = new JMenuItem("Text to Speech");
		menu.add(open);
		menu.add(festival);
		festival.add(txtSpeech);
		panel.add(menu, BorderLayout.NORTH);
	}
	

}
