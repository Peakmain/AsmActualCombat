package com.peakmain.asmactualcombat.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.peakmain.asmactualcombat.R;
import com.peakmain.sdk.annotation.DataFragmentTitle;
import com.peakmain.ui.utils.ToastUtils;

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：
 */
@DataFragmentTitle(title="测试Fragment")
public class TestClickFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        AppCompatButton button = view.findViewById(R.id.button);
        button.setTag(R.id.sensors_analytics_tag_view_fragment_name,TestClickFragment.class.getCanonicalName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("TestFragment click");
            }
        });
        return view;
    }
}
