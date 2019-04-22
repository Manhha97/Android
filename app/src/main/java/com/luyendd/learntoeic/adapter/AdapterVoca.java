package com.luyendd.learntoeic.adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.activity.MainActivity;
import com.luyendd.learntoeic.activity.VocaDetailsActivity;
import com.luyendd.learntoeic.obj.Voca;

import java.util.List;

public class AdapterVoca extends BaseAdapter {
    Context context;
    List<Voca> vocaList;
    ViewHolderVoca vhc;

    public AdapterVoca(Context context, List<Voca> vocaList) {
        this.context = context;
        this.vocaList = vocaList;
    }

    @Override
    public int getCount() {
        return vocaList.size();
    }

    @Override
    public Voca getItem(int position) {
        return vocaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater vi =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_voca, null);
            vhc = new ViewHolderVoca();
//            vhc.ibfavorite = convertView.findViewById(R.id.imagebuttonFavorite);
//            vhc.ibspeak = convertView.findViewById(R.id.imagebuttonSpeak);
            vhc.tvmean = convertView.findViewById(R.id.textviewMean);
            vhc.tvvoca = convertView.findViewById(R.id.textviewVoca);
            vhc.linearAdapter = convertView.findViewById(R.id.linear_adapter);
            convertView.setTag(vhc);
        }else{
            vhc = (ViewHolderVoca) convertView.getTag();
        }

        final Voca voca = vocaList.get(position);
        vhc.tvvoca.setText(voca.getVocabulary());
        vhc.tvmean.setText(voca.getTranslate());
        if (VocaDetailsActivity.mPostion == position)
            vhc.linearAdapter.setBackgroundColor(Color.YELLOW);
        else
            vhc.linearAdapter.setBackgroundColor(Color.WHITE);
        vhc.linearAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VocaDetailsActivity.listView.setVisibility(View.GONE);
                VocaDetailsActivity.mPostion = position;
                VocaDetailsActivity.viewPager.setCurrentItem(position);
            }
        });

        return convertView;
    }

    class ViewHolderVoca{
//        ImageButton ibfavorite, ibspeak;
        TextView tvvoca, tvmean;
        LinearLayout linearAdapter;
    }


}

