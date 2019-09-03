package com.yc.mugua.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.WriterException;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseFragment;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.databinding.FShareBinding;
import com.yc.mugua.utils.ClipboardUtils;
import com.yc.mugua.utils.ImageUtils;
import com.yc.mugua.weight.ZXingUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/1
 * Time: 14:01
 *  立即推广
 */
public class ShareFrg extends BaseFragment<BasePresenter, FShareBinding> implements View.OnClickListener {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_share;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.text3));
        mB.tvCopy.setOnClickListener(this::initView);
        mB.tvSave.setOnClickListener(this::initView);
        mB.tvCopyUrl.setOnClickListener(this::initView);
        mB.tvCode.setText("YESOOP");
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_copy:
                ClipboardUtils.copyText(mB.tvCode.getText().toString());
                showToast("复制成功");
                break;
            case R.id.tv_save:
                ImageUtils.viewSaveToImage(act, mB.ivZking);
                showToast("保存成功");
                break;
            case R.id.tv_copy_url:
                ClipboardUtils.copyText(mB.tvCode.getText().toString());
                showToast("复制成功");
                break;
        }
    }
}
