package com.yc.mugua.view.act;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.view.EquFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/22
 * Time: 18:06
 */
public class EquAct extends BaseActivity {
    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return  R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        setSwipeBackEnable(false);
        if (findFragment(EquFrg.class) == null) {
            loadRootFragment(R.id.fl_container, EquFrg.newInstance());
        }
    }

}
