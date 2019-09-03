package com.yc.mugua.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.TwoContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 15:31
 */
public class TwoPresenter extends TwoContract.Presenter{

    String[] strName = {"综合", "最多播放", "最近更新", "最多喜欢"};
    String[] strId = {"field", "most", "createTime", "isLike"};

    @Override
    public void onSearch() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < strName.length;i++){
            DataBean bean = new DataBean();
            bean.setName(strName[i]);
            bean.setId(strId[i]);
            list.add(bean);
        }
        mView.setSearchOne(list);
    }

    @Override
    public void onRequest(int pagerNumber, String field, String categoryId, String tagId) {
        CloudApi.findList(pagerNumber, null, categoryId, field, tagId)
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
                            List<DataBean> list = new ArrayList<>();
                            DataBean dataBean = new DataBean();
                            dataBean.setCategory(data.getCategory());
                            list.add(dataBean);
                            mView.setSearch(list);
                            mView.setData(data.getVideos());
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
