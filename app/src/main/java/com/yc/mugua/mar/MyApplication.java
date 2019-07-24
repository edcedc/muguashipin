package com.yc.mugua.mar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.StringUtils;
import com.nanchen.crashmanager.CrashApplication;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.service.InitializeService;
import com.yc.mugua.utils.cache.ShareEquCache;
import com.yc.mugua.utils.lock.BackgroundUtil;
import com.yc.mugua.utils.lock.Features;
import com.yc.mugua.utils.lock.service.MyService;

public class MyApplication extends CrashApplication {

    private int appCount = 0;
    private boolean isAppStatus = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null

        InitializeService.start(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
                if (!isAppStatus)return;
                String equPwd = ShareEquCache.getInstance(activity).getEquPwd();
                if (!StringUtils.isEmpty(equPwd)){
                    UIHelper.startEquAct();
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
                if (appCount == 0)
                    isAppStatus = true;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
//        startService();
    }

    private void startService() {
        Features.showForeground = true;
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        Features.BGK_METHOD = BackgroundUtil.BKGMETHOD_GETAPPLICATION_VALUE;
    }

    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}
