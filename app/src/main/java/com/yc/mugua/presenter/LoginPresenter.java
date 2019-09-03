package com.yc.mugua.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.yc.mugua.R;
import com.yc.mugua.base.User;
import com.yc.mugua.callback.Code;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.LoginContract;
import com.yc.mugua.utils.cache.ShareSessionIdCache;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 12:32
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String phone, String pwd, String name, int pot) {
        if (StringUtils.isEmpty(pwd)){
            showToast(act.getString(R.string.please_pwd3));
            return;
        }
        if (pot == 0){
            if (StringUtils.isEmpty(phone)){
                showToast(act.getString(R.string.please_phone));
                return;
            }
        }else {
            if (StringUtils.isEmpty(name)){
                showToast(act.getString(R.string.please_user));
                return;
            }
        }

        CloudApi.userLogin(phone, pwd)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            JSONObject data = jsonObject.optJSONObject("data");
//                            JSONObject user = data.optJSONObject("user");
//                            User.getInstance().setUserObj(user);
//                            User.getInstance().setLogin(true);
                            ShareSessionIdCache.getInstance(Utils.getApp()).save(data.optString("token"));
                            User.getInstance().setUserId(data.optString("userId"));
                            User.getInstance().setLogin(true);
                            mView.setLogin();
                        }
                        showToast(jsonObject.optString("message"));
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
