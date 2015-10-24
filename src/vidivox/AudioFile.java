package vidivox;

public class AudioFile {
	
	String absPath;
	String startMins;
	String startSecs;
	
	public AudioFile(String absPath, String startMins, String startSecs) {
		this.absPath = absPath;
		this.startMins = startMins;
		this.startSecs = startSecs;
	}
	
	public String getAbsPath() {
		return absPath;
	}

	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}

	public String getStartMins() {
		return startMins;
	}

	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}

	public String getStartSecs() {
		return startSecs;
	}

	public void setStartSecs(String startSecs) {
		this.startSecs = startSecs;
	}

}
