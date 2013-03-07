package de.bmeier.android.downloader.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ListView;
import de.bmeier.android.downloader.AD;
import de.bmeier.android.downloader.R;
import de.bmeier.android.downloader.crawler.CrawlerTask;
import de.bmeier.android.downloader.crawler.GenericCrawler;

/**
 * Displays the download queue.
 */
public class QueueActivity extends Activity implements OnMenuItemClickListener {
	private MenuItem	mAddItem;
	private MenuItem	mSettingsItem;
	private MenuItem	mExitItem;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queue);
		ListView downloadListView = (ListView) findViewById(R.id.downloadListView);
		downloadListView.setAdapter(new QueueListAdapter(this));
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		AD.getAD().startService(this);

		new CrawlerTask(new GenericCrawler()).execute("http://www.google.com");

		// DownloadQueue queue = AD.getAD().getDownloadQueue();
		//
		// Download dl = new Download();
		// dl.setFileName("test.bin");
		// queue.add(dl);
		//
		// dl = new Download();
		// dl.setFileName("whatever.exe");
		// queue.add(dl);
		//
		// dl = new Download();
		// dl.setFileName("nasty.jpg");
		// queue.add(dl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		mAddItem = menu.findItem(R.id.menu_add);
		mAddItem.setOnMenuItemClickListener(this);

		mSettingsItem = menu.findItem(R.id.menu_settings);
		mSettingsItem.setOnMenuItemClickListener(this);

		mExitItem = menu.findItem(R.id.menu_exit);
		mExitItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		AD.getAD().stopService(this);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		if (item == mSettingsItem) {
			Log.i("TAG", "Settings");
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		} else if (item == mAddItem) {
			Log.i("TAG", "Add URL");
		} else if (item == mExitItem) {
			finish();
		}
		return true;
	}

	@Override
	public void onBackPressed()
	{
		moveTaskToBack(true);
	}
}
