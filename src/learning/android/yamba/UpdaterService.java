package learning.android.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service{
	
	static final String TAG = "UpdaterService";
	
	static final int DELAY = 60000;
	private boolean runFlag=false;
	private Updater updater;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.updater=new Updater();
		Log.d(TAG, "onCreated");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.runFlag=false;
		this.updater.interrupt();
		this.updater=null;
		Log.d(TAG, "onDestroyed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		this.runFlag=true;
		this.updater.start();
		Log.d(TAG, "onStarted");
		return START_STICKY;
	}

	private class Updater extends Thread {
		
		public Updater(){
			super("UpdaterService-Updater");
		}
		
		@Override
		public void run(){
			UpdaterService updaterService=UpdaterService.this;
			while(updaterService.runFlag){
				Log.d(TAG, "Updater running");
				try{
					Log.d(TAG, "Updater ran");
					Thread.sleep(DELAY);
				}catch (InterruptedException e) {
					updaterService.runFlag=false;
				}
			}
		}
	}
}
