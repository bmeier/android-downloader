package de.bmeier.android.downloader;

public class Download {
	public enum State {
		CANCELED, ERROR, FINISHED, QUEUED, RUNNING, WAITING;
	}

	private String mFileName;
	private String mDirectory;
	private long mId;
	private String mURL;
	private long mSize;
	private State mState;

	public void setDirectory(String directory) {
		mDirectory = directory;
	}

	public String getDirectory() {
		return mDirectory;
	}

	public String getFileName() {
		return mFileName;
	}

	public long getId() {
		return mId;
	}

	public String getURL() {
		return mURL;
	}

	public long getSize() {
		return mSize;
	}

	public State getState() {
		return mState;
	}

	public void setFileName(String fileName) {
		mFileName = fileName;
	}

	public void setId(long id) {
		mId = id;
	}

	public void setURL(String url) {
		mURL = url;
	}

	public void setSize(long size) {
		mSize = size;
	}

	public void setState(State state) {
		if (state == mState)
			return;
		mState = state;
	}
}
