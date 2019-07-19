package com.yc.mugua.presenter;

import android.os.Handler;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.CashContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 20:18
 */
public class CashPresenter extends CashContract.Presenter {

    @Override
    public void onRequest() {
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
