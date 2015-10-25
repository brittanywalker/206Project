package vidivox;

public class AudioFile {
	
	String absPath;
	String startMins;
	String startSecs;
	String fileName;

	public AudioFile(String absPath, String fileName, String startMins, String startSecs) {
		this.absPath = absPath;
		this.fileName = fileName;
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
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
