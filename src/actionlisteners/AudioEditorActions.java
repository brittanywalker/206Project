package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import guicomponents.AudioEditor;
import guicomponents.EditingTable;
import tablemodel.AudioFileTableModel;
import vidivox.AudioFile;

public class AudioEditorActions {

	static String fullVideoDirectory;
	String fullAudioDirectory;
	JFileChooser videoChooser;
	JFileChooser audioChooser;
	JFrame frame;
	AudioFile audioFile;
	String startMins;
	String startSecs;
	EditingTable editor;

	public AudioEditorActions(JFrame frame) {
		this.frame = frame;
		openActionListeners();
		addAudioListener();
		checkBoxListener();
		editor = new EditingTable();
	}

	public void openActionListeners() {

		guicomponents.AudioEditor.openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				videoChooser = new JFileChooser("Choose a video(.mp4)");
				videoChooser.setFileFilter(new FileNameExtensionFilter(".mp4", "mp4"));
				videoChooser.showOpenDialog(frame);
				if (videoChooser.getSelectedFile() != null) {
					fullVideoDirectory = videoChooser.getSelectedFile().getAbsolutePath();
					AudioEditor.videoField.setText(videoChooser.getSelectedFile().getPath());
				}
			}
		});

		guicomponents.AudioEditor.mp3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				audioChooser = new JFileChooser("Choose an audio file (.mp3)");
				audioChooser.setFileFilter(new FileNameExtensionFilter(".mp3", "mp3"));
				audioChooser.showOpenDialog(frame);
				if (audioChooser.getSelectedFile() != null) {
					fullAudioDirectory = audioChooser.getSelectedFile().getAbsolutePath();
					AudioEditor.audioField.setText(audioChooser.getSelectedFile().getPath());
				}
			}
		});
	}

	public void addAudioListener() {
		guicomponents.AudioEditor.addtoVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((AudioEditor.startMins.getText().equals("") || AudioEditor.startMins.getText() == null)
						&& (AudioEditor.startSecs.getText().equals("") || AudioEditor.startSecs.getText() == null))
						&& !(AudioEditor.audioField.getText().equals("") || AudioEditor.audioField.getText() == null)) {

					startMins = AudioEditor.startMins.getText();
					startSecs = AudioEditor.startSecs.getText();
					if (startMins == null || startMins.equals("")) {
						startMins = "0";
					}
					if (startSecs == null || startSecs.equals("")) {
						startSecs = "0";
					}
					audioFile = new AudioFile(fullAudioDirectory, startMins, startSecs);
					AudioFileTableModel.files.add(audioFile);
					AudioEditor.startMins.setText("");
					AudioEditor.startSecs.setText("");
					AudioEditor.audioField.setText("");
					editor.model.fireTableDataChanged();
					

				} else if ((AudioEditor.startMins.getText().equals("") || AudioEditor.startMins.getText() == null)
						&& (AudioEditor.startSecs.getText().equals("") || AudioEditor.startSecs.getText() == null)) {
					JOptionPane.showMessageDialog(frame, "You must enter a time to start\n the audio file at");
				} else {
					JOptionPane.showMessageDialog(frame, "Please first select an mp3 file to add");
				}

			}
		});
	}

	public void checkBoxListener() {
		guicomponents.AudioEditor.keepYes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (guicomponents.AudioEditor.keepYes.isSelected()) {
					guicomponents.AudioEditor.keepNo.setSelected(false);
				}
				
			}
			
		});
		guicomponents.AudioEditor.keepNo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (guicomponents.AudioEditor.keepNo.isSelected()) {
					guicomponents.AudioEditor.keepYes.setSelected(false);
				}
				
			}
			
		});
	}

}
