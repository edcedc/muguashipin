package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 20:06
 */
public interface RegisterContract {

    interface View extends IBaseView {

        void onCode();

        void onResgist(String phone, String pwd);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void register(String phone, String pwd, String invitation, String code);

    }

}
