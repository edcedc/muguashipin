package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.BillboardAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.BNotTitleRecyclerBinding;
import com.yc.mugua.impl.ThreeContract;
import com.yc.mugua.presenter.ThreePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/15
 * Time: 16:36
 *  榜单
 */
public class BillboardFrg extends BaseFragment<ThreePresenter, BNotTitleRecyclerBinding> implements ThreeContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private BillboardAdapter adapter;
    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.b_not_title_recycler;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        if (adapter == null){
            adapter = new BillboardAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 60, 20, R.color.blue_15163d);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
            mB.refreshLayout.startRefresh();
            setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    mPresenter.onRequest(pagerNumber = 1, type);
                }

                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    super.onLoadMore(refreshLayout);
                    mPresenter.onRequest(pagerNumber += 1, type);
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
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

}
