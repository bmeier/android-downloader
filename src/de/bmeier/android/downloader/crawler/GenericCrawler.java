package de.bmeier.android.downloader.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.bmeier.android.downloader.Network;

public class GenericCrawler implements Crawler {
	private static final Pattern	URL_PATTERN		= Pattern
															.compile("http(s)?://.*?/(.*+((\\.htm(l)?)|(\\.php)))?");

	private static final Pattern	LINK_PATTERN	= Pattern
															.compile("\\<a.*?href\\=\"(.*?)\".*?\\>");

	@Override
	public List<String> getLinks(String url) {
		List<String> links = new ArrayList<String>();

		try {
			String page = Network.getText(new URL(url));
			Matcher m = LINK_PATTERN.matcher(page);
			while (m.find()) {
				links.add(m.group(1));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return links;
	}

	@Override
	public Pattern getURLPattern() {
		return URL_PATTERN;
	}

	@Override
	public String getName() {
		return "Generic";
	}

}
