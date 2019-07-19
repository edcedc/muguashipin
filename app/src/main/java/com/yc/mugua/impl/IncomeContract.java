package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/19
 * Time: 14:43
 */
public interface IncomeContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void onSubmit(String name, String moble, String balance);
    }

}
