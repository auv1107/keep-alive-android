package com.antiless.template.service

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.os.Process
import com.antiless.template.printLog
import java.util.*

class DoNothingService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        printLog("onBind")
        return null
    }

    var timer = Timer("DoNothingService")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        printLog("onStartCommand")
        timer.schedule(object : TimerTask() {
            override fun run() {
                printLog("still running")
            }
        }, 0, 3000)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        printLog("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        printLog("onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        printLog("onConfigurationChanged")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        printLog("onRebind")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        printLog("onLowMemory")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        printLog("onStart")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        printLog("onTaskRemoved")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        printLog("onTrimMemory")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        printLog("onUnbind")
        return super.onUnbind(intent)
    }
}