package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
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
 * Date: 2019/7/18
 * Time: 16:16
 */
public class PromoteAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public PromoteAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        switch (position){
            case 0:
                viewHolder.iv_img.setBackgroundResource(R.mipmap.champion);
                break;
            case 1:
                viewHolder.iv_img.setBackgroundResource(R.mipmap.runner_up);
                break;
            case 2:
                viewHolder.iv_img.setBackgroundResource(R.mipmap.third);
                break;
            case 3:
                viewHolder.iv_img.setBackgroundResource(R.mipmap.fourth);
                break;
        }
        viewHolder.tv_text.setText(bean.getName() + "\n" + "推广" +
                bean.getGrowth() +
                "人-每日观影次数 +" +
                bean.getCount());
        viewHolder.itemView.setOnClickListener(view -> {});
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_promote, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_text;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_text = itemView.findViewById(R.id.tv_text);
        }
    }

}
