package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.User;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FSetBinding;
import com.yc.mugua.utils.cache.ShareEquCache;
import com.yc.mugua.utils.cache.SharedAccount;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 11:50
 */
public class SetFrg extends BaseFragment<BasePresenter, FSetBinding> implements View.OnClickListener {

    public static SetFrg newInstance() {

        Bundle args = new Bundle();

        SetFrg fragment = new SetFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_set;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set));
        mB.lyEqu.setOnClickListener(this);
        mB.lyEqu1.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_equ:
                UIHelper.startEquAct();
                break;
            case R.id.ly_equ1:
                ShareEquCache.getInstance(act).remove();
                showToast("锁屏密码重置成功");
                break;
            case R.id.bt_submit:
                UIHelper.startLoginAct();
                SharedAccount.getInstance(act).remove();
                User.getInstance().setLogin(false);
                User.getInstance().setUserObj(null);
                ActivityUtils.finishAllActivities();
                break;
        }
    }
}
