package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/26
 * Time: 14:24
 */
public interface FindContract {

    interface View extends IBaseListView {
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumber, String id);
    }

}
