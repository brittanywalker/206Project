package videvox;

import javax.swing.SwingUtilities;

import guicomponents.MediaPlayer;

public class InitialiseProject {
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MediaPlayer();
			}
		});

	}

}
