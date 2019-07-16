package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.SearchAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BaseListContract;
import com.yc.mugua.base.BaseListPresenter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FFindClassBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 16:21
 *  分类
 */
public class FindClassFrg extends BaseFragment<BaseListPresenter, FFindClassBinding> implements BaseListContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private SearchAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_find_class;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.find_class));
        mB.btSubmit.setOnClickListener(this);
        mB.btCancel.setOnClickListener(this);
        if (adapter == null){
            adapter = new SearchAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView, R.color.blue_474578);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest("");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:

                break;
            case R.id.bt_cancel:
                pop();
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        mB.refreshLayout.finishRefreshing();
        listBean.clear();
        listBean.addAll((List<DataBean>) data);
        adapter.notifyDataSetChanged();
    }
}
