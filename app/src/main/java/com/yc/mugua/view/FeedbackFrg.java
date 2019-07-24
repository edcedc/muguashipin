package com.yc.mugua.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FFeedbackBinding;
import com.yc.mugua.impl.FeedbackContract;
import com.yc.mugua.presenter.FeedbackPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 18:22
 *  意见反馈
 */
public class FeedbackFrg extends BaseFragment<FeedbackPresenter, FFeedbackBinding> implements FeedbackContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_feedback;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.feedback));
        showLoadDataing();
        mB.btSubmit.setOnClickListener(this);
        mPresenter.onRequest();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.onFeed(mB.etText.getText().toString());
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        final List<DataBean> list = (List<DataBean>)data;
        mB.flLayout.removeAllViews();
        mB.flLayout.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean dataBean) {
                View view = View.inflate(act, R.layout.i_feed_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(position + "全部");
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
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                DataBean bean = list.get(position);
                delegate.setStrokeColor(act.getColor(R.color.red_F72A61));
                delegate.setStrokeWidth(1);
                delegate.setBackgroundColor(0);
                bean.setSelect(false);
            }
        });
    }

    @Override
    public void finish() {
        pop();
    }
}
