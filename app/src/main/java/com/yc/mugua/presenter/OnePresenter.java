package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.OneContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/13
 * Time: 18:33
 */
public class OnePresenter extends OneContract.Presenter{

    @Override
    public void onBanner() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setBanner(list);
            }
        }, 500);
    }

    @Override
    public void onListRequest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setLike(list);
            }
        }, 500);
    }

    @Override
    public void onRequest() {
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
