package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/2
 * Time: 18:40
 */
public interface CollectionContract {

    interface View extends IBaseListView {

        void setCollState(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNmber);

        public abstract void onCollect(int position, String id);
    }

}
