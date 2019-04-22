package com.luyendd.learntoeic.obj;

import java.io.Serializable;

public class Voca implements Serializable {

    private int id, topic, id_temp;

    private String vocabulary, vocalization, explaination, translate, exmaple, exmample_translate;

    private int favorite;

    public Voca(int id, int topic, int id_temp, String vocabulary, String vocalization,
                String explaination, String translate, String exmaple, String exmample_translate, int favorite) {
        this.id = id;
        this.topic = topic;
        this.id_temp = id_temp;
        this.vocabulary = vocabulary;
        this.vocalization = vocalization;
        this.explaination = explaination;
        this.translate = translate;
        this.exmaple = exmaple;
        this.exmample_translate = exmample_translate;
        this.favorite = favorite;
    }

    public Voca(int id, String vocalization){
        this.id = id;
        this.vocalization = vocalization;
    }
    public Voca() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public int getId_temp() {
        return id_temp;
    }

    public void setId_temp(int id_temp) {
        this.id_temp = id_temp;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getVocalization() {
        return vocalization;
    }

    public void setVocalization(String vocalization) {
        this.vocalization = vocalization;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getExmaple() {
        return exmaple;
    }

    public void setExmaple(String exmaple) {
        this.exmaple = exmaple;
    }

    public String getExmample_translate() {
        return exmample_translate;
    }

    public void setExmample_translate(String exmample_translate) {
        this.exmample_translate = exmample_translate;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
