package com.antiless.template.service

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.IBinder
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class DoNothingService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        printLog("onBind")
        return null
    }

    val bitmaps = ArrayList<Bitmap>()

    var timer: Timer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        printLog("onStartCommand")

        if (timer == null) {
            timer = Timer("DoNothingService")
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    printLog("still running")
                }
            }, 0, 3000)
        }
        thread {
//            for (i in 0 until 100) {
//                bitmaps[i] = BitmapFactory.decodeResource(resources, R.drawable.fc35093c16447a4a88649f712c339b36fss)
//            }
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        printLog("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
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

    private fun printLog(msg: String) {
        Log.i("DoNothingService", msg)
    }
}