package com.yc.mugua.presenter;

import com.yc.mugua.impl.PromoteContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 19:05
 */
public class PromotePresenter extends PromoteContract.Presenter {
    @Override
    public void onPrompte() {
        mView.setData();
    }
}
