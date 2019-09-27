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

    public CommentAdapter(Context act, List<DataBean> listBean, int type) {
        super(act, listBean);
        this.type = type;
    }

    private int type = 0;

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        if (type == 0){
            viewHolder.tv_title.setText(bean.getUserName() +
                    "  |  【楼主】" );
            viewHolder.tv_time.setText(bean.getCreateTime() +
                    "     " +
                    (bean.getVideoCommentList() == null ? "" : bean.getVideoCommentList().size()) +
                    "回复");
        }else {
            viewHolder.tv_title.setText(bean.getUserName() +
                    ( position == 0 ? "  |  【楼主】" : ""));
            viewHolder.tv_time.setText(bean.getCreateTime() +
                    "     " );
        }

        GlideLoadingUtils.load(act, bean.getHeadimg(), viewHolder.iv_head, true);

        viewHolder.tv_content.setText(bean.getContext());

        viewHolder.tv_zan.setText(bean.getLikeCount() + "");
        if (bean.getIsLike() == 0){
            viewHolder.tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.bofandianzan, null),
                    null, null, null);
            viewHolder.tv_zan.setTextColor(act.getResources().getColor(R.color.blue_7F7E9B));
        }else {
            viewHolder.tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y10, null),
                    null, null, null);
            viewHolder.tv_zan.setTextColor(act.getResources().getColor(R.color.red_F72A61));
        }


        viewHolder.tv_zan.setOnClickListener(view -> {
            if (listener != null)listener.onZanClick(position, bean.getId());
        });
        viewHolder.tv_report.setOnClickListener(view -> {
            if (listener != null)listener.onReportClick(position, bean.getId());
        });
        viewHolder.itemView.setOnClickListener(view -> {
            if (listener != null && bean.getVideoCommentList() != null)listener.onChildComment(position, bean, bean.getVideoCommentList());
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_comment, parent, false));
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onZanClick(int position, String id);
        void onReportClick(int position, String id);
        void onChildComment(int position, DataBean bean, List<DataBean> videoCommentList);
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
