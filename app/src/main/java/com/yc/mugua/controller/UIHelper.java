package com.yc.mugua.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mugua.MainActivity;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.view.FindClassFrg;
import com.yc.mugua.view.ForgetFrg;
import com.yc.mugua.view.LoginFrg;
import com.yc.mugua.view.MainFrg;
import com.yc.mugua.view.OneFrg;
import com.yc.mugua.view.RegisterFrg;
import com.yc.mugua.view.SearchFrg;
import com.yc.mugua.view.TwoFrg;
import com.yc.mugua.view.act.LoginAct;


/**
 * Created by Administrator on 2017/2/22.
 */

public final class UIHelper {

    private UIHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startMainAct() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     *  发现分类
     */
    public static void startFindClassFrg(BaseFragment root) {
        FindClassFrg frg = new FindClassFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  搜索
     */
    public static void startSearchFrg(BaseFragment root) {
        SearchFrg frg = new SearchFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  登录
     */
    public static void startLoginAct(){
        ActivityUtils.startActivity(LoginAct.class);
    }

    /**
     *  注册
     */
    public static void startRegisterFrg(BaseFragment root) {
        RegisterFrg frg = new RegisterFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  找回密码
     * @param root
     */
    public static void startForgetFrg(BaseFragment root) {
        ForgetFrg frg = new ForgetFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
}