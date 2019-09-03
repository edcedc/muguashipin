package com.yc.mugua.adapter;

import android.content.Context;
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
 * Date: 2019/7/22
 * Time: 16:37
 */
public class CommentAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public CommentAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, bean.getHeadimg(), viewHolder.iv_head, true);
        viewHolder.tv_title.setText(bean.getUserName() +
                "  |  楼主");
        viewHolder.tv_content.setText(bean.getContext());
        viewHolder.tv_time.setText(bean.getCreateTime() +
                "     " +
                bean.getLikeCount() +
                "人赞过 >");
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_comment, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView iv_head;
        AppCompatTextView tv_title;
        AppCompatTextView tv_report;
        AppCompatTextView tv_content;
        AppCompatTextView tv_time;
        AppCompatTextView tv_zan;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_report = itemView.findViewById(R.id.tv_report);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_zan = itemView.findViewById(R.id.tv_zan);
        }
    }

}
