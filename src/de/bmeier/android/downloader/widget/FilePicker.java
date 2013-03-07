package de.bmeier.android.downloader.widget;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import de.bmeier.android.downloader.R;

public class FilePicker extends FrameLayout implements OnItemClickListener,
		OnItemLongClickListener {
	private LayoutInflater mLayoutInflater;
	private FileFilter mFileFilter;
	private OnFileSelectedListener mCallback;
	private List<File> mFiles = new ArrayList<File>();
	private File mDirectory;
	private FileListAdapter mListAdapter;
	private TextView mDirectoryTextView;
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

	public interface OnFileSelectedListener {
		public void onFileSelected(File file);
	}

	public FilePicker(Context context) {
		this(context, null);
	}

	public FilePicker(Context context, AttributeSet attrs) {

		super(context, attrs);

		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLayoutInflater.inflate(R.layout.filepicker_layout, this);

		mDirectoryTextView = (TextView) findViewById(R.id.filepicker_directoryTextView);

		ListView listView = (ListView) findViewById(R.id.filepicker_listView);
		mListAdapter = new FileListAdapter();
		listView.setAdapter(mListAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}

	public void init(File directory, OnFileSelectedListener callback,
			FileFilter fileFilter) {
		if (!directory.isDirectory()) {
			throw new UnsupportedOperationException();
		}
		mFileFilter = fileFilter;
		mCallback = callback;

		mDirectory = directory;
		mFiles = listFiles(directory);

		mDirectoryTextView.setText(mDirectory.getAbsolutePath());
	}

	private List<File> listFiles(File directory) {
		mDirectory = directory;
		List<File> files = new ArrayList<File>();
		File parent = directory.getParentFile();
		if (parent != null) {
			files.add(parent);
		}
		files.addAll(Arrays.asList(directory.listFiles(mFileFilter)));
		Collections.sort(files);
		return files;
	}

	private class FileListAdapter extends BaseAdapter {
		private Drawable mFolder;
		private Drawable mFile;

		public FileListAdapter() {
			mFolder = getResources().getDrawable(R.drawable.ic_folder);
			mFile = getResources().getDrawable(R.drawable.ic_file);
		}

		private class ViewHolder {
			public ImageView imageView;
			public TextView fileNameTextView;
			public TextView fileSizeTextView;
			public TextView detailTextView;
			public TextView permissionTextView;
		}

		@Override
		public int getCount() {
			return mFiles.size();
		}

		@Override
		public Object getItem(int position) {
			return mFiles.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(
						R.layout.filepicker_rowlayout, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.fileNameTextView = (TextView) convertView
						.findViewById(R.id.filepicker_rowlayout_fileNameTextView);
				viewHolder.fileSizeTextView = (TextView) convertView
						.findViewById(R.id.filepicker_rowlayout_fileSizeTextView);
				viewHolder.permissionTextView = (TextView) convertView
						.findViewById(R.id.filepicker_rowlayout_permisssionTextView);
				viewHolder.detailTextView = (TextView) convertView
						.findViewById(R.id.filepicker_rowlayout_detailTextView);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.filepicker_rowlayout_imageView);
				convertView.setTag(viewHolder);
			}

			File f = mFiles.get(position);
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();

			if (f.equals(mDirectory.getParentFile())) {
				viewHolder.fileNameTextView.setText("..");
				viewHolder.detailTextView.setText("Parent folder");
				viewHolder.fileSizeTextView.setText("");
			} else {
				viewHolder.fileNameTextView.setText(f.getName());
				viewHolder.detailTextView.setText(DATE_FORMAT.format(f
						.lastModified()));
				viewHolder.fileSizeTextView.setText(f.isDirectory() ? ""
						: getFileSizeText(f.length()));
			}

			viewHolder.imageView.setImageDrawable(f.isDirectory() ? mFolder
					: mFile);

			viewHolder.permissionTextView.setText(getPermissionText(f));
			return convertView;
		}
	}

	private static String[] SIZE_UNITS = new String[] { "bytes", "kB", "MB",
			"GB", "TB" };

	private String getFileSizeText(long size) {
		double dSize = size;
		int i = 0;
		while (dSize > 1024 && i < SIZE_UNITS.length) {
			dSize /= 1024;
			i++;
		}

		return String.format("%.2f %s", dSize, SIZE_UNITS[i]);
	}

	private String getPermissionText(File f) {
		StringBuilder sb = new StringBuilder();
		sb.append(f.canRead() ? 'r' : '-');
		sb.append(f.canWrite() ? 'w' : '-');
		sb.append(f.canExecute() ? 'x' : '-');

		return sb.toString();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if (mCallback != null) {
			mCallback.onFileSelected(mFiles.get(position));
			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		File f = mFiles.get(position);
		if (f.isFile()) {
			if (mCallback != null)
				mCallback.onFileSelected(f);
		} else {
			if (f.canRead()) {
				mDirectoryTextView.setText(f.getAbsolutePath());
				mFiles = listFiles(f);
				mListAdapter.notifyDataSetChanged();
			}
		}
	}
}
