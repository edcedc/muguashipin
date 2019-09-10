package com.yc.mugua.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.User;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FFourBinding;
import com.yc.mugua.impl.FourContract;
import com.yc.mugua.presenter.FourPresenter;
import com.yc.mugua.utils.Constants;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.view.act.HtmlAct;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:14
 */
public class FourFrg extends BaseFragment<FourPresenter, FFourBinding> implements FourContract.View, View.OnClickListener {

    private String hotUrl;
    private String link;

    public static FourFrg newInstance() {
        Bundle args = new Bundle();
        FourFrg fragment = new FourFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest) {
            mB.refreshLayout.startRefresh();
        }
        mB.tvLogin.setVisibility(User.getInstance().isLogin() ? View.GONE : View.VISIBLE);
        List<File> files = FileUtils.listFilesInDir(Constants.videoUrl);
        if (files != null){
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#09FBFC"));
            SpannableString cText = new SpannableString("目前本地缓存" + files.size() + "部");
            cText.setSpan(colorSpan, 6, cText.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mB.tvCash.setText(cText);
        }
        setData(User.getInstance().getUserObj());
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_four;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.tvIncomeWithdrawal.setOnClickListener(this);
        mB.tvLogin.setOnClickListener(this);
        mB.tvVip.setOnClickListener(this);
        mB.tvPromote.setOnClickListener(this);
        mB.tvFeedback.setOnClickListener(this);
        mB.tvMessage.setOnClickListener(this);
        mB.tvHot.setOnClickListener(this);
        mB.ivImg.setOnClickListener(this);
        mB.lyHistory.setOnClickListener(this);
        mB.lyCash.setOnClickListener(this);
        mB.lyLike.setOnClickListener(this);
        mB.lyEqu.setOnClickListener(this);
        mB.refreshLayout.setEnableLoadmore(false);
        mPresenter.onHotGroup();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (User.getInstance().isLogin()){
                    mPresenter.onInfo();
//                    isRequest = false;
                }else {
                    mPresenter.onCommonUserInfo();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_income_withdrawal:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startIncomeFrg(this);
                break;
            case R.id.tv_login:
                UIHelper.startLoginAct();
                break;
            case R.id.tv_vip:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startVipFrg(this);
                break;
            case R.id.tv_promote:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startPromoteFrg(this);
                break;
            case R.id.tv_feedback:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startFeedbackFrg(this, 0);
                break;
            case R.id.tv_message:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startMsgFrg(this);
                break;
            case R.id.tv_hot:
                UIHelper.startHtmlAct(HtmlAct.HOTGROUP, hotUrl);
                break;
            case R.id.iv_img:
//                UIHelper.startHtmlAct(HtmlAct.BANNER, link);
                break;
            case R.id.ly_history:
                UIHelper.startHistoryFrg(this);
                break;
            case R.id.ly_cash:
                UIHelper.startCashFrg(this);
                break;
            case R.id.ly_like:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startCollectionFrg(this);
                break;
            case R.id.ly_equ:
                UIHelper.startSetAct();
                break;

        }
    }

    @Override
    public void setData(JSONObject userObj) {
        if (userObj == null)return;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#09FBFC"));
        SpannableString hText = new SpannableString("历史观看" + userObj.optInt("history") + "部");
        hText.setSpan(colorSpan, 4, hText.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvHistory.setText(hText);
        SpannableString lText = new SpannableString("您有" + userObj.optInt("like") + "部喜欢的影片");
        lText.setSpan(colorSpan, 2, lText.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvLike.setText(lText);
        GlideLoadingUtils.load(act, userObj.optString("headimg"), mB.ivHead, true);
        mB.tvName.setText(userObj.optString("name"));
    }

    @Override
    public void setAd(JSONObject ad) {
        if (ad != null){
            link = ad.optString("link");
            GlideLoadingUtils.load(act, ad.optString("imgUrl"), mB.ivImg);
            mB.ivImg.setVisibility(View.VISIBLE);
        }else {
            mB.ivImg.setVisibility(View.GONE);
        }
    }

    @Override
    public void setHotGroup(String url) {
        hotUrl = url;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

}
