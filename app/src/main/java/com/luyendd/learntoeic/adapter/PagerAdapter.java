package com.luyendd.learntoeic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.fragment.VocaDetailsFragment;
import com.luyendd.learntoeic.obj.Voca;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTab;
    List<Voca> vocaList;

    public PagerAdapter(FragmentManager fm, List<Voca> vocaList) {
        super(fm);
        this.numTab = vocaList.size();
        this.vocaList = vocaList;
    }

    @Override
    public Fragment getItem(int i) {
        return new VocaDetailsFragment(vocaList.get(i));
    }

    @Override
    public int getCount() {
        return numTab;
    }
}
