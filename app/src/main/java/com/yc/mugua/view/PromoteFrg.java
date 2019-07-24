package com.yc.mugua.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.adapter.PromoteAdapter;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FPromoteBinding;
import com.yc.mugua.impl.PromoteContract;
import com.yc.mugua.presenter.PromotePresenter;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.utils.PopupWindowTool;
import com.yc.mugua.weight.ZXingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 19:01
 *  我要推广
 */
public class PromoteFrg extends BaseFragment<PromotePresenter, FPromoteBinding> implements PromoteContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private PromoteAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_promote;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.me_promote1), getString(R.string.me_promote2));
        mB.btSubmit.setOnClickListener(this);
        mB.ivZking.setOnClickListener(this);
        if (adapter == null){
            adapter = new PromoteAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView, R.color.blue_474578);
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onPrompte();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:

                break;
            case R.id.iv_zking:
                PopupWindowTool.showZking(act);
                break;
        }
    }

    @Override
    public void setData() {
        listBean.add(new DataBean());
        listBean.add(new DataBean());
        listBean.add(new DataBean());
        listBean.add(new DataBean());
        adapter.notifyDataSetChanged();

        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivHead);
        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode("https://www.baidu.com/", mB.ivZking.getWidth());
                    mB.ivZking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        mB.tvName.setText("我是昵称啊YYTAX");
        mB.tvCode.setText("我的邀请码：" +
                "6X3GS9");

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F72A61"));

        final int one = 14;
        final int two = 15;
        String promoteText ="<font color='#F72A61'><small>" + one +
                "</small></font>" + "/" + two;
        mB.tvPromoteNum.setText(Html.fromHtml(promoteText));

        mB.progressBarHealthy.setMax(two);
        mB.progressBarHealthy.setProgress(one);

        mB.tvDifferenceNum.setText("距离下一等级还差 " +
                "1" +
                " 人");
        mB.tvAvailableToday.setText("1");
        mB.tvMovieWatches.setText("1");

        SpannableString dailyTex = new SpannableString("#每日任务" + "：" + "我是文字我是");
        dailyTex.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvDaily.setText(dailyTex);
        SpannableString extensionText = new SpannableString("#推广任务" + "：" + "我是文字我是");
        extensionText.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvExtension.setText(extensionText);
        SpannableString welfareText = new SpannableString("#福利任务" + "：");
        welfareText.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvWelfare.setText(welfareText);

        mB.tvContent.setText(
                "1、用户名注册" + "\n" +
                " 文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字  " + "\n" + "\n" +
                "2、手机号注册"  + "\n" +
                " 文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字  " + "\n" + "\n" +
                "3、填写邀请码"  + "\n" +
                " 文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字  " + "\n" + "\n" +
                "4、保存二维码"  + "\n" +
                " 文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字  "
        );
    }
}
