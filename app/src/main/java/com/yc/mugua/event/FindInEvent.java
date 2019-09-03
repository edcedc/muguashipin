package com.yc.mugua.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/22
 * Time: 12:02
 */
public class FindInEvent {

    public String field;
    public String categoryId;
    public String tagsId;

    public FindInEvent(String field, String categoryId, String tagsId) {
        this.field = field;
        this.categoryId = categoryId;
        this.tagsId = tagsId;
    }
}
