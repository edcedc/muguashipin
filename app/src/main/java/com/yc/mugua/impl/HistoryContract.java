package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/2
 * Time: 16:31
 */
public interface HistoryContract {

    interface View extends IBaseListView {

        void setDel();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumber);

        public abstract void onDel();
    }

}
