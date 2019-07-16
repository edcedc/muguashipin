package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.ThreeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/15
 * Time: 16:29
 */
public class ThreePresenter extends ThreeContract.Presenter{
    @Override
    public void onRequest(int pagetNumer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<15;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 500);
    }
}
