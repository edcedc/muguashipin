package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/15
 * Time: 17:46
 */
public interface FourContract {

    interface View extends IBaseView {
        void setData(JSONObject userObj);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onInfo();

    }

}
