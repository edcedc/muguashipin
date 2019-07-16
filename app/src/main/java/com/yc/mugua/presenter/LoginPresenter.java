package com.yc.mugua.presenter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.controller.CloudApi;
import com.yc.mugua.impl.LoginContract;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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


    }

}
