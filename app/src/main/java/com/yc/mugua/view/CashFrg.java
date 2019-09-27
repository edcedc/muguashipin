package com.yc.mugua.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.yc.mugua.R;
import com.yc.mugua.adapter.CashAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FCashBinding;
import com.yc.mugua.impl.CashContract;
import com.yc.mugua.presenter.CashPresenter;
import com.yc.mugua.utils.PopupWindowTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 20:17
 *  缓存
 */
public class CashFrg extends BaseFragment<CashPresenter, FCashBinding> implements CashContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private CashAdapter adapter;
    private AppCompatTextView topRight;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_cash;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.local_cache), getString(R.string.manage));
        topRight = view.findViewById(R.id.top_right);
        mB.tvAllDel.setOnClickListener(this);
        mB.tvDel.setOnClickListener(this);
        if (adapter == null){
            adapter = new CashAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.loadinglayout.setEmptyText("您还没有缓存" + "\n" + "在详情页可点击“离线缓存”按钮进行缓存");
        mPresenter.onRequest();
        /*mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest();
            }
        });*/
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

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (topRight.getText().toString().equals(getString(R.string.manage))){
            topRight.setText(getString(R.string.cancel));
            mB.layout.setVisibility(View.VISIBLE);
            adapter.setVisibbility(true);
        }else {
            topRight.setText(getString(R.string.manage));
            mB.layout.setVisibility(View.GONE);
            adapter.setVisibbility(false);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_all_del:
                PopupWindowTool.showDialog(act, PopupWindowTool.clear, 0, new PopupWindowTool.DialogListener() {
                    @Override
                    public void onClick() {
                        for (DataBean bean :listBean){
                            boolean b = FileUtils.deleteFile(bean.getContent());
                        }
                        listBean.clear();
                        adapter.notifyDataSetChanged();
                        if (listBean.size() == 0){
                            showLoadEmpty();
                        }
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
                break;
            case R.id.tv_del:
                List<DataBean> list = new ArrayList<>();
                for (DataBean bean :listBean){
                    if (bean.isSelect()){
                        list.add(bean);
                        FileUtils.deleteFile(bean.getContent());
                    }
                }
                listBean.removeAll(list);
                if (listBean.size() == 0){
                    showLoadEmpty();
                }
                mB.layout.setVisibility(View.GONE);
                adapter.setVisibbility(false);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
