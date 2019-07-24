package com.yc.mugua.view.act;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.flyco.roundview.RoundTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yc.mugua.R;
import com.yc.mugua.adapter.CollectionAdapter;
import com.yc.mugua.adapter.CommentAdapter;
import com.yc.mugua.base.GSYBaseActivityDetail;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FVideoBinding;
import com.yc.mugua.impl.VideoContract;
import com.yc.mugua.presenter.VideoPresenter;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.weight.GSYSampleADVideoPlayer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/21
 * Time: 16:34
 */
public class VideoAct extends GSYBaseActivityDetail<VideoPresenter, FVideoBinding, ListGSYVideoPlayer> implements VideoContract.View, View.OnClickListener {

    private ArrayList<GSYSampleADVideoPlayer.GSYADVideoModel> urls = new ArrayList<>();
    private List<DataBean> listBean = new ArrayList<>();
    private CollectionAdapter adapter;
    private BottomSheetBehavior behavior;
    private RecyclerView rvComment;
    private TwinklingRefreshLayout refreshLayoutComment;
    private RoundTextView etText;
    private AppCompatImageView ivXiao;
    private AppCompatImageView ivLike;
    private AppCompatImageView ivShare;

    private List<DataBean> listComment = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private int pagerNumberComment = 1;

    @Override
    protected void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        LinearLayout bottomSheet = findViewById(R.id.bottom_sheet);
        rvComment = findViewById(R.id.rv_comment);
        refreshLayoutComment = findViewById(R.id.refreshLayout1);
        etText = findViewById(R.id.et_text);
        ivXiao = findViewById(R.id.iv_xiao);
        ivLike = findViewById(R.id.iv_like);
        ivShare = findViewById(R.id.iv_share1);
        findViewById(R.id.tv_comment_title).setOnClickListener(this);

        mB.tvComment.setOnClickListener(this);
        mB.ivColl.setOnClickListener(this);
        mB.ivDow.setOnClickListener(this);
        mB.ivShare.setOnClickListener(this);
        mB.tvBiaoqian.setOnClickListener(this);
        //设置视频屏幕的三分之一
        ViewGroup.LayoutParams params = mB.videoPlayer.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight() / 3;
        mB.videoPlayer.setLayoutParams(params);

        ViewGroup.LayoutParams params1 = bottomSheet.getLayoutParams();
        params1.height = ScreenUtils.getScreenHeight() - ScreenUtils.getScreenHeight() / 3;
        bottomSheet.setLayoutParams(params1);

        if (adapter == null){
            adapter = new CollectionAdapter(act, listBean, false);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest( pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });

        if (commentAdapter == null){
            commentAdapter = new CommentAdapter(act, listComment);
        }
        setRecyclerViewType(rvComment);
        rvComment.setAdapter(commentAdapter);
        mPresenter.onCommentRequest(pagerNumberComment = 1);
        refreshLayoutComment.setEnableRefresh(false);
        setRefreshLayout(refreshLayoutComment, new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onCommentRequest(pagerNumberComment += 1);
            }
        });

        //普通模式
        initVideo();
        resolveNormalVideoUI();
        mB.videoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        mB.videoPlayer.setRotateViewAuto(false);
        mB.videoPlayer.setLockLand(false);
        mB.videoPlayer.setShowFullAnimation(false);
        mB.videoPlayer.setNeedLockFull(true);
        mB.videoPlayer.setVideoAllCallBack(this);
        mB.videoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
    }

    private void resolveNormalVideoUI() {
        //增加title
        mB.videoPlayer.getTitleTextView().setVisibility(View.GONE);
        mB.videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mB.videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_comment:
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.iv_coll:

                break;
            case R.id.iv_dow:

                break;
            case R.id.iv_share:

                break;
            case R.id.tv_comment_title:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
        super.setRefreshLayoutMode(listComment.size(), totalRow, refreshLayoutComment);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
        super.setRefreshLayout(pagerNumberComment, refreshLayoutComment);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", imageView);
        mB.videoPlayer.setThumbImageView(imageView);
        urls.add(new GSYSampleADVideoPlayer.GSYADVideoModel("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4",
                "", GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL));
        mB.videoPlayer.setAdUp(urls, true, 0);
        mB.videoPlayer.startPlayLogic();

        mB.tvTitle.setText("我是标题");
        mB.tvTime.setText("2019-07-01  " +
                "播放次数 " +
                "99999" +
                " 次");
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/62306eealy1g4xwb6ahatj20u01404qp.jpg", mB.ivImg);

        mB.flLabel.removeAllViews();
        mB.flLabel.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean dataBean) {
                View view = View.inflate(act, R.layout.i_video_label, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(position + "全部");
                return view;
            }
        });
    }

    @Override
    public ListGSYVideoPlayer getGSYVideoPlayer() {
        return mB.videoPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isPlay){
            getGSYVideoPlayer().getCurrentPlayer().onVideoPause();
        }
    }

    @Override
    public void onPrepared(String url, Object... objects) {
        super.onPrepared(url, objects);
        LogUtils.e(url);
        for (GSYSampleADVideoPlayer.GSYADVideoModel model : urls){
            String modelUrl = model.getUrl();
            if (modelUrl.equals(url) && model.getType() == GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL){
            }
        }
    }

    @Override
    public void setCommentData(List<DataBean> list) {
        if (pagerNumberComment == 1) {
            listComment.clear();
            refreshLayoutComment.finishRefreshing();
        } else {
            refreshLayoutComment.finishLoadmore();
        }
        listComment.addAll(list);
        commentAdapter.notifyDataSetChanged();
    }
}
