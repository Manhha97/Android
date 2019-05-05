package com.luyendd.learntoeic.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.obj.Topic;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AdapterTopic extends BaseAdapter {
    Context context;
    List<Topic> topicList;
    ViewHolderTopic vht;

    public AdapterTopic(Context context, List<Topic> topicList){
        this.context = context;
        this.topicList = topicList;
    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Topic getItem(int position) {
        return topicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_topic, null);
            vht = new ViewHolderTopic();
            vht.iv = convertView.findViewById(R.id.imageViewTopic);
            vht.tv = convertView.findViewById(R.id.textViewTopic);
            convertView.setTag(vht);
        }else{
            vht = (ViewHolderTopic) convertView.getTag();
        }

//        int resID = context.getResources().getIdentifier(drawableName, "drawable",  getPackageName());
//
        RequestOptions options = new RequestOptions();
        options.centerCrop();
//        options.fitCenter();
//        Glide.with(context)
//                .load("file:///android_asset/image/"+
//                        topicList.get(position).getName().toLowerCase()
//                                .replace(" ", "_") + ".jpg")
//                .apply(options).into(vht.iv);
        vht.iv.setBackgroundDrawable(getBitmapFromAssets("image/" + topicList.get(position).getName().toLowerCase()
                .replace(" ", "_") + ".jpg"));
        vht.tv.setText(topicList.get(position).getTranslate());

        return convertView;
    }

    public class ViewHolderTopic{
        ImageView iv;
        TextView tv;
    }

    public BitmapDrawable getBitmapFromAssets(String fileName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
