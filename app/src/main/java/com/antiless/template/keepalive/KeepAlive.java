package com.antiless.template.keepalive;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.antiless.daemon.KeepLive;
import com.antiless.daemon.config.KeepLiveService;
import com.antiless.daemon.config.ForegroundNotification;
import com.antiless.template.R;
import com.antiless.template.service.DoNothingService;

import java.util.ArrayList;

public class KeepAlive {
    public static void init(@NonNull final Context context) {
        ForegroundNotification notification = new ForegroundNotification(12345, "title", "desc", R.mipmap.ic_launcher);
        KeepLive.startWork(context, (new KeepLiveService() {

            public void onWorking() {
                printLog("onWorking");
                context.startService(new Intent(context, DoNothingService.class));
/*
                new Thread(() -> {
                    printLog("crashing");
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    printLog("crashed");
                    throw new RuntimeException("Crash Manually");
                }).start();
*/
            }

            public void onStop() {
                printLog("onStop");
            }
        }), notification);
    }
    private static void printLog(String msg) {
        Log.i("LocalService", msg);
    }

}
