package com.peakmain.asmactualcombat.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.peakmain.asmactualcombat.R;
import com.peakmain.sdk.annotation.LogFrameInfo;
import com.peakmain.sdk.annotation.LogMessage;

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：测试(不用看)
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @LogMessage
    public String getMethod(boolean b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "getMethod";
    }

    @LogMessage(isLogTime = true)
    public String getMethodTime(long l) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "getMethod";
    }

    @LogMessage(isLogParametersReturnValue = true)
    public String getMethodParametersReturnValue(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "treasure";
    }

    @LogMessage(isLogTime = true, isLogParametersReturnValue = true)
    public String test(int a) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "peakmain";
    }

    @LogFrameInfo
    public String testLogMethodStackMapFrame(int a) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "peakmain";
    }
}
