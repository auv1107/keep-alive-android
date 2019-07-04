package com.antiless.daemon.service;

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
import com.antiless.daemon.KeepLive;
import com.antiless.daemon.utils.ServiceUtils;

public final class LocalService extends Service {
    private MyBinder mBinder;
    private boolean mIsBoundRemoteService;

    @Override
    public void onCreate() {
        super.onCreate();
        printLog("onCreate");
        if (mBinder == null) {
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
        //绑定守护进程
        printLog("onStartCommand");
        try {
            mIsBoundRemoteService = this.bindService(new Intent(this, RemoteService.class), connection, Context.BIND_ABOVE_CLIENT);
            //隐藏服务通知
            if(Build.VERSION.SDK_INT < 25){
                startService(new Intent(this, HideForegroundService.class));
            }
        } catch (Exception e) {
        }
        if (KeepLive.keepLiveService != null) {
            KeepLive.keepLiveService.onWorking();
        }
        return START_STICKY;
    }

    private final class MyBinder extends GuardAidl.Stub {

        @Override
        public void wakeUp(int id, String title, String discription, int iconRes) throws RemoteException {

        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            printLog("onServiceDisconnected");
            if (ServiceUtils.isServiceRunning(getApplicationContext(), LocalService.class.getName())){
                Intent remoteService = new Intent(LocalService.this,
                        RemoteService.class);
                LocalService.this.startService(remoteService);
                Intent intent = new Intent(LocalService.this, RemoteService.class);
                mIsBoundRemoteService = LocalService.this.bindService(intent, connection,
                        Context.BIND_ABOVE_CLIENT);
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            printLog("onServiceConnected");
            try {
                if (mBinder != null && KeepLive.foregroundNotification != null) {
                    GuardAidl guardAidl = GuardAidl.Stub.asInterface(service);
                    guardAidl.wakeUp(KeepLive.foregroundNotification.getId(), KeepLive.foregroundNotification.getTitle(), KeepLive.foregroundNotification.getDescription(), KeepLive.foregroundNotification.getIconRes());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        printLog("onDestroy");
        if (connection != null){
            try {
                if (mIsBoundRemoteService){
                    unbindService(connection);
                }
            }catch (Exception e){}
        }
        if (KeepLive.keepLiveService != null) {
            KeepLive.keepLiveService.onStop();
        }
    }

    private void printLog(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i("LocalService", msg);
        }
    }
}
