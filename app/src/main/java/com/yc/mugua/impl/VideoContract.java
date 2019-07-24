package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/21
 * Time: 16:37
 */
public interface VideoContract {

    interface View extends IBaseListView {

        void setCommentData(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);
        
        public abstract void onCommentRequest(int pagerNumber);

    }

}
