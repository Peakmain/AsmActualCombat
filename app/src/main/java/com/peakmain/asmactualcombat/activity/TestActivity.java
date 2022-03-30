package com.peakmain.asmactualcombat.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.peakmain.asmactualcombat.R;
import com.peakmain.sdk.annotation.LogParametersReturnValue;
import com.peakmain.sdk.annotation.LogMessageTime;

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

    @LogMessageTime
    @LogParametersReturnValue
    public void getMethodTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @LogParametersReturnValue
    public String test(int a) {

        return "peakmain";
    }
}
