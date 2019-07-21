package com.yc.mugua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.GridDividerItemDecoration;
import com.yc.mugua.weight.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/14
 * Time: 14:33
 */
public class HomeAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public HomeAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", viewHolder.iv_img);

        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        LikeAdapter adapter = new LikeAdapter(act, list);
        viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(act, 2));
        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        viewHolder.recyclerView.addItemDecoration(new GridDividerItemDecoration(60, 20, ContextCompat.getColor(act,R.color.blue_474578)));
        viewHolder.recyclerView.setAdapter(adapter);
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
