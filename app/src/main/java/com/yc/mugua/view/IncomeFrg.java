package com.yc.mugua.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FIncomeBinding;
import com.yc.mugua.impl.IncomeContract;
import com.yc.mugua.presenter.IncomePresenter;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/19
 * Time: 13:15
 *  收益提现
 */
public class IncomeFrg extends BaseFragment<IncomePresenter, FIncomeBinding> implements IncomeContract.View, View.OnClickListener {

    private double canPullMoney;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_income;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.presentation));
        mB.btSubmit.setOnClickListener(this);
        mPresenter.onInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.onSubmit(mB.etName.getText().toString(), mB.etMoble.getText().toString(), mB.etBalance.getText().toString(), canPullMoney);
                break;
        }
    }

    @Override
    public void setData(DataBean data) {
        mB.tvPerformance.setText(data.getTotal() +
                " 钻石");
        mB.tvBalance.setText(data.getMoney() +
                " 钻石");
        canPullMoney = data.getCanPullMoney();
        mB.tvWithdrawable.setText(canPullMoney +
                " 元");
        mB.tvPayment.setText(data.getCost());
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F72A61"));
        SpannableString dailyTex = new SpannableString("#余额来源" + "：" + "当别人（注：新用户）通过你的推广链接下载 打开APP（或者输入你的邀请码）即可建立邀请关系，每次 他购买会员都会返利给你");
        dailyTex.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvYue.setText(dailyTex);
        SpannableString cTex = new SpannableString("#提交申请" + "：" + "当你已有下级用户购买会员之后，才可以进行提交申请");
        cTex.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvContent.setText(cTex);
    }

    @Override
    public void setShare() {
        pop();
    }
}
