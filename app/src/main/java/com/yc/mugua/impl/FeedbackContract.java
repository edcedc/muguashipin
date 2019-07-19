package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 18:34
 */
public interface FeedbackContract {

    interface View extends IBaseListView {

        void finish();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();

        public abstract void onFeed(String toString);
    }

}
