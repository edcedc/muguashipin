package com.yc.mugua.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.adapter.BillboardAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FSearchBinding;
import com.yc.mugua.impl.SearchContract;
import com.yc.mugua.presenter.SearchPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 17:07
 *  搜索
 */
public class SearchFrg extends BaseFragment<SearchPresenter, FSearchBinding> implements SearchContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private String searchText;
    private BillboardAdapter adapter;
    private AppCompatEditText etSearch;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search;
    }

    @Override
    protected void initView(View view) {
        etSearch = view.findViewById(R.id.et_search);
        AppCompatEditText etSearch = view.findViewById(R.id.et_search);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);
        mB.tvSearch.setOnClickListener(this);
        mB.fyClose.setOnClickListener(this);

        showLoadDataing();
//        mB.refreshLayout.startRefresh();
        mPresenter.onHot();
        if (adapter == null){
            adapter = new BillboardAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 60, 20, R.color.blue_15163d);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, searchText);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, searchText);
            }
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            //判断是否是“完成”键
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                return true;
            }
            return false;
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(() -> {
                    if (editable.length() == 0){
                        mB.tvSearch.setText("搜索");
                    }else {
                        mB.tvSearch.setText("取消");
                    }
                }, 300);
            }
        });

    }

    @Override
    public void setHot(final List<DataBean> list) {
        mB.refreshLayout.finishRefreshing();
        mB.flHot.removeAllViews();
        mB.flHot.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_search_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getName());
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                delegate.setBackgroundColor(act.getResources().getColor(R.color.red_F72A61));
                DataBean bean = list.get(position);
                bean.setSelect(true);
                searchText = bean.getName();
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
            }
        });
    }

    @Override
    public void setRecommend(final List<DataBean> list) {
        mB.flRecommend.removeAllViews();
        mB.flRecommend.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_search_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getName());
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                delegate.setBackgroundColor(act.getColor(R.color.red_F72A61));
                DataBean bean = list.get(position);
                bean.setSelect(true);
                searchText = bean.getName();
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
        mB.recyclerView.setVisibility(View.VISIBLE);
        mB.layout.setVisibility(View.GONE);
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
            case R.id.tv_search:
                searchText = etSearch.getText().toString().trim();
                mB.refreshLayout.startRefresh();
                break;
            case R.id.fy_close:
                pop();
                break;
        }
    }

}
