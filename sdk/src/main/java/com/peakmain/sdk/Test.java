package com.peakmain.sdk;


import android.util.Log;
import android.view.View;

/**
 * author ：Peakmain
 * createTime：2022/1/14
 * mail:2726449200@qq.com
 * describe：
 */
public class Test {

    public boolean  test(View view) {
        boolean flag=false;
        long curClickTime = System. currentTimeMillis();
        long lastClickTime =  null == view.getTag() ?  0L : (Long) view.getTag();
        if ((curClickTime - lastClickTime) >=  1500) {
            flag=true;
            if(view.isClickable()){
                view.setTag(curClickTime);
            }
        }
        if(!flag){
             return false;
        }
        return true;
    }


}
