package com.yc.mugua.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.UMShareAPI;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.view.LoginFrg;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:37
 */
public class LoginAct extends BaseActivity {

    private String id;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
    }

    @Override
    protected void initView() {
        if (findFragment(LoginFrg.class) == null) {
            loadRootFragment(R.id.fl_container, LoginFrg.newInstance());
        }
    }

}
