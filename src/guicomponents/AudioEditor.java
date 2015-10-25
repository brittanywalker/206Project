package guicomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import actionlisteners.AudioEditorActions;
import vidivox.JTextFieldLimit;

public class AudioEditor {
	
	public JFrame audioEditor;
	JPanel addmp3;
	JPanel videoPanel;
	JLabel keepSound;
	JLabel textVideo;
	public static JButton openButton;
	public static JCheckBox keepYes;
	public static JCheckBox keepNo;
	JPanel audioPanel;
	JLabel textAudio;
	public static JButton mp3Button;
	JPanel originalSoundPanel;
	JPanel timePanel;
	JLabel startTime;
	public static JTextField startMins;
	public static JTextField startSecs;
	public static JTextField videoField;
	public static JTextField audioField;
	public static JButton addtoVideo;
	JLabel secs;
	JLabel mins;
	AudioEditorActions actions;
	
	public AudioEditor() {
		
		audioEditor = new JFrame("Audio Editor");
		audioEditor.getContentPane().setLayout(new BoxLayout(audioEditor.getContentPane(), BoxLayout.Y_AXIS));
		audioEditor.setBounds(500, 750, 800, 200);
		createPanels();
		audioEditor.add(videoPanel);
		audioEditor.add(originalSoundPanel);
		audioEditor.add(audioPanel);
		audioEditor.add(timePanel);
		audioEditor.setVisible(true);
		actions = new AudioEditorActions(audioEditor);
	}
	
	public void createPanels() {
		videoPanel();
		audioPanel();
		originalSoundPanel();
		timePanel();
		videoPanelLayout();
		audioPanelLayout();
		soundPanelLayout();
		timePanelLayout();
	}
	
	public void videoPanel() {
		videoPanel = new JPanel();
		videoPanel.setBackground(Color.darkGray);
		textVideo = new JLabel("Open the video you would like to edit:");
		textVideo.setForeground(Color.WHITE);
		videoField = new JTextField();
		videoField.setEditable(false);
		videoField.setColumns(30);
		openButton = new JButton("Browse...");
		openButton.setForeground(Color.darkGray);
	}
	
	public void originalSoundPanel() {
		originalSoundPanel = new JPanel();
		originalSoundPanel.setBackground(Color.darkGray);
		originalSoundPanel.setLayout(new FlowLayout());
		keepSound = new JLabel("Would you like to keep the video's original audio?");
		keepSound.setForeground(Color.WHITE);
		keepYes = new JCheckBox("Yes");
		keepYes.setBackground(Color.darkGray);
		keepYes.setForeground(Color.WHITE);
		keepNo = new JCheckBox("No");
		keepNo.setBackground(Color.darkGray);
		keepNo.setForeground(Color.WHITE);
	}
	
	public void audioPanel() {
		audioPanel = new JPanel();
		audioPanel.setBackground(Color.darkGray);
		textAudio = new JLabel("Open the .mp3 file you would like to add:");
		textAudio.setForeground(Color.WHITE);
		audioField = new JTextField();
		audioField.setEditable(false);
		audioField.setColumns(30);
		mp3Button = new JButton("Browse...");
		mp3Button.setForeground(Color.darkGray);
	}
	
	public void timePanel() {
		timePanel = new JPanel(new FlowLayout());
		timePanel.setBackground(Color.darkGray);
		startTime = new JLabel("At what time would you like the mp3 to begin playing?");
		startTime.setForeground(Color.WHITE);
		startMins = new JTextField();
		startMins.setColumns(2);
		startMins.setDocument(new JTextFieldLimit(2));
		startSecs = new JTextField();
		startSecs.setColumns(2);
		startSecs.setDocument(new JTextFieldLimit(2));
		addtoVideo = new JButton("Add to Video");
		mins = new JLabel("mins");
		mins.setForeground(Color.WHITE);
		secs = new JLabel("secs");
		secs.setForeground(Color.WHITE);
	}
	
	public void audioPanelLayout() {
		audioPanel.setLayout(new FlowLayout());
		audioPanel.add(textAudio);
		audioPanel.add(audioField);
		audioPanel.add(mp3Button);
	}
	
	public void videoPanelLayout() {
		videoPanel.setLayout(new FlowLayout());
		videoPanel.add(textVideo);
		videoPanel.add(videoField);
		videoPanel.add(openButton);	
		
	}
	
	public void soundPanelLayout() {
		originalSoundPanel.add(keepSound);
		originalSoundPanel.add(keepYes);
		originalSoundPanel.add(keepNo);
	}
	
	public void timePanelLayout() {
		timePanel.setLayout(new FlowLayout());
		timePanel.add(startTime);
		timePanel.add(startMins);
		timePanel.add(mins);
		timePanel.add(startSecs);
		timePanel.add(secs);
		timePanel.add(addtoVideo);
	}

}
