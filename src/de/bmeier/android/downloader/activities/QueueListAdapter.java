package de.bmeier.android.downloader.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;

import de.bmeier.android.downloader.AD;
import de.bmeier.android.downloader.Download;
import de.bmeier.android.downloader.DownloadQueue;
import de.bmeier.android.downloader.R;

public class QueueListAdapter extends BaseAdapter {
	private DownloadQueue	mDownloadQueue	= AD.getAD().getDownloadQueue();
	private Activity		mContext;

	private static class ViewHolder {
		public TextView		filename;
		public ProgressBar	progressBar;
	}

	public QueueListAdapter(Activity context) {
		mContext = context;
		AD.getAD().register(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater
					.inflate(R.layout.queuelist_rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.filename = (TextView) convertView
					.findViewById(R.id.queuelist_filename);
			viewHolder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.queuelist_progressbar);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		Download download = getItem(position);
		viewHolder.filename.setText(download.getFileName());
		return convertView;
	}

	@Subscribe
	public void onQueueEvent(DownloadQueue.Event event) {
		notifyDataSetChanged();
	}

	@Subscribe
	public void onDownloadEvent(Download.Event event) {
		if (mDownloadQueue.getDownloads().contains(event.getSource())) {
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return mDownloadQueue.getSize();
	}

	@Override
	public Download getItem(int position) {
		return mDownloadQueue.getDownloads().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
