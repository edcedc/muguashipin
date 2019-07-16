package com.yc.mugua.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mugua.R;
import com.yc.mugua.adapter.MyPagerAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BaseListContract;
import com.yc.mugua.base.BaseListPresenter;
import com.yc.mugua.databinding.FThreeBinding;
import com.yc.mugua.weight.TabEntity;

import java.util.ArrayList;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:11
 */
public class ThreeFrg extends BaseFragment<BaseListPresenter, FThreeBinding> implements BaseListContract.View {

    public static ThreeFrg newInstance() {
        Bundle args = new Bundle();
        ThreeFrg fragment = new ThreeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private String[] listStr = {"周榜", "月榜", "热榜", "收藏榜"};

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_three;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < listStr.length; i++){
            BillboardFrg frg = new BillboardFrg();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, listStr));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(listStr.length - 1);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {


    }

}
