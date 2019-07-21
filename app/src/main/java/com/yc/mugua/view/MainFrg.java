package com.yc.mugua.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseView;
import com.yc.mugua.databinding.FMainBinding;
import com.yc.mugua.weight.buttonBar.BottomBar;
import com.yc.mugua.weight.buttonBar.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MainFrg extends BaseFragment<BasePresenter, FMainBinding> implements IBaseView, View.OnClickListener {

    public static MainFrg newInstance() {
        Bundle args = new Bundle();
        MainFrg fragment = new MainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private int position = 0;
    private int prePosition;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initView(View view) {
        mB.ly1.setOnClickListener(this);
        mB.ly2.setOnClickListener(this);
        mB.ly3.setOnClickListener(this);
        mB.ly4.setOnClickListener(this);
        setClick(position, mB.ly1);
        showHideFragment(mFragments[position], mFragments[prePosition]);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(OneFrg.class);
        if (firstFragment == null) {
            mFragments[FIRST] = OneFrg.newInstance();
            mFragments[SECOND] = TwoFrg.newInstance();
            mFragments[THIRD] = ThreeFrg.newInstance();
            mFragments[FOUR] = FourFrg.newInstance();

            loadMultipleRootFragment(R.id.fl_container,
                    FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(TwoFrg.class);
            mFragments[THIRD] = findChildFragment(ThreeFrg.class);
            mFragments[FOUR] = findChildFragment(FourFrg.class);
        }
        setSwipeBackEnable(false);
    }

    private void setClick(int position, LinearLayout linearLayout){
        linearLayout.setBackgroundColor(act.getColor(R.color.red_F72A61));
    }
    private void setNoClick(int position, LinearLayout linearLayout){
        linearLayout.setBackgroundColor(0);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setSofia(false);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public boolean onBackPressedSupport() {
        exitBy2Click();// 调用双击退出函数
//        return super.onBackPressedSupport();
        return true;
    }

    private Boolean isExit = false;

    private void exitBy2Click() {
        Handler tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
            tExit = new Handler();
            tExit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return;
        } else {
//            Cockroach.uninstall();
            act.finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_1:
                position = 0;
                setClick(position, mB.ly1);
                break;
            case R.id.ly_2:
                position = 1;
                setClick(position, mB.ly2);
                break;
            case R.id.ly_3:
                position = 2;
                setClick(position, mB.ly3);
                break;
            case R.id.ly_4:
                position = 3;
                setClick(position, mB.ly4);
                break;
        }
        showHideFragment(mFragments[position], mFragments[prePosition]);
        switch (prePosition){
            case 0:
                setNoClick(prePosition, mB.ly1);
                break;
            case 1:
                setNoClick(prePosition, mB.ly2);
                break;
            case 2:
                setNoClick(prePosition, mB.ly3);
                break;
            case 3:
                setNoClick(prePosition, mB.ly4);
                break;
        }
        prePosition = position;
    }

}
