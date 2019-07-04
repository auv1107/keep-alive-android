package com.antiless.template;

import android.app.Application;

import com.antiless.daemon.GlobalFunctions;
import com.antiless.template.keepalive.KeepAlive;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        if (GlobalFunctions.isMain(this)) {
            KeepAlive.init(this);
        }
    }
}
