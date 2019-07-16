package com.yc.mugua.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.impl.RegisterContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 20:06
 */
public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.please_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }

    }

    @Override
    public void register(String phone, String pwd, String invitation, String code) {
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(code)){
            showToast(act.getString(R.string.error_));
            return;
        }

    }
}
