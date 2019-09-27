package com.yc.mugua.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mugua.base.User;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.impl.FourContract;
import com.yc.mugua.utils.cache.ShareSessionIdCache;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/15
 * Time: 17:46
 */
public class FourPresenter extends FourContract.Presenter {
    @Override
    public void onInfo() {
        CloudApi.commonList()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        setData(jsonObject);
                        User.getInstance().setLogin(true);
                        qrCodeInfo();
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

    @Override
    public void onCommonUserInfo() {
        CloudApi.commonUserInfo()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        setData(jsonObject);
                        User.getInstance().setLogin(false);
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

    private void qrCodeInfo(){
        CloudApi.qrCodeInfo()
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
                            try {
                                JSONObject userObj = User.getInstance().getUserObj();
                                userObj.put("invitCode", data.getInvitCode());
                                userObj.put("link", data.getLink());
                            } catch (JSONException e) {
                                e.printStackTrace();
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

    @Override
    public void onHotGroup() {
        CloudApi.mineHotGroup()
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
                            mView.setHotGroup(data.getUrl());
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

    private void setData(JSONObject jsonObject) {
        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
            JSONObject data = jsonObject.optJSONObject("data");
            mView.setAd(data.optJSONObject("ad"));
            try {
                int currentCount = data.optInt("currentCount");
                int belowCount = data.optInt("belowCount");
                JSONObject user = data.optJSONObject("user");
                user.put("currentCount", currentCount);
                user.put("belowCount", belowCount);
                user.put("history", data.optInt("history"));
                user.put("like", data.optInt("like"));
                User.getInstance().setUserObj(user);
                mView.setData(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            UIHelper.startLoginAct();
            ShareSessionIdCache.getInstance(act).remove();
            showToast("身份信息失效,请重新登陆");
        }
    }
}
