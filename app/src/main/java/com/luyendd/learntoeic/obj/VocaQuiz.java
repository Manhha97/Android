package com.luyendd.learntoeic.obj;

public class VocaQuiz {

    private int id;

    private String vocabulary, translate;

    private boolean isExitsQuiz;

    public VocaQuiz(int id, String vocabulary, String translate, boolean isExitsQuiz) {
        this.id = id;
        this.vocabulary = vocabulary;
        this.translate = translate;
        this.isExitsQuiz = isExitsQuiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public boolean isExitsQuiz() {
        return isExitsQuiz;
    }

    public void setExitsQuiz(boolean exitsQuiz) {
        isExitsQuiz = exitsQuiz;
    }
}
