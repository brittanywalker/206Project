package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public TableListener(JTable table, JFrame frame, EmbeddedMediaPlayer mediaPlayer) {
		this.frame = frame;
		this.mediaPlayer = mediaPlayer;
		this.table = table;
		deleteListener();
		saveListener();
	}
	
	public void deleteListener(){
		guicomponents.EditingTable.deleteEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Please select an entry to delete");
				} else {
					int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want/nto delete this entry?");
					if (option == 1) {
						int del = table.getSelectedRow();
						AudioFileTableModel.files.remove(del-1);
						EditingTable.model.fireTableDataChanged();
					}
				}
				
			}
			
		});
	}
	
	public void saveListener() {
		guicomponents.EditingTable.saveNewVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String saveFileAs = JOptionPane.showInputDialog("Save As: ");
				if (AudioEditor.keepYes.isSelected() || AudioEditor.keepNo.isSelected()) {
					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JFileChooser saveNewVideo = new JFileChooser();
					saveNewVideo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					saveNewVideo.setAcceptAllFileFilterUsed(false);
					if (saveNewVideo.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
						String saveDirectory = saveNewVideo.getSelectedFile().getAbsolutePath();
						AudioVideoMerger addAudio = new AudioVideoMerger(AudioFileTableModel.files, saveFileAs, 
								AudioEditorActions.fullVideoDirectory, saveDirectory, mediaPlayer, frame);
								addAudio.execute();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select whether you want to keep\nthe original audio of the video first.");
				}
						
			}
		});
	}

}
