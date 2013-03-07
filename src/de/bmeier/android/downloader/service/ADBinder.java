package de.bmeier.android.downloader.service;

import android.os.Binder;

public class ADBinder extends Binder {
	private final ADService	mService;

	public ADBinder(ADService service)
	{
		mService = service;
	}

	public ADService getService()
	{
		return mService;
	}
}
