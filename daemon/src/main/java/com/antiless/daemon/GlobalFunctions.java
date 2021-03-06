package com.antiless.daemon;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class GlobalFunctions {
    public static boolean isMain(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        if (runningAppProcessInfos != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    processName = appProcess.processName;
                    break;
                }
            }
            String packageName = context.getPackageName();
            if (processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
