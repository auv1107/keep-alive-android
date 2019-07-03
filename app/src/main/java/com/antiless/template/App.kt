package com.antiless.template

import android.app.Application
import android.content.Intent
import com.antiless.template.keepalive.KeepAlive
import com.antiless.template.service.DoNothingService

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        KeepAlive.init(this)
        if (isMainProcess(this)) {
//            initDoNothingService()
        }
    }

    private fun initDoNothingService() {
        printLog("initDoNothingService")
        val intent = Intent(this, DoNothingService::class.java)
        startService(intent)
    }
}