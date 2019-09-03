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
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.utils.GlideLoadingUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 15:42
 */
public class FindVideoAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public FindVideoAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getImage(), viewHolder.iv_img);
        viewHolder.tv_time.setText(bean.getDuration());
        viewHolder.tv_zan.setText(bean.getLikeNum() + "");
        viewHolder.tv_title.setText(bean.getTitle());

        viewHolder.itemView.setOnClickListener(view -> UIHelper.startVideoAct(bean.getId()));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_find_video, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_zan;
        AppCompatTextView tv_time;
        AppCompatTextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_zan = itemView.findViewById(R.id.tv_zan);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}
