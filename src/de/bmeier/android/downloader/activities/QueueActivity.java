package de.bmeier.android.downloader.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import de.bmeier.android.downloader.AD;
import de.bmeier.android.downloader.R;
import de.bmeier.android.downloader.crawler.CrawlerTask;
import de.bmeier.android.downloader.crawler.GenericCrawler;
import de.bmeier.android.downloader.widget.AddURLDialog;
import de.bmeier.android.downloader.widget.FilePicker;

/**
 * Displays the download queue.
 */
public class QueueActivity extends Activity implements OnMenuItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FilePicker filePicker = new FilePicker(this);
		filePicker.init(Environment.getExternalStorageDirectory(), null, null);
		setContentView(filePicker);
		// ListView downloadListView = (ListView)
		// findViewById(R.id.downloadListView);
		// downloadListView.setAdapter(new QueueListAdapter(this));
	}

	@Override
	protected void onStart() {
		super.onStart();
		AD.getAD().startService(this);

		new CrawlerTask(new GenericCrawler()).execute("http://www.google.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		MenuItem menuItem = menu.findItem(R.id.menu_add);
		menuItem.setOnMenuItemClickListener(this);

		menuItem = menu.findItem(R.id.menu_settings);
		menuItem.setOnMenuItemClickListener(this);

		menuItem = menu.findItem(R.id.menu_exit);
		menuItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			new AddURLDialog(this, new AddURLDialog.OnURLAddedListener() {
				@Override
				public void onURLAdded(String url, String directory) {
					Intent intent = new Intent(QueueActivity.this,
							CrawlerActivity.class);
					intent.putExtra("URL", url);
					intent.putExtra("DIRECTORY", directory);
					startActivity(intent);
				}
			}).show();
			// FilePickerDialog dialog = new FilePickerDialog(this, null,
			// Environment.getExternalStorageDirectory(), null);
			// dialog.show();
			Log.i("TAG", "Add URL");
			return true;
		case R.id.menu_exit:
			AD.getAD().stopService(this);
			finish();
			return true;
		case R.id.menu_settings:
			Log.i("TAG", "Settings");
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
}
