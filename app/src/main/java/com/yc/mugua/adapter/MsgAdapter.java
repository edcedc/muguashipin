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
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.CircleImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 15:45
 */
public class MsgAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public MsgAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", viewHolder.iv_img);
        viewHolder.tv_title.setText("title");
        viewHolder.tv_content.setText("123");
        viewHolder.tv_time.setText("time");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_msg, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        AppCompatTextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

    }

}
