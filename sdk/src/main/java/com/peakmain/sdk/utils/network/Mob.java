package com.peakmain.sdk.utils.network;

import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;

import java.util.ArrayList;

/**
 * author ：Peakmain
 * createTime：2022/6/14
 * mail:2726449200@qq.com
 * describe：
 */
public class Mob {
    public static String httpGet(NetworkHelper networkHelper,String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkHelper.NetworkTimeOut timeout) throws Throwable {
        headers.clear();
        return networkHelper.httpGet(url,values,headers,timeout);
    }
}
