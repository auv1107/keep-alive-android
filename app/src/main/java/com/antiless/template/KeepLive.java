package com.antiless.template;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.antiless.template.config.ForegroundNotification;
import com.antiless.template.service.JobHandlerService;
import com.antiless.template.service.LocalService;
import com.antiless.template.service.RemoteService;

import java.util.List;

/**
 * 保活工具
 */
public final class KeepLive {

    public static KeepLiveService keepLiveService = null;
    public static ForegroundNotification foregroundNotification = null;

    /**
     * 启动保活
     *
     * @param application     your application
     * @param keepLiveService 保活业务
     */
    public static void startWork(@NonNull Application application, @NonNull KeepLiveService keepLiveService, @NonNull ForegroundNotification foregroundNotification) {
        KeepLive.foregroundNotification = foregroundNotification;
        if (GlobalFunctions.isMain(application)) {
            KeepLive.keepLiveService = keepLiveService;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                启动定时器，在定时器中启动本地服务和守护进程
                //启动定时器，在定时器中启动本地服务和守护进程
                Intent intent = new Intent(application, JobHandlerService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    application.startForegroundService(intent);
                } else {
                    application.startService(intent);
                }
            } else {
                //启动本地服务
                Intent localIntent = new Intent(application, LocalService.class);
                //启动守护进程
                Intent guardIntent = new Intent(application, RemoteService.class);
                application.startService(localIntent);
                application.startService(guardIntent);
            }
        }
    }
}
