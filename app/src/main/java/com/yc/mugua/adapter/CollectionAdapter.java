package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.utils.GlideLoadingUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 15:45
 */
public class CollectionAdapter extends BaseRecyclerviewAdapter<DataBean> {

    private boolean isCollection = true;

    public CollectionAdapter(Context act, List<DataBean> listBean, boolean isCollection) {
        super(act, listBean);
        this.isCollection = isCollection;
    }
    public CollectionAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }


    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, bean.getImage(), viewHolder.iv_img);
        viewHolder.tv_title.setText(bean.getTitle());

        String cash = "<font color='#F72A61'><small>" + bean.getViewCount() +
                "</small></font>" + "次播放";
        viewHolder.tv_content.setText(Html.fromHtml(cash));

        viewHolder.itemView.setOnClickListener(view -> UIHelper.startVideoAct(bean.getId()));
        viewHolder.bt_submit.setOnClickListener(view -> {
            if (listener != null)listener.onClick(position, bean.getId());
        });
        if (isCollection){
            viewHolder.bt_submit.setVisibility(View.VISIBLE);
        }else {
            viewHolder.bt_submit.setVisibility(View.GONE);
        }
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onClick(int position, String id);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_collection, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        RoundTextView bt_submit;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            bt_submit = itemView.findViewById(R.id.bt_submit);
        }

    }

}
