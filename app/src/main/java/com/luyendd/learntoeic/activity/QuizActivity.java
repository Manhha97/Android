package com.luyendd.learntoeic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.obj.ResultTest;
import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.obj.VocaQuiz;
import com.luyendd.learntoeic.utils.Const;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    int topic_id = 1;
    private final String TAG = "QuizActivity";
    List<VocaQuiz> vocaQuizs = new ArrayList<>();
    Random random;
    int positionQuiz = 2;
    TextView tvQuestions, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    int numQuestion = 0, numCorrect = 0;
    Button btnNext;
    boolean isAnswer = false;
    ConnectDataBase connectDataBase;
    List<Voca> vocaList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setTitle("Quiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        connectDataBase = new ConnectDataBase(this);
        topic_id = getIntent().getIntExtra(Const.TOPIC_ID, 1);
        Log.d(TAG, topic_id + "");
        try {
            if (topic_id == 0) {
                vocaList = connectDataBase.getListFavorite();
            } else {
                vocaList = connectDataBase.getVocaFromTopic(topic_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initDataQuiz();
    }

    private void initDataQuiz(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                random = new Random();
                tvQuestions = findViewById(R.id.tv_question);
                btnNext = findViewById(R.id.btnNext);
                tvAnswer1 = findViewById(R.id.tv_answer_1);
                tvAnswer2 = findViewById(R.id.tv_answer_2);
                tvAnswer3 = findViewById(R.id.tv_answer_3);
                tvAnswer4 = findViewById(R.id.tv_answer_4);

                btnNext.setOnClickListener(QuizActivity.this);
                tvAnswer1.setOnClickListener(QuizActivity.this);
                tvAnswer2.setOnClickListener(QuizActivity.this);
                tvAnswer3.setOnClickListener(QuizActivity.this);
                tvAnswer4.setOnClickListener(QuizActivity.this);

                vocaQuizs.clear();
                for (Voca voca : vocaList) {
                    vocaQuizs.add(new VocaQuiz(voca.getId(), voca.getVocabulary(), voca.getTranslate(), false));
                    Log.d(TAG, voca.toString());
                }

                setQuestion();

            }
        }).start();

    }

    private void setQuestion(){

        isAnswer = false;

        tvAnswer1.setBackgroundColor(Color.parseColor("#20bdbdbd"));
        tvAnswer2.setBackgroundColor(Color.parseColor("#20bdbdbd"));
        tvAnswer3.setBackgroundColor(Color.parseColor("#20bdbdbd"));
        tvAnswer4.setBackgroundColor(Color.parseColor("#20bdbdbd"));

        if (numQuestion == vocaQuizs.size()) {
            // todo show result
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "End Quiz" + numCorrect + "/" + numQuestion);
                    Toast.makeText(QuizActivity.this,
                            "End Quiz" + numCorrect + "/" + numQuestion, Toast.LENGTH_SHORT).show();

                    boolean isInsert = connectDataBase.insertResultData(new ResultTest(topic_id, numCorrect, numQuestion));
                    Log.d(TAG, "[Insert] : " + isInsert);
                    Intent i = new Intent(QuizActivity.this, StatisticResultTestActivity.class);
                    i.putExtra(Const.TOPIC_ID, topic_id);
                    startActivity(i);
                    finish();
                }
            });

            return;
        }

        setPositionQuiz();
        VocaQuiz voca = vocaQuizs.get(positionQuiz);
        tvQuestions.setText(voca.getVocabulary());
        setAnswerQuiz();


    }

    private void setPositionQuiz() {
        positionQuiz = random.nextInt(vocaQuizs.size());
        if (vocaQuizs.get(positionQuiz).isExitsQuiz()){
            setPositionQuiz();
        }else {
            numQuestion ++;
            vocaQuizs.get(positionQuiz).setExitsQuiz(true);
        }
    }

    private void setAnswerQuiz() {
        List<VocaQuiz> vocaAnswerList = new ArrayList<>();
        int firstPos = 0, secondPos = 0, thirdPos = 0, fourthPos = 0;
        firstPos = positionQuiz;
        while (secondPos == firstPos) {
            secondPos = random.nextInt(vocaQuizs.size());
        }

        while(thirdPos == secondPos || thirdPos == firstPos) {
            thirdPos = random.nextInt(vocaQuizs.size());
        }

        while (fourthPos == secondPos || fourthPos == thirdPos || fourthPos == firstPos) {
            fourthPos = random.nextInt(vocaQuizs.size());
        }

        vocaAnswerList.add(vocaQuizs.get(firstPos));
        vocaAnswerList.add(vocaQuizs.get(secondPos));
        vocaAnswerList.add(vocaQuizs.get(thirdPos));
        vocaAnswerList.add(vocaQuizs.get(fourthPos));
        Log.d(TAG, firstPos + "/" + secondPos + "/" + thirdPos + "/" + fourthPos);

        Collections.shuffle(vocaAnswerList);

        tvAnswer1.setText(vocaAnswerList.get(0).getTranslate());
        tvAnswer2.setText(vocaAnswerList.get(1).getTranslate());
        tvAnswer3.setText(vocaAnswerList.get(2).getTranslate());
        tvAnswer4.setText(vocaAnswerList.get(3).getTranslate());

        vocaAnswerList.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                setQuestion();
                break;

            case R.id.tv_answer_1 :
                textViewClick(tvAnswer1);
                break;

            case R.id.tv_answer_2 :
                textViewClick(tvAnswer2);
                break;

            case R.id.tv_answer_3 :
                textViewClick(tvAnswer3);
                break;

            case R.id.tv_answer_4 :
                textViewClick(tvAnswer4);
                break;
        }
    }

    private void textViewClick(final TextView tv) {
        if (vocaQuizs.get(positionQuiz).getTranslate().toString() == tv.getText().toString()) {
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                    tv.setBackgroundColor(Color.GREEN);
                }

                public void onFinish() {
                    setQuestion();
                }

            }.start();
            if (!isAnswer) numCorrect++;
            Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            tv.setBackgroundColor(Color.RED);
        }
        isAnswer = true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
