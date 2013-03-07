package de.bmeier.android.downloader;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.common.eventbus.EventBus;

import de.bmeier.android.downloader.service.ADService;
import de.bmeier.android.downloader.service.ADServiceConnection;

public class AD implements UncaughtExceptionHandler {
	private static AD			sAD				= new AD();
	private static boolean		sInitialized	= false;

	private static final String	TAG				= AD.class.getName();

	public static AD getAD()
	{
		return sAD;
	}

	private EventBus		mEventBus			= null;
	private DownloadQueue	mDownloadQueue		= null;
	private ADService		mDownloadService	= null;
	private ADServiceConnection	mConnection			= new ADServiceConnection();
	private boolean			mServiceBound		= false;

	private AD()
	{
		Thread.setDefaultUncaughtExceptionHandler(this);
		if (!sInitialized) {
			initialize();
		}
	}

	private void initialize()
	{
		sInitialized = true;
	}

	/**
	 * Get the event bus.
	 * 
	 * @return
	 */
	public EventBus getEventBus()
	{
		if (mEventBus == null)
			mEventBus = new EventBus();
		return mEventBus;
	}

	public void startService(Context context)
	{
		context.startService(new Intent(context, ADService.class));
		context.bindService(new Intent(context, ADService.class),
				mConnection, Context.BIND_AUTO_CREATE);
		mServiceBound = true;
	}

	public void stopService(Context context)
	{
		context.stopService(new Intent(context, ADService.class));
		if (mServiceBound) {
			context.unbindService(mConnection);
			mServiceBound = false;
		}
	}

	/**
	 * Post an event on the event bus.
	 * 
	 * @param event
	 */
	public void post(Object event)
	{
		EventBus eventBus = getEventBus();
		eventBus.post(event);
	}

	/**
	 * Register a subscriber to the event bus.
	 * 
	 * @param subscriber
	 */
	public void register(Object subscriber)
	{
		EventBus eventBus = getEventBus();
		eventBus.register(subscriber);
	}

	/**
	 * Unregister a subscriber from the event bus.
	 * 
	 * @param subscriber
	 */
	public void unregister(Object subscriber)
	{
		EventBus eventBus = getEventBus();
		eventBus.unregister(subscriber);
	}

	public DownloadQueue getDownloadQueue()
	{
		if (mDownloadQueue == null)
			mDownloadQueue = new DownloadQueue();
		return mDownloadQueue;
	}

	public ADService getDownloadService()
	{
		return mDownloadService;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		Log.e(TAG, "Caught an exception", ex);
	}

}
