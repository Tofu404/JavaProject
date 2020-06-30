package com.alltheway.forward;

import android.app.Application;

import com.pw.WinLib;

public class MyApplication extends Application {

    private static final String APPKEY = BuildConfig.APPKEY;

    @Override
    public void onCreate() {
        super.onCreate();

        //设置为测试模式
        WinLib.setTestMode(BuildConfig.TEST_MODE);

        //初始化win_sdk
        WinLib.init(this,APPKEY,"test");
    }
}
