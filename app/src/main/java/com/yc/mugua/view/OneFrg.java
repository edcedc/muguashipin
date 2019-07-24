package com.yc.mugua.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.yc.mugua.R;
import com.yc.mugua.adapter.HomeAdapter;
import com.yc.mugua.adapter.HomeBannerAdapter;
import com.yc.mugua.adapter.LikeAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FOneBinding;
import com.yc.mugua.impl.OneContract;
import com.yc.mugua.presenter.OnePresenter;

import java.util.ArrayList;
import java.util.List;

public class OneFrg extends BaseFragment<OnePresenter, FOneBinding> implements OneContract.View, View.OnClickListener {

    private AppCompatTextView tvLikeTitle;
    private RecyclerView rvLike;

    public static OneFrg newInstance() {
        Bundle args = new Bundle();
        OneFrg fragment = new OneFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBanner = new ArrayList<>();
    private HomeBannerAdapter homeBannerAdapter;
    private InfiniteScrollAdapter infiniteAdapter;

    private List<DataBean> listLike = new ArrayList<>();
    private LikeAdapter likeAdapter;

    private List<DataBean> listBean = new ArrayList<>();
    private HomeAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_one;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(true);
        if (homeBannerAdapter == null){
            homeBannerAdapter = new HomeBannerAdapter(act, this, listBanner);
        }
        mB.speedRecyclerView.setOrientation(DSVOrientation.HORIZONTAL);
        infiniteAdapter = InfiniteScrollAdapter.wrap(homeBannerAdapter);
        mB.speedRecyclerView.setAdapter(infiniteAdapter);
        mB.speedRecyclerView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        tvLikeTitle = view.findViewById(R.id.tv_like_title);
        rvLike = view.findViewById(R.id.rv_like);
        view.findViewById(R.id.iv_img).setVisibility(View.GONE);
        view.findViewById(R.id.et_search).setOnClickListener(this);
        view.findViewById(R.id.iv_dow).setOnClickListener(this);
        view.findViewById(R.id.iv_time).setOnClickListener(this);
        if (likeAdapter == null){
            likeAdapter = new LikeAdapter(act, listBanner);
        }
        setRecyclerViewGridType(rvLike, 2, 60, 20, R.color.blue_15163d);
        rvLike.setAdapter(likeAdapter);

        if (adapter == null){
            adapter = new HomeAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onBanner();
                mPresenter.onListRequest();
                mPresenter.onRequest();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
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
            case R.id.iv_dow:
                UIHelper.startCashFrg(this);
                break;
            case R.id.iv_time:
                UIHelper.startHistoryFrg(this);
                break;
        }
    }

    @Override
    public void setBanner(List<DataBean> list) {
        mB.refreshLayout.finishRefreshing();
        listBanner.clear();
        listBanner.addAll(list);
        homeBannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLike(List<DataBean> list) {
        listLike.clear();
        listLike.addAll(list);
        likeAdapter.notifyDataSetChanged();
    }

}
