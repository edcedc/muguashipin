package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.TwoContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 15:31
 */
public class TwoPresenter extends TwoContract.Presenter{
    @Override
    public void onSearch() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<2;i++){
                    list.add(new DataBean());
                }
                mView.setSearch(list);
            }
        }, 500);
    }

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
