package de.bmeier.android.downloader;

public class Download {
	public enum State {
		CANCELED, ERROR, FINISHED, QUEUED, RUNNING, WAITING;
	}

	public static class Event {
		private final Download	mSource;

		public Event(Download source) {
			mSource = source;
		}

		public Download getSource() {
			return mSource;
		}

	}

	public static class StateChangedEvent extends Event {
		private final State	mNewState;
		private final State	mOldState;

		public StateChangedEvent(Download source, State oldState, State newState) {
			super(source);
			mOldState = oldState;
			mNewState = newState;
		}

		public State getNewState() {
			return mNewState;
		}

		public State getOldState() {
			return mOldState;
		}

	}

	private String	mFileName;
	private String	mFilePath;
	private long	mId;
	private String	mLocal;
	private String	mRemote;
	private long	mSize;
	private State	mState;

	public String getFileName() {
		return mFileName;
	}

	public long getId() {
		return mId;
	}

	public String getLocal() {
		return mLocal;
	}

	public String getRemote() {
		return mRemote;
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

	public void setLocal(String local) {
		mLocal = local;
	}

	public void setRemote(String remote) {
		mRemote = remote;
	}

	public void setSize(long size) {
		mSize = size;
	}

	public void setState(State state) {
		if (state == mState)
			return;

		State oldState = mState;
		mState = state;
		StateChangedEvent event = new StateChangedEvent(this, oldState, mState);
		AD.getAD().post(event);
	}
}
