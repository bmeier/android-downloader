package de.bmeier.android.downloader.crawler;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class CrawlerTask extends AsyncTask<String, Void, List<String>> {
	private Crawler	mCrawler;

	public CrawlerTask(Crawler c) {
		mCrawler = c;
	}

	@Override
	protected List<String> doInBackground(String... params) {
		List<String> links = new ArrayList<String>();
		for (String url : params) {
			links.addAll(mCrawler.getLinks(url));
		}
		return links;
	}

}
