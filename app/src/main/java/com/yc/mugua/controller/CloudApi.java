package com.yc.mugua.controller;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.yc.mugua.bean.BaseListBean;
import com.yc.mugua.bean.BaseResponseBean;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.callback.JsonCallback;
import com.yc.mugua.callback.JsonConvert;
import com.yc.mugua.callback.NewsCallback;
import com.yc.mugua.utils.Constants;
import com.yc.mugua.utils.cache.ShareSessionIdCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {


    private static final String url =
            "52.82.32.62:9000";


    public static final String SERVLET_URL = "http://" +
            url + "/api/";

    public static final String SERVLET_IMG_URL = SERVLET_URL + "attach/showPic?attachId=";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *  通知列表
     */
    public final static String noticeGetSystemNoticeList = "notice/getSystemNoticeList";



    /**
     * 获取微信登陆返回值
     */
    public static Observable<JSONObject> wxLogin(String openid, String access_token) {
        return OkGo.<JSONObject>get("https://api.weixin.qq.com/sns/userinfo")
                .params("access_token", access_token)
                .params("openid", openid)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 重置密码
     */
    public static Observable<Response<BaseResponseBean>> userResetPwd(String iphone, String password, String vercoed) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/resetPwd")
                .params("mobile", iphone)
                .params("code", vercoed)
                .params("password", password)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 给评论点赞
     */
    public static Observable<Response<BaseResponseBean>> commonLikeComment(String commentId) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "common/likeComment")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("commentId", commentId)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 登陆接口
     */
    public static Observable<JSONObject> userLogin(String mobile, String password) {
        return OkGo.<JSONObject>post(SERVLET_URL + "auth/login")
                .params("mobileOrName", mobile)
                .params("password", password)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 游客-个人中心
     */
    public static Observable<JSONObject> commonUserInfo() {
        return OkGo.<JSONObject>get(SERVLET_URL + "common/userInfo")
                .params("udid", DeviceUtils.getUniqueDeviceId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 普通用户-个人中心
     */
    public static Observable<JSONObject> commonList() {
        return OkGo.<JSONObject>get(SERVLET_URL + "common/list")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取验证码
     * type 1 表示注册 type 2 表示密码找回
     */
    public static Observable<Response<BaseResponseBean>> userSendVcode(String iphone, int vcodeType) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "auth/code")
                .params("mobile", iphone)
                .params("type", vcodeType)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 注册
     */
    public static Observable<Response<BaseResponseBean>> userResgist(String phone, String password, String code, String inviteCode) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "auth/register")
                .params("password", password)
                .params("mobile", phone)
                .params("code", code)
                .params("inviteCode", inviteCode)
                .params("udid", DeviceUtils.getUniqueDeviceId())
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 轮播
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> homeGetBanner() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "home/getBanner")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 普通用户-获取二维码
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> qrCodeInfo() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "qrCode/info")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 火爆交流群
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> mineHotGroup() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "mine/hotGroup")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 榜单视频
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> rankingList(int pagetNumer, int type) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "ranking/list")
                .params("type", type)
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频详情
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonInfo(String id) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/info")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 历史列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonHistory(int pagetNumer) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/history")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 游客历史列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonTourist(int pagetNumer) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/tourist")
                .params("udid", DeviceUtils.getUniqueDeviceId())
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 收藏列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonCollectList(int pagetNumer) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/collectList")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 提现页面信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> shareInfo() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "share/info")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 我的推广页面
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> shareMyInfo() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "share/myInfo")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 任务列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonTask() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/task")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 提交-提现申请
     */
    public static Observable<Response<BaseResponseBean>> shareSubmit(String cardNum, String name, String price) {
        JSONObject object = new JSONObject();
        try {
            object.put("cardNum", cardNum);
            object.put("name", name);
            object.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "share/submit")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .upJson(object.toString())
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取会员套餐
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> mineVipWares() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "mine/vipWares")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 公告 -用户协议
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonQueryAPPAgreement(int flag) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/queryAPPAgreement")
                .params("flag", flag)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 渠道列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> vdPayChannelList() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "vdPayChannel/list")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 加载反馈原因列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> mineReasons() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "mine/reasons")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 已登陆用户-播放视频
     */
    public static Observable<Response<BaseResponseBean>> commonPlayVideo(String id) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "common/playVideo")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }
    /**
     * 提交意见反馈
     */
    public static Observable<Response<BaseResponseBean>> mineOpinion(String context, String id) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "mine/opinion")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("reasonId", id)
                .params("context", context)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 发布评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonSaveComment(String id, String text) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "common/saveComment")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .params("context", text)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 游客-播放视频
     */
    public static Observable<Response<BaseResponseBean>> commonVideo(String id) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "common/video")
                .params("videoId", id)
                .params("udid", DeviceUtils.getUniqueDeviceId())
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 点赞视频 踩视频
     */
    public static Observable<Response<BaseResponseBean>> commonLike(String id, int isLike) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "common/like")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .params("isLike", isLike)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 收藏视频-可取消
     */
    public static Observable<Response<BaseResponseBean>> commonCollect(String id) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "common/collect")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .converter(new JsonCallback<BaseResponseBean>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取视频的评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonComment(int pagetNumer, String id) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/comment")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", id)
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频详情-猜你喜欢
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonGuesslike(int pagetNumer) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "common/guesslike")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取分类下所有视频
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> homeVediosByClassify(int pagetNumer, String classify) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "home/vediosByClassify")
                .params("classify", classify)
                .params("pageIndex", pagetNumer)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取搜索关键词
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> homeKeywords() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "home/keywords")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 猜你喜欢
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> homeGuessLike() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "home/guessLike")
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 搜索-获取视频列表-分类
     *
     * @param pagerNumber
     * @param keyword
     * @param categoryId
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> findList(int pagerNumber, String keyword, String categoryId, String field, String tagId) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "find/list")
                .params("pageIndex", pagerNumber)
                .params("pageSize", Constants.pageSize)
                .params("keyword", keyword)
                .params("categoryId", categoryId)
                .params("field", field)
                .params("tagsId", tagId)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取主页列表视频-广告
     *
     * @param pagerNumber
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> homeVideoList(int pagerNumber) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "home/videoList")
                .params("pageIndex", pagerNumber)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通知列表
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> noticegetSystemNoticeList(int pagerNumber) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "notice/getSystemNoticeList")
                .headers("auth-authorization", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pageIndex", pagerNumber)
                .params("pageSize", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 通用list数据
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> list(int pageNumber, String url) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + url)
                .params("pageIndex", pageNumber)
                .params("pageSize", Constants.pageSize)
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list 2
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> list2(String url) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + url)
                .params("sessionId", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }
}