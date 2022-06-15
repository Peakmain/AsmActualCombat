package com.peakmain.sdk.utils.network;

import com.mob.tools.network.HttpResponseCallback;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.RawNetworkCallback;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * author ：Peakmain
 * createTime：2022/6/14
 * mail:2726449200@qq.com
 * describe：
 */
public class Mob {
    public static String httpGet(NetworkHelper networkHelper, String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkHelper.NetworkTimeOut timeout) throws Throwable {
        if (headers != null) {
            headers.clear();
        }
        return networkHelper.httpGet(url, values, headers, timeout);
    }

    public void rawGet(NetworkHelper networkHelper, String url, ArrayList<KVPair<String>> headers, RawNetworkCallback callback, NetworkHelper.NetworkTimeOut timeout) throws Throwable {
        if (headers != null) {
            headers.clear();
        }
        networkHelper.rawGet(url, headers, callback, timeout);
    }
    public void jsonPost(NetworkHelper networkHelper,String url, HashMap<String, Object> values, ArrayList<KVPair<String>> headers, NetworkHelper.NetworkTimeOut timeout, HttpResponseCallback callback) throws Throwable {
         if(headers!=null){
             headers.clear();
         }
         networkHelper.jsonPost(url,values,headers,timeout,callback);
    }

}
