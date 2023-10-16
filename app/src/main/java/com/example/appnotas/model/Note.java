package com.example.appnotas.model;

public class Note {
    public String uid,id,time, content;

    public Note(String uid, String id,String time,  String content) {
        this.uid = uid;
        this.id = id;
        this.time = time;
        this.content = content;
    }

    public Note() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
