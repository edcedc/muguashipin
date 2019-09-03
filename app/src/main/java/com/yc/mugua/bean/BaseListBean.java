package com.yc.mugua.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yc on 2017/8/17.
 */

public class BaseListBean<T> implements Serializable {

    private int totalRow;
    private int pageIndex;
    private int totalPage;
    private int pageSize;
    private boolean firstPage;
    private boolean lastPage;
    private List<T> list;

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageNumber() {
        return pageIndex;
    }

    public void setPageNumber(int pageNumber) {
        this.pageIndex = pageNumber;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
