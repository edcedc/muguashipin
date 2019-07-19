package com.yc.mugua.presenter;

import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.FeedbackContract;
import com.yc.mugua.impl.ForgetContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 18:34
 */
public class FeedbackPresenter extends FeedbackContract.Presenter {

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

    @Override
    public void onFeed(String toString) {
        if (StringUtils.isEmpty(toString)){
            showToast(act.getString(R.string.error_));
            return;
        }
        mView.finish();
    }

}
