package de.bmeier.android.downloader.widget;

import android.app.AlertDialog;
import android.content.Context;

public class FilePickerDialog extends AlertDialog {
	public interface OnFilePickedListener {

	}

	FilePickerDialog.OnFilePickedListener	mCallback;

	public FilePickerDialog(Context context,
			FilePickerDialog.OnFilePickedListener callback, String file) {
		super(context);

		mCallback = callback;
	}
}
