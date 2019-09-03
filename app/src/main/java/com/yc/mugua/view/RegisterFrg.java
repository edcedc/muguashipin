package com.yc.mugua.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.databinding.FRegisterBinding;
import com.yc.mugua.impl.RegisterContract;
import com.yc.mugua.presenter.RegisterPresenter;
import com.yc.mugua.utils.CountDownTimerUtils;
import com.yc.mugua.utils.cache.SharedAccount;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 19:49
 *  注册
 */
public class RegisterFrg extends BaseFragment<RegisterPresenter, FRegisterBinding> implements RegisterContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_register;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.register));
        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.tvLogin.setOnClickListener(this);
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
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.register(mB.etPhone.getText().toString(), mB.etPwd.getText().toString(), mB.etInvitation.getText().toString(), mB.etCode.getText().toString());
                break;
            case R.id.tv_login:
                pop();
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void onResgist(String phone, String pwd) {
        SharedAccount.getInstance(act).save(phone, pwd);
        pop();
    }

}
