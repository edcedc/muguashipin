package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.CashAdapter;
import com.yc.mugua.adapter.CollectionAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.BRecyclerBinding;
import com.yc.mugua.impl.CashContract;
import com.yc.mugua.presenter.CashPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 20:17
 *  缓存
 */
public class CashFrg extends BaseFragment<CashPresenter, BRecyclerBinding> implements CashContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private CashAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.local_cache));
        if (adapter == null){
            adapter = new CashAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest();
            }
        });
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        listBean.clear();
        mB.refreshLayout.finishRefreshing();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

}
