package com.yc.mugua.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.roundview.RoundTextView;
import com.yc.mugua.R;
import com.yc.mugua.base.BaseRecyclerviewAdapter;
import com.yc.mugua.bean.DataBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;
import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/19
 * Time: 11:21
 */
public class CashAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public CashAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private boolean isVisibbility = false;

    public void setVisibbility(boolean visibbility) {
        isVisibbility = visibbility;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        viewHolder.bt_submit.setVisibility(View.GONE);
        viewHolder.cb.setVisibility(isVisibbility ? View.VISIBLE : View.GONE);

        viewHolder.iv_img.setImageBitmap(getVideoThumbnail(bean.getContent(), 100, 100, MINI_KIND));
        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_content.setText(bean.getContext());
        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String path = bean.getContent();//该路径可以自定义
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "video/*");
            startActivity(intent);
        });
    }

    /**
     * 给出url，获取视频的第一帧
     *
     * @param url
     * @return
     */
    private Bitmap getVideoThumbnail(String url) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            LogUtils.e(url);
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_collection, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        RoundTextView bt_submit;
        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            bt_submit = itemView.findViewById(R.id.bt_submit);
            cb = itemView.findViewById(R.id.cb);
        }

    }

}
