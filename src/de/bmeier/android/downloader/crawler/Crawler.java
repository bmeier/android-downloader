package de.bmeier.android.downloader.crawler;

import java.util.List;
import java.util.regex.Pattern;

public interface Crawler {
	public List<String> getLinks(String url);

	public Pattern getURLPattern();

	public String getName();
}
