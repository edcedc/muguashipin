package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FEquBinding;
import com.yc.mugua.utils.cache.ShareEquCache;
import com.yc.mugua.weight.NumLockPanel;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/22
 * Time: 18:07
 *  密码锁
 */
public class EquFrg extends BaseFragment<BasePresenter, FEquBinding> implements View.OnClickListener {

    private ShareEquCache equCache;

    public static EquFrg newInstance() {
        Bundle args = new Bundle();
        EquFrg fragment = new EquFrg();
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
        return R.layout.f_equ;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.tvFeek.setOnClickListener(this);
        equCache = ShareEquCache.getInstance(act);
        mB.numLock.setInputListener(new NumLockPanel.InputListener() {
            @Override
            public void inputFinish(String result) {
                LogUtils.e(result);

                if (StringUtils.isEmpty(equCache.getEquPwd())){
                    equCache.save(result);
                    mB.numLock.resetResult();
                    showToast("设置密码成功，请再重复输入一遍");
                }else if (equCache.getEquPwd().equals(result)){
                    act.finish();
                }else {
                    mB.numLock.showErrorStatus();
                }
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_feek:
                UIHelper.startFeedbackFrg(this, 0);
                break;
        }
    }
}
