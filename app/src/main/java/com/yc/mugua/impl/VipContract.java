package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 18:04
 */
public interface VipContract {

    interface View extends IBaseListView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

    }


}
