package com.gold.arshow;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by Administrator on 2016/4/29.
 */
public class hidesdklogoAdapter extends AuthorizeAdapter {
    public void onCreate() {
        // 隐藏标题栏右部的ShareSDK Logo
        hideShareSDKLogo();
    }
}
