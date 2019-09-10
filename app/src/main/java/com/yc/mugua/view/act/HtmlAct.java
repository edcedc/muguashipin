package com.yc.mugua.view.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.databinding.AHtmlBinding;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  统一H5
 */

public class HtmlAct extends BaseActivity<BasePresenter, AHtmlBinding> {

    private int type = -1;
    private String url;

    public static final int HOTGROUP = 1;//火爆交流群
    public static final int BANNER = 2;//轮播图跳进来


    @Override
    public void initPresenter() {mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.a_html;
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        url = bundle.getString("url");
    }

    @Override
    protected void initView() {
        setSofia(true);
        switch (type){
            case HOTGROUP:
                setTitle("火爆交流群");
                mB.webView.loadUrl(url);
                break;
            case BANNER:
                setTitle("广告");
                mB.webView.loadUrl(url);
                break;
            default:
                setTitle("详情");
                break;
        }
        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                LogUtils.e(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                mB.progressBar.setVisibility(View.GONE);
                ToastUtils.showShort("网页加载失败");
            }
        });
        //进度条
        mB.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mB.progressBar.setVisibility(View.GONE);
                    return;
                }
                mB.progressBar.setVisibility(View.VISIBLE);
                mB.progressBar.setProgress(newProgress);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mB.webView.canGoBack()) {
            mB.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }
}
