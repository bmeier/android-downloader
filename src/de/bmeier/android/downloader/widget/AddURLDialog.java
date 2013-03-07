package de.bmeier.android.downloader.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import de.bmeier.android.downloader.R;

public class AddURLDialog extends AlertDialog implements OnClickListener {
	private EditText mURLEditText;
	private EditText mDirectoryEditText;

	private OnURLAddedListener mCallback;

	public interface OnURLAddedListener {
		public void onURLAdded(String url, String directory);
	}

	public AddURLDialog(Context context, OnURLAddedListener callback) {
		super(context);
		mCallback = callback;
		View content = View.inflate(context, R.layout.dialog_addurl, null);
		setView(content);
		setButton(AlertDialog.BUTTON_POSITIVE, "Ok", this);
		setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", this);
		setTitle("Add URL");

		mURLEditText = (EditText) content
				.findViewById(R.id.dialog_addurl_urlTextView);
		mDirectoryEditText = (EditText) content
				.findViewById(R.id.dialog_addurl_directoryTextView);

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String directory = preferences.getString("download_folder", Environment
				.getExternalStorageDirectory().getAbsolutePath());
		mDirectoryEditText.setText(directory);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_POSITIVE) {
			if (mCallback != null) {
				mCallback.onURLAdded(mURLEditText.getText().toString(),
						mDirectoryEditText.getText().toString());
			}
		}
	}
}
