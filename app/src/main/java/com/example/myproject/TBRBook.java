package com.example.myproject;

public class TBRBook {
    private String nameAndAuthor;

    private boolean done;

    public TBRBook(){
    }

    public String getNameAndAuthor() {
        return nameAndAuthor;
    }

    public void setNameAndAuthor(String nameAndAuthor) {
        this.nameAndAuthor = nameAndAuthor;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
