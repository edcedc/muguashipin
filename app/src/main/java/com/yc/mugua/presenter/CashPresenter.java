package com.yc.mugua.presenter;

import com.blankj.utilcode.util.FileUtils;
import com.yc.mugua.bean.DataBean;
import com.yc.mugua.impl.CashContract;
import com.yc.mugua.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/18
 * Time: 20:18
 */
public class CashPresenter extends CashContract.Presenter {

    @Override
    public void onRequest() {
        final List<File> files = FileUtils.listFilesInDir(Constants.videoUrl);
        if (files != null && files.size() != 0) {
            final List<DataBean> list = new ArrayList<>();
            new Thread(() -> {
                for (File file : files) {
                    DataBean bean = new DataBean();
                    String fileName = FileUtils.getFileName(file);
                    bean.setTitle(fileName.substring(0, fileName.length() - 4));
                    bean.setContent(file.toString());
                    bean.setContext(FileUtils.getFileSize(file));
                    list.add(bean);
                }
                act.runOnUiThread(() -> {
                    mView.setData(list);
                    mView.hideLoading();
                });
            }).start();
        }else {
            mView.showLoadEmpty();
        }
    }

}
