package com.yc.mugua.view;

import android.os.Bundle;
import android.text.Html;
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
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onInfo();
                mB.refreshLayout.finishRefreshing();
            }
        });
        String his ="历史观看<font color='#09FBFC'><small>" + 10 +
                "</small></font>" + "部";
        mB.tvHistory.setText(Html.fromHtml(his));
        String cash = "目前本地缓存<font color='#09FBFC'><small>" + 10 +
                "</small></font>" + "部";
        mB.tvCash.setText(Html.fromHtml(cash));
        String like = "您有<font color='#09FBFC'><small>" + 10 +
                "</small></font>" + "部喜欢的影片";
        mB.tvLike.setText(Html.fromHtml(like));
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivHead);
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivImg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_income_withdrawal:

                break;
            case R.id.tv_login:
                UIHelper.startLoginAct();
                break;
            case R.id.tv_vip:

                break;
            case R.id.tv_promote:

                break;
            case R.id.tv_feedback:

                break;
            case R.id.tv_message:

                break;
            case R.id.tv_hot:

                break;
            case R.id.iv_img:

                break;
            case R.id.ly_history:

                break;
            case R.id.ly_cash:

                break;
            case R.id.ly_like:

                break;
        }
    }

    @Override
    public void setData(JSONObject userObj) {
        if (userObj == null)return;

    }

}
