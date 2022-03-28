package com.peakmain.asmactualcombat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peakmain.asmactualcombat.R;

import java.util.Locale;

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：
 */
public class TextViewListViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;

    public TextViewListViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_listview, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.title.setText(String.format(Locale.CHINA, "位置 %d", position));

        return convertView;
    }

    private static class ViewHolder {
        ViewHolder(View viewRoot) {
            title = viewRoot.findViewById(R.id.title);
        }

        public TextView title;
    }

}
