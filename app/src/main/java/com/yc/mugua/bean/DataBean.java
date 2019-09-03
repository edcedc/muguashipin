package com.yc.mugua.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/8/17.
 */

public class DataBean implements Serializable {

    private String name;
    private int img;
    private boolean isSelect = false;
    private int position;
    private double price;
    private String title;
    private String content;
    private String image;
    private int type;
    private String id;
    private boolean isSelected;
    private String head;
    private String createTime;
    private String createtime;
    private String imgUrl;
    private String duration;
    private int likeNum;
    private String link;
    private String headimg;
    private String udid;
    private int history;
    private int viewCount;
    private int badNum;
    private int isLike;
    private String[] tagsName;
    private int commentCount;
    private boolean isCollect;
    private String userName;
    private String context;
    private int likeCount;
    private String url;
    private int total;
    private int money;
    private double canPullMoney;
    private String cost;
    private String tel;

    public String getTel() {
        return tel;
    }

    public int getTotal() {
        return total;
    }

    public int getMoney() {
        return money;
    }

    public double getCanPullMoney() {
        return canPullMoney;
    }

    public String getCost() {
        return cost;
    }

    public String getUrl() {
        return url;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getContext() {
        return context;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public String getCreatetime() {
        return createtime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getIsLike() {
        return isLike;
    }

    public int getBadNum() {
        return badNum;
    }

    public String[] getTagsName() {
        return tagsName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getHistory() {
        return history;
    }

    public String getUdid() {
        return udid;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public String getDuration() {
        return duration;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private DataBean videoAd;
    private DataBean listAd;
    private DataBean videoInfo;

    public DataBean getVideoInfo() {
        return videoInfo;
    }

    public DataBean getListAd() {
        return listAd;
    }

    public DataBean getVideoAd() {
        return videoAd;
    }

    private List<DataBean> banners, vedios, ad, video, videos, tags, hot, recommend, videoList,
            comment, reasons, noticeList, vipWares, collectList, shareTask, wealTask, user;

    public List<DataBean> getUser() {
        return user;
    }

    public List<DataBean> getWealTask() {
        return wealTask;
    }

    public List<DataBean> getShareTask() {
        return shareTask;
    }

    public List<DataBean> getCollectList() {
        return collectList;
    }

    public List<DataBean> getVipWares() {
        return vipWares;
    }

    public List<DataBean> getNoticeList() {
        return noticeList;
    }

    public List<DataBean> getReasons() {
        return reasons;
    }

    public List<DataBean> getComment() {
        return comment;
    }

    public List<DataBean> getVideoList() {
        return videoList;
    }

    public List<DataBean> getRecommend() {
        return recommend;
    }

    public List<DataBean> getHot() {
        return hot;
    }

    public List<DataBean> getTags() {
        return tags;
    }

    private List<DataBean> category = new ArrayList<>();

    public List<DataBean> getVideos() {
        return videos;
    }

    public List<DataBean> getAd() {
        return ad;
    }

    public List<DataBean> getVideo() {
        return video;
    }

    public List<DataBean> getVedios() {
        return vedios;
    }

    public List<DataBean> getBanners() {
        return banners;
    }

    public List<DataBean> getCategory() {
        return category;
    }

    public void setCategory(List<DataBean> category) {
        this.category = category;
    }

}