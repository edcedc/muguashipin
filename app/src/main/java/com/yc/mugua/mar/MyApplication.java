package com.yc.mugua.mar;

import android.content.Context;

import com.nanchen.crashmanager.CrashApplication;
import com.yc.mugua.service.InitializeService;

public class MyApplication extends CrashApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null

        InitializeService.start(this);
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}
