package com.yc.mugua;

import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.UMShareAPI;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.utils.cache.ShareIsLoginCache;
import com.yc.mugua.view.MainFrg;

public class MainActivity extends BaseActivity {

    public static boolean isForeground = false;
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        ShareIsLoginCache.getInstance(act).save(false);
        if (findFragment(MainFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MainFrg.newInstance());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
