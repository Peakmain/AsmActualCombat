package com.peakmain.asmactualcombat.activity

import android.content.Intent
import android.util.Log
import cn.jiguang.w.c
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.asmactualcombat.viewmodel.ClickDealViewModel
import com.peakmain.asmactualcombat.R
import com.peakmain.asmactualcombat.databinding.ActivityMainBinding
import com.peakmain.asmactualcombat.utils.Utils
import com.peakmain.ui.utils.LogUtils
import com.peakmain.ui.utils.ToastUtils
import com.qiniu.android.http.UserAgent

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
        Log.e("TAG","获取七牛云的ua:"+ UserAgent.instance().getUa("peakmain"))
        mBinding.buttonClick1.setOnClickListener {
            //startActivity(Intent(this,TestActivity::class.java))
            //ToastUtils.showLong(Utils.getAndroidId(this))
        }
    }

}