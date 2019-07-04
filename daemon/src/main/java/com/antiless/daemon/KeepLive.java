package com.antiless.daemon;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.antiless.daemon.config.ForegroundNotification;
import com.antiless.daemon.config.KeepLiveService;
import com.antiless.daemon.service.JobHandlerService;
import com.antiless.daemon.service.LocalService;
import com.antiless.daemon.service.RemoteService;

/**
 * 保活工具
 */
public final class KeepLive {

    public static KeepLiveService keepLiveService = null;
    public static ForegroundNotification foregroundNotification = null;

    /**
     * 启动保活
     *
     * @param context     your application
     * @param keepLiveService 保活业务
     */
    public static void startWork(@NonNull Context context, @NonNull KeepLiveService keepLiveService, @NonNull ForegroundNotification foregroundNotification) {
        KeepLive.foregroundNotification = foregroundNotification;
        if (GlobalFunctions.isMain(context)) {
            KeepLive.keepLiveService = keepLiveService;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //启动定时器，在定时器中启动本地服务和守护进程
                Intent intent = new Intent(context, JobHandlerService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Android O 之后需要使用 startForegroundService, Service 启动后5秒内调用 startForeground
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            } else {
                //启动本地服务
                Intent localIntent = new Intent(context, LocalService.class);
                //启动守护进程
                Intent guardIntent = new Intent(context, RemoteService.class);
                context.startService(localIntent);
                context.startService(guardIntent);
            }
        }
    }
}
