package com.yc.mugua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 16:02
 */
public class SearchAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public SearchAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        final List<DataBean> list = new ArrayList<>();
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        viewHolder.fl_search.removeAllViews();
        viewHolder.fl_search.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean dataBean) {
                View view = View.inflate(act, R.layout.i_search_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(position + "全部");
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
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_search_list, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TagFlowLayout fl_search;

        public ViewHolder(View itemView) {
            super(itemView);
            fl_search = itemView.findViewById(R.id.fl_search);
        }
    }

}
