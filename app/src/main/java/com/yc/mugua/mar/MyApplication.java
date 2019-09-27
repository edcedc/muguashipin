package com.yc.mugua.mar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.StringUtils;
import com.nanchen.crashmanager.CrashApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.service.InitializeService;
import com.yc.mugua.utils.Constants;
import com.yc.mugua.utils.cache.ShareEquCache;
import com.yc.mugua.utils.lock.BackgroundUtil;
import com.yc.mugua.utils.lock.Features;
import com.yc.mugua.utils.lock.service.MyService;

public class MyApplication extends CrashApplication {

    private int appCount = 0;
    private boolean isRunInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null

        UMConfigure.init(this, Constants.ShareID, "Umeng", UMConfigure.DEVICE_TYPE_PHONE,Constants.ShareSecret);
        PlatformConfig.setWeixin(Constants.WX_APPID, Constants.WX_SECRER);
        PlatformConfig.setQQZone(Constants.QQ_APPID, Constants.QQ_SECRET);
        PlatformConfig.setSinaWeibo(Constants.WB_APPID, Constants.WB_SECRET, "https://api.weibo.com/oauth5/default.html");
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        /*PushAgent.getInstance(this).register(new IUmengRegisterCallback(){
            @Override
            public void onSuccess(String s) {
                LogUtils.e(s);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("walle", "--->>> onFailure, s is " + s + ", s1 is " + s1);
            }
        });*/
        InitializeService.start(this);
        CrashReport.initCrashReport(getApplicationContext(), "02bb70b5b5", false);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
                if (isRunInBackground) {
                    //应用从后台回到前台 需要做的操作
                    back2App(activity);
                    String equPwd = ShareEquCache.getInstance(activity).getEquPwd();
                    if (!StringUtils.isEmpty(equPwd)) {
                        UIHelper.startEquAct();
                    }
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
                if (appCount == 0) {
                    //应用进入后台 需要做的操作
                    leaveApp(activity);
                }
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


    /**
     * 从后台回到前台需要执行的逻辑
     *
     * @param activity
     */
    private void back2App(Activity activity) {
        isRunInBackground = false;
    }

    /**
     * 离开应用 压入后台或者退出应用
     *
     * @param activity
     */
    private void leaveApp(Activity activity) {
        isRunInBackground = true;
    }

}
