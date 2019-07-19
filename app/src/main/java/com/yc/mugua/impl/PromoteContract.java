package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 19:03
 */
public interface PromoteContract {

    interface View extends IBaseView {

        void setData();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onPrompte();
        
    }

}
