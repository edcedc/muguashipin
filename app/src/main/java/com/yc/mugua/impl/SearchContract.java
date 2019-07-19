package com.yc.mugua.impl;

import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 17:45
 */
public interface SearchContract {

    interface View extends IBaseListView {

        void setHot(List<DataBean> list);

        void setRecommend(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onHot();

        public abstract void onRecommend();

        public abstract void onSearch(BaseFragment root);

        public abstract void onRequest(int pagetNumber, String text);
    }

}
