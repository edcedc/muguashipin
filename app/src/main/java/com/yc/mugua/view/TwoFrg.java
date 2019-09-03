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
import com.yc.mugua.event.FindInEvent;
import com.yc.mugua.impl.TwoContract;
import com.yc.mugua.presenter.TwoPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private String field;
    private String categoryId;
    private String tagsId;

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
        setSwipeBackEnable(false);
        mB.ivClass.setOnClickListener(this);
        view.findViewById(R.id.et_search).setOnClickListener(this);
        if (searchAdapter == null){
            searchAdapter = new SearchAdapter(act, listSearch);
        }
        setRecyclerViewType(mB.rvSearch);
        mB.rvSearch.setAdapter(searchAdapter);

        if (adapter == null){
            adapter = new FindVideoAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        mPresenter.onSearch();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, field, categoryId, tagsId);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, field, categoryId, tagsId);
            }
        });
        searchAdapter.setListener((position, keyword, categoryId) -> {
            this.categoryId = categoryId;
            mB.refreshLayout.startRefresh();
        });
        EventBus.getDefault().register(this);
    }

    private boolean isSearchData = false;
    @Override
    public void setSearch(final List<DataBean> listBean) {
//        listSearch.clear();
        if (isSearchData)return;
        isSearchData = true;
        listSearch.addAll(listBean);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSearchOne(List<DataBean> list) {
        mB.flSearch.removeAllViews();
        mB.flSearch.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean dataBean) {
                View view = View.inflate(act, R.layout.i_search_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(dataBean.getName());
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                DataBean bean = list.get(position);
                delegate.setBackgroundColor(act.getColor(R.color.red_F72A61));
                bean.setSelect(true);
                field = bean.getId();
                mB.refreshLayout.startRefresh();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                DataBean bean = list.get(position);
                delegate.setBackgroundColor(0);
                bean.setSelect(false);
                field = null;
                mB.refreshLayout.startRefresh();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_search:
                UIHelper.startSearchFrg(this);
                break;
            case R.id.iv_class:
                UIHelper.startFindClassFrg(this);
                break;
        }
    }

    @Subscribe
    public void onMainFindInEvent(FindInEvent event){
        if (event.field == null && event.categoryId == null && event.tagsId == null)return;
        this.field = event.field;
        this.categoryId = event.categoryId;
        this.tagsId = event.tagsId;
        mB.refreshLayout.startRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
