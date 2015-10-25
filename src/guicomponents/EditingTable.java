package guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import actionlisteners.TableListener;
import tablemodel.AudioFileTableModel;
import vidivox.AudioFile;
import java.awt.Font;

public class EditingTable {
	
	public static JFrame editingPanel;
	public JTable audioEntries;
	public static AudioFileTableModel model;
	public static JButton deleteEntry;
	public static JButton saveNewVideo;
	JPanel bottomPanel;
	JPanel tablePanel;
	TableListener actions;
	
	public EditingTable() {
		
		editingPanel = new JFrame("Current .mp3 Files");
		editingPanel.setBounds(1350, 300, 300, 600);
		editingPanel.setBackground(Color.darkGray);
		editingPanel.getContentPane().setLayout(new BorderLayout());
		createJTable();
		createBottomPanel();
		editingPanel.getContentPane().add(new JScrollPane(audioEntries), BorderLayout.CENTER);
		editingPanel.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		editingPanel.setVisible(true);
		actions = new TableListener(audioEntries, editingPanel, MediaPlayer.mediaPlayer);
	}
	
	public void createJTable() {
		model = new AudioFileTableModel();
		audioEntries = new JTable(model);
		audioEntries.setRowSelectionAllowed(true);	
	}
	
	public void createBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.darkGray);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		
		deleteEntry = new JButton("Delete Entry");
		deleteEntry.setFont(new Font("Dialog", Font.PLAIN, 11));
		deleteEntry.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteEntry.setBackground(Color.darkGray);
		deleteEntry.setForeground(Color.WHITE);
		deleteEntry.setBorderPainted(false);
		
		saveNewVideo = new JButton("Merge Audio with Video");
		saveNewVideo.setFont(new Font("Dialog", Font.PLAIN, 11));
		saveNewVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveNewVideo.setBackground(Color.darkGray);
		saveNewVideo.setForeground(Color.WHITE);
		saveNewVideo.setBorderPainted(false);
		
		bottomPanel.add(deleteEntry);
		bottomPanel.add(saveNewVideo);
	}

}
