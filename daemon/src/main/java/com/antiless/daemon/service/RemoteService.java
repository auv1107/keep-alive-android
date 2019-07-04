package com.antiless.daemon.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.antiless.daemon.BuildConfig;
import com.antiless.daemon.receiver.NotificationClickReceiver;
import com.antiless.daemon.utils.NotificationUtils;
import com.antiless.daemon.utils.ServiceUtils;

/**
 * 守护进程
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public final class RemoteService extends Service {
    private MyBinder mBinder;
    private boolean mIsBoundLocalService ;


    @Override
    public void onCreate() {
        super.onCreate();
        printLog("onCreate");
        if (mBinder == null){
            mBinder = new MyBinder();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        printLog("onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printLog("onStartCommand");
        try {
            mIsBoundLocalService = this.bindService(new Intent(RemoteService.this, LocalService.class),
                    connection, Context.BIND_ABOVE_CLIENT);
            if (BuildConfig.DEBUG) {
//                crashIt();
            }
        }catch (Exception e){
        }
        return START_STICKY;
    }

    private void crashIt() {
        new Thread() {
            @Override
            public void run() {
                printLog("Remote crashing");
                try {
                    Thread.sleep(7 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printLog("Remote crashed");
                throw new RuntimeException("Remote crashed");
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        printLog("onDestroy");
        if (connection != null){
            try {
                if (mIsBoundLocalService){
                    unbindService(connection);
                }
            }catch (Exception e){}
        }
    }
    private final class MyBinder extends GuardAidl.Stub {

        @Override
        public void wakeUp(int id, String title, String discription, int iconRes) throws RemoteException {
            printLog("wakeUp");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Intent intent2 = new Intent(getApplicationContext(), NotificationClickReceiver.class);
                intent2.setAction(NotificationClickReceiver.CLICK_NOTIFICATION);
                Notification notification = NotificationUtils.createNotification(RemoteService.this, title, discription, iconRes, intent2);
                RemoteService.this.startForeground(id, notification);
            }
        }

    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            printLog("onServiceDisconnected");
            if (ServiceUtils.isRunningTaskExist(getApplicationContext(), getPackageName() + ":remote")){
                Intent localService = new Intent(RemoteService.this,
                        LocalService.class);
                RemoteService.this.startService(localService);
                mIsBoundLocalService = RemoteService.this.bindService(new Intent(RemoteService.this,
                        LocalService.class), connection, Context.BIND_ABOVE_CLIENT);
            }
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            printLog("onServiceConnected");
        }
    };

    private static void printLog(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i("RemoteService", msg);
        }
    }
}
