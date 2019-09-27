package com.yc.mugua.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.AppUtils;
import com.lzy.okgo.model.Response;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.databinding.FMainBinding;
import com.yc.mugua.utils.PopupWindowTool;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MainFrg extends BaseFragment<BasePresenter, FMainBinding> implements IBaseView, View.OnClickListener {

    public static MainFrg newInstance() {
        Bundle args = new Bundle();
        MainFrg fragment = new MainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private int position = 0;
    private int prePosition;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initView(View view) {
        mB.ly1.setOnClickListener(this);
        mB.ly2.setOnClickListener(this);
        mB.ly3.setOnClickListener(this);
        mB.ly4.setOnClickListener(this);
        setClick(position, mB.ly1);
        showHideFragment(mFragments[position], mFragments[prePosition]);
        onVersion();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(OneFrg.class);
        if (firstFragment == null) {
            mFragments[FIRST] = OneFrg.newInstance();
            mFragments[SECOND] = TwoFrg.newInstance();
            mFragments[THIRD] = ThreeFrg.newInstance();
            mFragments[FOUR] = FourFrg.newInstance();

            loadMultipleRootFragment(R.id.fl_container,
                    FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(TwoFrg.class);
            mFragments[THIRD] = findChildFragment(ThreeFrg.class);
            mFragments[FOUR] = findChildFragment(FourFrg.class);
        }
        setSwipeBackEnable(false);
    }

    private void setClick(int position, LinearLayout linearLayout){
        linearLayout.setBackgroundColor(act.getResources().getColor(R.color.red_F72A61));
    }
    private void setNoClick(int position, LinearLayout linearLayout){
        linearLayout.setBackgroundColor(0);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setSofia(false);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public boolean onBackPressedSupport() {
        exitBy2Click();// 调用双击退出函数
//        return super.onBackPressedSupport();
        return true;
    }

    private Boolean isExit = false;

    private void exitBy2Click() {
        Handler tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
            tExit = new Handler();
            tExit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return;
        } else {
//            Cockroach.uninstall();
            act.finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_1:
                position = 0;
                setClick(position, mB.ly1);
                break;
            case R.id.ly_2:
                position = 1;
                setClick(position, mB.ly2);
                break;
            case R.id.ly_3:
                position = 2;
                setClick(position, mB.ly3);
                break;
            case R.id.ly_4:
                position = 3;
                setClick(position, mB.ly4);
                break;
        }
        showHideFragment(mFragments[position], mFragments[prePosition]);

        if (prePosition == position)return;
        switch (prePosition){//上一个变色
            case 0:
                setNoClick(prePosition, mB.ly1);
                break;
            case 1:
                setNoClick(prePosition, mB.ly2);
                break;
            case 2:
                setNoClick(prePosition, mB.ly3);
                break;
            case 3:
                setNoClick(prePosition, mB.ly4);
                break;
        }
        prePosition = position;
    }


    private void onProfitOne() {
        CloudApi.commonQueryAPPAgreement(1)
                .doOnSubscribe(disposable -> {
                })
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
                            if (data != null){
                                PopupWindowTool.showAdvertisement(act, data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MainFrg.this.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void onVersion() {
        CloudApi.versionList()
                .doOnSubscribe(disposable -> {
                })
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
                            List<DataBean> version = data.getVersion();
                            for (DataBean bean : version){
                                if (bean.getPlatform() == 0){
                                    String appVersionName = AppUtils.getAppVersionName();
                                    if (!bean.getAppVersion().equals(appVersionName)){
                                        PopupWindowTool.showDialog(act, PopupWindowTool.version, bean.getType(), new PopupWindowTool.DialogListener() {
                                            @Override
                                            public void onClick() {
                                                Uri uri = Uri.parse(bean.getDownloadUrl());
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                                onProfitOne();
                                            }

                                            @Override
                                            public void onDismiss() {
                                                onProfitOne();
                                            }
                                        });
                                    }

                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        MainFrg.this.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
