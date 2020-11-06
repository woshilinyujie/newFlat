package com.wl.wlflatproject.MUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @Author: HSL
 * @Time: 2019/4/15 9:42
 * @E-mail: xxx@163.com
 * @Description: 域名匹配规则
 */
public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        //return hostname.equals("server.jeasonlzy.com");
        return true;
    }
}
