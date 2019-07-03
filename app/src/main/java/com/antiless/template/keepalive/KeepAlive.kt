package com.antiless.template.keepalive

import android.app.Application
import android.content.Intent
import com.antiless.template.R
import com.antiless.template.printLog
import com.antiless.template.service.DoNothingService
import com.fanjun.keeplive.KeepLive
import com.fanjun.keeplive.config.ForegroundNotification
import com.fanjun.keeplive.config.ForegroundNotificationClickListener
import com.fanjun.keeplive.config.KeepLiveService


class KeepAlive {
    companion object {

        fun init(app: Application) {

            //定义前台服务的默认样式。即标题、描述和图标
            val foregroundNotification = ForegroundNotification("测试", "描述", R.mipmap.ic_launcher,
                    //定义前台服务的通知点击事件
                    ForegroundNotificationClickListener { context, intent -> })
            //启动保活服务
            KeepLive.useSilenceMusice(false)
            KeepLive.startWork(app, KeepLive.RunMode.ENERGY, foregroundNotification,
                    //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                    object : KeepLiveService {
                        /**
                         * 运行中
                         * 由于服务可能会多次自动启动，该方法可能重复调用
                         */
                        override fun onWorking() {
                            printLog("onWorking")
                            app.startService(Intent(app, DoNothingService::class.java))
                        }

                        /**
                         * 服务终止
                         * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                         */
                        override fun onStop() {
                            printLog("onStop")
                        }
                    }
            )
        }

    }


}
