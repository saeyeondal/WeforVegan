package com.example.weforvegan;

public class Request {
    String title;
    String state;
    String reply;

    public Request(String title, String state, String reply){
        this.title = title;
        this.state = state;
        this.reply = reply;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReply() { return reply; }

    public void setReply(String reply) { this.reply = reply; }
}
