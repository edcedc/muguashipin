package com.yc.mugua.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.HistoryContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/2
 * Time: 16:31
 */
public class HistoryPresenter extends HistoryContract.Presenter{
    @Override
    public void onRequest(int pagetNumber) {
        CloudApi.commonHistory(pagetNumber)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().data;
                            mView.setData(data.getVedios());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
