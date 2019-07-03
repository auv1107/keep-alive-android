package com.antiless.template

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import java.lang.Exception
import kotlin.concurrent.thread

fun doAndLater(task: ()->Unit) {
    task()
    thread {  }
}

fun openApp(context: Context?, packageName: String) {
    try {
        val intent = context?.packageManager?.getLaunchIntentForPackage(packageName)
        context?.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun openGooglePlay(packageName: String, context: Context) {
    val packageNameGooglePlay = "com.android.vending"
    if (TextUtils.isEmpty(packageName)) return

    if (isAppInstalled(context, packageNameGooglePlay)) {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(packageNameGooglePlay)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } else {
        openInBrowser(context, "https://play.google.com/store/apps/details?id=$packageName")
    }
}

/**
 * 检测某个应用是否安装
 *
 * @param context
 * @param packageName
 * @return
 */
fun isAppInstalled(context: Context?, packageName: String): Boolean {
    return try {
        context?.packageManager?.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

}

fun openInBrowser(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

fun runOnUIThread(task : ()->Unit) {
    Handler(Looper.getMainLooper())
            .post(task)
}

fun printLog(text: String) {
    Log.i("printLog", text)
}

/**
 * 获取当前进程名
 */
fun getCurrentProcessName(app: Application): String {
    val pid = android.os.Process.myPid()
    var processName = "";
    val manager = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (process in manager.runningAppProcesses) {
        if (process.pid == pid) {
            processName = process.processName
        }
    }
    return processName
}

/**
 * 包名判断是否为主进程
 *
 * @param
 * @return
 */
fun isMainProcess(app: Application): Boolean {
    return app.getPackageName().equals(getCurrentProcessName(app))
}