package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;

import guicomponents.AudioEditor;
import guicomponents.EditingTable;
import swingworker.AudioVideoMerger;
import tablemodel.AudioFileTableModel;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class TableListener {

	JFrame frame;
	EmbeddedMediaPlayer mediaPlayer;
	JTable table;
	String saveFileAs;
	String saveDirectory;

	public TableListener(JTable table, JFrame frame, EmbeddedMediaPlayer mediaPlayer) {
		this.frame = frame;
		this.mediaPlayer = mediaPlayer;
		this.table = table;
		deleteListener();
		saveListener();
	}

	public void deleteListener() {
		guicomponents.EditingTable.deleteEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Please select an entry to delete");
				} else {
					int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want\nto delete this entry?",
							"Please Confirm", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						int del = table.getSelectedRow();
						AudioFileTableModel.files.remove(del);
						EditingTable.model.fireTableDataChanged();
					}
				}

			}

		});
	}

	public void saveListener() {
		guicomponents.EditingTable.saveNewVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((AudioEditor.videoField.getText().equals("")) || AudioEditor.videoField.getText() == null) {
					JOptionPane.showMessageDialog(frame, "Please select a video to edit first.");
				} else if (AudioFileTableModel.files.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"You must have added at least one .mp3 file\nto save to your video.");
				} else if (!AudioEditor.keepYes.isSelected() && !AudioEditor.keepNo.isSelected()) {
					JOptionPane.showMessageDialog(frame,
							"Please select whether you want to\n keep the original audio of the\n video"
									+ " first, using the checkboxes provided.");
				} else {
					getFileDirectory();
					saveFileAs = JOptionPane.showInputDialog("Save As: ");
					int option;
					if (saveFileAs != null) {
						if (new File(saveDirectory + "/" + saveFileAs + ".mp3").exists()) {
								option = JOptionPane.showConfirmDialog(null,
										"There is already a file with this name,\n would you like" + "to overwrite it?");
								if (option == JOptionPane.YES_OPTION) {
									AudioVideoMerger addAudio = new AudioVideoMerger(AudioFileTableModel.files, saveFileAs,
											AudioEditorActions.fullVideoDirectory, saveDirectory, mediaPlayer, frame);
									addAudio.execute();
								}
						} else {
							AudioVideoMerger addAudio = new AudioVideoMerger(AudioFileTableModel.files, saveFileAs,
								AudioEditorActions.fullVideoDirectory, saveDirectory, mediaPlayer, frame);
						addAudio.execute();
						}
					} else if (saveFileAs.equals("")) {
						JOptionPane.showMessageDialog(frame, "File names can not be empty.");
					} else {
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
		if (filechooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			saveDirectory = filechooser.getSelectedFile().getAbsolutePath();
		} else {
			JOptionPane.showMessageDialog(frame, "No directory selected");
		}
	}

}
