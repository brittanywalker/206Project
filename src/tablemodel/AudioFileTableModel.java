package tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import vidivox.AudioFile;

public class AudioFileTableModel extends AbstractTableModel{
	
	public static ArrayList<AudioFile> files;

    public AudioFileTableModel() {
        files = new ArrayList<AudioFile>();
    }

    @Override
    public int getRowCount() {
        return files.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "Audio Name";
                break;
            case 1:
                name = "Start Minute";
                break;
            case 2:
            	name = "Start Seconds";
        }
        return name;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class type = String.class;
        return type;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AudioFile file = files.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = file.getFileName();
                break;
            case 1:
                value = file.getStartMins();
                break;
            case 2:
            	value = file.getStartSecs();
        }
        return value;
    }            
        
}
