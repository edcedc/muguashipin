package com.yc.mugua.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
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
import com.yc.mugua.utils.cache.ShareEquCache;
import com.yc.mugua.view.act.HtmlAct;

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
       /* if (homeBannerAdapter == null){
            homeBannerAdapter = new HomeBannerAdapter(act, this, listBanner);
        }
        mB.speedRecyclerView.setOrientation(DSVOrientation.HORIZONTAL);
        infiniteAdapter = InfiniteScrollAdapter.wrap(homeBannerAdapter);
        mB.speedRecyclerView.setAdapter(infiniteAdapter);
        mB.speedRecyclerView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());*/



        tvLikeTitle = view.findViewById(R.id.tv_like_title);
        rvLike = view.findViewById(R.id.rv_like);
        view.findViewById(R.id.iv_img).setVisibility(View.GONE);
        view.findViewById(R.id.et_search).setOnClickListener(this);
        view.findViewById(R.id.iv_dow).setOnClickListener(this);
        view.findViewById(R.id.iv_time).setOnClickListener(this);
        mB.lyChange.setOnClickListener(this);
        if (likeAdapter == null){
            likeAdapter = new LikeAdapter(act, listLike);
        }
        setRecyclerViewGridType(rvLike, 2, 60, 20, R.color.blue_15163d);
        rvLike.setAdapter(likeAdapter);

        if (adapter == null){
            adapter = new HomeAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mPresenter.onBanner();
//        mPresenter.onVersionList();
//        mPresenter.onListRequest();
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

        String equPwd = ShareEquCache.getInstance(act).getEquPwd();
        if (!StringUtils.isEmpty(equPwd)){
            UIHelper.startEquAct();
        }
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
            case R.id.ly_change:
                mPresenter.onListRequest();
                break;
        }
    }

    @Override
    public void setBanner(List<DataBean> list) {
//        listBanner.clear();
//        listBanner.addAll(list);
//        homeBannerAdapter.notifyDataSetChanged();
        List<String> list1 = new ArrayList<>();
        for (DataBean bean : list){
            list1.add(bean.getImgUrl());
        }
        mB.banner.initBanner(list1, true)//开启3D画廊效果
                .addPageMargin(10, 50)//参数1page之间的间距,参数2中间item距离边界的间距
                .addPoint(6)//添加指示器
                .addStartTimer(3)//自动轮播5秒间隔
                .addPointBottom(7)
                .addRoundCorners(10)//圆角
                .finishConfig()//这句必须加
                .addBannerListener(position -> {
                    DataBean bean = list.get(position);
                    if (bean.getType() == 0){
                        UIHelper.startVideoAct(bean.getLink());
                    }else {
                        UIHelper.startHtmlAct(act, HtmlAct.BANNER, bean.getLink());
                    }
                });
    }

    @Override
    public void setLike(List<DataBean> list) {
        tvLikeTitle.setText("猜你喜欢");
        listLike.clear();
        listLike.addAll(list);
        likeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setVersion(int type, String downloadUrl, String appVersion) {
        String appVersionName = AppUtils.getAppVersionName();
        if (!appVersion.equals(appVersionName)){
//            PopupWindowTool.showDialog(act, PopupWindowTool.version, type ,() -> {
//                Uri uri = Uri.parse(downloadUrl);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            });
        }
    }

}
