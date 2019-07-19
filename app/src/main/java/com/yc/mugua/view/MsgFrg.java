package com.yc.mugua.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.CollectionAdapter;
import com.yc.mugua.adapter.MsgAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BaseListContract;
import com.yc.mugua.base.BaseListPresenter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.BRecyclerBinding;
import com.yc.mugua.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 16:41
 */
public class MsgFrg extends BaseFragment<BaseListPresenter, BRecyclerBinding> implements BaseListContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private MsgAdapter adapter;

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
        setTitle(getString(R.string.message));
        if (adapter == null){
            adapter = new MsgAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  1));
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest("", pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest("", pagerNumber += 1);
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
