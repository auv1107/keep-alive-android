package com.antiless.daemon.whitelist;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.annotation.NonNull;

import java.util.*;

public class IntentWrapper {


    protected Intent intent;
    protected int type;

    protected IntentWrapper(Intent intent, int type) {
        this.intent = intent;
        this.type = type;
    }

    /**
     * 判断本机上是否有能处理当前Intent的Activity
     */
    protected boolean doesActivityExists(Context context) {
//        if (!DaemonEnv.sInitialized) return false;
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }

    /**
     * 安全地启动一个Activity
     */
    protected void startActivitySafely(Activity activityContext) {
        try {
            activityContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}