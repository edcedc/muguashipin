package com.yc.mugua.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FLoginBinding;
import com.yc.mugua.impl.LoginContract;
import com.yc.mugua.presenter.LoginPresenter;
import com.yc.mugua.utils.cache.SharedAccount;
import com.yc.mugua.weight.TabEntity;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 16:04
 *  登录
 */
public class LoginFrg extends BaseFragment<LoginPresenter, FLoginBinding> implements LoginContract.View, View.OnClickListener {

    public static LoginFrg newInstance() {
        Bundle args = new Bundle();
        LoginFrg fragment = new LoginFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int position;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_login;
    }

    @Override
    protected void initView(View view) {
        setSofia(true);
        setSwipeBackEnable(false);
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] strings = {getString(R.string.phone_login)};
        for (String s : strings){
            mTabEntities.add(new TabEntity(s));
        }
        mB.tbLayout.setTabData(mTabEntities);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int posit) {
                if (position == 0){
                    mB.lyPhone.setVisibility(View.GONE);
                    mB.lyName.setVisibility(View.VISIBLE);
                }else {
                    mB.lyPhone.setVisibility(View.VISIBLE);
                    mB.lyName.setVisibility(View.GONE);
                }
                position = posit;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mB.fyClose.setOnClickListener(this);
        mB.tvRegister.setOnClickListener(this);
        mB.tvForget.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.ivPwd.setOnCheckedChangeListener((compoundButton, b) -> {
            int passwordLength = mB.etPwd.getText().length();
            if (b){
                mB.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                mB.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mB.etPwd.setSelection(passwordLength);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.tv_register:
                UIHelper.startRegisterFrg(this);
                break;
            case R.id.tv_forget:
                UIHelper.startForgetFrg(this);
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etPwd.getText().toString(), mB.etName.getText().toString(), position);
                break;
        }
    }

    @Override
    public void onCode() {

    }

    @Override
    public void setLogin() {
        UIHelper.startMainAct();
        act.finish();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SharedAccount account = SharedAccount.getInstance(act);
        String mobile = account.getMobile();
        String pwd = account.getPwd();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)){
            mB.etPhone.setText(mobile);
            mB.etPwd.setText(pwd);
        }
    }

}
