package com.peakmain.asmactualcombat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.peakmain.sdk.SensorsDataTrackViewOnClick;
import com.peakmain.ui.imageLoader.ImageLoader;
import com.peakmain.ui.imageLoader.glide.GlideLoader;

/**
 * author ：Peakmain
 * createTime：2022/1/14
 * mail:2726449200@qq.com
 * describe：
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RatingBar ratingBar = findViewById(R.id.comment_net_rating_bar);
         ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 float rating1 = ratingBar.getRating();
             }
         });
    }

}
