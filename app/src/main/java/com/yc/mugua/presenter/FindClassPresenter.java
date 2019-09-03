package com.yc.mugua.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.FindClassContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/22
 * Time: 11:32
 */
public class FindClassPresenter extends FindClassContract.Presenter{

    String[] strName = {"综合", "最多播放", "最近更新", "最多喜欢"};
    String[] strId = {"field", "most", "createTime", "isLike"};

    @Override
    public void onRequest() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < strName.length;i++){
            DataBean bean = new DataBean();
            bean.setName(strName[i]);
            bean.setId(strId[i]);
            list.add(bean);
        }
        mView.setComprehensive(list);

        CloudApi.findList(1, null, null, null, null)
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
                            mView.setClass(data.getCategory());
                            mView.setLabel(data.getTags());
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
