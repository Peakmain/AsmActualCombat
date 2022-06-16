package com.peakmain.asmactualcombat.activity;

import android.content.Intent;
import android.view.View;

import com.peakmain.asmactualcombat.R;
import com.peakmain.asmactualcombat.databinding.ActivityMainBinding;
import com.peakmain.asmactualcombat.viewmodel.ClickDealViewModel;
import com.peakmain.basiclibrary.base.activity.BaseActivity;

/**
 * author ：Peakmain
 * createTime：2022/6/15
 * mail:2726449200@qq.com
 * describe：
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, ClickDealViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mBinding.buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ClickDealActivity.class));
            }
        });
        mBinding.buttonClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),TestActivity.class));
            }
        });
    }
}
