package com.luyendd.learntoeic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.adapter.AdapterStatistic;
import com.luyendd.learntoeic.obj.ResultTest;
import com.luyendd.learntoeic.utils.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticResultTestActivity extends AppCompatActivity {

    AdapterStatistic as;
    int topicId = 1;
    ListView lv;
    ConnectDataBase connectDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_result_test);
        getSupportActionBar().setTitle("Statistic Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        topicId = getIntent().getIntExtra(Const.TOPIC_ID, 1);
        connectDataBase = new ConnectDataBase(this);
        List<ResultTest> resultTestList = connectDataBase.getListResultByTopic(topicId);
        Collections.reverse(resultTestList);
        as = new AdapterStatistic(this, resultTestList);

        lv = findViewById(R.id.list_view_statistic);
        lv.setAdapter(as);
        as.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
