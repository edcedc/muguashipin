package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.GridDividerItemDecoration;
import com.yc.mugua.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 14:33
 */
public class HomeAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public HomeAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        String image = bean.getImage();
        if (image == null){
            viewHolder.iv_img.setVisibility(View.GONE);
        }else {
            GlideLoadingUtils.load(act, image, viewHolder.iv_img);
            viewHolder.iv_img.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_like_title.setText(bean.getName());
        viewHolder.tv_like_title.setCompoundDrawablesWithIntrinsicBounds(null,
                null, act.getResources().getDrawable(R.mipmap.home_more, null), null);
        List<DataBean> video = bean.getVideo();
        if (video != null && video.size() != 0){
            viewHolder.recyclerView.setVisibility(View.VISIBLE);
            LikeAdapter adapter = new LikeAdapter(act, video);
            viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(act, 2));
            viewHolder.recyclerView.setHasFixedSize(true);
            viewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            viewHolder.recyclerView.addItemDecoration(new GridDividerItemDecoration(60, 20, ContextCompat.getColor(act,R.color.blue_15163d)));
            viewHolder.recyclerView.setAdapter(adapter);
        }else {
            viewHolder.recyclerView.setVisibility(View.GONE);
        }
        viewHolder.tv_like_title.setOnClickListener(view -> UIHelper.startFindFrg(root, bean.getId()));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_like_title;
        RecyclerView recyclerView;
        RoundImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_like);
            tv_like_title = itemView.findViewById(R.id.tv_like_title);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

}
