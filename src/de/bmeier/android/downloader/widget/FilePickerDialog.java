package de.bmeier.android.downloader.widget;

import java.io.File;
import java.io.FileFilter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import de.bmeier.android.downloader.R;

public class FilePickerDialog extends AlertDialog {
	private FilePicker mFilePicker;

	public FilePickerDialog(Context context,
			FilePicker.OnFileSelectedListener callback, File directory,
			FileFilter filter) {
		super(context);

		Context themeContext = getContext();

		LayoutInflater inflater = (LayoutInflater) themeContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.filepicker_dialog_layout, null);
		setView(view);
		mFilePicker = (FilePicker) view.findViewById(R.id.filepicker);
		mFilePicker.init(directory, callback, filter);
	}
}
