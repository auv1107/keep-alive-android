package com.antiless.template;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.antiless.daemon.KeepLive;
import com.antiless.daemon.dpm.DeviceManager;
import com.antiless.daemon.whitelist.WhiteListIntentFactory;
import com.antiless.template.keepalive.KeepAlive;
import com.antiless.template.receiver.DevicePolicyReceiver;

/**
 * Created by gc on 16/10/21.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化各控件
     */
    private void initView() {
        findViewById(R.id.activeDevicePolicy).setOnClickListener(v -> {
            DeviceManager manager = new DeviceManager(MainActivity.this,
                    new ComponentName(MainActivity.this, DevicePolicyReceiver.class));
            manager.active();
        });
        findViewById(R.id.unActiveDevicePolicy).setOnClickListener(v -> {
            DeviceManager manager = new DeviceManager(MainActivity.this,
                    new ComponentName(MainActivity.this, DevicePolicyReceiver.class));
            manager.unActivate();
        });
        findViewById(R.id.whiteList).setOnClickListener(v -> {
            WhiteListIntentFactory.askForWhiteList(MainActivity.this, "哈哈哈");
        });
        findViewById(R.id.openKeepLive).setOnClickListener(v -> {
            KeepAlive.init(this);
            Toast.makeText(MainActivity.this, "守护进程已开启，请点击手动崩溃触发守护行为", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.makeCrash).setOnClickListener(v -> {
            throw new RuntimeException("手动崩溃");
        });
    }
}
