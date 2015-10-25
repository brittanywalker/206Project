package swingworker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.swing.SwingWorker;

import guicomponents.AddCommentary;

/*
 * BackGroundProcessor is the SwingWorker class that is used for running the Festival process
 * in the background. If we didn't have this there is the possibility that the GUI would
 * freeze when Festival is running.
 */
public class PreviewFestival extends SwingWorker<Void, String> {
	String text;
	Process process;

	public PreviewFestival(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 * In this case the doInBackground function simply runs the Festival command using
	 * a ProcessBuilder to communicate with bash. This means a string is passed into the
	 * ProcessBuilder which is actually the commands we want bash to execute.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		String cmd = "echo '" + text + "' | festival --tts ";
		ProcessBuilder build = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			process = build.start();
			process.waitFor();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 * We want to different things to happen here depending on whether the user 
	 * presses the Stop button or allows Festival to finish talking. If the user allows
	 * Festival to finish then we don't need to do anything. However if the user presses
	 * Stop then we want festival to terminate. We do this by figuring out its PID and 
	 * killing it.
	 */
	public void done() {
		if (isCancelled()) {
			if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
				Field f;
				try {
					f = process.getClass().getDeclaredField("pid");
					f.setAccessible(true);
					int pid = f.getInt(process);
					String cmd1 = "pstree -lp | grep " + pid;
					ProcessBuilder builder1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
					Process process1 = builder1.start();
					InputStream out = process1.getInputStream();
					BufferedReader in = new BufferedReader(new InputStreamReader(out));
					String line;

					if ((line = in.readLine()) != null) {
						int index = line.indexOf("play");
						String playNum = line.substring(index);

						int end = playNum.indexOf(")");
						playNum = playNum.substring(5, end);
						int playPID = Integer.parseInt(playNum);

						String cmd2 = "kill -9 " + playPID;
						ProcessBuilder builder2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
						builder2.start();
					}
				} catch (NoSuchFieldException | SecurityException | IOException | IllegalArgumentException
						| IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else {
			AddCommentary.btnSpeak.setText("Speak");
		}
	}
}