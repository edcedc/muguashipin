package com.yc.mugua.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.User;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FFourBinding;
import com.yc.mugua.impl.FourContract;
import com.yc.mugua.presenter.FourPresenter;
import com.yc.mugua.utils.GlideLoadingUtils;

import org.json.JSONObject;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:14
 */
public class FourFrg extends BaseFragment<FourPresenter, FFourBinding> implements FourContract.View, View.OnClickListener {

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
        if (isRequest){
            isRequest = false;
            mB.refreshLayout.startRefresh();
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
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onInfo();
                mB.refreshLayout.finishRefreshing();
            }
        });

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#09FBFC"));
        SpannableString hText = new SpannableString("历史观看" + 10 + "部");
        hText.setSpan(colorSpan, 4, hText.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvHistory.setText(hText);
        SpannableString cText = new SpannableString("目前本地缓存" + 10 + "部");
        cText.setSpan(colorSpan, 6, cText.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvCash.setText(cText);
        SpannableString lText = new SpannableString("您有" + 10 + "部喜欢的影片");
        lText.setSpan(colorSpan, 2, lText.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvLike.setText(lText);
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivHead);
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivImg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_income_withdrawal:
                UIHelper.startIncomeFrg(this);
                break;
            case R.id.tv_login:
                UIHelper.startLoginAct();
                break;
            case R.id.tv_vip:
                UIHelper.startVipFrg(this);
                break;
            case R.id.tv_promote:
                UIHelper.startPromoteFrg(this);
                break;
            case R.id.tv_feedback:
                UIHelper.startFeedbackFrg(this, 0);
                break;
            case R.id.tv_message:
                UIHelper.startMsgFrg(this);
                break;
            case R.id.tv_hot:

                break;
            case R.id.iv_img:

                break;
            case R.id.ly_history:
                UIHelper.startHistoryFrg(this);
                break;
            case R.id.ly_cash:
                UIHelper.startCashFrg(this);
                break;
            case R.id.ly_like:
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

    }

}
