package com.luyendd.learntoeic.obj;

public class Topic {
    private int id;
    private String name;
    private String translate;

    public Topic(int id, String name, String translate) {
        this.id = id;
        this.name = name;
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
