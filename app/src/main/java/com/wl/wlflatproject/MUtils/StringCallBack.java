package com.wl.wlflatproject.MUtils;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.MUtils
 * @Author: HSL
 * @Time: 2019/07/30 17:07
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public abstract class StringCallBack extends StringCallback {

    @Override
    public void onCacheSuccess(Response<String> response) {
        onFastSuccess(response, true);
    }

    @Override
    public void onSuccess(Response<String> response) {
        onFastSuccess(response, false);
    }

    /**
     * 快速回调成功
     *
     * @param response
     * @param isCache
     */
    public abstract void onFastSuccess(Response<String> response, boolean isCache);
}
