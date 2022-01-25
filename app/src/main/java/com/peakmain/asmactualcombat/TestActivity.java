package com.peakmain.asmactualcombat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
        ImageView tvName = findViewById(R.id.tv_name);
        ImageLoader.getInstance().displayImage(this,"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbj-yuantu.fotomore.com%2Fcreative%2Fvcg%2Fnew%2FVCG211360573823.jpg%3FExpires%3D1643621485%26OSSAccessKeyId%3DLTAI2pb9T0vkLPEC%26Signature%3D6S5Z69RbKn%252BdGWNRKyhF7WEP%252F4o%253D%26x-oss-process%3Dimage%252Fauto-orient%252C0%252Fsaveexif%252C1%252Fresize%252Cm_lfit%252Ch_1200%252Cw_1200%252Climit_1%252Fsharpen%252C100%252Fquality%252CQ_80%252Fwatermark%252Cg_se%252Cx_0%252Cy_0%252Cimage_d2F0ZXIvdmNnLXdhdGVyLTIwMDAucG5nP3gtb3NzLXByb2Nlc3M9aW1hZ2UvcmVzaXplLG1fbGZpdCxoXzE3MSx3XzE3MSxsaW1pdF8x%252F&refer=http%3A%2F%2Fbj-yuantu.fotomore.com&app=2002&size=f10000,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645688961&t=2a2628c40e3e9a298dd0e37ef7444718"
                ,tvName);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "你好", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
