package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/3
 * Time: 16:20
 */
public interface MyPromoteContract {

    interface View extends IBaseListView {


    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);
    }

}
