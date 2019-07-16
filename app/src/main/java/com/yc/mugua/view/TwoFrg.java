package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.FindVideoAdapter;
import com.yc.mugua.adapter.SearchAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FTwoBinding;
import com.yc.mugua.impl.TwoContract;
import com.yc.mugua.presenter.TwoPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class TwoFrg extends BaseFragment<TwoPresenter, FTwoBinding> implements TwoContract.View, View.OnClickListener {

    public static TwoFrg newInstance() {
        Bundle args = new Bundle();
        TwoFrg fragment = new TwoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private FindVideoAdapter adapter;

    private List<DataBean> listSearch = new ArrayList<>();
    private SearchAdapter searchAdapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_two;
    }

    @Override
    protected void initView(View view) {
        mB.ivClass.setOnClickListener(this);
        view.findViewById(R.id.ly_search).setOnClickListener(this);
        if (searchAdapter == null){
            searchAdapter = new SearchAdapter(act, listSearch);
        }
        setRecyclerViewType(mB.rvSearch, R.color.blue_15163d);
        mB.rvSearch.setAdapter(searchAdapter);

        if (adapter == null){
            adapter = new FindVideoAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView, R.color.blue_15163d);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mPresenter.onSearch();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
    }

    @Override
    public void setSearch(final List<DataBean> listBean) {
        listSearch.addAll(listBean);
        searchAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_search:

                break;
            case R.id.iv_class:
                UIHelper.startFindClassFrg(this);
                break;
        }
    }

}
