package com.yc.mugua.impl;


import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 12:33
 */
public interface LoginContract {

    interface View extends IBaseView {

        void onCode();

        void setLogin();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void login(String phone, String pwd, String name, int position);

    }

}
