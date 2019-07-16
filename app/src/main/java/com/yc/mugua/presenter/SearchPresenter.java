package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.SearchContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 17:45
 */
public class SearchPresenter extends SearchContract.Presenter{
    @Override
    public void onHot() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setHot(list);
                mView.hideLoading();
            }
        }, 500);
    }

    @Override
    public void onRecommend() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setRecommend(list);
                mView.hideLoading();
            }
        }, 500);
    }
}
