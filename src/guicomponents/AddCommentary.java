package guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import actionlisteners.Text2SpeechActions;
import swingworker.PreviewFestival;

public class AddCommentary {

	JFrame commentary;
	private JPanel topPanel;
	JPanel bottomPanel;
	public static JTextArea textArea;
	public static JButton btnSpeak;
	public static JButton btnSave;
	Text2SpeechActions actionListeners;

	/**
	 * The constructor makes the frame and attaches a panel to it. Three methods are
	 * then used to create the fields, organise the layout and create Action Listeners.
	 */
	public AddCommentary() {
		
		commentary = new JFrame("Save Text to MP3");
		commentary.setBounds(100, 100, 350, 300);
		
		createPanels();
		createFields();
		createLayout();
		
		actionListeners = new Text2SpeechActions(commentary);
		commentary.getContentPane().add(topPanel, BorderLayout.CENTER);
		commentary.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		commentary.setVisible(true);
	}
	
	public void createPanels() {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBackground(Color.darkGray);
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.darkGray);
		bottomPanel.setLayout(new FlowLayout());
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
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		textArea.setBorder(new LineBorder(Color.BLACK, 2));

		btnSpeak = new JButton("Speak");
		btnSpeak.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSpeak.setBackground(Color.darkGray);
		btnSpeak.setForeground(Color.WHITE);
		btnSpeak.setToolTipText("Preview text as audio");
		btnSpeak.setBorderPainted(false);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSave.setBackground(Color.darkGray);
		btnSave.setForeground(Color.WHITE);
		btnSave.setToolTipText("Save text to mp3");
		btnSave.setBorderPainted(false);

	}

	/*
	 * createLayout is simply used to add each of the components made in 
	 * createFields to the panel.
	 */
	public void createLayout() {

		topPanel.add(textArea, BorderLayout.CENTER);
		bottomPanel.add(btnSpeak);
		bottomPanel.add(btnSave);

	}
}
