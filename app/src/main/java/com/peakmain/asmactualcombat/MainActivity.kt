package com.peakmain.asmactualcombat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.peakmain.ui.imageLoader.ImageLoader
import com.peakmain.ui.imageLoader.glide.GlideLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvName = findViewById<ImageView>(R.id.tv_name)
        Glide.with(this).asBitmap().load("https://img2.baidu.com/it/u=98371021,1121096365&fm=253&app=53&size=w500&n=0&g=0n&f=jpeg?sec=1645688508&t=adc15ac34fa4b265ad23453a84ed5ffb")
            .into(tvName)
        tvName.setOnClickListener {
            Toast.makeText(this, "你好", Toast.LENGTH_SHORT).show()
        }
    }
}