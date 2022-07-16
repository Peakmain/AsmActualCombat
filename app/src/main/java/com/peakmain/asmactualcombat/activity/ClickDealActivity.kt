package com.peakmain.asmactualcombat.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import com.peakmain.asmactualcombat.R
import com.peakmain.asmactualcombat.databinding.ActivityDealBinding
import com.peakmain.asmactualcombat.viewmodel.ClickDealViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.sdk.annotation.SensorsDataTrackViewOnClick
import com.peakmain.ui.utils.ToastUtils
import java.util.*

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：点击事件处理页面
 */
class ClickDealActivity(override val layoutId: Int = R.layout.activity_deal) :
    BaseActivity<ActivityDealBinding, ClickDealViewModel>() {


    private fun initTabHostButton() {
        val button = findViewById<AppCompatButton>(R.id.tabHostButton)
        button.setOnClickListener {
            val intent = Intent(this@ClickDealActivity, TabHostTestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initExpandableListViewTest() {
        val button = findViewById<AppCompatButton>(R.id.expandableListViewTest)
        button.setOnClickListener {
            val intent = Intent(this@ClickDealActivity, ExpandableListViewTestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initAdapterViewTest() {
        val button = findViewById<AppCompatButton>(R.id.adapterViewTest)
        button.setOnClickListener {
            val intent = Intent(this@ClickDealActivity, AdapterViewTestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initSpinner() {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val dataList: MutableList<String> = ArrayList()
        dataList.add("北京")
        dataList.add("上海")
        dataList.add("广州")
        dataList.add("深圳")
        dataList.add("咸宁")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //为spinner绑定我们定义好的数据适配器
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initSeekBar() {
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun initRatingBar() {
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser -> }
    }

    private fun initCheckBox() {
        val checkBox = findViewById<AppCompatCheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { compoundButton, b -> }
    }

    private fun initShowMultiChoiceDialogButton() {
        val button = findViewById<AppCompatButton>(R.id.showMultiChoiceDialogButton)
        button.setOnClickListener { showMultiChoiceDialog(this@ClickDealActivity) }
    }

    private fun initShowDialogButton() {
        val button = findViewById<AppCompatButton>(R.id.showDialogButton)
        button.setOnClickListener { showDialog(this@ClickDealActivity) }
    }

    /**
     * Lambda 语法
     */
    private fun initLambdaButton() {
        val button = findViewById<AppCompatButton>(R.id.lambdaButton)
        button.setOnClickListener { view: View? -> showToast("Lambda OnClick") }
    }

    private fun initKotlinLambadaButton() {
       mBinding.kotlinOnClick.setOnClickListener {
           showToast("kotlin Lambda OnClick")
       }
    }
    /**
     * 普通 setOnClickListener
     */
    private fun initButton() {
        val button = findViewById<AppCompatButton>(R.id.button)
        button.setOnClickListener {
            showToast("普通")
        }
        registerForContextMenu(button)
    }

    /**
     * 通过 DataBinding 绑定点击事件
     *
     * @param view View
     */
    fun dataBindingOnClick(view: View) {
        showToast("DataBinding Onclick")
    }

    /**
     * 通过 layout 中的 Android:onClick 属性绑定点击事件 android:onClick="xmlOnClick"
     *
     * @param view View
     */
    @SensorsDataTrackViewOnClick
    fun xmlOnClick(view: View?) {
        showToast("XML OnClick")
    }


    private fun showToast(message: String) {
        ToastUtils.showShort(message)
    }

    private fun showMultiChoiceDialog(context: Context) {
        val dialog: Dialog
        val selected = booleanArrayOf(true, true, true, true)
        val items = arrayOf<CharSequence>("北京", "上海", "深圳", "合肥")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("城市选择？")
        val mutiListener = OnMultiChoiceClickListener { dialogInterface, which, isChecked ->
            selected[which] = isChecked
        }
        builder.setMultiChoiceItems(items, selected, mutiListener)
        dialog = builder.create()
        dialog.show()
    }

    private fun showDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("标题")
        builder.setMessage("内容")
        builder.setNegativeButton("取消") { dialog, which -> }
        builder.setPositiveButton("确定") { dialog, which -> }
        val dialog = builder.create()
        dialog.show()
    }

    override fun initView() {
        mBinding.handlers = this
        initButton()
        initLambdaButton()
        initKotlinLambadaButton()
        initShowDialogButton()
        initShowMultiChoiceDialogButton()
        initCheckBox()
        initRatingBar()
        initSeekBar()
        initSpinner()
        initAdapterViewTest()
        initExpandableListViewTest()
        initTabHostButton()
    }
}