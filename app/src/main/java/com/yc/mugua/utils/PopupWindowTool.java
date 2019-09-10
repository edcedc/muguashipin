package com.yc.mugua.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.adapter.PayAdapter;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.weight.WPopupWindow;
import com.yc.mugua.weight.WithScrollGridView;
import com.yc.mugua.weight.ZXingUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static void showAdvertisement(final Context act, final DataBean bean){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_adv, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        AppCompatTextView tv_title = wh.findViewById(R.id.tv_title);
//        String title = "我是文字" +
//                "：";
//        SpannableString spanString = new SpannableString(title + "www.xxxx.com");
//        URLSpan span = new URLSpan("https://www.baidu.com/");
//        spanString.setSpan(span, title.length(), spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_title.setText(bean.getTitle());
        tv_title.setMovementMethod(LinkMovementMethod.getInstance());

        tv_title.setOnClickListener(view -> {
//            Intent intent= new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse("https://www.baidu.com/");
//            intent.setData(content_url);
//            act.startActivity(intent);
        });
        AppCompatTextView tv_content = wh.findViewById(R.id.tv_content);
        tv_content.setText(bean.getContext());

        AppCompatTextView tv_name = wh.findViewById(R.id.tv_name);
//        SpannableString nameString = new SpannableString("我是文字我是文字我是文字");
//        URLSpan nameSpan = new URLSpan("https://www.baidu.com/");
//        nameString.setSpan(nameSpan, 0, nameString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_name.setText(nameString);
//        tv_name.setMovementMethod(LinkMovementMethod.getInstance());

        wh.findViewById(R.id.bt_submit).setOnClickListener(view -> popupWindow.dismiss());
    }

    public static void showZking(final Context act, String link) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_zking, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        final AppCompatImageView iv_zking = wh.findViewById(R.id.iv_zking);
        iv_zking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_zking.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode(link, iv_zking.getWidth());
                    iv_zking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showPay(final Context act, List<DataBean> listPay, final onPayListener listener) {
        AtomicInteger type = new AtomicInteger();
        type.set(-1);
        View wh = LayoutInflater.from(act).inflate(R.layout.p_vip, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        AppCompatTextView tv_title = wh.findViewById(R.id.tv_title);
        final AppCompatEditText et_text = wh.findViewById(R.id.et_text);
        final AppCompatImageView iv_img = wh.findViewById(R.id.iv_img);
        WithScrollGridView listView = wh.findViewById(R.id.listView);
        PayAdapter adapter = new PayAdapter(act, listPay);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            DataBean bean = listPay.get(i);
            tv_title.setText("选择支付方式：" + bean.getName());
            type.set(i);
            GlideLoadingUtils.load(act, bean.getIcon(), iv_img);
        });
        wh.findViewById(R.id.bt_cancel).setOnClickListener(view -> popupWindow.dismiss());
        wh.findViewById(R.id.bt_submit).setOnClickListener(view -> {
            if (listener != null){
                if (type.get() == -1){
                    popupWindow.dismiss();
                }else {
                    listView.setVisibility(View.GONE);
                    iv_img.setVisibility(View.VISIBLE);
                    type.set(-1);
                }
//                listener.onClick(-1, et_text.getText().toString());
            }
        });
    }

    public interface onPayListener{
        void onClick(int pos, String text);
    }


}
