package de.bmeier.android.downloader.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.google.common.eventbus.EventBus;

public class ADService extends Service {

	private EventBus			mEventBus;
	private Handler				mHandler;
	private ExecutorService		mExecuter;
	private NotificationManager	mNotificationManager;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mExecuter = Executors.newCachedThreadPool();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Toast.makeText(this, "Download service started", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return new ADBinder(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mExecuter.shutdownNow();
		Toast.makeText(this, "Download service stopped", Toast.LENGTH_LONG)
				.show();
	}
}
