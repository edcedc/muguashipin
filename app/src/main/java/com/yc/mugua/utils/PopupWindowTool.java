package com.yc.mugua.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.weight.WPopupWindow;
import com.yc.mugua.weight.ZXingUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static final int clear = 1; //清除缓存
    public static final int version = 2; //版本更新


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
//        tv_title.setText(bean.getTitle());
        tv_title.setText(bean.getTitle());
//        tv_title.setMovementMethod(LinkMovementMethod.getInstance());

        tv_title.setOnClickListener(view -> {
//            Intent intent= new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse("https://www.baidu.com/");
//            intent.setData(content_url);
//            act.startActivity(intent);
        });
        AppCompatTextView tv_content = wh.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(bean.getContext()));
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());

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
        final AppCompatTextView tv_wx = wh.findViewById(R.id.tv_wx);
        final AppCompatTextView tv_zfb = wh.findViewById(R.id.tv_zfb);
        final AppCompatImageView iv_zking = wh.findViewById(R.id.iv_zking);
//        tv_title.setText("选择支付方式：" + bean.getName());
        tv_wx.setOnClickListener(view -> {
            String zkingImg = payChannel(listPay, "1");
            if (!StringUtils.isEmpty(zkingImg)){
                tv_wx.setVisibility(View.GONE);
                tv_zfb.setVisibility(View.GONE);
                iv_zking.setVisibility(View.VISIBLE);
                GlideLoadingUtils.load(act, zkingImg, iv_zking);
            }else {
                ToastUtils.showShort("当前渠道已关闭");
            }
        });
        tv_zfb.setOnClickListener(view -> {
            String zkingImg = payChannel(listPay, "2");
            if (!StringUtils.isEmpty(zkingImg)){
                tv_wx.setVisibility(View.GONE);
                tv_zfb.setVisibility(View.GONE);
                iv_zking.setVisibility(View.VISIBLE);
                GlideLoadingUtils.load(act, zkingImg, iv_zking);
            }else {
                ToastUtils.showShort("当前渠道已关闭");
            }
        });

        wh.findViewById(R.id.bt_cancel).setOnClickListener(view -> popupWindow.dismiss());
        wh.findViewById(R.id.bt_submit).setOnClickListener(view -> {
            String pay = et_text.getText().toString();
            if (listener != null){
               if (!StringUtils.isEmpty(pay)){
                   listener.onClick(-1, pay);
                   popupWindow.dismiss();
               }else {
                   ToastUtils.showShort("充值码不能为空");
               }
            }
        });
    }

    private static String payChannel(List<DataBean> listPay, String id){
        for (DataBean bean : listPay){
            if (bean.getId().equals(id)){
                return bean.getQrcodeUrl();
            }
        }
        return null;
    }

    public interface onPayListener{
        void onClick(int pos, String text);
    }


    public static void showDialog(final Context act, final int type, int versionType, final DialogListener listener) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_dialog, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        TextView tvTitle = wh.findViewById(R.id.tv_title);
        TextView btCancel = wh.findViewById(R.id.tv_cancel);
        TextView btSubmit = wh.findViewById(R.id.tv_confirm);
        switch (type) {
            case clear:
                tvTitle.setText("确定清除缓存吗？");
                break;
            case version:
                tvTitle.setText("版本更新");
                break;
        }

        btCancel.setOnClickListener(view1 ->{
            if (listener != null) {
                listener.onDismiss();
                popupWindow.dismiss();
            }
        }
        );
        btSubmit.setOnClickListener(view12 -> {
            if (listener != null) {
                listener.onClick();
                popupWindow.dismiss();
            }
        });
    }

    public interface DialogListener {
        void onClick();
        void onDismiss();
    }
}
