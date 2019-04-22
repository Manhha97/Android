package com.luyendd.learntoeic.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.adapter.AdapterVoca;
import com.luyendd.learntoeic.adapter.PagerAdapter;
import com.luyendd.learntoeic.utils.Const;

public class VocaDetailsActivity extends AppCompatActivity {

    // kieu du lieu static de goi bien va xu ly tu cac class khac
    public static ViewPager viewPager;
    public static ListView listView;
    // La mot bo chuyen doi cac item duoc su dung voi list view
    AdapterVoca adapterVoca;
    String TAG = "VocaDetailActivity";
    //Luu lai vi tri cua cua item hien tai
    public static int mPostion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_details);

        //set title va hien thi nut back
        String titleName = getIntent().getStringExtra(Const.TOPIC_NAME);
        getSupportActionBar().setTitle(titleName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.list_view_voca);
        adapterVoca = new AdapterVoca(this, MainActivity.vocaList);
        listView.setAdapter(adapterVoca);
        adapterVoca.notifyDataSetChanged();


        viewPager = findViewById(R.id.pager);
        // su dung pageradapter trong view pager
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), MainActivity.vocaList);
        viewPager.setAdapter(adapter);
        // su kien thay doi cac page
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "[position] : " + position);
                mPostion = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set current item cho view pager
        viewPager.setCurrentItem(mPostion);
    }

    //Khoi tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.voca_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Bat su kien click vao cac button menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //an hien list voca
        if(item.getItemId() == R.id.menu_show_voca){

            if (listView.getVisibility() != View.VISIBLE) {
                listView.setVisibility(View.VISIBLE);
                adapterVoca.notifyDataSetChanged();
            } else {
                listView.setVisibility(View.GONE);
            }

        } else  if (item.getItemId() == R.id.menu_quiz) {

            Intent i = new Intent(VocaDetailsActivity.this, QuizActivity.class);
            i.putExtra(Const.TOPIC_ID, getIntent().getIntExtra(Const.TOPIC_ID, 2));
            startActivity(i);

        } else if (item.getItemId() == android.R.id.home){

            finish();

        } else if (item.getItemId() == R.id.menu_statistic) {
            Intent i = new Intent(VocaDetailsActivity.this, StatisticResultTestActivity.class);
            i.putExtra(Const.TOPIC_ID, getIntent().getIntExtra(Const.TOPIC_ID, 1));
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
}