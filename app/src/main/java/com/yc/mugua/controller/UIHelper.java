package com.yc.mugua.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.MainActivity;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.view.CashFrg;
import com.yc.mugua.view.CollectionFrg;
import com.yc.mugua.view.FeedbackFrg;
import com.yc.mugua.view.FindClassFrg;
import com.yc.mugua.view.FindFrg;
import com.yc.mugua.view.ForgetFrg;
import com.yc.mugua.view.HistoryFrg;
import com.yc.mugua.view.IncomeFrg;
import com.yc.mugua.view.MainFrg;
import com.yc.mugua.view.MsgFrg;
import com.yc.mugua.view.MyPromoteFrg;
import com.yc.mugua.view.PromoteFrg;
import com.yc.mugua.view.RegisterFrg;
import com.yc.mugua.view.SearchFrg;
import com.yc.mugua.view.SearchTextFrg;
import com.yc.mugua.view.ShareFrg;
import com.yc.mugua.view.VipFrg;
import com.yc.mugua.view.act.EquAct;
import com.yc.mugua.view.act.LoginAct;
import com.yc.mugua.view.act.SetAct;
import com.yc.mugua.view.act.VideoAct;


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
     *  首页分类
     */
    public static void startFindFrg(BaseFragment root, String id) {
        FindFrg frg = new FindFrg();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
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
     *  设置
     */
    public static void startSetAct(){
        ActivityUtils.startActivity(SetAct.class);
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

    /**
     *  视频详情
     * @param id
     */
    public static void startVideoAct(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        ActivityUtils.startActivity(bundle, VideoAct.class);
    }

     /**
     *  搜索数据也
     * @param root
     */
    public static void startSearchTextFrg(BaseFragment root) {
        SearchTextFrg frg = new SearchTextFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
     /**
     *  H5
     */
    public static void startHtmlAct(Activity act, int type, String url) {
        if (StringUtils.isEmpty(url))return;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        act.startActivity(intent);
//        Bundle bundle = new Bundle();
//        bundle.putInt("type", type);
//        bundle.putString("url", url);
//        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }

    /**
     *  观看历史
     */
    public static void startHistoryFrg(BaseFragment root) {
        HistoryFrg frg = new HistoryFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  通知
     */
    public static void startMsgFrg(BaseFragment root) {
        MsgFrg frg = new MsgFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  我的收藏
     */
    public static void startCollectionFrg(BaseFragment root) {
        CollectionFrg frg = new CollectionFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }
    /**
     *  收益提现
     */
    public static void startIncomeFrg(BaseFragment root) {
        IncomeFrg frg = new IncomeFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  我的缓存
     */
    public static void startCashFrg(BaseFragment root) {
        CashFrg frg = new CashFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  意见反馈
     */
    public static void startFeedbackFrg(BaseFragment root, int type) {
        FeedbackFrg frg = new FeedbackFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        if (type == 0){
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }else {
            root.start(frg);
        }
    }

    /**
     *  我要推广
     */
    public static void startPromoteFrg(BaseFragment root) {
        PromoteFrg frg = new PromoteFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }
    /**
     *  VIP
     */
    public static void startVipFrg(BaseFragment root) {
        VipFrg frg = new VipFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  密码锁
     */
    public static void startEquAct() {
        ActivityUtils.startActivity(EquAct.class);
    }

    /**
     *  立即推广
     * @param root
     */
    public static void startShareFrg(BaseFragment root) {
        ShareFrg frg = new ShareFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  我的推广
     * @param root
     */
    public static void startMyPromoteFrg(BaseFragment root) {
        MyPromoteFrg frg = new MyPromoteFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
}