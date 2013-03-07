package de.bmeier.android.downloader;

import java.util.regex.Pattern;

public interface Downloader {
	public Pattern getURLPattern();
}
