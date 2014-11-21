package com.example.henry.moretomato.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class Task implements Serializable {

    public final static int NOCHILD = 0;
    public final static int ISCHILDEXIST = 0;
    private String mContent;
    private String mTitle;

    private long mCreatedTime;
    private long mUpdatedTime;
    private long mEndTime;

    private int _id;
    private int mLevel;
    private int mParentId;
    private boolean mIsChildExsit;

    public Task() {
        mCreatedTime = System.currentTimeMillis();
        mUpdatedTime = System.currentTimeMillis();
    }

    public Task(Cursor cursor) {
    }

    public void SetContent(String content){
        mContent = content;
    }

    public String GetContent(){
        return mContent;
    }

    public void SetTitle(String title){
        mTitle = title;
    }

    public String GetTitle(){
        return mTitle;
    }
}


