package com.luyendd.learntoeic.obj;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultTest {
    private int id, idTopic, correct, total;

    private String date;

    public ResultTest(int idTopic, int correct, int total) {
        this.idTopic = idTopic;
        this.correct = correct;
        this.total = total;
    }

    public ResultTest(int id, int idTopic, int correct, int total, String date) {
        this.id = id;
        this.idTopic = idTopic;
        this.correct = correct;
        this.total = total;
        this.date = date;
    }

    public ResultTest(int idTopic, int correct, int total, String date) {
        this.idTopic = idTopic;
        this.correct = correct;
        this.total = total;
        this.date = date;
    }

    public ResultTest() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
