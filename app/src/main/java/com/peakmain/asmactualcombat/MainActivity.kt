package com.peakmain.asmactualcombat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.peakmain.ui.imageLoader.ImageLoader
import com.peakmain.ui.imageLoader.glide.GlideLoader
import com.peakmain.ui.utils.LogUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ratingBar:RatingBar = findViewById(R.id.comment_net_rating_bar)
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                run {
                    val rating = ratingBar.getRating()
                    LogUtils.e("星星的number：$rating")
                }
            }
    }
}