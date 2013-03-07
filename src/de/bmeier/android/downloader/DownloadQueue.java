package de.bmeier.android.downloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.eventbus.Subscribe;

public class DownloadQueue {
	private List<Download>	mDownloads	= new ArrayList<Download>();

	public DownloadQueue() {
	}

	public void add(Download download) {
		mDownloads.add(download);
	}

	public void moveUp(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx > 0) {
			mDownloads.remove(idx);
			mDownloads.add(idx - 1, download);
		}

	}

	public void moveDown(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx >= 0 && idx < mDownloads.size() - 2) {
			mDownloads.remove(idx);
			mDownloads.add(idx + 1, download);
		}
	}

	public void moveToFront(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx > 0) {
			mDownloads.remove(idx);
			mDownloads.add(0, download);
		}
	}

	public void moveToBottom(Download download) {
		int idx = mDownloads.indexOf(download);
		if (idx >= 0 && idx < mDownloads.size() - 2) {
			mDownloads.remove(idx);
			mDownloads.add(download);
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
