package de.bmeier.android.downloader.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ADServiceConnection implements ServiceConnection {
	private ADService	mService;

	@Override
	public void onServiceConnected(ComponentName name, IBinder service)
	{
		mService = ((ADBinder) service).getService();

	}

	@Override
	public void onServiceDisconnected(ComponentName name)
	{
		mService = null;
	}

	public ADService getService()
	{
		return mService;
	}

}
