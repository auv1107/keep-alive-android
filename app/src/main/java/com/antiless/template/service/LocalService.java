package com.antiless.template.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.antiless.template.KeepLive;
import com.antiless.template.ServiceUtils;

public final class LocalService extends Service {
    private MyBinder mBinder;
    private boolean mIsBoundRemoteService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("LocalService", "onCreate");
        if (mBinder == null) {
            mBinder = new MyBinder();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("LocalService", "onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //绑定守护进程
        Log.i("LocalService", "onStartCommand");
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
        public void wakeUp(String title, String discription, int iconRes) throws RemoteException {

        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("LocalService", "onServiceDisconnected");
            if (ServiceUtils.isServiceRunning(getApplicationContext(), "com.antiless.template.service.LocalService")){
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
            Log.i("LocalService", "onServiceConnected");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LocalService", "onDestroy");
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
}
