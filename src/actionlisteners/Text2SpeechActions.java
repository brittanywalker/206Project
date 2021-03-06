package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import guicomponents.AddCommentary;
import swingworker.PreviewFestival;

public class Text2SpeechActions {

	String saveDirectory;
	JFrame contentPane;
	Text2SpeechActions actions;
	PreviewFestival pr = null;

	public Text2SpeechActions(JFrame commentary) {
		this.contentPane = commentary;
		createActionListeners();
	}

	public void createActionListeners() {

		AddCommentary.btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (guicomponents.AddCommentary.textArea.getText() == null
						|| guicomponents.AddCommentary.textArea.getText().equals("")) {

					JOptionPane.showMessageDialog(contentPane,
							"There must be text in the text box\nin order to listen to it.");

				} else {
					String text = guicomponents.AddCommentary.textArea.getText();
					if (guicomponents.AddCommentary.btnSpeak.getText().equals("Speak")) {
						guicomponents.AddCommentary.btnSpeak.setText("Stop");
						pr = new PreviewFestival(text);
						pr.execute();
					} else {
						guicomponents.AddCommentary.btnSpeak.setText("Speak");
						pr.cancel(true);
					}
				}
			}
		});

		/*
		 * When the save button is pressed the text that the user has entered
		 * will be converted into an mp3 file and saved to a directory called
		 * BCAudio which can be found in their home directory. The user will be
		 * prompted for the name that they want to save the file as.
		 */
		AddCommentary.btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (guicomponents.AddCommentary.textArea.getText() == null
						|| (guicomponents.AddCommentary.textArea.getText().equals(""))) {

					JOptionPane.showMessageDialog(contentPane,
							"There must be text in the text box\nin order to convert it to mp3.");

				} else {
					getFileDirectory();
					String name = JOptionPane.showInputDialog("Save file as: ");
					int option;
					if (name == null) {
					} else if (name.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "File names can not be empty.");
					} else if (new File(saveDirectory + "/" + name + ".mp3").exists()) {
						option = JOptionPane.showConfirmDialog(contentPane,
								"There is already a file with this name,\n would you like to overwrite it?",
								"OverWrite?", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							String text = guicomponents.AddCommentary.textArea.getText();
							String convert = "echo '" + text + "' | text2wave -o " + saveDirectory + "/output.wav";
							ProcessBuilder text2wave = new ProcessBuilder("/bin/bash", "-c", convert);

							String save = "ffmpeg -i " + saveDirectory + "/output.wav " + saveDirectory + "/" + name
									+ ".mp3";
							ProcessBuilder wav2mp3 = new ProcessBuilder("/bin/bash", "-c", save);

							String remove = "rm " + saveDirectory + "/output.wav";
							ProcessBuilder removetemp = new ProcessBuilder("/bin/bash", "-c", remove);

							try {
								Process p = text2wave.start();
								p.waitFor();
								Process p1 = wav2mp3.start();
								p1.waitFor();
								removetemp.start();
							} catch (IOException | InterruptedException e2) {
								e2.printStackTrace();
							}
							JOptionPane.showMessageDialog(contentPane, "Your mp3 has been saved.");
						}
					} else {
						String text = guicomponents.AddCommentary.textArea.getText();
						String convert = "echo '" + text + "' | text2wave -o " + saveDirectory + "/output.wav";
						ProcessBuilder text2wave = new ProcessBuilder("/bin/bash", "-c", convert);

						String save = "ffmpeg -i " + saveDirectory + "/output.wav " + saveDirectory + "/" + name
								+ ".mp3";
						ProcessBuilder wav2mp3 = new ProcessBuilder("/bin/bash", "-c", save);

						String remove = "rm " + saveDirectory + "/output.wav";
						ProcessBuilder removetemp = new ProcessBuilder("/bin/bash", "-c", remove);

						try {
							Process p = text2wave.start();
							p.waitFor();
							Process p1 = wav2mp3.start();
							p1.waitFor();
							removetemp.start();
						} catch (IOException | InterruptedException e2) {
							e2.printStackTrace();
						}
						JOptionPane.showMessageDialog(contentPane, "Your mp3 has been saved.");
					}
				}

			}
		});
	}

	public void getFileDirectory() {
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser filechooser = new JFileChooser("Save..");
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		filechooser.setAcceptAllFileFilterUsed(false);
		if (filechooser.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
			saveDirectory = filechooser.getSelectedFile().getAbsolutePath();
		} else {
			JOptionPane.showMessageDialog(contentPane, "No directory selected");
		}
	}

}
