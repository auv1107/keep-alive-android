package com.antiless.template;

import android.app.Application;
import android.content.Intent;

import com.antiless.daemon.GlobalFunctions;
import com.antiless.daemon.whitelist.WhiteListIntentFactory;
import com.antiless.template.keepalive.KeepAlive;
import com.antiless.template.service.DoNothingService;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        if (GlobalFunctions.isMain(this)) {
//            KeepAlive.init(this);
            WhiteListIntentFactory.init(this);
            startService(new Intent(this, DoNothingService.class));
        }
    }
}
