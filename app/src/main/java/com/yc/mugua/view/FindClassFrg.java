package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FFindClassBinding;
import com.yc.mugua.event.FindInEvent;
import com.yc.mugua.impl.FindClassContract;
import com.yc.mugua.presenter.FindClassPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 16:21
 *  分类
 */
public class FindClassFrg extends BaseFragment<FindClassPresenter, FFindClassBinding> implements FindClassContract.View, View.OnClickListener {

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
        return R.layout.f_find_class;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.find_class));
        mB.btSubmit.setOnClickListener(this);
        mB.btCancel.setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                EventBus.getDefault().post(new FindInEvent(field, categoryId, tagsId));
                pop();
                break;
            case R.id.bt_cancel:
                pop();
                break;
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setComprehensive(List<DataBean> list) {
        mB.flComp.removeAllViews();
        mB.flComp.setAdapter(new TagAdapter<DataBean>(list){
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
                delegate.setBackgroundColor(act.getResources().getColor(R.color.red_F72A61));
                bean.setSelect(true);
                field = bean.getId();
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
            }
        });
    }

    @Override
    public void setClass(List<DataBean> list) {
        mB.flClass.removeAllViews();
        mB.flClass.setAdapter(new TagAdapter<DataBean>(list){
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
                delegate.setBackgroundColor(act.getResources().getColor(R.color.red_F72A61));
                bean.setSelect(true);
                categoryId = bean.getId();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                DataBean bean = list.get(position);
                delegate.setBackgroundColor(0);
                bean.setSelect(false);
                categoryId = null;
            }
        });
    }

    @Override
    public void setLabel(List<DataBean> list) {
        mB.flLabel.removeAllViews();
        mB.flLabel.setAdapter(new TagAdapter<DataBean>(list){
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
                delegate.setBackgroundColor(act.getResources().getColor(R.color.red_F72A61));
                bean.setSelect(true);
                StringBuilder sb = new StringBuilder();
                for (DataBean dataBean : list){
                    if (dataBean.isSelect()){
                        sb.append(dataBean.getId()).append(",");
                    }
                }
                tagsId = sb.toString();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                DataBean bean = list.get(position);
                delegate.setBackgroundColor(0);
                bean.setSelect(false);
                StringBuilder sb = new StringBuilder();
                for (DataBean dataBean : list){
                    if (dataBean.isSelect()){
                        sb.append(dataBean.getId()).append(",");
                    }
                }
                tagsId = sb.toString();
            }
        });
    }
}
