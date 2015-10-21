package swingworker;

import javax.swing.SwingWorker;

import guicomponents.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import videvox.ActionListeners;

/* 
 * This rewind class makes a swing worker object to rewind our media.  
 * We used swing worker as to not freeze the gui, and so that the rewind could be cancelled (using the play button)
 */

public class Rewind extends SwingWorker<Void, String> {
	
	EmbeddedMediaPlayer mediaPlayer;
	
	public Rewind(EmbeddedMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 * continuously skip back unless cancelled or the start of the video is reached

	 */
	@Override
	protected Void doInBackground() throws Exception {
		while (!isCancelled() && mediaPlayer.getTime() > 0) {
			mediaPlayer.skip(-15);
			
		}
		return null;
	}

	/* 	 
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 * when the rewind is done or cancelled - set the object to be null, and if it is not cancelled the play button needs to be set to pause. (non-Javadoc)
	 */
	public void done() {
		//if (!isCancelled()) {
		//	play.setIcon(MediaPlayer.pauseImage);
		//}
		//ActionListeners.goBack = null;
	}
}