package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/21
 * Time: 16:34
 */
public class VideoFrg extends BaseFragment {

    public static VideoFrg newInstance() {

        Bundle args = new Bundle();

        VideoFrg fragment = new VideoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video;
    }

    @Override
    protected void initView(View view) {

    }
}
