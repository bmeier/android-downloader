package de.bmeier.android.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.common.io.CharStreams;

public class Network {

	public static String getText(URL url) throws IOException {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String text = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			text = CharStreams.toString(reader);
			connection.disconnect();
		} finally {
			if (reader != null)
				reader.close();
		}

		return text;
	}
}
