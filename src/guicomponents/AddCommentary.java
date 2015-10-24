package guicomponents;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import actionlisteners.Text2SpeechActions;
import swingworker.PreviewFestival;

public class AddCommentary {

	private static final long serialVersionUID = 1L;
	JFrame commentary;
	private JPanel contentPane;
	JLabel lblAddCommentary;
	public static JTextArea textArea;
	public static ImageIcon speakImage;
	ImageIcon saveImage;
	public static ImageIcon stopImage;
	public static JButton btnSpeak;
	public static JButton btnSave;
	JButton btnClose;
	JLabel lblMaxWords;
	Text2SpeechActions actionListeners;

	/**
	 * The constructor makes the frame and attaches a panel to it. Three methods are
	 * then used to create the fields, organise the layout and create Action Listeners.
	 */
	public AddCommentary() {
		
		commentary = new JFrame("Create an .mp3 file from written text");
		commentary.setBounds(100, 100, 450, 295);
		contentPane = new JPanel();
		contentPane.setBackground(Color.darkGray);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		createFields();
		createLayout();
		actionListeners = new Text2SpeechActions(contentPane);
		commentary.setContentPane(contentPane);
		commentary.setVisible(true);
	}

	/*
	 * The createFields method creates each of the components that will be going on the panel.
	 * This includes the label, text area and buttons. It then sets where they will be on the
	 * panel and in the case of the text area makes it light gray. Here we also created a label
	 * which tells the user that the maximum word limit is 30. We decided 30 because otherwise 
	 * there is the possibility that for small videos the audio length will exceed the video
	 * length. We chose however not to code the restriction because it may be the case that 
	 * the user has a particularly long video that they want to add a long commentary to.
	 */
	public void createFields() {

		textArea = new JTextArea();
		textArea.setBounds(60, 80, 320, 100);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.darkGray);

		speakImage = new ImageIcon("buttons/speak.png");
		stopImage = new ImageIcon("buttons/stop.png");
		btnSpeak = new JButton(speakImage);
		btnSpeak.setBounds(250, 210, 50, 50);
		btnSpeak.setBackground(Color.darkGray);

		saveImage = new ImageIcon("buttons/Save.png");
		btnSave = new JButton(saveImage);
		btnSave.setBounds(309, 210, 50, 50);
		btnSave.setBackground(Color.darkGray);

	}

	/*
	 * createLayout is simply used to add each of the components made in 
	 * createFields to the panel.
	 */
	public void createLayout() {

		contentPane.add(textArea);
		contentPane.add(btnSpeak);
		contentPane.add(btnSave);

	}
}
