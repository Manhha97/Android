package com.luyendd.learntoeic.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.adapter.AdapterTopic;
import com.luyendd.learntoeic.obj.Topic;
import com.luyendd.learntoeic.utils.AlarmUtil;
import com.luyendd.learntoeic.utils.Const;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
    GridView gridView;
    public static ConnectDataBase cdb;
    List<Topic> topicList;
    AdapterTopic adapterTopic;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_remider_favourite:
//                        Toast.makeText(MainActivity.this, "menu_remider_voca",Toast.LENGTH_SHORT).show();
//                        if (vocaFavorite.size() != 0) {
//                            turnOnAlarm(MainActivity.this, vocaFavorite);
//                            Toast.makeText(MainActivity.this, "Turn on alarm voca",Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Voca favourite is null",Toast.LENGTH_SHORT).show();
//                        }
                        if (AlarmUtil.initAlarm(MainActivity.this, true)) {
                            showDialogRemiderFavourite();
                        }
                        return true;

                    case R.id.menu_favourite:
                        Intent i = new Intent(MainActivity.this, VocaDetailsActivity.class);
                        i.putExtra(Const.TOPIC_FAVOURITE, true);
                        i.putExtra(Const.TOPIC_NAME, "Favourite");
                        startActivity(i);
                        return true;

                    case R.id.menu_reminder_voca :
                        if (AlarmUtil.initAlarm(MainActivity.this, false)) {
                            showDialogReminderVoca();
                        }
                        return true;
                    default:
                        return true;
                }

            }
        });

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        setSliderViews();

        cdb = new ConnectDataBase(this);
        try {
            cdb.createDataBase();
            topicList = cdb.getListTopic();
//            vocaFavorite = cdb.getRandomListQuiz();
//            for (Voca voca : vocaFavorite) {
//                Log.d(TAG, "[QUIZ] : "+voca.getVocabulary());
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapterTopic = new AdapterTopic(this, topicList);
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(adapterTopic);
        adapterTopic.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, topicList.get(position).getTranslate(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, VocaDetailsActivity.class);
                i.putExtra(Const.TOPIC_ID, topicList.get(position).getId());
                i.putExtra(Const.TOPIC_NAME, topicList.get(position).getTranslate());
                startActivity(i);
            }
        });
    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    sliderView.setDescription("Dao Dinh Luyen");
                    sliderView.setDescriptionTextColor(Color.RED);
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void settingTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, final int selectedHour, final int selectedMinute) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT).show();
                        editor.putInt(Const.ALARM_LIST_FAVORITE_HOUR, selectedHour);
                        editor.putInt(Const.ALARM_LIST_FAVORITE_MINUTE, selectedMinute);
                        editor.commit();
                    }
                });
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void showDialogRemiderFavourite() {

        int hour, minute;
        boolean isReminder;
        hour = sharedPreferences.getInt(Const.ALARM_LIST_FAVORITE_HOUR, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        minute = sharedPreferences.getInt(Const.ALARM_LIST_FAVORITE_MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
        isReminder = sharedPreferences.getBoolean(Const.IS_REMINDER_FAVORITE, false);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_remider);

        final Switch swReminder = dialog.findViewById(R.id.switchReminder);
        final TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT > 22) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }


        Button btSave = dialog.findViewById(R.id.btnSaveTime);
        Button btClose = dialog.findViewById(R.id.btnClose);

        swReminder.setChecked(isReminder);
        swReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(Const.IS_REMINDER_FAVORITE, isChecked).commit();
                Log.d(TAG, "[switch status] : " + isChecked);
                if (isChecked) {
                    if (!AlarmUtil.initAlarm(MainActivity.this, true)) {
                        Toast.makeText(MainActivity.this, "Voca favourite is null", Toast.LENGTH_SHORT).show();
                        swReminder.setChecked(false);
                    }
                }
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean(Const.IS_REMINDER_FAVORITE, false)) {
                    AlarmUtil.turnOnAlarm(MainActivity.this, true);
                } else {
                    AlarmUtil.turnOffAlarm();
                }
                dialog.dismiss();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Build.VERSION.SDK_INT > 22 ? timePicker.getHour() : timePicker.getCurrentHour();
                int minute = Build.VERSION.SDK_INT > 22 ? timePicker.getMinute() : timePicker.getCurrentMinute();
                editor.putInt(Const.ALARM_LIST_FAVORITE_MINUTE, minute);
                editor.putInt(Const.ALARM_LIST_FAVORITE_HOUR, hour);
                editor.commit();
                Log.d(TAG, "[Button Save] " + hour + " : " + minute);
                if (sharedPreferences.getBoolean(Const.IS_REMINDER_FAVORITE, false)) {
                    AlarmUtil.turnOnAlarm(MainActivity.this, true);
                } else {
                    AlarmUtil.turnOffAlarm();
                 }

                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();


    }

    private void showDialogReminderVoca() {

        boolean isReminder;
        int minute = sharedPreferences.getInt(Const.ALARM_VOCA_MINUTE_REPEAT, 10);
        isReminder = sharedPreferences.getBoolean(Const.IS_REMINDER_VOCA, false);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_repeat_voca);

        final Switch swReminder = dialog.findViewById(R.id.switchReminder);

        Button btSave = dialog.findViewById(R.id.btnSaveTime);
        Button btClose = dialog.findViewById(R.id.btnClose);

        final EditText edMinute = dialog.findViewById(R.id.edit_text_minute_repeat);
        edMinute.setText(minute + "");

        swReminder.setChecked(isReminder);
        swReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (!AlarmUtil.initAlarm(MainActivity.this, false)) {
                        Toast.makeText(MainActivity.this, "Voca favourite is null", Toast.LENGTH_SHORT).show();
                        swReminder.setChecked(false);
                    }
                }
                editor.putBoolean(Const.IS_REMINDER_VOCA, swReminder.isChecked()).commit();
                Log.d(TAG, "[switch status] : " + swReminder.isChecked());

            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean(Const.IS_REMINDER_VOCA, false)) {
                    AlarmUtil.turnOnAlarm(MainActivity.this, false);
                } else {
                    AlarmUtil.turnOffAlarm();
                }
                dialog.dismiss();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMinute.getText().length() > 0) {
                    editor.putInt(Const.ALARM_VOCA_MINUTE_REPEAT, Integer.parseInt(edMinute.getText().toString()));
                    editor.commit();
                    Log.d(TAG, "[Button Save] Minute : " + edMinute.getText());
                    if (sharedPreferences.getBoolean(Const.IS_REMINDER_VOCA, false)) {
                        AlarmUtil.turnOnAlarm(MainActivity.this, false);
                    } else {
                        AlarmUtil.turnOffAlarm();
                    }

                    dialog.dismiss();
                } else {
                    edMinute.setError("Input is empty");
                }

            }
        });

        dialog.create();
        dialog.show();

    }

}
