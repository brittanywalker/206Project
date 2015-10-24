package guicomponents;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tablemodel.AudioFileTableModel;
import vidivox.AudioFile;

public class EditingTable {
	
	public static JFrame editingPanel;
	ArrayList<AudioFile> audioFiles;
	public JTable audioEntries;
	AudioFileTableModel model;
	
	public EditingTable(ArrayList<AudioFile> audioFiles) {
		this.audioFiles = audioFiles;
		
		editingPanel = new JFrame("Editing Table");
		editingPanel.setBounds(1350, 300, 300, 600);
		createJTable();
		editingPanel.add(new JScrollPane(audioEntries));
		editingPanel.setVisible(true);
	}
	
	public void createJTable() {
		model = new AudioFileTableModel(audioFiles);
		audioEntries = new JTable(model);
	}

}
