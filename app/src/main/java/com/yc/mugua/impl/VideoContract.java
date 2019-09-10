package com.yc.mugua.impl;

import com.yc.mugua.base.BasePresenter;
import com.yc.mugua.base.IBaseListView;
import com.yc.mugua.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/21
 * Time: 16:37
 */
public interface VideoContract {

    interface View extends IBaseListView {

        void setCommentData(List<DataBean> list);

        void setVideoAd(DataBean videoAd);

        void setVideoListAd(DataBean listAd);

        void setVideoDesc(DataBean mvideo);

        void setPlayUrl(String data);

        void setZan(int isLike);

        void setCollState(boolean collect);

        void setFirstComment(DataBean data);

        void setCommentZan(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, String id);
        
        public abstract void onCommentRequest(int pagerNumber, String id);

        public abstract void onVideoInfo(String id);

        public abstract void onPlayVideo(String id);

        public abstract void onCommonVideo(String id);

        public abstract void onLike(String id, int i);

        public abstract void onCollVideo(String id, boolean isColl);

        public abstract void onSaveComment(String id, String text);

        public abstract void onVideoDownload(String videoUrl, String title);

        public abstract void onCommetnZan(int position, String id);
    }

}
