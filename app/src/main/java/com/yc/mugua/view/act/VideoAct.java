package com.yc.mugua.view.act;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yc.mugua.R;
import com.yc.mugua.adapter.CollectionAdapter;
import com.yc.mugua.adapter.CommentAdapter;
import com.yc.mugua.base.BaseActivity;
import com.yc.mugua.base.GSYBaseActivityDetail;
import com.yc.mugua.base.User;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.databinding.FVideoBinding;
import com.yc.mugua.impl.VideoContract;
import com.yc.mugua.presenter.VideoPresenter;
import com.yc.mugua.utils.GlideLoadingUtils;
import com.yc.mugua.view.bottomFrg.CommentBottomFrg;
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
    private AppCompatEditText etText;
    private AppCompatImageView ivXiao;

    private List<DataBean> listComment = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private int pagerNumberComment = 1;
    private String id;
    private boolean isColl;
    private CommentBottomFrg commentBottomFrg;
    private String videoUrl;

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
        id = bundle.getString("id");
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheet.setOnClickListener(this);
        rvComment = findViewById(R.id.rv_comment);
        refreshLayoutComment = findViewById(R.id.refreshLayout1);
        etText = findViewById(R.id.et_text);
        ivXiao = findViewById(R.id.iv_xiao);
        findViewById(R.id.tv_comment_title).setOnClickListener(this);

        commentBottomFrg = new CommentBottomFrg();
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onSaveComment(id, text);
            }

            @Override
            public void onSecondComment(int position, String infoId, String discussId, String text, String pUserId) {

            }
        });
        mB.tvComment.setOnClickListener(this);
        mB.ivDel.setOnClickListener(this);
        mB.ivColl.setOnClickListener(this);
        mB.ivDow.setOnClickListener(this);
        mB.ivShare.setOnClickListener(this);
        mB.tvBiaoqian.setOnClickListener(this);
        mB.ivZan.setOnClickListener(this);
        mB.ivCai.setOnClickListener(this);
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

        mPresenter.onVideoInfo(id);
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, id);
            }
        });

        if (commentAdapter == null){
            commentAdapter = new CommentAdapter(act, listComment);
        }
        setRecyclerViewType(rvComment);
        rvComment.setAdapter(commentAdapter);
        mPresenter.onCommentRequest(pagerNumberComment = 1, id);
        refreshLayoutComment.setEnableRefresh(false);
        setRefreshLayout(refreshLayoutComment, new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onCommentRequest(pagerNumberComment += 1, id);
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
        mB.videoPlayer.setLockClickListener((view, lock) -> {
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils.setEnable(!lock);
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
        mB.videoPlayer.getBackButton().setOnClickListener(view -> finish());
    }


    @Override
    public void onClick(View view) {
        if (!((BaseActivity)act).isLogin())return;
        switch (view.getId()){
            case R.id.tv_comment:
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.iv_coll:
                mPresenter.onCollVideo(id, isColl);
                break;
            case R.id.iv_dow:
                mPresenter.onVideoDownload(videoUrl, mB.tvTitle.getText().toString());
                break;
            case R.id.iv_share:

                break;
            case R.id.tv_comment_title:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.iv_del:
                if (User.getInstance().isLogin()){
                    mPresenter.onPlayVideo(id);
                }else {
                    mPresenter.onCommonVideo(id);
                }
//                mB.videoPlayer.startPlayLogic();
                break;
            case R.id.bottom_sheet:
                commentBottomFrg.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.iv_zan:

                mPresenter.onLike(id, 1);
                break;
            case R.id.iv_cai:
                mPresenter.onLike(id, 2);
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

    @Override
    public void setVideoAd(DataBean bean) {
        String imgUrl = bean.getImgUrl();
        if (!StringUtils.isEmpty(imgUrl)){
            mB.fyAdv.setVisibility(View.VISIBLE);
            GlideLoadingUtils.load(act, imgUrl, mB.ivAdv);
        }
    }

    @Override
    public void setVideoListAd(DataBean bean) {
        GlideLoadingUtils.load(act, bean.getImgUrl(), mB.ivImg);
    }

    @Override
    public void setVideoDesc(DataBean bean) {
        mB.tvTitle.setText(bean.getTitle());
        mB.tvTime.setText(bean.getCreatetime() +
                "  播放次数 " +
                bean.getViewCount() +
                " 次");
        String[] tagsName = bean.getTagsName();
        if (tagsName.length != 0){
            mB.layout.setVisibility(View.VISIBLE);
            mB.flLabel.removeAllViews();
            mB.flLabel.setAdapter(new TagAdapter<String>(tagsName){
                @Override
                public View getView(FlowLayout parent, int position, String dataBean) {
                    View view = View.inflate(act, R.layout.i_video_label, null);
                    TextView tvText = view.findViewById(R.id.tv_text);
                    tvText.setText(dataBean);
                    return view;
                }
            });
        }
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.load(act, bean.getImage(), imageView);
        mB.videoPlayer.setThumbImageView(imageView);
        setVideoPlay("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");

        mB.tvZan.setText(bean.getLikeNum() + "");
        mB.tvCai.setText(bean.getBadNum() + "");
        mB.tvComment.setText(bean.getCommentCount() + "点评");
//        setCollState(bean.getIsLike());
        setZan(bean.getIsLike());
    }

    @Override
    public void setZan(int isLike) {
        if (isLike == 1){
            mB.ivCai.setBackgroundResource(R.mipmap.bumanyi);
            mB.ivZan.setBackgroundResource(R.mipmap.y6);
        }else {
            mB.ivCai.setBackgroundResource(R.mipmap.y7);
            mB.ivZan.setBackgroundResource(R.mipmap.bofandianzan);
        }
    }

    @Override
    public void setPlayUrl(String videoUrl) {
        videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        this.videoUrl = videoUrl;
        mB.fyAdv.setVisibility(View.GONE);
        mB.videoPlayer.setVisibility(View.VISIBLE);
        setVideoPlay(videoUrl);
    }

    private void setVideoPlay(String url) {
        urls.add(new GSYSampleADVideoPlayer.GSYADVideoModel(url,
                "", GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL));
        mB.videoPlayer.setAdUp(urls, true, 0);
//        mB.videoPlayer.startPlayLogic();
    }

    @Override
    public void setCollState(boolean isTrue) {
        if (isTrue) {
            mB.ivColl.setBackgroundResource(R.mipmap.y8);
        } else {
            mB.ivColl.setBackgroundResource(R.mipmap.y3);
        }
        isColl = !isTrue;
    }

    @Override
    public void setFirstComment(DataBean data) {
        listComment.add(0, data);
        commentAdapter.notifyItemChanged(0);
    }

}
