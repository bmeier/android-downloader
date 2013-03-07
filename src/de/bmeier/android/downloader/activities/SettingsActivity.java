package de.bmeier.android.downloader.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.bmeier.android.downloader.R;

public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
