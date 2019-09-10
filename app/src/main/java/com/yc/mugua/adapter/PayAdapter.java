package com.yc.mugua.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mugua.R;
import com.yc.mugua.base.BaseListViewAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.SizeImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 11:55
 */
public class PayAdapter extends BaseListViewAdapter<DataBean> {
    public PayAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_pay
                    , null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getIcon(), viewHolder.iv_img);
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.iv_img.setWH(1, 1, true);
        return convertView;
    }

    class ViewHolder{

        SizeImageView iv_img;
        AppCompatTextView tv_name;

        public ViewHolder(View convertView) {
            iv_img = convertView.findViewById(R.id.iv_img);
            tv_name = convertView.findViewById(R.id.tv_name);
        }

    }

}
