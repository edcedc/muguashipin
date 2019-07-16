package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 20:15
 */
public interface ForgetContract {

    interface View extends IBaseView {

        void onCode();

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void forget(String phone, String pwd, String code);

    }

}
