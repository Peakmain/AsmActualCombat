package com.peakmain.asmactualcombat.activity

import android.content.Intent
import cn.jiguang.w.c
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.asmactualcombat.viewmodel.ClickDealViewModel
import com.peakmain.asmactualcombat.R
import com.peakmain.asmactualcombat.databinding.ActivityMainBinding
import com.peakmain.ui.utils.ToastUtils

/**
 * author ：Peakmain
 * createTime：2022/1/14
 * mail:2726449200@qq.com
 * describe：
 */
class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, ClickDealViewModel>() {

    override fun initView() {
        mBinding.buttonClick.setOnClickListener {
            startActivity(Intent(this, ClickDealActivity::class.java))
        }
        mBinding.buttonClick1.setOnClickListener {
            //startActivity(Intent(this,TestActivity::class.java))
            ToastUtils.showLong(c.a(this))
        }
    }

}