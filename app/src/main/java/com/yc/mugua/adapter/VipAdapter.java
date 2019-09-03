package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 18:09
 */
public class VipAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public VipAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getPrice() + "");
        viewHolder.tv_content.setText(bean.getContext());
        viewHolder.tv_vip.setText(bean.getName());

        viewHolder.bt_submit.setOnClickListener(view -> {
            if (listener != null){
                listener.onClick(position);
            }
        });
        viewHolder.itemView.setOnClickListener(view -> {

        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onClick(int position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_vip, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RoundLinearLayout layout;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        AppCompatTextView tv_vip;
        RoundTextView bt_submit;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_vip = itemView.findViewById(R.id.tv_vip);
            bt_submit = itemView.findViewById(R.id.bt_submit);
        }
    }

}
