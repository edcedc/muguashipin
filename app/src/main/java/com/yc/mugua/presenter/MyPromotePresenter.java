package com.yc.mugua.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.MyPromoteContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/3
 * Time: 16:20
 */
public class MyPromotePresenter extends MyPromoteContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        CloudApi.shareMyInfo()
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        mView.hideLoading();
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().data;
                            List<DataBean> user = data.getUser();
                            if (user != null && user.size() != 0){
                                mView.setData(user);
                            }else {
                                mView.showLoadEmpty();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
