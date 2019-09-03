package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/22
 * Time: 11:32
 */
public interface FindClassContract {

    interface View extends IBaseView {

        void setComprehensive(List<DataBean> list);

        void setClass(List<DataBean> list);

        void setLabel(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();
    }

}
