package com.luyendd.learntoeic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.activity.MainActivity;
import com.luyendd.learntoeic.obj.Voca;


@SuppressLint("ValidFragment")
public class VocaDetailsFragment extends Fragment  {

    ImageView imageView;
    TextView textViewVoca, textViewMean, textViewVocabulazion;
    ImageButton imageButtonSpeak, imageButtonFavorite;
    int position = 0;
    Voca voca ;
//    ConnectDataBase connectDataBase;
    public VocaDetailsFragment(Voca voca){
        this.voca = voca;
        Bundle args = new Bundle();
        args.putInt("position", position);
        setArguments(args);
        this.position = position;
//        connectDataBase = new ConnectDataBase(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voca_details, container, false);
        imageView = view.findViewById(R.id.imageView);
        textViewVoca = view.findViewById(R.id.textViewVoca);
        textViewMean = view.findViewById(R.id.textViewMean);
        textViewVocabulazion = view.findViewById(R.id.textViewVocabulazion);
        imageButtonSpeak = view.findViewById(R.id.imageButtonSpeak);
        imageButtonFavorite = view.findViewById(R.id.imagebuttonFavorite);
        textViewMean.setText(voca.getTranslate());
        textViewVoca.setText(voca.getVocabulary());
        textViewVocabulazion.setText(voca.getVocalization());

        if (voca.getFavorite() == 0) {
            imageButtonFavorite.setBackgroundDrawable(getContext().getDrawable(R.drawable.ic_not_favorite));
        } else {
            imageButtonFavorite.setBackgroundDrawable(getContext().getDrawable(R.drawable.ic_favorite));
        }

        imageButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voca.getFavorite() == 1) {
                    imageButtonFavorite.setBackgroundDrawable(getContext().getDrawable(R.drawable.ic_not_favorite));
                    voca.setFavorite(0);
                    MainActivity.cdb.UpdateVoca(voca);
                } else {
                    imageButtonFavorite.setBackgroundDrawable(getContext().getDrawable(R.drawable.ic_favorite));
                    voca.setFavorite(1);
                    MainActivity.cdb.UpdateVoca(voca);
                }
            }
        });



        return view;
    }

}
