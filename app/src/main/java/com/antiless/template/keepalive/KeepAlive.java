package com.antiless.template.keepalive;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.antiless.daemon.KeepLive;
import com.antiless.daemon.config.KeepLiveService;
import com.antiless.daemon.config.ForegroundNotification;
import com.antiless.template.R;
import com.antiless.template.service.DoNothingService;

import java.util.ArrayList;

public class KeepAlive {
    public static void init(@NonNull final Application app) {
        ForegroundNotification notification = new ForegroundNotification(12345, "title", "desc", R.mipmap.ic_launcher);
        KeepLive.startWork(app, (new KeepLiveService() {
            @NonNull
            private final ArrayList bitmaps = new ArrayList(100);

            @NonNull
            public final ArrayList getBitmaps() {
                return this.bitmaps;
            }

            public void onWorking() {
                printLog("onWorking");
                app.startService(new Intent((Context) app, DoNothingService.class));
                new Thread(() -> {
                    printLog("crashing");
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    printLog("crashed");
                    throw new RuntimeException("Crash Manually");
                });
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
