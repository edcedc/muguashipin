package com.yc.mugua.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.weight.WPopupWindow;
import com.yc.mugua.weight.ZXingUtils;

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
        String title = "我是文字" +
                "：";
        SpannableString spanString = new SpannableString(title + "www.xxxx.com");
        URLSpan span = new URLSpan("https://www.baidu.com/");
        spanString.setSpan(span, title.length(), spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_title.setText(spanString);
        tv_title.setMovementMethod(LinkMovementMethod.getInstance());

        tv_title.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("https://www.baidu.com/");
            intent.setData(content_url);
            act.startActivity(intent);
        });
        AppCompatTextView tv_content = wh.findViewById(R.id.tv_content);
        tv_content.setText("       我是文字我是文字我是文字我是文字我是文字我是文字我是文字我是文字我是文字我是文字我是文字我是文        字");

        AppCompatTextView tv_name = wh.findViewById(R.id.tv_name);
        SpannableString nameString = new SpannableString("我是文字我是文字我是文字");
        URLSpan nameSpan = new URLSpan("https://www.baidu.com/");
        nameString.setSpan(nameSpan, 0, nameString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_name.setText(nameString);
        tv_name.setMovementMethod(LinkMovementMethod.getInstance());

        wh.findViewById(R.id.bt_submit).setOnClickListener(view -> popupWindow.dismiss());
    }

    public static void showZking(final Context act) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_zking, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        final AppCompatImageView iv_zking = wh.findViewById(R.id.iv_zking);
        iv_zking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_zking.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode("https://www.baidu.com/", iv_zking.getWidth());
                    iv_zking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showPay(final Context act, final onPayListener listener) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_vip, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        final AppCompatEditText et_text = wh.findViewById(R.id.et_text);
        wh.findViewById(R.id.tv_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(0, null);
                    popupWindow.dismiss();
                }
            }
        });
        wh.findViewById(R.id.tv_zfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(1, null);
                    popupWindow.dismiss();
                }
            }
        });
        wh.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        wh.findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(-1, et_text.getText().toString());
                    popupWindow.dismiss();
                }
            }
        });
    }

    public interface onPayListener{
        void onClick(int pos, String text);
    }


}
