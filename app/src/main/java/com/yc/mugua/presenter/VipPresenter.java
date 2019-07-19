package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.VipContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 18:04
 */
public class VipPresenter extends VipContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 500);
    }
}
