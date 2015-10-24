package vidivox;

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

import swingworker.PreviewFestival;

public class AddCommentary {

	private static final long serialVersionUID = 1L;
	JFrame commentary;
	private JPanel contentPane;
	PreviewFestival pr = null;
	JLabel lblAddCommentary;
	JTextArea textArea;
	public static ImageIcon speakImage;
	ImageIcon saveImage;
	ImageIcon stopImage;
	public static JButton btnSpeak;
	JButton btnSave;
	JButton btnClose;
	JLabel lblMaxWords;
	String saveDirectory;

	/**
	 * The constructor makes the frame and attaches a panel to it. Three methods are
	 * then used to create the fields, organise the layout and create Action Listeners.
	 */
	public AddCommentary() {
		
		commentary = new JFrame();
		commentary.setBounds(100, 100, 450, 295);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		commentary.setContentPane(contentPane);
		contentPane.setLayout(null);

		createFields();
		createLayout();
		createActionListeners();
		
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

		lblAddCommentary = new JLabel("Add Commentary");
		lblAddCommentary.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 16));
		lblAddCommentary.setBounds(155, 29, 145, 28);

		textArea = new JTextArea();
		textArea.setBounds(60, 80, 320, 100);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.LIGHT_GRAY);

		speakImage = new ImageIcon("buttons/speak.png");
		stopImage = new ImageIcon("buttons/stop.png");
		btnSpeak = new JButton(speakImage);
		btnSpeak.setBounds(250, 210, 50, 50);

		saveImage = new ImageIcon("buttons/Save.png");
		btnSave = new JButton(saveImage);
		btnSave.setBounds(309, 210, 50, 50);
		
		lblMaxWords = new JLabel("Max. words 30");
		lblMaxWords.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 11));
		lblMaxWords.setBounds(70, 179, 112, 15);

	}

	/*
	 * createLayout is simply used to add each of the components made in 
	 * createFields to the panel.
	 */
	public void createLayout() {

		contentPane.add(lblAddCommentary);
		contentPane.add(textArea);
		contentPane.add(btnSpeak);
		contentPane.add(btnSave);	
		contentPane.add(lblMaxWords);

	}

	/*
	 * createActionListeners for the speak, save and close buttons. The Speak button
	 * when pressed creates a BackGroundProcessor which will pass the text to Festival, 
	 * and change the button to the Stop image. If the button is then pressed again while 
	 * Festival is still speaking, then Festival will be terminated by cancelling the
	 * BackGroundProcessor instance.
	 */
	public void createActionListeners() {

		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getText();
				if (btnSpeak.getIcon().equals(speakImage)) {
					btnSpeak.setIcon(stopImage);
					pr = new PreviewFestival(text);			
					pr.execute();
				} else {
					btnSpeak.setIcon(speakImage);
					pr.cancel(true);
				}
			}
		});

		/*
		 * When the save button is pressed the text that the user has entered will be converted
		 * into an mp3 file and saved to a directory called BCAudio which can be found in their
		 * home directory. The user will be prompted for the name that they want to save the 
		 * file as.
		 */
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = JOptionPane.showInputDialog("Save file as: ");
				getFileDirectory();
				
				String text = textArea.getText();
				String convert = "echo '" + text + "' | text2wave -o " + saveDirectory + "/output.wav";
				ProcessBuilder text2wave = new ProcessBuilder("/bin/bash", "-c", convert);
				
				String save = "ffmpeg -i " + saveDirectory + "/output.wav " + saveDirectory + "/" + name + ".mp3";				
				ProcessBuilder wav2mp3 = new ProcessBuilder("/bin/bash", "-c", save);
				
				//String remove = "rm " + saveDirectory + "/output.wav";
				//ProcessBuilder removetemp = new ProcessBuilder("/bin/bash", "-c", remove);
				
				try {
					Process p = text2wave.start();
					p.waitFor();
					Process p1 = wav2mp3.start();
					p1.waitFor();
					///removetemp.start();
				} catch (IOException | InterruptedException e2) {
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(contentPane, "Your mp3 has been saved.");
			}
		});
	}
	
	public void getFileDirectory() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filechooser.setAcceptAllFileFilterUsed(false);
		if (filechooser.showSaveDialog(commentary) == JFileChooser.APPROVE_OPTION) {
			saveDirectory = filechooser.getSelectedFile().getAbsolutePath();
		} else {
			JOptionPane.showMessageDialog(commentary, "No directory selected");
		}
	}
}
