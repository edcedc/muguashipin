package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.HistoryAdapter;
import com.yc.mugua.adapter.VipAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.BRecyclerBinding;
import com.yc.mugua.impl.VipContract;
import com.yc.mugua.presenter.VipPresenter;
import com.yc.mugua.utils.PopupWindowTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 18:02
 *  VIP
 */
public class VipFrg extends BaseFragment<VipPresenter, BRecyclerBinding> implements VipContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private VipAdapter adapter;

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
        setTitle(getString(R.string.vip));
        if (adapter == null){
            adapter = new VipAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new VipAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                PopupWindowTool.showPay(act, new PopupWindowTool.onPayListener() {
                    @Override
                    public void onClick(int pos, String text) {

                    }
                });
            }
        });

        showLoadDataing();
        mB.refreshLayout.startRefresh();
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
