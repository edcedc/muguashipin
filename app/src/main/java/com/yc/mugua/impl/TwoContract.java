package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 15:32
 */
public interface TwoContract {

    interface View extends IBaseListView {

        void setSearch(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSearch();

        public abstract void onRequest(int pagerNumber);

    }

}
