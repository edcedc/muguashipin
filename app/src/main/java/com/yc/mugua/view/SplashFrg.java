package com.yc.mugua.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.User;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FSplashBinding;
import com.yc.mugua.utils.CountDownTimer;
import com.yc.mugua.utils.cache.ShareSessionIdCache;
import com.yc.mugua.weight.RuntimeRationale;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/6/15.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class SplashFrg extends BaseFragment<BasePresenter, FSplashBinding> implements OnBannerListener {

    public static SplashFrg newInstance() {
        Bundle args = new Bundle();
        SplashFrg fragment = new SplashFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<Integer> listImage = new ArrayList<>();
    private List<String> tips = new ArrayList<String>();

    private final int mHandle_splash = 0;
    private final int mHandle_permission = 1;

    private Activity act;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_splash;
    }

    @Override
    protected void initView(View view) {
        act = getActivity();
        final List<String> images = new ArrayList<>();
//        images.add("http://wx1.sinaimg.cn/mw600/0076BSS5ly1g4286f37zhj30p80zvtfc.jpg");
//        images.add("http://wx3.sinaimg.cn/mw600/0076BSS5ly1g425w3lk61j30bq0kw43c.jpg");
//        images.add("http://wx3.sinaimg.cn/mw600/0076BSS5ly1g425ebwtm7j30k00u8e81.jpg");
//        mB.banner.setImages(images)
//                .setImageLoader(new GlideImageLoader())
//                .setOnBannerListener(this)
//                .setBannerAnimation(DefaultTransformer.class);
        handler.sendEmptyMessageDelayed(mHandle_permission, 1000);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ShareIsLoginCache.getInstance(act).getIsLogin()){
                    mB.banner.setVisibility(View.VISIBLE);
                    mB.banner.start();
                }else {
                    mB.ivImg.setBackgroundResource(R.mipmap.cpic);
                    mB.fyClose.setVisibility(View.VISIBLE);
                    mB.ivImg.setVisibility(View.VISIBLE);
                    downTimer.start();
                }
            }
        }, 1000);*/
        /*mB.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.size() - 1) {
                    handler.sendEmptyMessageDelayed(mHandle_permission, 1000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    private CountDownTimer downTimer = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mB.tvText.setText(3 - millisUntilFinished / 1000 + "");
        }

        @Override
        public void onFinish(long millisUntilFinished) {
            handler.sendEmptyMessage(mHandle_splash);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mHandle_splash:
                    startNext();
                    break;
                case mHandle_permission:
                    setHasPermission();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /**
     * 设置权限
     */
    private void setHasPermission() {
        AndPermission.with(SplashFrg.this)
                .runtime()
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入外部存储, 允许程序写入外部存储，如SD卡
                        Manifest.permission.CAMERA//拍照权限, 允许访问摄像头进行拍照
                )
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> setPermissionOk())
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(SplashFrg.this, permissions)) {
                        showSettingDialog(act, permissions);
                    } else {
                        setPermissionCancel();
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> setPermission())
                .setNegativeButton(R.string.cancel, (dialog, which) -> setPermissionCancel())
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        setHasPermission();
//                        ToastUtils.showShort("用户从设置页面返回。");
                    }
                })
                .start();
    }

    /**
     * 权限有任何一个失败都会走的方法
     */
    private void setPermissionCancel() {
        act.finish();
    }

    /**
     * 权限都成功
     */
    private void setPermissionOk() {
        String sessionId = ShareSessionIdCache.getInstance(act).getSessionId();
        if (!StringUtils.isEmpty(sessionId)) {
            User.getInstance().setLogin(true);
        } else {
            User.getInstance().setLogin(false);
        }
        startNext();
    }

    private void startNext() {
        UIHelper.startMainAct();
        ActivityUtils.finishAllActivities();
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
