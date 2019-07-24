package com.yc.mugua.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.weight.WPopupWindow;
import com.yc.mugua.weight.ZXingUtils;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

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
