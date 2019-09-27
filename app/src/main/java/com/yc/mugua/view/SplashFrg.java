package com.yc.mugua.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.User;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FSplashBinding;
import com.yc.mugua.utils.CountDownTimer;
import com.yc.mugua.utils.cache.ShareSessionIdCache;
import com.yc.mugua.view.act.HtmlAct;
import com.yc.mugua.weight.RuntimeRationale;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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
    private String link;
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
//        mB.banner.setImages(images)
//                .setImageLoader(new GlideImageLoader())
//                .setOnBannerListener(this)
//                .setBannerAnimation(DefaultTransformer.class);
//        handler.sendEmptyMessageDelayed(mHandle_permission, 1000);
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
        mB.ivImg.setOnClickListener(view1 -> UIHelper.startHtmlAct(act, HtmlAct.BANNER, link));
        mubgrabetv();
    }

    private CountDownTimer downTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mB.tvText.setText(3 - millisUntilFinished / 1000 + "跳过");
        }

        @Override
        public void onFinish(long millisUntilFinished) {
            handler.sendEmptyMessageDelayed(mHandle_permission, 0);
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

    private void mubgrabetv(){
        CloudApi.mubgrabetv()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONArray>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONArray array) {
                        JSONObject object = array.optJSONObject(0);
                        String description = object.optString("description");
                        byte[] decode = Base64.decode(description, Base64.DEFAULT);
                        String url = new String(decode);
                        LogUtils.e(url);
                        CloudApi.SERVLET_URL = url + "/api/";
                        commonGetAppStartupPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        commonGetAppStartupPage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void commonGetAppStartupPage(){
        CloudApi.commonGetAppStartupPage()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().data;
                            List<DataBean> banners = data.getPage();
                            if (banners != null && banners.size() != 0){
                                mB.tvText.setVisibility(View.VISIBLE);
                                link = banners.get(0).getValue();
//                                GlideLoadingUtils.load(act, banners.get(0).getUrl(), mB.ivImg);
                                Glide.with(act).load(banners.get(0).getUrl()).into(mB.ivImg);

                                downTimer.start();
                                mB.tvText.setOnClickListener(view1 -> {
                                    handler.sendEmptyMessageDelayed(mHandle_permission, 0);
                                    downTimer.stop();
                                    downTimer.cancel();
                                });
                            }else {
                                handler.sendEmptyMessageDelayed(mHandle_permission, 0);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        SplashFrg.this.onError(e);
                        showToast("服务器异常");
                        act.finish();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
