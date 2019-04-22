package com.luyendd.learntoeic.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.adapter.AdapterVoca;
import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.utils.Const;

import java.sql.SQLException;
import java.util.List;

public class VocaActivity extends AppCompatActivity {

    ConnectDataBase cdb;
    ListView listView;
    AdapterVoca adapterVoca;
    List<Voca> vocaList;
    int topic_id = 1;
    String name_topic= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca);
        topic_id = getIntent().getIntExtra(Const.TOPIC_ID, 1);
        name_topic = getIntent().getStringExtra(Const.TOPIC_NAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name_topic);

        listView = findViewById(R.id.listview);
        cdb = new ConnectDataBase(this);
        try {
            vocaList = cdb.getVocaFromTopic(topic_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterVoca = new AdapterVoca(this, vocaList);
        listView.setAdapter(adapterVoca);
        adapterVoca.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
