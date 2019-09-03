package com.yc.mugua.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/1
 * Time: 17:52
 */
public class MyPromoteAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public MyPromoteAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_phone.setText(bean.getTel());
        viewHolder.tv_time.setText(bean.getCreatetime().split(" ")[0]);
        if(position % 2 == 0){
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#1D1E45"));
        }else {
            viewHolder.itemView.setBackgroundColor(0);
        }
        viewHolder.itemView.setOnClickListener(view -> {});
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_my_promote, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_name;
        AppCompatTextView tv_phone;
        AppCompatTextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

}
