package com.yc.mugua.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mugua.R;
import com.yc.mugua.impl.IncomeContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/19
 * Time: 14:43
 */
public class IncomePresenter extends IncomeContract.Presenter{

    @Override
    public void onSubmit(String name, String moble, String balance) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(moble) || StringUtils.isEmpty(balance)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }

}
