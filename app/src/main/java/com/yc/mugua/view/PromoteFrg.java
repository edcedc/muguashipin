package com.yc.mugua.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.StringUtils;
import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.adapter.PromoteAdapter;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.User;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.controller.UIHelper;
import com.yc.mugua.databinding.FPromoteBinding;
import com.yc.mugua.impl.PromoteContract;
import com.yc.mugua.presenter.PromotePresenter;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.ZXingUtils;

import org.json.JSONObject;

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
    private String link;

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
        if (!((BaseActivity) act).isLogin()) return;
        switch (view.getId()){
            case R.id.bt_submit:
                UIHelper.startShareFrg(this);
                break;
            case R.id.iv_zking:
                UIHelper.startShareFrg(this);
//                PopupWindowTool.showZking(act, link);
                break;
        }
    }

    @Override
    public void setData(DataBean bean) {
        listBean.addAll(bean.getLevelList());
        adapter.notifyDataSetChanged();

        JSONObject userObj = User.getInstance().getUserObj();
        mB.tvName.setText(userObj.optString("name"));
        GlideLoadingUtils.load(act, userObj.optString("headimg"), mB.ivHead);
        mB.tvCode.setText("我的邀请码：" +
                userObj.optString("invitcode"));
        link = userObj.optString("link");
        if (!link.equals("null") && !StringUtils.isEmpty(link)){
            mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mB.ivZking.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    try {
                        Bitmap bitmap = ZXingUtils.creatBarcode(link, mB.ivZking.getWidth());
                        mB.ivZking.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F72A61"));
        int currentCount = userObj.optInt("currentCount");
        int belowCount = userObj.optInt("belowCount");
        SpannableString cText = new SpannableString(currentCount + "/" + belowCount);
        cText.setSpan(new ForegroundColorSpan(Color.parseColor("#F72A61")), 0, String.valueOf(currentCount).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvPromoteNum.setText(cText);
        mB.progressBarHealthy.setMax(belowCount);
        mB.progressBarHealthy.setProgress(currentCount);
        mB.tvDifferenceNum.setText("距离下一等级还差 " +
                (belowCount - currentCount) +
                " 人");
        mB.tvAvailableToday.setText(userObj.optString("totaltimes"));
        mB.tvMovieWatches.setText(userObj.optString("lastTimes"));
        int level = userObj.optInt("level");//判断等级
        switch (level){
            default:
                mB.ivZlevel.setBackgroundResource(R.mipmap.champion);
                mB.ivYlevel.setBackgroundResource(R.mipmap.runner_up);
                break;
            case 2:
                mB.ivZlevel.setBackgroundResource(R.mipmap.runner_up);
                mB.ivYlevel.setBackgroundResource(R.mipmap.third);
                break;
            case 3:
                mB.ivZlevel.setBackgroundResource(R.mipmap.third);
                mB.ivYlevel.setBackgroundResource(R.mipmap.fourth);
                break;
            case 4:
                mB.ivZlevel.setBackgroundResource(R.mipmap.fourth);
                break;
        }

        DataBean dayTask = bean.getDayTask();
        if (dayTask != null){
            String name = dayTask.getName();
            String context = dayTask.getContext();
            SpannableString dailyTex = new SpannableString("#" +
                    name + "：" + context);
            dailyTex.setSpan(colorSpan, 0, name.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mB.tvDaily.setText(dailyTex);
        }
        DataBean shareTask = bean.getShareTask();
        if (shareTask != null){
            String name = shareTask.getName();
            String context = shareTask.getContext();
            SpannableString extensionText = new SpannableString("#" +
                    name + "：" + context);
            extensionText.setSpan(colorSpan, 0, name.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mB.tvExtension.setText(extensionText);
        }
        SpannableString welfareText = new SpannableString("#福利任务" + "：");
        welfareText.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvWelfare.setText(welfareText);

        List<DataBean> wealTask = bean.getWealTask();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < wealTask.size();i++){
            DataBean dataBean = wealTask.get(i);
            String name = (i + 1) + "、" + dataBean.getName();
            sb.append(name).append("\n").append(dataBean.getContext()).append("\n").append("\n");
        }
        mB.tvContent.setText(sb.toString());
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (!((BaseActivity) act).isLogin()) return;
        UIHelper.startMyPromoteFrg(this);
    }
}
