package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.view.act.HtmlAct;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/13
 * Time: 18:50
 */
public class HomeBannerAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public HomeBannerAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getImgUrl(), viewHolder.iv_img);
        viewHolder.itemView.setOnClickListener(view -> {
            if (bean.getType() == 0){
                UIHelper.startVideoAct(bean.getId());
            }else {
                UIHelper.startHtmlAct(HtmlAct.BANNER, bean.getLink());
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_view_card, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }


}
