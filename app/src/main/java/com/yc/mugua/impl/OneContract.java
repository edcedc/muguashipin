package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/13
 * Time: 18:33
 */
public interface OneContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setLike(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onBanner();

        public abstract void onListRequest();

        public abstract void onRequest(int pagerNumber);

    }

}
