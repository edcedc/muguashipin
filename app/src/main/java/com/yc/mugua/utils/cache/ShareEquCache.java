package com.yc.mugua.utils.cache;

import android.content.Context;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/22
 * Time: 18:36
 */
public class ShareEquCache {

    private static Context act;

    private static class Holder {
        private static final ShareEquCache INSTANCE = new ShareEquCache();
    }

    private ShareEquCache() {
    }

    public static final ShareEquCache getInstance(Context act) {
        ShareEquCache.act = act;
        return ShareEquCache.Holder.INSTANCE;
    }

    private String equipment = "EQUIPMENT";

    public void save(String pwd){
        ACache.get(act).put(equipment, pwd, 2592000);
    }

    public String getEquPwd(){
        String asObject = ACache.get(act).getAsString(equipment);
        return asObject;
    }

    public void remove(){
        ACache.get(act).remove(equipment);
    }

}
