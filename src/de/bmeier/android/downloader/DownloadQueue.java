package de.bmeier.android.downloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.eventbus.Subscribe;

public class DownloadQueue {
	public static class Event {
		private final DownloadQueue	mSource;

		public Event(DownloadQueue source) {
			mSource = source;
		}

		public DownloadQueue getSource() {
			return mSource;
		}
	}

	public static class AddEvent extends Event {
		private final Download	mDownload;

		public AddEvent(DownloadQueue source, Download download) {
			super(source);
			mDownload = download;
		}

		public Download getDownload() {
			return mDownload;
		}
	}

	private List<Download>	mDownloads	= new ArrayList<Download>();

	public DownloadQueue() {
		AD.getAD().getEventBus().register(this);
	}

	@Subscribe
	public void onStateChanged(Download.StateChangedEvent event) {
		Download.State state = event.getNewState();
		switch (state) {
		case RUNNING:
		case WAITING:
		case QUEUED:
			break;
		default:
			startNextDownload();
			break;
		}
	}

	private void startNextDownload() {
		for (Download download : mDownloads) {
			if (download.getState() == Download.State.QUEUED) {
				download.setState(Download.State.RUNNING);
			}
		}
	}

	public void add(Download download) {
		mDownloads.add(download);
		AD.getAD().getEventBus().post(new AddEvent(this, download));
	}

	public void moveUp(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx > 0) {
			mDownloads.remove(idx);
			mDownloads.add(idx - 1, download);
			Event event = new Event(this);
			AD.getAD().post(event);
		}

	}

	public void moveDown(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx >= 0 && idx < mDownloads.size() - 2) {
			mDownloads.remove(idx);
			mDownloads.add(idx + 1, download);
			Event event = new Event(this);
			AD.getAD().post(event);
		}
	}

	public void moveToFront(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx > 0) {
			mDownloads.remove(idx);
			mDownloads.add(0, download);
			Event event = new Event(this);
			AD.getAD().post(event);
		}
	}

	public void moveToBottom(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx >= 0 && idx < mDownloads.size() - 2) {
			mDownloads.remove(idx);
			mDownloads.add(download);
			Event event = new Event(this);
			AD.getAD().post(event);
		}
	}

	public void cancel(Download download) {
		if (mDownloads.contains(download)) {
			switch (download.getState()) {
			case RUNNING:
			case WAITING:
			case QUEUED:
				download.setState(Download.State.CANCELED);
				break;
			default:
				break;
			}
		}
	}

	public void remove(Download download) {
		if (mDownloads.contains(download)) {
			switch (download.getState()) {
			case RUNNING:
			case WAITING:
			case QUEUED:
				download.setState(Download.State.CANCELED);
				break;
			default:
				break;

			}
			mDownloads.remove(download);
		}
	}

	public List<Download> getDownloads() {
		return Collections.unmodifiableList(mDownloads);
	}

	public int getSize() {
		return mDownloads.size();
	}
}
