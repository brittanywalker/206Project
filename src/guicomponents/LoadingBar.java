package guicomponents;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class LoadingBar extends JFrame{
	
	public LoadingBar() {
		super("Loading...");
		setLayout(new BorderLayout());
		JProgressBar progress = new JProgressBar();
		progress.setIndeterminate(true);
		JLabel label = new JLabel("Please be patient this may take awhile...");
		add(label, BorderLayout.NORTH);
		add(progress, BorderLayout.CENTER);
	}

}
